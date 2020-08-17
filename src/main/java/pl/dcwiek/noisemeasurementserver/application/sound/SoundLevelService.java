package pl.dcwiek.noisemeasurementserver.application.sound;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.util.fft.FFT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
@Slf4j
public class SoundLevelService {

    private AudioDispatcher dispatcher;
    private double amplitudeReferenceValue;
    private final static int FFT_SIZE = 2048;
    final ArrayList<Float> allAmplitudes = new ArrayList<>();

    public void startSoundAnalyzer() throws InterruptedException {
        final int[] counter = {1};
        AudioProcessor FFTProc = new AudioProcessor() {
            final FFT fft = new FFT(FFT_SIZE);
            final float[] amplitudes = new float[FFT_SIZE];

            @Override
            public boolean process(AudioEvent audioEvent) {
                float[] audioBuffer = audioEvent.getFloatBuffer();

                fft.forwardTransform(audioBuffer);
                fft.modulus(audioBuffer, amplitudes);

                Collections.addAll(allAmplitudes, ArrayUtils.toObject(amplitudes));
                log.debug(String.format("%s. Amplitudes  : ", counter[0]) + Arrays.toString(amplitudes));

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
    }

    public void initDispatcher(String audioFilePath, float amplitudeReferenceValue) {
        this.amplitudeReferenceValue = amplitudeReferenceValue;
        dispatcher = AudioDispatcherFactory.fromPipe(audioFilePath, 44100, 4096, 2048);
    }

    public double countDbLevel() {
//        double aRef = amplitudeReferenceValue;
//        double aMeasured = rootMeanSquare(allAmplitudes);
//        double amplitudeRatio = aMeasured/aRef;
//        return 20 * Math.log10(amplitudeRatio);

        double aMeasured = allAmplitudes.stream().mapToDouble(val -> val).average().getAsDouble();
        System.out.println(String.format("A average value: %s", aMeasured));
        double amplitudeRatio = aMeasured/amplitudeReferenceValue;
        return 20 * Math.log10(amplitudeRatio);
    }

    public double rootMeanSquare(ArrayList<Float> nums) {
        double sum = 0.0;
        for (double num : nums)
            sum += num * num;
        return Math.sqrt(sum / nums.size());
    }
}

