package pl.sutkowski.storageconnector.googledrive.util;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.Getter;

/**
 * Created by es on 10.04.16.
 */
public final class GoogleDriveCommons {

    /**
     * Be sure to specify the name of your application. If the application name is {@code null} or
     * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
     */
    @Getter
    private static final String applicationName = "";

    /**
     * Global instance of the JSON factory.
     */
    @Getter
    private static final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

}
