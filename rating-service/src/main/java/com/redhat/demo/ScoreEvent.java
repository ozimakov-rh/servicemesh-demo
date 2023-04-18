package com.redhat.demo;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ScoreEvent(long objectId, int score) {
}
