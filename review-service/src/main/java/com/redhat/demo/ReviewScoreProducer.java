package com.redhat.demo;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import org.eclipse.microprofile.reactive.messaging.Channel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.smallrye.reactive.messaging.kafka.Record;

@ApplicationScoped
public class ReviewScoreProducer {

    @Inject
    @Channel("scores")
    MutinyEmitter<Record<Long, ScoreEvent>> emitter;

    public Uni<Review> publishReviewScore(Review review) {
        return Uni.createFrom().item(() -> new ScoreEvent(review.objectId, review.score))
                .chain(scoreEvent -> emitter.send(Record.of(scoreEvent.objectId, scoreEvent)))
                .replaceWith(review);
    }

    public record ScoreEvent(long objectId, int score) {
    }

}
