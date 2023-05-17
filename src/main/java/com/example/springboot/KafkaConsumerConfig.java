package com.example.springboot;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;


@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, MBTIEntity> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "b-1.jackymskcluster1.w889sm.c4.kafka.ap-northeast-2.amazonaws.com:9092,b-3.jackymskcluster1.w889sm.c4.kafka.ap-northeast-2.amazonaws.com:9092,b-2.jackymskcluster1.w889sm.c4.kafka.ap-northeast-2.amazonaws.com:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "techgeeknext-group");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(MBTIEntity.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MBTIEntity> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MBTIEntity>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, SurveyEntity> surveyConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "b-1.jackymskcluster1.w889sm.c4.kafka.ap-northeast-2.amazonaws.com:9092,b-3.jackymskcluster1.w889sm.c4.kafka.ap-northeast-2.amazonaws.com:9092,b-2.jackymskcluster1.w889sm.c4.kafka.ap-northeast-2.amazonaws.com:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "techgeeknext-group");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(SurveyEntity.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SurveyEntity> surveyKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SurveyEntity>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(surveyConsumerFactory());
        return factory;
    }

}

