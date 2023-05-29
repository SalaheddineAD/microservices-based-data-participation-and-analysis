package sa.kafkalistener.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateServiceResponse {
    private String serviceName;
    private String interval;
    private Set<String> topics;

}
