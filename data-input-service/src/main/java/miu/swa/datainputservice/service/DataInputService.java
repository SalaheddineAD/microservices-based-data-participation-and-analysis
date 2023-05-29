package miu.swa.datainputservice.service;

import miu.swa.datainputservice.model.InputData;
import miu.swa.datainputservice.repository.DataInputRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataInputService {
	
    @Autowired
    DataInputRepository repository;
    
    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }
    
    public void addMessage(InputData inputData){
        repository.save(inputData);
    }

    public List<InputData> getMessages(){
        return repository.findAll();
    }

    public void clearAllMessages(){
        repository.clear();
    }

    public void setScheduledTasks(boolean enabled){
        this.enabled = enabled;
    }
}
