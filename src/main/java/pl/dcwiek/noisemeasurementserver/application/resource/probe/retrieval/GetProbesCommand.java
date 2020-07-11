package pl.dcwiek.noisemeasurementserver.application.resource.probe.retrieval;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetProbesCommand {

    private final int userId;
    private final Integer number;
    private final Integer pageSize;
}
