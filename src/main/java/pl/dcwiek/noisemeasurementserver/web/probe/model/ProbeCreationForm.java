package pl.dcwiek.noisemeasurementserver.web.probe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.dcwiek.noisemeasurementserver.domain.database.model.validation.GeoPoints;

@Getter
@AllArgsConstructor
public class ProbeCreationForm {
    @GeoPoints
    private final String location;

    private final String place;

    private final String standard;

    private final Integer result;

    private final String comment;

    private final long createdDate;
}
