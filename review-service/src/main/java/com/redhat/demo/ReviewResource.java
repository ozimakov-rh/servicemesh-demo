package com.redhat.demo;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Path("/review")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReviewResource {

    @Inject
    ReviewScoreProducer scorePublisher;

    @GET
    @Path("/object/{objectId}")
    public Multi<Review> fetchReviewsForObject(@PathParam("objectId") long objectId) {
        return Review.stream("objectId", Sort.descending("lastUpdated"), objectId);
    }

    @GET
    @Path("/{reviewId}")
    public Uni<Review> fetchReviewById(@PathParam("reviewId") long reviewId) {
        return Review.findById(reviewId)
                .onItem().ifNull().failWith(NotFoundException::new)
                .map(entity -> (Review) entity);
    }

    @POST
    @Path("/object/{objectId}")
    @ReactiveTransactional
    public Uni<Review> postReview(@PathParam("objectId") long objectId, Review review) {
        review.objectId = objectId;
        return this.postReview(review);
    }

    @POST
    @ReactiveTransactional
    public Uni<Review> postReview(Review review) {
        review.lastUpdated = new Date();
        return review.persist()
                .map(entity -> (Review) entity)
                .chain(scorePublisher::publishReviewScore);
    }

    @PUT
    @Path("/{reviewId}")
    @ReactiveTransactional
    public Uni<Review> updateReview(@PathParam("reviewId") long reviewId, Review review) {
        return Review.findById(reviewId)
                .onItem().ifNull().failWith(NotFoundException::new)
                .map(entity -> {
                    var r = (Review) entity;
                    r.text = review.text;
                    r.lastUpdated = new Date();
                    return r;
                })
                .flatMap(r -> r.persist());
    }

    @DELETE
    @Path("/{reviewId}")
    @ReactiveTransactional
    public Uni<Void> deleteReview(@PathParam("reviewId") long reviewId) {
        return Review.deleteById(reviewId)
                .flatMap(result -> {
                    if (!result) throw new NotFoundException();
                    return Uni.createFrom().voidItem();
                });
    }

    @DELETE
    @Path("/object/{objectId}")
    @ReactiveTransactional
    public Uni<Void> deleteAllReviewsForObject(@PathParam("objectId") long objectId) {
        return Review.delete("objectId", objectId)
                .replaceWithVoid();
    }

}