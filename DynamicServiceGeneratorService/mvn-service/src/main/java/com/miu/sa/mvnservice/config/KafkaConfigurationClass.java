package com.miu.sa.mvnservice.config;

//
//@Configuration
//public class KafkaConfigurationClass {
//
//    @Bean
//    public ConsumerFactory<String, Object> consumerFactory() {
//        return getStringObjectConsumerFactory();
//    }
//
//    public static ConsumerFactory<String, Object> getStringObjectConsumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//
//        props.put("bootstrap.servers", "alert-cricket-6290-us1-kafka.upstash.io:9092");
//        props.put("sasl.mechanism", "SCRAM-SHA-256");
//        props.put("security.protocol", "SASL_SSL");
//        props.put("auto.offset.reset", "earliest");//earliest //default //none
//        props.put("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required " +
//                "username=\"YWxlcnQtY3JpY2tldC02MjkwJI15DGb5T9i8SGIlnrntnj4LLY2sHmqBwcEfq14\" password=\"************\";");
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "default");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
//    @Bean
//    public ProducerFactory<String, Object> producerFactory() {
//        return getStringObjectProducerFactory();
//    }
//
//    public static ProducerFactory<String, Object> getStringObjectProducerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put("bootstrap.servers", "alert-cricket-6290-us1-kafka.upstash.io:9092");
//        configProps.put("sasl.mechanism", "SCRAM-SHA-256");
//        configProps.put("security.protocol", "SASL_SSL");
//        configProps.put("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required " +
//                "username=\"YWxlcnQtY3JpY2tldC02MjkwJI15DGb5T9i8SGIlnrntnj4LLY2sHmqBwcEfq14\" password=\"************\";");
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put("key.serializer", StringSerializer.class);
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }
//
//    @Bean
//    public KafkaTemplate<String, Object> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//}
