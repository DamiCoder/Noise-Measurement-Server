package pl.dcwiek.noisemeasurementserver.application.web.probe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProbeRetrievalForm {

    private final Integer number;

    private final Integer pageSize;
}
