package space.zeinab.demo.kafka.topology;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.zeinab.demo.kafka.config.KafkaConfig;

@Component
public class OutputConsumer {

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        streamsBuilder
                .stream(KafkaConfig.OUTPUT_TOPIC, Consumed.with(Serdes.String(), Serdes.String()))
                .peek((key, value) -> System.out.println("application worked well: "+  value));
    }
}
