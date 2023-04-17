package com.redhat.demo;

import java.util.Optional;

public class GetObjectRatingResult {

    private static GetObjectRatingResult NOT_FOUND = new GetObjectRatingResult(null);

    private final ObjectRating result;

    private GetObjectRatingResult(ObjectRating result) {
        this.result = result;
    }

    public static GetObjectRatingResult found(ObjectRating data) {
        return new GetObjectRatingResult(data);
    }

    public static GetObjectRatingResult notFound() {
        return NOT_FOUND;
    }

    public Optional<ObjectRating> getResult() {
        return Optional.ofNullable(result);
    }

}
