package edu.miu.kafkaserver.controller;

import edu.miu.kafkaserver.domain.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IDataConsumerController {

    Map<Long, Computer> latestComputers = new HashMap<>();

    default void addComputer(Metric metric) {
        if (!latestComputers.containsKey(metric.getComputer().getId())) {
            latestComputers.put(metric.getComputer().getId(), metric.getComputer());
        }
    }

    List<Computer> getComputers();

}
