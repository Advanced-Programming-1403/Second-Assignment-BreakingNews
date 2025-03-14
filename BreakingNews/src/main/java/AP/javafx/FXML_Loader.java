package AP.javafx;

import java.io.InputStream;
import java.net.URL;

public class FXML_Loader {

    private FXML_Loader() {}

    public static URL loadURL(String path) {
        return FXML_Loader.class.getResource(path);
    }

    public static String load(String path) {
        return loadURL(path).toString();
    }

    public static InputStream loadStream(String name) {
        return FXML_Loader.class.getResourceAsStream(name);
    }

}
