package pl.dcwiek.noisemeasurementserver.web.regulation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dcwiek.noisemeasurementserver.domain.resource.RegulationModel;
import pl.dcwiek.noisemeasurementserver.domain.service.RegulationService;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.constants.UserRole;

import java.util.List;

@Controller
@RequestMapping(value = "/api/regulation")
public class RegulationController {

    private final RegulationService regulationService;

    @Autowired
    public RegulationController(RegulationService regulationService) {
        this.regulationService = regulationService;
    }


    @GetMapping("/retrieveAll")
    @PreAuthorize("hasAuthority('" + UserRole.APP_USER_ROLE + "')")
    public ResponseEntity<Object> getTypes() {

        List<RegulationModel> regulationModels = regulationService.getRegulations();
        return ResponseEntity.ok(regulationModels);
    }
}
