package sa.kafkalistener.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(org.apache.kafka.clients.producer.KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(Object data, String topic) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = mapper.writeValueAsString(data);
        kafkaTemplate.send(topic, jsonInString);
//        LOGGER.info(String.format("Message sent -> %s", jsonInString));
    }
}
