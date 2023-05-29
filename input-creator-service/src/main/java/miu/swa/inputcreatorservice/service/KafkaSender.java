package miu.swa.inputcreatorservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import miu.swa.inputcreatorservice.model.InputCreatorData;
import miu.swa.inputcreatorservice.model.ServiceRunningInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaSender {
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    InputCreatorService service;
    @Autowired
    ServiceRunningService serviceRunningService;

    public void send(String topic, InputCreatorData csData) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(csData);
        kafkaTemplate.send(topic, jsonInString);
        System.out.println("Sending Topic:  " + topic + ", Message: " + jsonInString);
        service.addMessage(csData);
    }

    public void sendServiceMessage(String topic, ServiceRunningInfo message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(message);
        kafkaTemplate.send(topic, jsonInString);
        System.out.println("Sending Topic:  " + topic + ", Message: " + message);
        serviceRunningService.addMessage(message);
    }

}