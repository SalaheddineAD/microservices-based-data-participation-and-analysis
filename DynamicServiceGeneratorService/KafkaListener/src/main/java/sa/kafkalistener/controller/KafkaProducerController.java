package sa.kafkalistener.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa.kafkalistener.data.CreateServiceData;
import sa.kafkalistener.data.GeneratedServiceDTO;
import sa.kafkalistener.data.ServiceRunningData;
import sa.kafkalistener.producer.KafkaProducer;
import sa.kafkalistener.utils.AppConstants;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/kafka")
public class KafkaProducerController {

    @Autowired
    private KafkaProducer kafkaProducer;


    @GetMapping("/publish")
    public ResponseEntity<String> publish() throws JsonProcessingException {
        kafkaProducer.sendMessage(new ServiceRunningData("DS_23", "START"),
                AppConstants.DSGS_CREATION);
        return ResponseEntity.ok("Message sent to kafka topic");
    }

}
