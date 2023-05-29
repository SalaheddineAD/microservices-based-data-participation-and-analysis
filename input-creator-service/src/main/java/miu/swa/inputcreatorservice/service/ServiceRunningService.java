package miu.swa.inputcreatorservice.service;

import miu.swa.inputcreatorservice.model.ServiceRunningInfo;
import miu.swa.inputcreatorservice.repository.ServiceRunningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ServiceRunningService {
	
    @Autowired
    ServiceRunningRepository repository;
    
    public void addMessage(ServiceRunningInfo inputData){
        repository.save(inputData);
    }

    public Map<String, ServiceRunningInfo> getMessages(){
        return repository.findAll();
    }

    public ServiceRunningInfo find(String key) {
        return repository.find(key);
    }

    public void clearAllMessages(){
        repository.clear();
    }

}
