package com.redhat.demo;

import io.quarkus.kafka.client.serialization.ObjectMapperSerializer;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import org.eclipse.microprofile.reactive.messaging.Channel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ReviewScoreProducer {

    @Inject
    @Channel("scores")
    MutinyEmitter<ScoreEvent> emitter;

    public Uni<Review> publishReviewScore(Review review) {
        return Uni.createFrom().item(() -> new ScoreEvent(review.objectId, review.score))
                .chain(emitter::send)
                .replaceWith(review);
    }

    public record ScoreEvent(long objectId, int score) {
    }

}
