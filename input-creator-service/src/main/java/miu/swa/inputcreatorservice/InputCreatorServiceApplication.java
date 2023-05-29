package miu.swa.inputcreatorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InputCreatorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InputCreatorServiceApplication.class, args);
    }

}
