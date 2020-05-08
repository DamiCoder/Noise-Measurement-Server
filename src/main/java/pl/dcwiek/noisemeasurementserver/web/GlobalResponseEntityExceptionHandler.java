package pl.dcwiek.noisemeasurementserver.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.dcwiek.noisemeasurementserver.web.model.response.ExceptionResponse;
import pl.dcwiek.noisemeasurementserver.web.user.UserCredentialsException;
import pl.dcwiek.noisemeasurementserver.web.user.UsernameAlreadyExistsException;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
@Slf4j
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    @Autowired
    public GlobalResponseEntityExceptionHandler(MessageSource messageSource) {
        super();
        this.messageSource = messageSource;
    }

    @ExceptionHandler({UserCredentialsException.class})
    private @ResponseBody ResponseEntity<Object> handleUserCredentialsException(BindException ex, HttpHeaders headers,
                                                          HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),
                ex.getClass().getSimpleName(), "No such user exception");
        log.error(ex.getMessage(), ex);
        return this.handleExceptionInternal(ex, exceptionResponse, headers, status, request);
    }

    @ExceptionHandler({NoSuchAlgorithmException.class})
    private @ResponseBody ResponseEntity<Object> handleNoSuchAlgorithmException(NoSuchAlgorithmException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                NoSuchAlgorithmException.class.getSimpleName(), "Internal server error.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    @ExceptionHandler({UsernameAlreadyExistsException.class})
    private @ResponseBody ResponseEntity<Object> handleUsernameAlreadyExistsException(
            UsernameAlreadyExistsException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),
                UsernameAlreadyExistsException.class.getSimpleName(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),
                ex.getClass().getSimpleName(), generateBindExceptionMessage(ex.getAllErrors()));
        log.error(ex.getMessage(), ex);
        return this.handleExceptionInternal(ex, exceptionResponse, headers, status, request);
    }

    private String generateBindExceptionMessage(List<ObjectError> errors) {
        final StringBuilder stringBuilder = new StringBuilder();
        errors.forEach(it -> {
            if(it.getCode() != null) {
                String errorMessage = messageSource.getMessage(it.getCode(), null, Locale.getDefault());
                if(StringUtils.isNotBlank(errorMessage)) {
                    stringBuilder.append(
                            String.format("[field: '%s'; message: '%s'],", it.getObjectName(), errorMessage));
                }
            }
        });
        return stringBuilder.substring(0, stringBuilder.length()-1);
    }
}
