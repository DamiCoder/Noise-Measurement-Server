package pl.dcwiek.noisemeasurementserver.web.probe.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import pl.dcwiek.noisemeasurementserver.application.resource.probe.creation.CreateProbeCommand;
import pl.dcwiek.noisemeasurementserver.application.resource.probe.creation.CreateProbeService;
import pl.dcwiek.noisemeasurementserver.application.resource.probe.process.CountDbLevelCommand;
import pl.dcwiek.noisemeasurementserver.application.resource.probe.process.ProbeProcessingService;
import pl.dcwiek.noisemeasurementserver.application.resource.probe.retrieval.GetProbeService;
import pl.dcwiek.noisemeasurementserver.application.resource.probe.retrieval.GetProbesCommand;
import pl.dcwiek.noisemeasurementserver.domain.resource.ProbeModel;
import pl.dcwiek.noisemeasurementserver.domain.service.ServiceException;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.constants.UserRole;
import pl.dcwiek.noisemeasurementserver.security.model.AppUser;
import pl.dcwiek.noisemeasurementserver.web.probe.model.ProbeCreationForm;
import pl.dcwiek.noisemeasurementserver.web.probe.model.ProbeRetrievalForm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Controller
@RequestMapping(value = "/api/probe")
@Slf4j
public class ProbeController {

    private final CreateProbeService createProbeService;
    private final GetProbeService getProbeService;
    private final ProbeProcessingService probeProcessingService;

    private final Validator probeCreationFormValidator;
    private final Validator probeRetrievalFormValidator;

    @Autowired
    public ProbeController(CreateProbeService createProbeService,
                           GetProbeService getProbeService,
                           ProbeProcessingService probeProcessingService,
                           @Qualifier("probeCreationFormValidator") Validator probeCreationFormValidator,
                           @Qualifier("probeRetrievalFormValidator") Validator probeRetrievalFormValidator) {
        this.createProbeService = createProbeService;
        this.getProbeService = getProbeService;
        this.probeProcessingService = probeProcessingService;
        this.probeCreationFormValidator = probeCreationFormValidator;
        this.probeRetrievalFormValidator = probeRetrievalFormValidator;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('" + UserRole.APP_USER_ROLE + "')")
    public ResponseEntity<Object> create(@RequestBody ProbeCreationForm probeCreationForm,
                                         BindingResult bindingResult,
                                         Authentication authentication) throws ServiceException, BindException {

        log.info(new Gson().toJson(probeCreationForm));
        ValidationUtils.invokeValidator(probeCreationFormValidator, probeCreationForm, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        AppUser appUser = (AppUser) authentication.getPrincipal();
        CreateProbeCommand command = new CreateProbeCommand(
                appUser.getId(),
                probeCreationForm.getResult(),
                probeCreationForm.getPlaceId(),
                probeCreationForm.getRegulationId(),
                probeCreationForm.getStandardIds(),
                probeCreationForm.getLocation(),
                probeCreationForm.getComment(),
                probeCreationForm.getUserRating());

        ProbeModel probe = createProbeService.createProbe(command);
        return ResponseEntity.ok(probe);
    }

    @PostMapping("/retrieveAll")
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

    @PostMapping(value = "/process/dbLevel", consumes = "multipart/form-data")
    @PreAuthorize("hasAuthority('" + UserRole.APP_USER_ROLE + "')")
    public ResponseEntity<Object> processProbe(@RequestParam("probe") MultipartFile multipart,
                                               @RequestParam("referenceValue") float referenceValue) throws IOException, ServiceException {
        String tmpDir = System.getProperty("java.io.tmpdir");
        File tmpFile = new File(String.format("%s/%s.mp3", tmpDir, LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()));
        tmpFile.deleteOnExit();

        OutputStream fileOutputStream = new FileOutputStream(tmpFile);
        fileOutputStream.write(multipart.getBytes());

        CountDbLevelCommand countDbLevelCommand = new CountDbLevelCommand(tmpFile, referenceValue);
        double dbLevel = probeProcessingService.countDbLevel(countDbLevelCommand);

        return ResponseEntity.ok(dbLevel);
    }

    @Bean(name="multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(1000*1000);
        return commonsMultipartResolver;
    }
}
