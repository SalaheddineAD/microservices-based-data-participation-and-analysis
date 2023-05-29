package sa.kafkalistener.data;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneratedServiceDTO implements Serializable {
    private String serviceName;
    private Set<String> topics;
}
