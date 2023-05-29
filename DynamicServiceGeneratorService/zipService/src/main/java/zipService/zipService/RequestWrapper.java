package zipService.zipService;


import java.util.Set;

public class RequestWrapper {

    private byte[] zipFile;
    private String serviceName;
    private Set<String> topics;

    RequestWrapper(){}

    public RequestWrapper(byte[] zipFile, String serviceName, Set<String> topics) {
        this.zipFile = zipFile;
        this.serviceName = serviceName;
        this.topics = topics;
    }

    public byte[] getZipFile() {
        return zipFile;
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
                "zipFile='" + zipFile + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", topics=" + topics +
                '}';
    }
}
