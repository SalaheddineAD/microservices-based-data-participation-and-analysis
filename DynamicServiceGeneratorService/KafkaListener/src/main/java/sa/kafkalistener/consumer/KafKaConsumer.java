package sa.kafkalistener.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import sa.kafkalistener.data.CreateServiceData;
import sa.kafkalistener.data.ServiceRunningData;
import sa.kafkalistener.producer.KafkaProducer;
import sa.kafkalistener.service.NameService;
import sa.kafkalistener.utils.AppConstants;

@Service
@AllArgsConstructor
public class KafKaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafKaConsumer.class);

    private KafkaProducer kafkaProducer;
    @Autowired
    private NameService nameService;

    @KafkaListener(topics = AppConstants.CS_CREATION, groupId = AppConstants.GROUP_ID)
    public void createService(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CreateServiceData createServiceData = mapper.readValue(message, CreateServiceData.class);
        LOGGER.info(String.format("Message received -> %s", createServiceData));
        nameService.generateNames(createServiceData);
    }

    @KafkaListener(topics = AppConstants.CS_START_SERVICE, groupId = AppConstants.GROUP_ID)
    public void startService(String message) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ServiceRunningData serviceRunningData = mapper.readValue(message, ServiceRunningData.class);
        LOGGER.info(String.format("Message received -> %s", serviceRunningData));

        if (serviceRunningData.getServiceStatus().equals("START")){
            nameService.startServices(serviceRunningData);
        }else {
            nameService.stopServices(serviceRunningData);
        }
    }
}
