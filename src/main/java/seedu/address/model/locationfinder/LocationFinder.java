package seedu.address.model.locationfinder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author HEARTOFAL1ON
/**
 * Represents a Google Maps location search query
 */
public class LocationFinder {

    public static final String MESSAGE_LOCATION_FINDER_CONSTRAINTS = "A Google Maps location search query"
            + " should be of the following format https://www.google.com/maps/search/?api=1&parameters and"
            + " adhere to the following constraints:\n"
            + "1. The URL should start with https://www.google.com/maps/search/?api=1&\n"
            + "2. The \"parameters\"-part can take any values, and it should not be blank. It also has to be"
            + " a URL-escaped string but Java API has a dedicated URLEncoder class which contains methods to"
            + " convert a string into HTML format.";
    private static final String URL_REGEX = "https://www.google.com/maps/search/?api=1&";

    /*
     * The first character of the parameters must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    private static final String PARAMETERS_VALIDATION_REGEX = "[^\\s].*";

    public static final String QUERY_VALIDATION_REGEX = URL_REGEX + PARAMETERS_VALIDATION_REGEX;
    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param locationQuery A valid locationQuery.
     */
    public LocationFinder(String locationQuery) {
        requireNonNull(locationQuery);
        checkArgument(isValidLocationQuery(locationQuery), MESSAGE_LOCATION_FINDER_CONSTRAINTS);
        this.value = locationQuery;
    }

    /**
     * Returns true if a given string is a valid location query.
     */
    public static boolean isValidLocationQuery(String test) {
        return test.matches(QUERY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocationFinder // instanceof handles nulls
                && this.value.equals(((LocationFinder) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
