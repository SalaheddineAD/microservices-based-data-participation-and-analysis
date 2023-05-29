package miu.swa.inputcreatorservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import miu.swa.inputcreatorservice.model.InputCreatorData;
import miu.swa.inputcreatorservice.model.ServiceRunningInfo;
import miu.swa.inputcreatorservice.service.InputCreatorService;
import miu.swa.inputcreatorservice.service.KafkaSender;
import miu.swa.inputcreatorservice.service.ServiceRunningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class InputCreatorController {

    @Autowired
    KafkaSender sender;
    @Autowired
    InputCreatorService service;
    @Autowired
    ServiceRunningService serviceRunningService;
    @Value("${cs.topic.creation}")
    private String TOPIC_CREATION;
    @Value("${cs.topic.start-service}")
    private String CS_START_SERVICE;

    @PostMapping("/create")
    public ResponseEntity<?> createTopic(@RequestBody InputCreatorData csData) throws JsonProcessingException {
        if (service.find(csData.getTopicName()) != null) {
            return new ResponseEntity<>("The topic: " + csData.getTopicName() + " is already exist.", HttpStatus.BAD_REQUEST);
        }
        sender.send(TOPIC_CREATION, csData);
        return new ResponseEntity<>("Topic created successfully!", HttpStatus.OK);
    }

    @PostMapping("/start")
    public ResponseEntity<?> startTopic(@RequestParam String topic) throws JsonProcessingException {
        if (service.find(topic) == null) {
            return new ResponseEntity<>("The service " + topic + " is not exist.", HttpStatus.BAD_REQUEST);
        }
        ServiceRunningInfo serviceRunningInfo = serviceRunningService.find(topic);
        if (serviceRunningInfo != null && serviceRunningInfo.getServiceStatus().equals("START")) {
            return new ResponseEntity<>("The service " + topic + " is already started.", HttpStatus.BAD_REQUEST);
        }
        ServiceRunningInfo info = new ServiceRunningInfo(topic, "START");
        sender.sendServiceMessage(CS_START_SERVICE, info);
        return new ResponseEntity<>("Service started successfully!", HttpStatus.OK);
    }

    @PostMapping("/stop")
    public ResponseEntity<?> stopTopic(@RequestParam String topic) throws JsonProcessingException {
        if (service.find(topic) == null) {
            return new ResponseEntity<>("The service " + topic + " is not exist.", HttpStatus.BAD_REQUEST);
        }
        ServiceRunningInfo serviceRunningInfo = serviceRunningService.find(topic);
        if (serviceRunningInfo != null && serviceRunningInfo.getServiceStatus().equals("STOP")) {
            return new ResponseEntity<>("The service " + topic + " is already stopped.", HttpStatus.BAD_REQUEST);
        }
        ServiceRunningInfo info = new ServiceRunningInfo(topic, "STOP");
        sender.sendServiceMessage(CS_START_SERVICE, info);
        return new ResponseEntity<>("Service stopped successfully!", HttpStatus.OK);
    }

}
