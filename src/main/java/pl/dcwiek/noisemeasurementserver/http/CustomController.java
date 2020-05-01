package pl.dcwiek.noisemeasurementserver.http;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dcwiek.noisemeasurementserver.http.service.CustomService;

@RestController
@Log
public class CustomController {

    private CustomService customService;

    @Autowired
    public CustomController(CustomService customService) {
        this.customService = customService;
    }

    @GetMapping(value = "/test")
    @PreAuthorize("authentication.authorities.contains(" +
            "new org.springframework.security.core.authority.SimpleGrantedAuthority('ADMIN'))")
    public Authentication test(Authentication authentication) {
        log.info("TestController.test: endpoint called. Principal is: " + authentication.toString());
        return authentication;
    }

    @GetMapping(value = "/test1")
    @PreAuthorize("authentication.authorities.contains(" +
            "new org.springframework.security.core.authority.SimpleGrantedAuthority('USER'))")
    public String test() throws Exception{
        customService.getAllUsers().forEach(it -> log.info(it.toString()));
        log.info("test");
        return "test";
    }
}
