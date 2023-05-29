package com.miu.sa.mvnservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miu.sa.mvnservice.config.RequestWrapper;
import org.apache.maven.shared.invoker.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class KafkaListener {

    private static Integer port = 9900;

    @Autowired
    private Environment env;

    private String mvnHome;

    //Save
    List<ServiceDetails> serviceDetails = new ArrayList<>();

    @org.springframework.kafka.annotation.KafkaListener(topics = "DSGS_START_SERVICE", groupId = "default")
    public void listenToStartService(String serviceName) {
        Process process = null;
        try {
            int port = getPortNumber(serviceName.replace("\"", ""));
            if (port != 0) {
                String startCommand = "curl -X POST http://localhost:" + port + "/data-input/start";
                process = Runtime.getRuntime().exec(startCommand);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Objects.requireNonNull(process).destroy();
        }
    }

    @org.springframework.kafka.annotation.KafkaListener(topics = "DSGS_STOP_SERVICE", groupId = "default")
    public void listenToStopService(String serviceName) {
        Process process = null;
        try {
            int port = getPortNumber(serviceName.replace("\"", ""));
            if (port != 0) {
                String stopCommand = "curl -X POST http://localhost:" + port + "/data-input/stop";
                process = Runtime.getRuntime().exec(stopCommand);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Objects.requireNonNull(process).destroy();
        }
    }

    private int getPortNumber(String topics) {
        Optional<Integer> first = serviceDetails.stream()
                .filter(services -> services.getServiceName().equals(topics))
                .map(ServiceDetails::getPort).findFirst();

        return first.orElse(0);
    }


    @org.springframework.kafka.annotation.KafkaListener(topics = "fileunziped", groupId = "default")
    public void listenForDeployment(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            RequestWrapper requestWrapper = mapper.readValue(message, RequestWrapper.class);

            System.out.println(requestWrapper);
            executeMvn(requestWrapper);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeMvn(RequestWrapper requestWrapper) {
        execute(requestWrapper.getServiceName(), requestWrapper.getZipFilePath(), Arrays.asList("clean", "spring-boot:run " +
                        "-Dspring-boot.run.jvmArguments=\"-Dserver.port=" + port++ + " -Ddemo.topic=" + String.join("-", requestWrapper.getTopics()) + "\""),
                requestWrapper.getTopics());
    }

    private void execute(String serviceName, String dirPath, List<String> goals, Set<String> topics) {
        try {
            int p = port - 1;
            InvocationRequest request = new DefaultInvocationRequest();
            request.setInputStream(InputStream.nullInputStream());
            request.setBaseDirectory(new File(dirPath));
            request.setGoals(goals);

            String os = System.getProperty("os.name");

            if (os.contains("Mac")) {
                mvnHome = env.getProperty("mvn.home.mac");
            } else {
                mvnHome = env.getProperty("mvnHome");
            }

            request.setMavenHome(new File(mvnHome + "\\mvn"));
            request.setBatchMode(false);

            request.setOutputHandler(s -> {
            });

            Invoker invoker = new DefaultInvoker();
            new Thread(() -> {
                try {
                    InvocationResult result = invoker.execute(request);
                    if (result.getExecutionException() != null) {
                        result.getExecutionException().printStackTrace();
                    }
                } catch (MavenInvocationException e) {
                    e.printStackTrace();
                }
            }).start();
            serviceDetails.add(new ServiceDetails(topics.toString().replace("[", "").replace("]", ""), p));
            System.out.println("Service: " + serviceName + " running in port: " + p);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
