package seedu.address.model.person.timetable;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

/**
 * A class containing utility methods for Timetable
 */
public class TimetableUtil {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static final String SPLIT_QUESTION_MARK = "\\?";
    private static final String SPLIT_AMPERSAND = "&";
    private static final String SPLIT_EQUALS = "=";
    private static final String SPLIT_COLON = ":";
    private static final int MODULE_INFORMATION_INDEX = 1;
    private static final int MODULE_CODE_INDEX = 0;
    private static final int MODULE_CODE_REMAINING_INDEX = 1;
    private static final int LESSON_TYPE_INDEX = 0;
    private static final int CLASS_TYPE_INDEX = 1;

    /**
     * Sets up attributes of a given {@code Timetable}.
     * @param timetable Timetable to be set up
     */
    public static void setUpTimetableInfo(Timetable timetable) {
        timetable.setExpandedUrl(expandShortTimetableUrl(timetable.value));
        timetable.setListOfModules(splitLongTimetableUrl(timetable.getExpandedUrl()));
    }

    /**
     * Expands {@code timetableUrl} from a short NUSMods timetable URL to a long NUSMods timetable URL.
     * Returns the expanded URL.
     *
     * @param timetableUrl URL to be modified
     */
    public static String expandShortTimetableUrl(String timetableUrl) {
        String expandedUrl = null;
        try {
            final URL shortUrl = new URL(timetableUrl);
            final HttpURLConnection urlConnection = (HttpURLConnection) shortUrl.openConnection();
            urlConnection.setInstanceFollowRedirects(false);
            expandedUrl = urlConnection.getHeaderField("location");
        } catch (MalformedURLException e) {
            logger.warning("URL provided is invalid");
        } catch (IOException e) {
            logger.warning("Failed to open connection");
        }
        return expandedUrl;
    }

    /**
     * Splits expanded NUSMods timetable URL into the different {@code TimetableModule}s.
     * Expanded timetable URL is in the format ...?[MODULE_CODE]=[LESSON_TYPE]:[CLASS_NUM]&...
     * @param timetableUrl
     * @return ArrayList of {@code TimetableModule}
     */
    public static ArrayList<TimetableModule> splitLongTimetableUrl(String timetableUrl) {
        String[] moduleInformation = timetableUrl.split(SPLIT_QUESTION_MARK);
        String[] modules = moduleInformation[MODULE_INFORMATION_INDEX].split(SPLIT_AMPERSAND);

        ArrayList<TimetableModule> listOfModules = new ArrayList<TimetableModule>();
        String moduleCode;
        String lessonType;
        String classType;

//        for (String currentModule : modules) {
//            moduleCode = currentModule.split(SPLIT_EQUALS)[MODULE_CODE_INDEX];
//            lessonType = currentModule.split(SPLIT_EQUALS)[MODULE_CODE_REMAINING_INDEX]
//                    .split(SPLIT_COLON)[LESSON_TYPE_INDEX];
//            classType = currentModule.split(SPLIT_EQUALS)[MODULE_CODE_REMAINING_INDEX]
//                    .split(SPLIT_COLON)[CLASS_TYPE_INDEX];
//
//            listOfModules.add(new TimetableModule(moduleCode, lessonType, classType));
//        }
        return listOfModules;
    }

    public static void getModuleInfoFromApi() {

    }
}
