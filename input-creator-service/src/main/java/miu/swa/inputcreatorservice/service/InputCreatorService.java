package miu.swa.inputcreatorservice.service;

import miu.swa.inputcreatorservice.model.InputCreatorData;
import miu.swa.inputcreatorservice.repository.InputCreatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class InputCreatorService {
	
    @Autowired
    InputCreatorRepository repository;
    
    public void addMessage(InputCreatorData inputData){
        repository.save(inputData);
    }

    public Map<String, InputCreatorData> getMessages(){
        return repository.findAll();
    }

    public InputCreatorData find(String key) {
        return repository.find(key);
    }

    public void clearAllMessages(){
        repository.clear();
    }

}
