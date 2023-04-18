package com.redhat.demo;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ScoreEventDeserializer extends ObjectMapperDeserializer<ScoreEvent> {
    public ScoreEventDeserializer() {
        super(ScoreEvent.class);
    }
}