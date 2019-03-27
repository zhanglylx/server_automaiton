package server_automaiton_gather.server_automaiton_Utils;

import com.mfeia.book.server_automaiton.Test;
import server_automaiton_gather.ErrException;
import server_automaiton_gather.RealizePerform;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

public class PropertiesConfig {
    public static final String HTTP_CONFIG = "httpConfig.properties";
    public static final String SERVER_AUTOMAITON_CONFIG = "server_automaiton.properties";
    public static final String DATA_BASE_CONFIG = "config/server_automaiton_database.properties";
    public static final String MAIL_CONFIG = "server_automaiton_mail.properties";
    public static final String HTML_STATIC = "static.html";


    public static File getConfigFile(String fileName){
        File file = new File("config" + File.separator + fileName);
        if (!Test.isJarRun()) {
            file = new File(
                    Objects.requireNonNull(
                            PropertiesConfig
                                    .class
                                    .getClassLoader()
                                    .getResource(""))
                            .getPath() + file);
        }
        return file;
    }

    public static Properties getPropertiesConfig(String fileName) throws IOException {
        InputStream inputStream = null;
        try {
            Properties properties
                    = new Properties();
            inputStream = new BufferedInputStream(
                    new FileInputStream(getConfigFile(fileName)));
            properties.load(inputStream);
            return properties;
        } catch (Exception e) {
            e.printStackTrace();
            RealizePerform.getRealizePerform().addtestFrameList(new ErrException(PropertiesConfig.class, "getPropertiesConfig", e));
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return null;
    }
}
