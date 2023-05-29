package miu.swa.datainputservice.repository;

import miu.swa.datainputservice.model.InputData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DataInputRepository {
    private List<InputData> messages;

    public DataInputRepository() {
        this.messages = new ArrayList<>();
    }

    public List<InputData> getMessages() {
        return messages;
    }

    public void save(InputData inputData) {
        this.messages.add(inputData);
    }

    public void clear(){
        this.messages.clear();
    }

    public List<InputData> findAll() {
        return this.messages;
    }
}
