package pl.dcwiek.noisemeasurementserver.infrastructure.sound;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.domain.sound.SoundLevelService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class SoundLevelServiceImpl implements SoundLevelService {

    private double amplitudeReferenceValue;
    private final static int FFT_SIZE = 2048;
    final ArrayList<Float> allAmplitudes = new ArrayList<>();

    public double countSoundDbLevel(String audioFilePath, float amplitudeReferenceValue) throws InterruptedException {
        this.amplitudeReferenceValue = amplitudeReferenceValue;
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe(audioFilePath, 44100, 4096, 2048);

        final int[] counter = {1};
        AudioProcessor FFTProc = new AudioProcessor() {
//            final FFT fft = new FFT(FFT_SIZE);
//            final float[] amplitudes = new float[FFT_SIZE];
            @Override
            public boolean process(AudioEvent audioEvent) {
                float[] audioBuffer = audioEvent.getFloatBuffer();

//                fft.forwardTransform(audioBuffer);
//                fft.modulus(audioBuffer, amplitudes);

                Collections.addAll(allAmplitudes, ArrayUtils.toObject(audioBuffer));
                log.debug(String.format("%s. Amplitudes  : ", counter[0]) + Arrays.toString(audioBuffer));

                counter[0]++;
                return false;
            }
            @Override
            public void processingFinished() {
                double dBLevel = countDbLevel();
                log.info("Probe processing finished. Used reference value: {}, processed dB level: {}", amplitudeReferenceValue, dBLevel);
            }
        };
        dispatcher.addAudioProcessor(FFTProc);
        Thread audioThreadOne = new Thread(dispatcher);
        audioThreadOne.start();
        audioThreadOne.join();

        return countDbLevel();
    }

    public double countDbLevel() {
        double aMeasured = calculateRMS(allAmplitudes);
        log.info(String.format("An average signal value: %s", aMeasured));
        double amplitudeRatio = aMeasured/amplitudeReferenceValue;
        return 20 * Math.log10(amplitudeRatio);
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

//    public double rootMeanSquare(ArrayList<Float> nums) {
//        double sum = 0.0;
//        for (double num : nums)
//            sum += num * num;
//        return Math.sqrt(sum / nums.size());
//    }
}

