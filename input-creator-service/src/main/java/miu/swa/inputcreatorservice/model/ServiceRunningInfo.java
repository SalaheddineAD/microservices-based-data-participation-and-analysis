package miu.swa.inputcreatorservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServiceRunningInfo {

    private String serviceName;
    private String serviceStatus;
}
