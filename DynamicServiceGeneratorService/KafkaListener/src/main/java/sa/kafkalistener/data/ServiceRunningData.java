package sa.kafkalistener.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceRunningData {
    private String serviceName;
    private String serviceStatus;

    public String getServiceStatus() {
        return serviceStatus.toUpperCase(Locale.ROOT);
    }
}
