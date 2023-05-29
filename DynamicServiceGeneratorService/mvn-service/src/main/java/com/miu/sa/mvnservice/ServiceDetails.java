package com.miu.sa.mvnservice;

public class ServiceDetails {

    private String serviceName;

    private int port;

    public ServiceDetails(String serviceName, int port) {
        this.serviceName = serviceName;
        this.port = port;
    }

    public ServiceDetails() {}

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
