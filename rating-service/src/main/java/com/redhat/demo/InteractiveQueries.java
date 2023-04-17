package com.redhat.demo;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InteractiveQueries {

    @Inject
    KafkaStreams streams;

    public GetObjectRatingResult getObjectRating(long objectId) {
        ObjectScoreAggregation result = getObjectScoreStore().get(objectId);
        if (result != null) {
            return GetObjectRatingResult.found(ObjectRating.from(result));
        } else {
            return GetObjectRatingResult.notFound();
        }
    }

    private ReadOnlyKeyValueStore<Long, ObjectScoreAggregation> getObjectScoreStore() {
        while (true) {
            try {
                return streams.store(StoreQueryParameters.fromNameAndType(ScoreStreamTopologyProducer.OBJECT_SCORE_TOPIC, QueryableStoreTypes.keyValueStore()));
            } catch (InvalidStateStoreException e) {
                // ignore, store not ready yet
            }
        }
    }
}
