package UnzipService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableKafka
public class UnzipServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(UnzipServiceApplication.class, args);
	}

	@Autowired
	private Sender sender;

	@Override
	public void run(String... args) throws Exception {
//		Set<String> stringSet = new HashSet<>();
//		stringSet.add("topic 1");
//		stringSet.add("topic 2");
//		stringSet.add("topic 3");
//		RequestWrapper responseWrapper = new RequestWrapper("new test","test Service Name",stringSet);
//		ObjectMapper mapper = new ObjectMapper();
//		String jsonInString = mapper.writeValueAsString(responseWrapper);
//		sender.send("tester",jsonInString);
	}
}
