package com.redhat.demo;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ScoreEventDeserializer extends ObjectMapperDeserializer<ScoreEvent> {
    public ScoreEventDeserializer() {
        super(ScoreEvent.class);
    }
}