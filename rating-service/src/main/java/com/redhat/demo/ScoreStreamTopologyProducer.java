package com.redhat.demo;

import io.quarkus.kafka.client.serialization.ObjectMapperSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class ScoreStreamTopologyProducer {

    private final static String SCORES_TOPIC = "scores";
    protected final static String AGGREGATION_TOPIC = "scores-aggregated";
    protected final static String OBJECT_SCORE_TOPIC = "object-scores";

    @Produces
    public Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();

        ObjectMapperSerde<ScoreEvent> scoreEventSerde = new ObjectMapperSerde<>(ScoreEvent.class);
        ObjectMapperSerde<ObjectScoreAggregation> aggregationSerde = new ObjectMapperSerde<>(ObjectScoreAggregation.class);

        KeyValueBytesStoreSupplier storeSupplier = Stores.persistentKeyValueStore(OBJECT_SCORE_TOPIC);

        builder.stream(
                        SCORES_TOPIC,
                        Consumed.with(Serdes.Long(), scoreEventSerde)
                )
                .groupByKey()
                .aggregate(
                        ObjectScoreAggregation::new,
                        (objectId, scoreEvent, aggregator) -> aggregator.updateByScore(scoreEvent),
                        Materialized.<Long, ObjectScoreAggregation>as(storeSupplier)
                                .withKeySerde(Serdes.Long())
                                .withValueSerde(aggregationSerde)
                )
                .toStream()
                .to(
                        AGGREGATION_TOPIC,
                        Produced.with(Serdes.Long(), aggregationSerde)
                );
        return builder.build();

    }

}
