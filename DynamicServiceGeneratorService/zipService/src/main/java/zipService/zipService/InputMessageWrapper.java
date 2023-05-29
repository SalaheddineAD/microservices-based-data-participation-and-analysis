package zipService.zipService;

import java.util.Set;

public class InputMessageWrapper {


    private String serviceName;
    private Set<String> topics;
    private String interval;


    public InputMessageWrapper(){};


    public InputMessageWrapper(String serviceName, Set<String> topics, String interval) {
        this.serviceName = serviceName;
        this.topics = topics;
        this.interval = interval;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Set<String> getTopics() {
        return topics;
    }

    public void setTopics(Set<String> topics) {
        this.topics = topics;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }
}
