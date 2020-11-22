package pl.dcwiek.noisemeasurementserver.domain.sound;

public interface SoundLevelService {
    double countSoundDbLevel(String audioFilePath, float amplitudeReferenceValue) throws InterruptedException;
}
