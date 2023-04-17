package com.redhat.demo;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ObjectRating {

    public final long objectId;
    public final double min;
    public final double max;
    public final double sum;
    public final int count;
    public final double avg;

    private ObjectRating(long objectId, double min, double max, double sum, int count, double avg) {
        this.objectId = objectId;
        this.min = min;
        this.max = max;
        this.sum = sum;
        this.count = count;
        this.avg = avg;
    }

    public static ObjectRating from(ObjectScoreAggregation aggregation) {
        return new ObjectRating(
                aggregation.objectId,
                aggregation.min,
                aggregation.max,
                aggregation.sum,
                aggregation.count,
                aggregation.avg
        );
    }

}
