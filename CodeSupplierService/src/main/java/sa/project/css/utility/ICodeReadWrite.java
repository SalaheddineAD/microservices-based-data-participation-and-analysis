package sa.project.css.utility;

import java.io.IOException;

public interface ICodeReadWrite {
    String readFromSource(String sourceName) throws IOException;
    String writeProcessedCode(String code, String filename);
}
