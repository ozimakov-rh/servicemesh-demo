package com.redhat.demo;

import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/rating")
@RegisterRestClient(configKey = "rating-api")
public interface RatingService {

    @GET
    @Path("/{objectId}")
    Uni<ProductRating> getProductRating(@PathParam("objectId") long objectId);

}
