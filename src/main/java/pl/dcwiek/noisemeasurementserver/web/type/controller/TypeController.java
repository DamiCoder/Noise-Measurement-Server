package pl.dcwiek.noisemeasurementserver.web.type.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dcwiek.noisemeasurementserver.application.resource.service.TypeService;
import pl.dcwiek.noisemeasurementserver.database.model.constants.UserRole;
import pl.dcwiek.noisemeasurementserver.domain.resource.TypeModel;

import java.util.List;

@Controller
@RequestMapping(value = "/api/type")
public class TypeController {

    private final TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }


    @GetMapping("")
    @PreAuthorize("hasAuthority('" + UserRole.APP_USER_ROLE + "')")
    public ResponseEntity<Object> getTypes() {

        List<TypeModel> typeModels = typeService.getTypeModels();
        return ResponseEntity.ok(typeModels);
    }
}
