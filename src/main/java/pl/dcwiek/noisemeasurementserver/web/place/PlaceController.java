package pl.dcwiek.noisemeasurementserver.web.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.dcwiek.noisemeasurementserver.application.resource.service.PlaceService;
import pl.dcwiek.noisemeasurementserver.database.model.constants.UserRole;
import pl.dcwiek.noisemeasurementserver.domain.resource.PlaceModel;

import java.util.List;

@Controller
@RequestMapping(value = "/api/place")
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
        return ResponseEntity.ok(placeModels);
    }
}
