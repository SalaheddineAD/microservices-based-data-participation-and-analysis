package edu.miu.kafkaserver.controller;

import edu.miu.kafkaserver.domain.Computer;
import edu.miu.kafkaserver.domain.RamData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/ram-data")
@CrossOrigin
public class RamDataConsumerController implements IDataConsumerController {
    Map<Long, RamData> latestNetworkData = new HashMap<>();

    @KafkaListener(topics = "${kafka.topics.ram-data}", groupId = "ram-data-consumer-group", containerFactory = "ramDataKafkaListenerContainerFactory")
    public void receiveCpuData(RamData data) {
        latestNetworkData.put(data.getComputer().getId(), data);
        addComputer(data);
        System.out.println("Ram Data received from Kafka");
    }

    @GetMapping("/{computerID}/get-current-data")
    public RamData sendData(@PathVariable("computerID") Long computerId) {
        if(computerId == null || latestNetworkData.isEmpty()) return null;
        return latestNetworkData.get(computerId);
    }

    @GetMapping("/get-current-computers")
    @Override
    public List<Computer> getComputers() {
        return latestComputers.values().stream().toList();
    }
}
