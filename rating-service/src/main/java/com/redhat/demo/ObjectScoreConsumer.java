package com.redhat.demo;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObjectScoreConsumer {

    public static final Logger log = LoggerFactory.getLogger(ObjectScoreConsumer.class);

    @Incoming("scores")
    public void consume(ScoreEvent score) {
        log.info("Event received: {}", score);
    }

    public record ScoreEvent(long objectId, int score) {
    }

}
