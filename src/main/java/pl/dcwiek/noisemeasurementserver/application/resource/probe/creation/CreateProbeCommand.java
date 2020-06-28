package pl.dcwiek.noisemeasurementserver.application.resource.probe.creation;

import lombok.Data;
import pl.dcwiek.noisemeasurementserver.domain.database.model.validation.GeoPoints;

@Data
public class CreateProbeCommand {

    private final long userId;
    private final Integer result;
    private final String place;
    private final String standard;
    private final long createdDate;
    @GeoPoints
    private final String location;
    private final String comment;

}
