package pl.dcwiek.noisemeasurementserver.database.model.constants;

public enum Regulation {
    LEGAL,
    SCIENTIFIC;

    public static boolean contains(String standardName) {
        for (Regulation value : values()) {
            if(value.name().equalsIgnoreCase(standardName)) {
                return true;
            }
        }
        return false;
    }
}
