package seedu.address.model.locationfinder;

import java.util.HashMap;

//@@author HEARTOFAL1ON
/**
 * Represents a location query to the Google Maps http server which helps to get a location result
 */
public class LocationQuery {

    private String locationQuery;
    private HashMap<String, String> locationResult; // Key is locationQuery type, Value is locationResult type

    public LocationQuery(String locationQuery, HashMap<String, String> locationResult) {
        this.locationQuery = locationQuery;
        this.locationResult = locationResult;
    }

    public String getLocationQuery() {
        return locationQuery;
    }

    public HashMap<String, String> getLocationResult() {
        return locationResult;
    }

    @Override
    public boolean equals(Object other) {
        return (other == this // short circuit if same object
                || (other instanceof LocationQuery // instanceof handles nulls
                && this.locationQuery.equals(((LocationQuery) other).locationQuery)
                && this.locationResult.equals(((LocationQuery) other).locationResult))); // state check
    }

}
