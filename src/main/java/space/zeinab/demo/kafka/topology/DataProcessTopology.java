package space.zeinab.demo.kafka.topology;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.zeinab.demo.kafka.config.KafkaConfig;

import java.util.Random;
import java.util.function.Supplier;

@Component
public class DataProcessTopology {
    private final Timer processingTimeTimer;

    public DataProcessTopology(MeterRegistry meterRegistry) {
        this.processingTimeTimer = Timer.builder("kafka.streams.input_processing_time")
                .description("Tracks the time taken to process inputs")
                .register(meterRegistry);
    }

    @Autowired
    public void buildPipeline(StreamsBuilder streamsBuilder) {
        streamsBuilder.stream(
                KafkaConfig.INPUT_TOPIC, Consumed.with(Serdes.String(), Serdes.String())
        ).mapValues( // Monitoring step
                input -> measureProcessingTime(() -> processInput(input))
        ).to(KafkaConfig.OUTPUT_TOPIC, Produced.with(Serdes.String(), Serdes.String()));
    }

    private <T> T measureProcessingTime(Supplier<T> operation) {
        return processingTimeTimer.record(operation);
    }

    private String processInput(String input) {
        try {
            long processingTime = 100 + new Random().nextInt(900); // Simulating dynamic processing times
            Thread.sleep(processingTime); // Simulate input processing delay
            return input.toUpperCase();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}
