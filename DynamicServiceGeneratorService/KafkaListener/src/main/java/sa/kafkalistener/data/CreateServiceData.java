package sa.kafkalistener.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateServiceData implements Serializable {
    private String topicName;
    private long topicInterval;
}
