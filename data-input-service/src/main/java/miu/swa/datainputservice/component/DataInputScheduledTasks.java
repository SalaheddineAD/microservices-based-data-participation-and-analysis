package miu.swa.datainputservice.component;

import miu.swa.datainputservice.service.DataInputService;
import miu.swa.datainputservice.service.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DataInputScheduledTasks {
	
    @Autowired
    Sender sender;

    @Autowired
    DataInputService service;
    @Value("${kafka.topics.dis-output-topic}")
    private String SERVICE_TOPIC_X;
    @Value("${app.properties.upper-bound}")
    private long UPPER_BOUND;

    private long getRandomNumber(long upperBound){
        Random rand = new Random();
        return rand.nextLong(upperBound);
    }

    @Scheduled(fixedRateString = "${scheduled.fixed-rate-milliseconds}")
    public void sendData() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_X, getRandomNumber(UPPER_BOUND));
        }
    }
}
