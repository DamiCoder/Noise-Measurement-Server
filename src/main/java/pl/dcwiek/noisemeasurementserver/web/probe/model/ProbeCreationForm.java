package pl.dcwiek.noisemeasurementserver.web.probe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.dcwiek.noisemeasurementserver.database.model.validation.GeoPoints;

@Getter
@AllArgsConstructor
public class ProbeCreationForm {
    @GeoPoints
    private final String location;

    private final Integer placeId;

    private final Integer typeId;

    private final Integer result;

    private final String comment;

    private final Integer userRating;
}
