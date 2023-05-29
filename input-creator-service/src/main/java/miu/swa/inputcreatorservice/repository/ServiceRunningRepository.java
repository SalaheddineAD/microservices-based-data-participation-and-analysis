package miu.swa.inputcreatorservice.repository;

import miu.swa.inputcreatorservice.model.ServiceRunningInfo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ServiceRunningRepository {
    private Map<String, ServiceRunningInfo> messages;

    public ServiceRunningRepository() {
        this.messages = new HashMap<>();
    }

    public void save(ServiceRunningInfo inputData) {
        this.messages.put(inputData.getServiceName(), inputData);
    }

    public void clear(){
        this.messages.clear();
    }

    public Map<String, ServiceRunningInfo> findAll() {
        return this.messages;
    }

    public ServiceRunningInfo find(String key) {
        return this.messages.get(key);
    }
}
