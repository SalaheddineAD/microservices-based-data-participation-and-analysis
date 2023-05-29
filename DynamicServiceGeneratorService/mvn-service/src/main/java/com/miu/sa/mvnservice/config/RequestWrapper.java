package com.miu.sa.mvnservice.config;

import java.io.Serializable;
import java.util.Set;

public class RequestWrapper implements Serializable {

    private String zipFilePath;
    private String serviceName;
    private Set<String> topics;

    public RequestWrapper() {}

    public RequestWrapper(String zipFilePath, String serviceName, Set<String> topics) {
        this.zipFilePath = zipFilePath;
        this.serviceName = serviceName;
        this.topics = topics;
    }

    public String getZipFilePath() {
        return zipFilePath;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Set<String> getTopics() {
        return topics;
    }

    @Override
    public String toString() {
        return "RequestWrapper{" +
                "zipFilePath='" + zipFilePath + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", topics=" + topics +
                '}';
    }
}