package zipService.zipService;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Set;


@Service
public class Receiver {

    @Autowired
    Sender sender;

    @Autowired
    SupplierServiceClient supplierServiceClient;

    @KafkaListener(topics = "DSGS_CREATION", groupId = "default")
    public void receive(String message) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            System.out.println("got a message from client: " + message);

            //converting the message to an object
            InputMessageWrapper inputMessageWrapper = mapper.readValue(message, InputMessageWrapper.class);

            String serviceName = inputMessageWrapper.getServiceName();
            Set<String> topics = inputMessageWrapper.getTopics();
            String interval = inputMessageWrapper.getInterval();

            // receiving file from css
            byte[] zipFile = getSourceCode(serviceName, topics, interval);

//			  System.out.println("zipFile: "+zipFile);

            // sending message to unzip service
            RequestWrapper requestWrapper = new RequestWrapper(zipFile, serviceName, topics);
            System.out.println("sending to kafka " + requestWrapper);

            // convert file to string before sending to kafka
            String jsonInString = mapper.writeValueAsString(requestWrapper);
            sender.send("filedownloaded", jsonInString);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public byte[] getSourceCode(String serviceName, Set<String> topics, String interval) {
        byte[] output = null;
        try {
            switch (serviceName.toLowerCase()) {
                case "cds":
                    output = supplierServiceClient.getCDSCode(String.join(",", topics)).getBody().getInputStream().readAllBytes();
                    break;
                case "ss":
                    String joinString = URLDecoder.decode(String.join("-", topics), "UTF-8");
                    output = supplierServiceClient.getSSCode(joinString).getBody().getInputStream().readAllBytes();
                    break;
                case "rs":
                    output = supplierServiceClient.getRSCode().getBody().getInputStream().readAllBytes();
                    break;
                case "ds":
                    output = supplierServiceClient.getDIRCode(String.join(",", topics), interval).getBody().getInputStream().readAllBytes();
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

}

