package sa.kafkalistener.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sa.kafkalistener.consumer.KafKaConsumer;
import sa.kafkalistener.data.CreateServiceData;
import sa.kafkalistener.data.CreateServiceResponse;
import sa.kafkalistener.data.ServiceRunningData;
import sa.kafkalistener.producer.KafkaProducer;
import sa.kafkalistener.utils.AppConstants;

import java.util.HashSet;
import java.util.Set;

@Service
public class NameService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafKaConsumer.class);
    @Autowired
    private KafkaProducer kafkaProducer;
    private static Set<String> createdDSTopics = new HashSet<>();
    private static Set<String> createdCDSTopics = new HashSet<>();
    private static Set<String> createdSSTopics = new HashSet<>();
    private static Set<String> createdRSTopics = new HashSet<>();

    private static Set<String> runningDSTopics = new HashSet<>();

    public void generateNames(CreateServiceData createServiceData) throws JsonProcessingException {
        String ds = generateAndCreateDS(createServiceData.getTopicName(), createServiceData.getTopicInterval());
        String cds = generateAndCreateCDS(ds);
        generateAndCreateSS(cds);
        generateAndCreateRs(cds);

        if(!isCDSExist(cds))
            createdCDSTopics.add(cds);

    }

    public void startServices(ServiceRunningData serviceRunningData) throws Exception {
        generateAndStartDs(serviceRunningData.getServiceName());
    }

    public void stopServices(ServiceRunningData serviceRunningData) throws Exception {
        stopDS(serviceRunningData.getServiceName());
    }

    private String generateAndStartDs(String ds) throws Exception {
        if(!isDSExist(ds))
            throw new Exception();

        if(!isDSRunning(ds)) {
            sendToStartKafka(ds);
            runningDSTopics.add(ds);
        }

        return ds;
    }

    private String generateAndCreateDS(String ds, long interval) throws JsonProcessingException {
        if(!isDSExist(ds)) {
            sendToCreationKafka(new CreateServiceResponse("DS", String.valueOf(interval), Set.of(ds)));
            createdDSTopics.add(ds);
        }
        return ds;
    }


    private String generateAndCreateCDS(String ds) throws JsonProcessingException {
        String cds = generateCDS(ds);
        if(!isCDSExist(cds)) {
            sendToCreationKafka(new CreateServiceResponse("CDS", String.valueOf(0), Set.of(cds)));
        }

        return cds;
    }

    private String generateCDS(String ds) {
        return "CDS" + ds.substring(ds.lastIndexOf("_"));
    }

    private void generateAndCreateSS(String newCds) throws JsonProcessingException {
        Set<Set<String>> ssSet = generateSS(newCds, createdCDSTopics);
        for (Set<String> ss : ssSet) {
            if(!ss.isEmpty()){
                sendToCreationKafka(new CreateServiceResponse("SS", String.valueOf(0), ss));
//                createdSSTopics.addAll(ss);
            }
        }

//        createdSSTopics.addAll(ssSet);
    }

    private Set<Set<String>> generateSS(String newCds, Set<String> CDSs) {

        Set<Set<String>> ssSet = new HashSet<>();
            for (String s : CDSs) {
                Set<String> newSs = new HashSet<>();
                String first = s.substring(s.lastIndexOf("_"));
                String second = newCds.substring(newCds.lastIndexOf("_"));
                String element = "";
                if (!first.equals(second))
                {
                    newSs.add("CDS" + first);
                    newSs.add("CDS" + second);
                    element = "SS" + first + second;
                }
                ssSet.add(newSs);
                createdSSTopics.add(element);
            }

        return ssSet;
    }

    private void generateAndCreateRs(String newCds) throws JsonProcessingException {
        Set<String> rsSet = generateRs(newCds, createdCDSTopics);
        for (String rs : rsSet) {
            if(!isRSExist(rs)) {
                sendToCreationKafka(new CreateServiceResponse("RS", String.valueOf(0), Set.of(rs)));
                createdRSTopics.add(rs);
            }
        }
//        createdRSTopics.addAll(rsSet);
    }

    private Set<String> generateRs(String newCds, Set<String> CDSs) throws JsonProcessingException {
        Set<String> rsNames = new HashSet<>();

        for (String s : CDSs) {
            String first = s.substring(s.lastIndexOf("_"));
            String second = newCds.substring(newCds.lastIndexOf("_"));
            if (!first.equals(second))
                rsNames.add("RS" + first + second);
        }

        return rsNames;
    }


    private void sendToCreationKafka(CreateServiceResponse createServiceResponse) throws JsonProcessingException {
        LOGGER.info(String.format("Message sent -> %s to %s", createServiceResponse, AppConstants.DSGS_CREATION));
        kafkaProducer.sendMessage(createServiceResponse, AppConstants.DSGS_CREATION);
    }

    private void sendToStartKafka(String name) throws JsonProcessingException {
        LOGGER.info(String.format("Message sent -> %s to %s", name, AppConstants.DSGS_START_SERVICE));
        kafkaProducer.sendMessage(name, AppConstants.DSGS_START_SERVICE);
    }

    private void sendToStopKafka(String name) throws JsonProcessingException {
        LOGGER.info(String.format("Message sent -> %s to %s", name, AppConstants.DSGS_STOP_SERVICE));
        kafkaProducer.sendMessage(name, AppConstants.DSGS_STOP_SERVICE);
    }

    private void stopDS(String ds) throws JsonProcessingException {
        if(isDSRunning(ds)) {
            sendToStopKafka(ds);
            runningDSTopics.remove(ds);
        }
    }


    private boolean isDSExist(String ds) {
        return createdDSTopics.contains(ds);
    }

    private boolean isCDSExist(String cds) {
        return createdCDSTopics.contains(cds);
    }

    private boolean isSSExist(String ss) {
        return createdSSTopics.contains(ss);
    }

    private boolean isRSExist(String rs) {
        return createdRSTopics.contains(rs);
    }

    private boolean isDSRunning(String ds) {
        return runningDSTopics.contains(ds);
    }
}
