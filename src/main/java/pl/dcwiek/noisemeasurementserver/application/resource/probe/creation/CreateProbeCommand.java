package pl.dcwiek.noisemeasurementserver.application.resource.probe.creation;

import lombok.Data;
import pl.dcwiek.noisemeasurementserver.database.model.validation.GeoPoints;

@Data
public class CreateProbeCommand {

    private final int userId;
    private final Integer result;
    private final Integer placeId;
    private final int typeId;
    @GeoPoints
    private final String location;
    private final String comment;

}
