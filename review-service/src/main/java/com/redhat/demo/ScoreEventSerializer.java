package com.redhat.demo;

import io.quarkus.kafka.client.serialization.ObjectMapperSerializer;

public class ScoreEventSerializer extends ObjectMapperSerializer<ReviewScoreProducer.ScoreEvent> {
}
