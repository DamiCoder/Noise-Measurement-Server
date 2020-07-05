package pl.dcwiek.noisemeasurementserver.web.probe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dcwiek.noisemeasurementserver.application.resource.probe.creation.CreateProbeCommand;
import pl.dcwiek.noisemeasurementserver.application.resource.probe.creation.CreateProbeService;
import pl.dcwiek.noisemeasurementserver.application.resource.service.ServiceException;
import pl.dcwiek.noisemeasurementserver.domain.resource.ProbeModel;
import pl.dcwiek.noisemeasurementserver.security.model.AppUser;
import pl.dcwiek.noisemeasurementserver.web.probe.model.ProbeCreationForm;

@Controller
@RequestMapping(value = "/api")
public class ProbeController {

    private final CreateProbeService createProbeService;

    private final Validator probeCreationFormValidator;

    @Autowired
    public ProbeController(CreateProbeService createProbeService, @Qualifier("probeCreationFormValidator") Validator probeCreationFormValidator) {
        this.createProbeService = createProbeService;
        this.probeCreationFormValidator = probeCreationFormValidator;
    }

    @PostMapping("/probe/upload")
    public ResponseEntity<Object> create(@RequestBody ProbeCreationForm probeCreationForm, BindingResult bindingResult, Authentication authentication) throws ServiceException, BindException {
        ValidationUtils.invokeValidator(probeCreationFormValidator, probeCreationForm, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        AppUser appUser = (AppUser) authentication.getPrincipal();
        CreateProbeCommand command = new CreateProbeCommand(
                appUser.getId(),
                probeCreationForm.getResult(),
                probeCreationForm.getPlaceId(),
                probeCreationForm.getTypeId(),
                probeCreationForm.getLocation(),
                probeCreationForm.getComment());

        ProbeModel probe = createProbeService.createProbe(command);
        return ResponseEntity.ok(probe);
    }
}
