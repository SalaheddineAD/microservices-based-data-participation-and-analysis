package edu.miu.kafkaserver.controller;

import edu.miu.kafkaserver.domain.Computer;
import edu.miu.kafkaserver.domain.NetworkData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/network-data")
@CrossOrigin
public class NetworkDataConsumerController implements IDataConsumerController {
    Map<Long, NetworkData> latestNetworkData = new HashMap<>();

    @KafkaListener(topics = "${kafka.topics.network-data}", groupId = "network-data-consumer-group", containerFactory = "networkDataKafkaListenerContainerFactory")
    public void receiveCpuData(NetworkData data) {
        latestNetworkData.put(data.getComputer().getId(), data);
        addComputer(data);
        System.out.println("Network Data received from Kafka");
    }

    @GetMapping("/{computerID}/get-current-data")
    public NetworkData sendData(@PathVariable("computerID") Long computerId) {
        if(computerId == null || latestNetworkData.isEmpty()) return null;
        return latestNetworkData.get(computerId);
    }

    @GetMapping("/get-current-computers")
    @Override
    public List<Computer> getComputers() {
        return latestComputers.values().stream().toList();
    }
}
