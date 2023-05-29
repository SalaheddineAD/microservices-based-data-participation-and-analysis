package sa.project.css.service;

import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sa.project.css.utility.IZipper;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@Service
@RequiredArgsConstructor
public class CodeSupplierService implements ICodeSupplierService{

    private final IZipper zipper;
     @Value("${css.cds.src}")
     private String cdsSrc;
    @Value("${css.ss.src}")
    private String ssSrc;

    @Value("${css.dis.src}")
    private String disSrc;


    @Value("${css.rs.src}")
    private String rsSrc;

    @Value("${zipDir}")
    private String zipDir;

    @Value("${codeDir}")
    private String codeDir;

    @Value("${zipOutDir}")
    private String zipOutDir;

    @Value("${css.cds.projectName}")
    private String cdsProjectName;

    @Value("${css.ss.projectName}")
    private String ssProjectName;

    @Value("${css.dis.projectName}")
    private String disProjectName;
    private final static String APPLICATION_YML_REL_PATH = "/src/main/resources/application.yml";
    private final static int FILE_READ_CHAR_BUFFER_SIZE = 10;

    private void downloadZip(String serviceName, String dest) throws IOException {
        String srcUrl = "";
        if (serviceName.equals("cds"))
            srcUrl = cdsSrc;
        else if (serviceName.equals("ss"))
            srcUrl = ssSrc;
        else if (serviceName.equals("dis"))
            srcUrl = disSrc;
        else
            srcUrl = rsSrc;

        try(FileOutputStream fileOutputStream = new FileOutputStream(dest)) {
            ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(srcUrl).openStream());
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        }
    }

    private File getApplicationPropertiesFile(File workDir, String projectName) {
        System.out.println(workDir.getAbsolutePath());
        File applicationProperties = new File(workDir.getAbsolutePath() + "/" + projectName + APPLICATION_YML_REL_PATH);
        if (!applicationProperties.exists()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Properties file not found!");
        }
        return applicationProperties;
    }

    private String getStringFromFile(File file) throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(file));) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] charArrBuffer = new char[FILE_READ_CHAR_BUFFER_SIZE];
            while (reader.read(charArrBuffer) != -1) {
                stringBuilder.append(new String(charArrBuffer));
                charArrBuffer = new char[FILE_READ_CHAR_BUFFER_SIZE];
            }
            return stringBuilder.toString();
        }
    }

    private void writeContentToFile(File file, String content) throws IOException {
        String refinedContent = content.replace('\0', ' '); // Bypass the null character
        try(FileWriter fw = new FileWriter(file);BufferedWriter writer = new BufferedWriter(fw);) {
            writer.write(refinedContent);
        }
    }

    @Override
    public void getCDSCode(String topic, File workDir) throws IOException {
        File applicationPropertiesFile = getApplicationPropertiesFile(workDir, cdsProjectName);
        String propertiesString = getStringFromFile(applicationPropertiesFile);
        String newPropertiesString = propertiesString.replace("$$input$$", topic);
        String[] topicParts = topic.split("_");
        int dsTopicIndex = Integer.parseInt(topicParts[topicParts.length - 1]);
        String outputTopic = "CDS_" + dsTopicIndex;
        newPropertiesString = newPropertiesString.replace("$$output$$", outputTopic);
        writeContentToFile(applicationPropertiesFile, newPropertiesString);
    }

    @Override
    public void getSSCode(String topic1, String topic2, File workDir) throws IOException {
        File applicationPropertiesFile = getApplicationPropertiesFile(workDir, ssProjectName);
        String propertiesString = getStringFromFile(applicationPropertiesFile);
        String newPropertiesString = propertiesString.replace("$$inputX$$", topic1);
        newPropertiesString = newPropertiesString.replace("$$inputY$$", topic2);
        String[] topicParts1 = topic1.split("_");
        String[] topicParts2 = topic2.split("_");
        int cds1TopicIndex = Integer.parseInt(topicParts1[topicParts1.length - 1]);
        int cds2TopicIndex = Integer.parseInt(topicParts2[topicParts2.length - 1]);
        String siOutputTopic = "SI_" + cds1TopicIndex + "_" + cds2TopicIndex;
        String nsiOutputTopic = "NSI_" + cds1TopicIndex + "_" + cds2TopicIndex;
        newPropertiesString = newPropertiesString.replace("$$sioutput$$", siOutputTopic);
        newPropertiesString = newPropertiesString.replace("$$nioutput$$", nsiOutputTopic);
        writeContentToFile(applicationPropertiesFile, newPropertiesString);
    }

    @Override
    public void getDISCode(String topic, String interval, File workDir) throws IOException {
        //Output update
        File applicationPropertiesFile = getApplicationPropertiesFile(workDir, disProjectName);
        String propertiesString = getStringFromFile(applicationPropertiesFile);
        String newPropertiesString = propertiesString.replace("$$output$$", topic);
        writeContentToFile(applicationPropertiesFile, newPropertiesString);

        //Interval update
        File applicationPropertiesFile2 = getApplicationPropertiesFile(workDir, disProjectName);
        String propertiesString2 = getStringFromFile(applicationPropertiesFile2);
        String newPropertiesString2 = propertiesString2.replace("$$interval$$", interval);
        writeContentToFile(applicationPropertiesFile, newPropertiesString2);


    }


    private void validateTopics(String[] topics) {
        boolean isValid = true;
        if (topics.length == 0) {
            isValid = false;
        }
        else {
            for (String topic : topics) {
                if (!topic.matches("^(DS_\\d+)|(CDS_\\d+)|(SS_\\d+_\\d+)|(NSI_\\d+_\\d+)$")) {
                    isValid = false;
                    break;
                }
            }
        }

        if (!isValid) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Topics!");
        }
    }

    @Override
    public File getRSCode( ) throws IOException {
        String serviceName="rs";
        long currentTimeStamp = System.currentTimeMillis();
        String zipSrc = zipDir + "/" + serviceName + "_" + currentTimeStamp + ".zip";
        downloadZip(serviceName, zipSrc);
        return new File(zipSrc);
    }

    @Override
    public File getCDSCode(String topics) throws IOException {
        String[] topicArr = topics.split(" *, *");
        validateTopics(topicArr);
        String serviceName="cds";
        long currentTimeStamp = System.currentTimeMillis();
        String zipSrc = zipDir + "/" + serviceName + "_" + currentTimeStamp + ".zip";
        String extractSrc = codeDir + "/" + serviceName + "_" + currentTimeStamp;
        String exportSrc = zipOutDir + "/" + serviceName + "_" + currentTimeStamp + ".zip";
        File extractDir = new File(extractSrc);
        if (!extractDir.mkdir())
            throw new IOException("Failed to create directory: " + extractDir);

        downloadZip(serviceName, zipSrc);
        zipper.unZip(zipSrc, extractSrc);
        getCDSCode(topics, extractDir);
        File[] filesInExtractDir = extractDir.listFiles();
        if (filesInExtractDir == null || filesInExtractDir.length == 0)
            throw new IOException("Empty Extract Dir!");
        zipper.zipDir(filesInExtractDir[0].getAbsolutePath(), exportSrc);
        return new File(exportSrc);
    }

    @Override
    public File getSSCode(String topics) throws IOException {
        String[] topicArr = topics.split(" *, *");
        validateTopics(topicArr);
        String serviceName="ss";
        long currentTimeStamp = System.currentTimeMillis();
        String zipSrc = zipDir + "/" + serviceName + "_" + currentTimeStamp + ".zip";
        String extractSrc = codeDir + "/" + serviceName + "_" + currentTimeStamp;
        String exportSrc = zipOutDir + "/" + serviceName + "_" + currentTimeStamp + ".zip";
        File extractDir = new File(extractSrc);
        if (!extractDir.mkdir())
            throw new IOException("Failed to create directory: " + extractDir);

        downloadZip(serviceName, zipSrc);
        zipper.unZip(zipSrc, extractSrc);
        getSSCode(topicArr[0], topicArr[1], extractDir);
        File[] filesInExtractDir = extractDir.listFiles();
        if (filesInExtractDir == null || filesInExtractDir.length == 0)
            throw new IOException("Empty Extract Dir!");
        zipper.zipDir(filesInExtractDir[0].getAbsolutePath(), exportSrc);

        return new File(exportSrc);
    }
    @Override
    public File getDISCode(String topics,String interval) throws IOException {
        String[] topicArr = topics.split(" *, *");
        validateTopics(topicArr);

        String serviceName="dis";
        long currentTimeStamp = System.currentTimeMillis();
        String zipSrc = zipDir + "/" + serviceName + "_" + currentTimeStamp + ".zip";
        String extractSrc = codeDir + "/" + serviceName + "_" + currentTimeStamp;
        String exportSrc = zipOutDir + "/" + serviceName + "_" + currentTimeStamp + ".zip";
        File extractDir = new File(extractSrc);
        if (!extractDir.mkdir())
            throw new IOException("Failed to create directory: " + extractDir);

        downloadZip(serviceName, zipSrc);
        zipper.unZip(zipSrc, extractSrc);
        getDISCode(topics,interval, extractDir);
        File[] filesInExtractDir = extractDir.listFiles();
        if (filesInExtractDir == null || filesInExtractDir.length == 0)
            throw new IOException("Empty Extract Dir!");
        zipper.zipDir(filesInExtractDir[0].getAbsolutePath(), exportSrc);
        return new File(exportSrc);
    }
}
