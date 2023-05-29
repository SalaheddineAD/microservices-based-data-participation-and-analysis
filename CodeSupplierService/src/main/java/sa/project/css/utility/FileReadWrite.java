package sa.project.css.utility;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileReadWrite implements ICodeReadWrite {

    @Override
    public String readFromSource(String sourceName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(sourceName)));
    }

    @Override
    public String writeProcessedCode(String code, String filename) {
        return null;
    }
}
