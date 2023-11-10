package com.eoe.osori.global.advice.config;


import com.eoe.osori.domain.chat.domain.mongo.ChatMessage;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

// 어노테이션은 Kafka를 사용하여 메시징을 처리하는 데 필요한 구성을 활성화합니다.
@EnableKafka
@Configuration
public class KafkaConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value(value = "${spring.kafka.template.default-topic}")
    private String topic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        // BOOTSTRAP_SERVERS_CONFIG 프로퍼티를 설정하여 Kafka 클러스터의 주소를 지정합니다.
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic() {
        //  Kafka에 메시지를 전송할 때 사용할 토픽을 정의합니다. topic 이름을 사용하고 1개의 파티션 및 복제 팩터 1을 지정합니다.
        return new NewTopic(topic, 1, (short) 1);
    }

    // Kafka 메시지를 생성하기 위한 Kafka Producer Factory를 설정합니다.
    @Bean
    public ProducerFactory<String, ChatMessage> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        // configProps라는 Map을 만듭니다.
        // 이 Map은 Kafka Producer의 설정을 저장하는 데 사용됩니다.
        configProps.put(
                // Kafka 서버의 주소를 나타내며
                // 이 프로퍼티를 설정하여 Kafka 클러스터에 연결합니다.
                // bootstrapAddress 변수에 정의된 주소를 사용합니다.
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                // 메시지의 키와 값의 직렬화를 위한 클래스를 지정합니다.
                // 문자열 키와 JSON 형식의 메시지 값을 직렬화합니다.
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                // JSON 형식 설정
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        // DefaultKafkaProducerFactory를 생성하고 반환합니다.
        // 즉 이미 만들어져있는 설정클래스에서 설정들만 바꾸어서 내보내준다.
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    // Kafka 메시지를 생성하고 전송하기 위한 KafkaTemplate을 설정합니다.
    @Bean
    public KafkaTemplate<String, ChatMessage> kafkaTemplate() {
        // DefaultKafkaProducerFactory로 만든 설정으로 temlplate 설정을 해준다.
        // 다른곳에서 template를 사용할 때 factory에 설정된 설정들로 설정되어 kafka에 메시지를 전송한다.
        return new KafkaTemplate<>(producerFactory());
    }

    // Kafka 메시지를 수신하기 위한 Kafka Consumer Factory를 설정합니다.
    @Bean
    public ConsumerFactory<String, ChatMessage> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                // Kafka 서버의 주소를 나타내며
                // 이 프로퍼티를 설정하여 Kafka 클러스터에 연결합니다.
                // bootstrapAddress 변수에 정의된 주소를 사용합니다.
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        props.put(
                // Kafka Consumer의 그룹 ID를 설정합니다.
                // 서버가 다른 포트로 켰을 때 같은 채팅방에 들어갈 수 있게 해준다.
                ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);
        props.put(
                // JSON 역직렬화 중에 신뢰할 수 있는 패키지를 지정합니다.
                JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    //  Kafka 메시지를 비동기적으로 수신하는 데 사용되는 Kafka Listener Container Factory를 설정합니다.
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ChatMessage>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ChatMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
