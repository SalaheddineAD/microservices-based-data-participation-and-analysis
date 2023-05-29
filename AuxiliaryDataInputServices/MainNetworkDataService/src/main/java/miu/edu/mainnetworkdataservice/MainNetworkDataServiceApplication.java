package miu.edu.mainnetworkdataservice;

import miu.edu.mainnetworkdataservice.domain.Metric;
import miu.edu.mainnetworkdataservice.service.NetworkDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@EnableDiscoveryClient
public class MainNetworkDataServiceApplication /*implements CommandLineRunner*/ {
    @Autowired
    NetworkDataService networkDataService;

    public static void main(String[] args) {
        SpringApplication.run(MainNetworkDataServiceApplication.class, args);
    }

    /*@Override
    public void run(String... args) throws Exception {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //String apiUrl = "http://localhost:19999/api/v1/data?chart=system.ipv4"; For MacOS
                //String apiUrl = "http://localhost:19999/api/v1/data?chart=system.ip"; // For Windows

                String apiUrl = "http://localhost:19999/api/v1/data";
                String osType = System.getProperty("os.name");
                apiUrl += (osType.toLowerCase().contains("Windows".toLowerCase())) ?  "?chart=system.ip" : "?chart=system.ipv4";
                apiUrl += "&after=-2&format=json&points=1";

                Metric data = networkDataService.getData(apiUrl);
                //System.out.println("Fetched data is: " + data);
                if(data != null) networkDataService.sendData(data);
            }
        }, 0, 1000);
    }*/

}
