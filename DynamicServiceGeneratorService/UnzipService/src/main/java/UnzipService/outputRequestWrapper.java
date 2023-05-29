package UnzipService;

import java.util.Set;

public class outputRequestWrapper {

    private String zipFilePath;
    private String serviceName;
    private Set<String> topics;

    outputRequestWrapper(){}

    public outputRequestWrapper(String zipFilePath, String serviceName, Set<String> topics ) {
        this.zipFilePath = zipFilePath;
        this.serviceName = serviceName;
        this.topics = topics;
    }

    public String getzipFilePath() {
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
        return "OutputRequestWrapper{" +
                "zipFilePath='" + zipFilePath + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", topics=" + topics +
                '}';
    }
}
