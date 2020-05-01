package pl.dcwiek.noisemeasurementserver.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccessError {
    private Integer errorCode;
    private String errorMessage;
}
