package sa.project.css.service;

import jakarta.servlet.ServletOutputStream;

import java.io.File;
import java.io.IOException;

public interface ICodeSupplierService {
    void getCDSCode(String topic, File workDir) throws IOException;
    void getSSCode(String topic1, String topic2, File workDir) throws IOException;
    void getDISCode(String topic, String interval, File workDir) throws IOException;

    File getCDSCode(String topics) throws IOException;
    File getSSCode(String topics) throws IOException;
    File getRSCode() throws IOException;

    File getDISCode(String topics, String interval) throws IOException;

}
