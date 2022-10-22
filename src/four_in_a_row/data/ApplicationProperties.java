package four_in_a_row.data;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class ApplicationProperties {

    private static Properties applicationProperties;

    static {
        buildProperties();
    }

    public static String getProperty(String prop) {
        return applicationProperties.getProperty(prop);
    }

    public static Properties getProperties() {
        return applicationProperties;
    }

    private static void buildProperties() {
        try {
            applicationProperties = new Properties();
            applicationProperties.load(
                    new BufferedInputStream(
                            new FileInputStream(Paths.get("src/four_in_a_row/app.properties")
                                    .toAbsolutePath().toString())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}