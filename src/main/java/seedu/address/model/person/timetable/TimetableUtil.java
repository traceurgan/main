package seedu.address.model.person.timetable;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * A class containing utility methods for Timetable
 */
public class TimetableUtil {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static final String SPLIT_QUESTION_MARK = "\\?";
    private static final String SPLIT_AMPERSAND = "&";
    private static final String SPLIT_EQUALS = "=";
    private static final String SPLIT_COMMA = ",";
    private static final String SPLIT_COLON = ":";
    private static final String REPLACE_NON_DIGIT_CHARACTERS = "[^0-9]";
    private static final String INVALID_SHORT_URL_RESULT = "http://modsn.us";
    private static final String MESSAGE_INVALID_SHORT_URL = "Invalid short NUSMods URL provided.";
    private static final String MESSAGE_INVALID_CLASS_TYPE = "Invalid class type";
    private static final String HTTP_METHOD_GET = "GET";
    private static final int HTTP_METHOD_RESPONSE_OK = 200;
    private static final int SEM_NUMBER_INDEX = 0;
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
        try {
            timetable.setExpandedUrl(expandShortTimetableUrl(timetable.value));
            timetable.setCurrentSemester(getSemNumFromExpandedUrl(timetable.getExpandedUrl()));
            timetable.setListOfModules(splitLongTimetableUrl(timetable.getExpandedUrl()));
        } catch (ParseException e) {
            logger.warning(MESSAGE_INVALID_SHORT_URL);
        }
    }

    /**
     * Expands {@code timetableUrl} from a short NUSMods timetable URL to a long NUSMods timetable URL.
     * Returns the expanded URL.
     *
     * @param timetableUrl URL to be modified
     */
    public static String expandShortTimetableUrl(String timetableUrl) throws ParseException {
        checkArgument(Timetable.isValidTimetable(timetableUrl), Timetable.MESSAGE_TIMETABLE_CONSTRAINTS);
        String expandedUrl = null;
        try {
            final URL shortUrl = new URL(timetableUrl);
            final HttpURLConnection urlConnection = (HttpURLConnection) shortUrl.openConnection();
            urlConnection.setInstanceFollowRedirects(false);
            expandedUrl = urlConnection.getHeaderField("location");

            if (expandedUrl.equals(INVALID_SHORT_URL_RESULT)) {
                throw new ParseException(MESSAGE_INVALID_SHORT_URL);
            }
        } catch (MalformedURLException e) {
            logger.warning("URL provided is malformed");
        } catch (IOException e) {
            logger.warning("Failed to open connection");
        }
        return expandedUrl;
    }

    public static int getSemNumFromExpandedUrl(String expandedUrl) {
        requireNonNull(expandedUrl);
        String[] moduleInformation = expandedUrl.split(SPLIT_QUESTION_MARK);
        return Integer.valueOf(moduleInformation[SEM_NUMBER_INDEX]
                .replaceAll(REPLACE_NON_DIGIT_CHARACTERS, ""));
    }

    /**
     * Splits expanded NUSMods timetable URL into the different {@code TimetableModule}s.
     * Expanded timetable URL is in the format ...?[MODULE_CODE]=[LESSON_TYPE]:[CLASS_NUM]&...
     * @param expandedUrl Long url to be split
     * @return ArrayList of {@code TimetableModule}
     */
    public static HashMap<String, TimetableModule> splitLongTimetableUrl(String expandedUrl) {
        String[] moduleInformation = expandedUrl.split(SPLIT_QUESTION_MARK);
        String[] modules = moduleInformation[MODULE_INFORMATION_INDEX].split(SPLIT_AMPERSAND);

        HashMap<String, TimetableModule> listOfModules = new  HashMap<String, TimetableModule>();
        HashMap<String, String> listOfLessons;
        String moduleCode;
        String lessonType;
        String classType;
        String[] lessons;

        for (String currentModule : modules) {
            listOfLessons = new HashMap<String, String>();

            moduleCode = currentModule.split(SPLIT_EQUALS)[MODULE_CODE_INDEX];
            lessons = currentModule.split(SPLIT_EQUALS)[MODULE_CODE_REMAINING_INDEX].split(SPLIT_COMMA);
            for (String currLesson : lessons) {
                lessonType = currLesson.split(SPLIT_COLON)[LESSON_TYPE_INDEX];
                classType = currLesson.split(SPLIT_COLON)[CLASS_TYPE_INDEX];

                try {
                    listOfLessons.put(convertLessonType(lessonType), classType);
                } catch (IllegalValueException e) {
                    logger.warning("Unable to convert lesson type");
                }
            }
            listOfModules.put(moduleCode, new TimetableModule(moduleCode, listOfLessons));
        }
        return listOfModules;
    }

    public static void getModuleInfoFromApi(Timetable timetable) {
        requireNonNull(timetable.getListOfModules());

        String currentModuleSlot;
        Set entrySet = timetable.getListOfModules().entrySet();
        Iterator it = entrySet.iterator();

        while (it.hasNext()) {
            Map.Entry currentModule = (Map.Entry) it.next();
            currentModuleSlot = getJsonContentsFromNusModsApi("2017-2018",
                    Integer.toString(timetable.getCurrentSemester()), currentModule.getKey().toString());
        }
    }

    /**
     * Retrieves json file from NUSMods api and converts to String
     * @param acadYear String representing academic year
     * @param semNum String representing semester number
     * @param moduleCode String representing module code
     * Format: http://api.nusmods.com/[acadYear]/[semNum]/modules/[moduleCode]/timetable.json
     * e.g. http://api.nusmods.com/2017-2018/2/modules/CS3241/timetable.json
     * @return String containing contents of json file
     */
    public static String getJsonContentsFromNusModsApi(String acadYear, String semNum, String moduleCode) {
        String contents = null;
        String nusmodsApiUrlString = "http://api.nusmods.com/" + acadYear + "/" + semNum + "/modules/" + moduleCode
                + "/timetable.json";
        try {
            URL nusmodsApiUrl = new URL(nusmodsApiUrlString);
            HttpURLConnection urlConnection = (HttpURLConnection) nusmodsApiUrl.openConnection();
            urlConnection.setRequestMethod(HTTP_METHOD_GET);
            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HTTP_METHOD_RESPONSE_OK) {
                contents = readStream(urlConnection.getInputStream());
            } else {
                contents = "Error in accessing API - " + readStream(urlConnection.getErrorStream());
            }
        } catch (MalformedURLException e) {
            logger.warning("URL provided is malformed");
        } catch (ProtocolException e) {
            logger.warning("Protocol exception");
        } catch (IOException e) {
            logger.warning("Failed to open connection");
        }
        return contents;
    }

    /**
     * Read the responded result
     * @param inputStream from {@code NUSMODS_API_URL}
     * @return string containing contents of {@code NUSMODS_API_URL}
     * @throws IOException from readLine()
     */
    public static String readStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String inputLine;

        while ((inputLine = reader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        reader.close();
        return stringBuilder.toString();
    }

    /**
     * Parses contents of json file contents result from {@code readStream()}
     * @param contents contents of json file in String
     * @param timetable timetable to set list of modules slots
     * @param moduleCode current module
     * @return
     */
    public static void parseContents(String contents, Timetable timetable, String moduleCode) {
        requireNonNull(timetable.getListOfModules());

        JSONObject jsonObject = null;
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(contents);
            jsonObject = (JSONObject) obj;
        } catch (Exception e) {
            logger.warning("Exception caught in parsing JSONObject");
        }

        JSONArray arrOfClassInformation = null;
        Object object = jsonObject.get("");
        arrOfClassInformation = (JSONArray) object;

        String tempLessonType;
        String tempClassType;
        String tempWeekFreq;
        String tempDay;
        String tempStartTime;
        String tempEndTime;
        String tempVenue;
        HashMap<String, TimetableModule> listOfModules = timetable.getListOfModules();
        TimetableModule timetableModule = listOfModules.get(moduleCode);
        HashMap<String, String> listOfLessons = timetableModule.getListOfLessons();

        ArrayList<TimetableModuleSlot> listOfModuleSlots = new ArrayList<TimetableModuleSlot>();
        for (Object t : arrOfClassInformation) {
            tempLessonType = ((JSONObject) t).get("LessonType").toString();
            tempClassType = ((JSONObject) t).get("ClassNo").toString();
            if (listOfLessons.get(tempLessonType).equals(tempClassType)) {
                tempWeekFreq = ((JSONObject) t).get("WeekText").toString();
                tempDay = ((JSONObject) t).get("DayText").toString();
                tempStartTime = ((JSONObject) t).get("StartTime").toString();
                tempEndTime = ((JSONObject) t).get("EndTime").toString();
                tempVenue = ((JSONObject) t).get("Venue").toString();
                listOfModuleSlots.add(new TimetableModuleSlot(moduleCode, tempLessonType, tempClassType, tempWeekFreq,
                        tempDay, tempVenue, tempStartTime, tempEndTime));
            }
        }
    }

    /**
     * Converts shortened lesson type from URL to long lesson type in API
     * @param lessonType shortened lesson type from URL
     * @return long lesson type in API
     */
    public static String convertLessonType(String lessonType) throws IllegalValueException {
        switch (lessonType) {
        case "LEC":
            return "Lecture";

        case "TUT":
            return "Tutorial";

        case "LAB":
            return "Laboratory";

        case "SEM":
            return "Seminar-Style Module Class";

        case "SEC":
            return "Sectional Teaching";

        case "REC":
            return "Recitation";

        case "TUT2":
            return "Tutorial Type 2";

        case "TUT3":
            return "Tutorial Type 3";

        default:
            throw new IllegalValueException(MESSAGE_INVALID_CLASS_TYPE);
        }
    }
}
