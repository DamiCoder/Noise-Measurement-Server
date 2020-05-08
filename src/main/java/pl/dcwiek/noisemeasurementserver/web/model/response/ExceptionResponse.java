package pl.dcwiek.noisemeasurementserver.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private int errorCode;
    private String exceptionType;
    private String message;
}
