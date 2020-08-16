package pl.dcwiek.noisemeasurementserver.application.sound;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.util.fft.FFT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

@Service
@Slf4j
public class SoundLevelService {

    private AudioDispatcher dispatcher;
    private double maxAmplitude = 0;
    private final static int FFT_SIZE = 2048;

    public void startSoundAnalyzer(float amplitudeReferenceValue) throws InterruptedException {
        final int[] counter = {1};
        AudioProcessor FFTProc = new AudioProcessor() {
            final FFT fft = new FFT(FFT_SIZE);
            final float[] amplitudes = new float[FFT_SIZE];

            @Override
            public boolean process(AudioEvent audioEvent) {
                float[] audioBuffer = audioEvent.getFloatBuffer();

                fft.forwardTransform(audioBuffer);
                fft.modulus(audioBuffer, amplitudes);

                DoubleStream doubleStream = IntStream
                        .range(0, amplitudes.length)
                        .mapToDouble(i -> amplitudes[i]);

                double tempMaxValue = doubleStream.max().orElse(0);

                if (tempMaxValue > maxAmplitude) {
                    maxAmplitude = tempMaxValue;
                }
                log.debug(String.format("%s. Amplitudes  : ", counter[0]) + Arrays.toString(amplitudes));
                counter[0]++;
                return false;
            }
            @Override
            public void processingFinished() {
                double dBLevel = countDbLevel();
                log.info("Probe processing finished. Max amplitude: {}; used reference value: {}, processed dB level: {}", maxAmplitude, amplitudeReferenceValue, dBLevel);
            }
        };
        dispatcher.addAudioProcessor(FFTProc);
        Thread audioThreadOne = new Thread(dispatcher);
        audioThreadOne.start();
        audioThreadOne.join();
    }

    public void initDispatcher(String audioFilePath) {
        dispatcher = AudioDispatcherFactory.fromPipe(audioFilePath, 44100, 4096, 2048);
    }



    public double countDbLevel() {
        double aRef = 0.0070536;
        double aMeasured = maxAmplitude;
        double amplitudeRatio = aMeasured/aRef;
        return 20 * Math.log10(amplitudeRatio);
    }

//    public double countARef() {
//        double a = maxAmplitude;
//        double dB = 64.4;
//        double divisor = Math.pow(10d, dB/20d);
//        return a/divisor;
//    }
}

