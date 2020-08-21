package pl.dcwiek.noisemeasurementserver.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import pl.dcwiek.noisemeasurementserver.infrastructure.security.model.AccessError;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e)
            throws ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            ObjectMapper mapper = new ObjectMapper();
            AccessError response = new AccessError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            mapper.writeValue(httpServletResponse.getOutputStream(), response);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}
