package com.redhat.demo;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RegisterForReflection
public class ObjectScoreAggregation {

    public long objectId;
    public double min = Double.MAX_VALUE;
    public double max = Double.MIN_VALUE;
    public double sum;
    public int count;
    public double avg;

    public ObjectScoreAggregation updateByScore(ScoreEvent event) {
        this.objectId = event.objectId();
        min = Math.min(min, event.score());
        max = Math.max(max, event.score());
        sum += event.score();
        count++;
        avg = BigDecimal.valueOf(sum / count).setScale(3, RoundingMode.HALF_UP).doubleValue();
        return this;
    }

}
