package pl.dcwiek.noisemeasurementserver.application.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private int errorCode;
    private String exceptionType;
    private String message;
}
