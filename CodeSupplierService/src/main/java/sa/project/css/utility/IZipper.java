package sa.project.css.utility;

import java.io.IOException;

public interface IZipper {
    void unZip(String zipSrc, String dest) throws IOException;
    void zipDir(String src, String dest) throws IOException;
}
