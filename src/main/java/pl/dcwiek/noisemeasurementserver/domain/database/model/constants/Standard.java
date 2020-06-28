package pl.dcwiek.noisemeasurementserver.domain.database.model.constants;

public enum Standard {
    LEGAL,
    SCIENTIFIC;

    public static boolean contains(String standardName) {
        for (Standard value : values()) {
            if(value.name().equalsIgnoreCase(standardName)) {
                return true;
            }
        }
        return false;
    }
}
