package seedu.address.model.locationfinder;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author HEARTOFAL1ON
/**
 * A class containing utility methods for LocationFinder
 */
public class LocationFinderUtil {

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static final String INVALID_URL_RESULT = "https://www.google.com/maps/search/?api=1&query=";
    private static final String MESSAGE_INVALID_URL = "Invalid Google Maps search query URL provided.";
    private static final String HTTPS_METHOD_GET = "GET";
    private static final int HTTPS_METHOD_RESPONSE_OK = 200;

    /**
     * Sets up attributes of a given {@code LocationFinder}.
     * @param locationFinder LocationFinder to be set up
     */
    public static void setUpTimetableInfo(LocationFinder locationFinder) {
        try {
            setLocationFinderUrl(locationFinder);
            setLocationFinderUrl(locationFinder);
            getResultFromApi(locationFinder);
        } catch (ParseException e) {
            logger.warning(MESSAGE_INVALID_URL);
        }
    }

    /**
     * Sets the URL for {@code locationFinder}.
     */
    public static void setLocationFinderUrl(LocationFinder locationFinder) throws ParseException {
        String locationFinderUrl = locationFinder.value;
        String finalUrl = null;
        checkArgument(LocationFinder.isValidLocationQuery(locationFinderUrl),
                LocationFinder.MESSAGE_LOCATION_FINDER_CONSTRAINTS);
        String expandedUrl = null;
        try {
            URLEncoder.encode(locationFinderUrl);
            final URL lFUrl = new URL(locationFinderUrl);
            final HttpsURLConnection urlConnection = (HttpsURLConnection) lFUrl.openConnection();
            urlConnection.setInstanceFollowRedirects(false);
            finalUrl = urlConnection.getHeaderField("address");

            if (finalUrl.equals(INVALID_URL_RESULT)) {
                throw new ParseException(MESSAGE_INVALID_URL);
            }
        } catch (MalformedURLException e) {
            logger.warning("URL provided is malformed");
        } catch (IOException e) {
            logger.warning("Failed to open connection");
        }
        locationFinder.setLocationFinderUrl(finalUrl);
    }

    public static void getResultFromApi(LocationFinder locationFinder) {
        // dummy method
    }

    /**
     * Retrieves json file from Google Maps API and converts to String
     */
    public static String getJsonContentsFromNusModsApi() {
        // dummy method
        return null;
    }

    /**
     * Read the responded result
     * @throws IOException from readLine()
     */
    public static String readStream(InputStream inputStream) throws IOException {
        // dummy method
        return null;
    }

}
