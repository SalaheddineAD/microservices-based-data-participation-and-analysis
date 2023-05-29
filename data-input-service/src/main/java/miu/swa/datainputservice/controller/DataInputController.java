package miu.swa.datainputservice.controller;

import miu.swa.datainputservice.model.InputData;
import miu.swa.datainputservice.service.DataInputService;
import miu.swa.datainputservice.service.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/data-input")
public class DataInputController {

    @Autowired
    DataInputService service;

    @Autowired
    Sender sender;

    @GetMapping("/messages")
    public ResponseEntity<List<InputData>> getMessages(){
        return new ResponseEntity<>(service.getMessages(), HttpStatus.OK);
    }

    @GetMapping("/messages/{topic}")
    public List<InputData> getMessage(@PathVariable String topic){
        List<InputData> messages =  service.getMessages();
        return messages.stream().filter(m -> m.getTopic().equals(topic)).collect(Collectors.toList());
    }

    @PostMapping("/stop")
    public ResponseEntity<?> setScheduledTasksFalse(){
        service.setScheduledTasks(false);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/start")
    public ResponseEntity<?> setScheduledTasksTrue(){
        service.setScheduledTasks(true);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/messages/clear")
    public ResponseEntity<?> clearAllMessages(){
        service.clearAllMessages();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/messages")
    public ResponseEntity<?> addMessage(@RequestParam("topic") String topic, @RequestParam("message") Long message){
        sender.send(topic, message);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
