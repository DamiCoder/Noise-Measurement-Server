package pl.dcwiek.noisemeasurementserver.web.standard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dcwiek.noisemeasurementserver.domain.resource.StandardModel;
import pl.dcwiek.noisemeasurementserver.domain.service.StandardService;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.constants.UserRole;

import java.util.List;

@Controller
@RequestMapping(value = "/api/standard")
public class StandardController {

    private final StandardService standardService;

    @Autowired
    public StandardController(StandardService standardService) {
        this.standardService = standardService;
    }


    @GetMapping("/retrieveAll")
    @PreAuthorize("hasAuthority('" + UserRole.APP_USER_ROLE + "')")
    public ResponseEntity<Object> getStandards() {

        List<StandardModel> standardModels = standardService.getStandards();
        return ResponseEntity.ok(standardModels);
    }
}
