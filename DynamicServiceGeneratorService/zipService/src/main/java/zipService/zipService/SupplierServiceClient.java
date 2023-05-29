package zipService.zipService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Code-Supplier-Service", url="http://localhost:8085/api/v1/sources")
public interface SupplierServiceClient {
	@GetMapping(value = "/cds", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	ResponseEntity<Resource> getCDSCode(@RequestParam("topics") String topics);

	@GetMapping(value = "/ss")
	ResponseEntity<Resource> getSSCode(@RequestParam("topics") String topics);

	@GetMapping(value = "/rs", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	ResponseEntity<Resource> getRSCode();

	@GetMapping(value = "/dis", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	ResponseEntity<Resource> getDIRCode(@RequestParam String topics,@RequestParam String interval);
}
