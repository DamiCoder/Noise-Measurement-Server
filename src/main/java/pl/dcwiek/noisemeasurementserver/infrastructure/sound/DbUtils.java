package pl.dcwiek.noisemeasurementserver.infrastructure.sound;

class DbUtils {

    public static double convertTodB(double value) {
        return 20 * Math.log10(value);
    }
}
