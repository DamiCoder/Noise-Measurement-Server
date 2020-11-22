package pl.dcwiek.noisemeasurementserver.infrastructure.sound;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.domain.sound.SoundLevelService;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class SoundLevelServiceImpl implements SoundLevelService {

    private double amplitudeReferenceValue;
    private List<Float> aggregatedAmplitudes;
    private final static int TARGET_SAMPLE_RATE = 44100;
    private final static int AUDIO_BUFFER_SIZE = 4096;
    private final static int BUFFER_OVERLAP = 2048;

    public double countSoundDbLevel(String audioFilePath, float amplitudeReferenceValue) throws InterruptedException {
        this.amplitudeReferenceValue = amplitudeReferenceValue;
        aggregatedAmplitudes = new LinkedList<>();

        AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe(audioFilePath, TARGET_SAMPLE_RATE, AUDIO_BUFFER_SIZE, BUFFER_OVERLAP);
        AudioProcessor FFTProc = new AudioProcessor() {
            private int amplitudeSeriesNumber = 0;
            @Override
            public boolean process(AudioEvent audioEvent) {
                ++amplitudeSeriesNumber;
                float[] audioBuffer = audioEvent.getFloatBuffer();
                Collections.addAll(aggregatedAmplitudes, ArrayUtils.toObject(audioBuffer));
                log.trace("{}. Amplitudes: {}",amplitudeSeriesNumber, Arrays.toString(audioBuffer));
                return false;
            }
            @Override
            public void processingFinished() {
                double dBLevel = countDbLevel();
                log.info("Aggregated amplitudes count: {}", aggregatedAmplitudes.size());
                log.info("Probe processing finished. Used sound reference value: {}, processed dB level: {}", amplitudeReferenceValue, dBLevel);
            }
        };
        dispatcher.addAudioProcessor(FFTProc);
        Thread audioThread = new Thread(dispatcher);
        audioThread.start();
        audioThread.join();

        return countDbLevel();
    }

    public double countDbLevel() {
        double aMeasured = calculateRMS(aggregatedAmplitudes);
        log.debug(String.format("Calculated average amplitude value: %s", aMeasured));
        double amplitudeRatio = aMeasured/amplitudeReferenceValue;
        return DbUtils.convertTodB(amplitudeRatio);
    }

    public static double calculateRMS(List<Float> floatBuffer){
        double rms = 0.0;
        for (Float aFloat : floatBuffer) {
            rms += aFloat * aFloat;
        }
        rms = rms / (double) floatBuffer.size();
        rms = Math.sqrt(rms);
        return rms;
    }
}

