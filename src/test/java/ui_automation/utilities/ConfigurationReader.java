package ui_automation.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    /**
     * The getProperty() is designed to read the .properties file and
     * return the value of respective key
     *
     * @param fileName
     * @param key
     * @return
     */
    public static String getProperty(String fileName, String key) {
        // Create a properties object
        Properties properties = new Properties();
        try {
            // complete the path
            String path = "src" +
                    File.separator +
                    "test" +
                    File.separator +
                    "resources" + File.separator + fileName;
            // read the file as fileInputStream
            FileInputStream stream = new FileInputStream(path);
            // load properties object with fileInputStream
            properties.load(stream);
            //close the stream
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return the value of key
        return properties.getProperty(key);
    }
}