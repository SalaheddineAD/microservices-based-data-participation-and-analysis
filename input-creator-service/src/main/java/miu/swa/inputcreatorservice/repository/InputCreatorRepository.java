package miu.swa.inputcreatorservice.repository;

import miu.swa.inputcreatorservice.model.InputCreatorData;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InputCreatorRepository {
    private Map<String, InputCreatorData> messages;

    public InputCreatorRepository() {
        this.messages = new HashMap<>();
    }

    public void save(InputCreatorData inputData) {
        this.messages.put(inputData.getTopicName(), inputData);
    }

    public void clear(){
        this.messages.clear();
    }

    public Map<String, InputCreatorData> findAll() {
        return this.messages;
    }

    public InputCreatorData find(String key) {
        return this.messages.get(key);
    }
}
