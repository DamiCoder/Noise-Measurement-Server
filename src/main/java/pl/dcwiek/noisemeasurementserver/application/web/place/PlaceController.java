package pl.dcwiek.noisemeasurementserver.application.web.place;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dcwiek.noisemeasurementserver.domain.resource.PlaceModel;
import pl.dcwiek.noisemeasurementserver.domain.service.PlaceService;
import pl.dcwiek.noisemeasurementserver.infrastructure.model.constants.UserRole;

import java.util.List;

@Controller
@RequestMapping(value = "/api/place")
@Slf4j
public class PlaceController {

    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }


    @GetMapping("/retrieveAll")
    @PreAuthorize("hasAuthority('" + UserRole.APP_USER_ROLE + "')")
    public ResponseEntity<Object> getPlaces() {

        List<PlaceModel> placeModels = placeService.getPlaces();
        log.info("Sent place models: {}", new Gson().toJson(placeModels));
        return ResponseEntity.ok(placeModels);
    }
}
