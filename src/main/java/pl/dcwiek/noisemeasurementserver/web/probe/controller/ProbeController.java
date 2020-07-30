package pl.dcwiek.noisemeasurementserver.web.probe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dcwiek.noisemeasurementserver.application.resource.probe.creation.CreateProbeCommand;
import pl.dcwiek.noisemeasurementserver.application.resource.probe.creation.CreateProbeService;
import pl.dcwiek.noisemeasurementserver.application.resource.probe.retrieval.GetProbeService;
import pl.dcwiek.noisemeasurementserver.application.resource.probe.retrieval.GetProbesCommand;
import pl.dcwiek.noisemeasurementserver.application.resource.service.ServiceException;
import pl.dcwiek.noisemeasurementserver.database.model.constants.UserRole;
import pl.dcwiek.noisemeasurementserver.domain.resource.ProbeModel;
import pl.dcwiek.noisemeasurementserver.security.model.AppUser;
import pl.dcwiek.noisemeasurementserver.web.probe.model.ProbeCreationForm;
import pl.dcwiek.noisemeasurementserver.web.probe.model.ProbeRetrievalForm;

import java.util.List;

@Controller
@RequestMapping(value = "/api/probe")
public class ProbeController {

    private final CreateProbeService createProbeService;
    private final GetProbeService getProbeService;

    private final Validator probeCreationFormValidator;
    private final Validator probeRetrievalFormValidator;

    @Autowired
    public ProbeController(CreateProbeService createProbeService,
                           GetProbeService getProbeService, @Qualifier("probeCreationFormValidator") Validator probeCreationFormValidator,
                           @Qualifier("probeRetrievalFormValidator") Validator probeRetrievalFormValidator) {
        this.createProbeService = createProbeService;
        this.getProbeService = getProbeService;
        this.probeCreationFormValidator = probeCreationFormValidator;
        this.probeRetrievalFormValidator = probeRetrievalFormValidator;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('" + UserRole.APP_USER_ROLE + "')")
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
                probeCreationForm.getComment(),
                probeCreationForm.getUserRating());

        ProbeModel probe = createProbeService.createProbe(command);
        return ResponseEntity.ok(probe);
    }

    @GetMapping("/retrieve")
    @PreAuthorize("hasAuthority('" + UserRole.APP_USER_ROLE + "')")
    public ResponseEntity<Object> getUserProbes(
            @RequestBody ProbeRetrievalForm probeRetrievalForm,
            BindingResult bindingResult,
            Authentication authentication) throws ServiceException, BindException {
        ValidationUtils.invokeValidator(probeRetrievalFormValidator, probeRetrievalForm, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        AppUser appUser = (AppUser) authentication.getPrincipal();

        GetProbesCommand command = new GetProbesCommand(
                appUser.getId(),
                probeRetrievalForm.getNumber(),
                probeRetrievalForm.getPageSize());

        List<ProbeModel> probeModels = getProbeService.getUserProbeModels(command);

        return ResponseEntity.ok(probeModels);
    }
}
