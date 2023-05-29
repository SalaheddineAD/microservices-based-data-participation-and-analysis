package edu.miu.networkdataservice;

import edu.miu.networkdataservice.domain.Metric;
import edu.miu.networkdataservice.service.NetworkDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class NetworkDataServiceApplication implements CommandLineRunner {
    @Autowired
    NetworkDataService networkDataService;

    public static void main(String[] args) {
        SpringApplication.run(NetworkDataServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //String apiUrl = "http://localhost:19999/api/v1/data?chart=system.ipv4"; // For MacOS
                //String apiUrl = "http://localhost:19999/api/v1/data?chart=system.ip"; // For Windows

                String apiUrl = "http://localhost:19999/api/v1/data";
                String osType = System.getProperty("os.name");
                apiUrl += (osType.toLowerCase().contains("mac")) ? "?chart=system.ipv4" : "?chart=system.ip";
                apiUrl += "&after=-2&format=json&points=1";

                Metric data = networkDataService.getData(apiUrl);
                //System.out.println("Fetched data is: " + data);
                if(data != null) networkDataService.sendData(data);
            }
        }, 0, 1000);
    }

}
