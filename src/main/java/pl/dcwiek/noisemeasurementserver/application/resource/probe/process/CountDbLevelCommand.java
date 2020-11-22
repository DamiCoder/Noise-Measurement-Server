package pl.dcwiek.noisemeasurementserver.application.resource.probe.process;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;


@Data
@AllArgsConstructor
public class CountDbLevelCommand {

    private final File probe;
    private final float amplitudeReferenceValue;
}
