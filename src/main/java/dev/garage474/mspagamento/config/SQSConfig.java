package dev.garage474.mspagamento.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class SQSConfig {

    @Bean
    SqsClient sqlsClient() {
        return SqsClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }
}
