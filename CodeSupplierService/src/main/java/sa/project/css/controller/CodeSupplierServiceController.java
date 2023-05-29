package sa.project.css.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sa.project.css.service.ICodeSupplierService;

import java.time.LocalDateTime;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/sources")
public class CodeSupplierServiceController {

    private final ICodeSupplierService codeSupplierService;

    /**
     * Example:
     * Change Detector Service: GET /get-code?service-name=cds&topics=t1
     * Scoring Service: GET /get-code?service-name=ss&topics=t1,t2
     * Reporting Service: GET /get-code?service-name=rs&topics=t1,t2,t3,t4
     * @param serviceName
     * @param topics
     * @return
     */
        @GetMapping("/cds")
    public ResponseEntity<Resource> getCDSCode( @RequestParam String topics) {
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            String serviceName="cds";
            File zipFile = codeSupplierService.getCDSCode(topics);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(zipFile));
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Disposition", "attachment;filename="+serviceName+".zip");
            log.info(currentTime + "\tService Name: " + serviceName + ", Topics: " + topics);
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(zipFile.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/ss")
    public ResponseEntity<Resource> getSSCode( @RequestParam String topics) {
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            String serviceName="ss";
            File zipFile = codeSupplierService.getSSCode(topics);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(zipFile));
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Disposition", "attachment;filename="+serviceName+".zip");
            log.info(currentTime + "\tService Name: " + serviceName + ", Topics: " + topics);
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(zipFile.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/rs")
    public ResponseEntity<Resource> getRSCode(  ) {
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            String serviceName="rs";
            File zipFile = codeSupplierService.getRSCode();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(zipFile));
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Disposition", "attachment;filename="+serviceName+".zip");
            log.info(currentTime + "\tService Name: " + serviceName );
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(zipFile.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/dis")
    public ResponseEntity<Resource> getDIRCode( @RequestParam String topics,@RequestParam String interval) {
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            String serviceName="dis";
            File zipFile = codeSupplierService.getDISCode(topics, interval);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(zipFile));
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Disposition", "attachment;filename="+serviceName+".zip");
            log.info(currentTime + "\tService Name: " + serviceName + ", Topics: " + topics);
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(zipFile.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
