package pl.dcwiek.noisemeasurementserver.database.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.database.model.PlaceEntity;
import pl.dcwiek.noisemeasurementserver.database.model.ProbeEntity;
import pl.dcwiek.noisemeasurementserver.database.model.StandardEntity;
import pl.dcwiek.noisemeasurementserver.database.model.UserEntity;
import pl.dcwiek.noisemeasurementserver.database.model.mapper.ProbeMapper;
import pl.dcwiek.noisemeasurementserver.domain.DataMissingException;
import pl.dcwiek.noisemeasurementserver.domain.NoSuchUserException;
import pl.dcwiek.noisemeasurementserver.domain.resource.ProbeModel;
import pl.dcwiek.noisemeasurementserver.domain.resource.repository.ProbeRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class ProbeRepositoryImpl implements ProbeRepository {

    private final ProbeEntityRepository probeEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final PlaceEntityRepository placeEntityRepository;
    private final StandardEntityRepository standardEntityRepository;

    @Autowired
    public ProbeRepositoryImpl(ProbeEntityRepository probeEntityRepository,
                               UserEntityRepository userEntityRepository,
                               PlaceEntityRepository placeEntityRepository,
                               StandardEntityRepository standardEntityRepository) {
        this.probeEntityRepository = probeEntityRepository;
        this.userEntityRepository = userEntityRepository;
        this.placeEntityRepository = placeEntityRepository;
        this.standardEntityRepository = standardEntityRepository;
    }

    @Override
    public List<ProbeModel> findByUserIdAndOrderByCreatedDate(int userId, Integer number, Integer pageSize) throws NoSuchUserException {
        UserEntity user = userEntityRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new NoSuchUserException("User with provided ID doesn't exist");
        }
        Pageable pageable;
        if(number == null || pageSize == null) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(number, pageSize);
        }

        List<ProbeEntity> probeEntities = probeEntityRepository.findByUserOrderByCreatedDateDesc(user, pageable).getContent();

        return probeEntities.stream()
                .map(ProbeMapper::mapEntityToModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProbeModel createProbeModel(String location, Integer placeId, int userId, Integer result, List<Integer> standardIds, String comment, LocalDateTime createdDate, Integer userRating) throws NoSuchUserException, DataMissingException {
        PlaceEntity place = placeEntityRepository.findById(placeId).orElse(null);

        if(place == null) {
            throw new DataMissingException("There is no place with provided ID");
        }

        Set<StandardEntity> standards = new HashSet<>();
        for (Integer standardId : standardIds) {
            StandardEntity standard = standardEntityRepository.findById(standardId).orElse(null);
            if(standard == null) {
                throw new DataMissingException("There is no standard with provided ID");
            } else {
                standards.add(standard);
            }
        }

        UserEntity user = userEntityRepository.findById(userId).orElse(null);
        if(user == null) {
            throw new NoSuchUserException("There is no user with provided ID");
        }

        ProbeEntity probe = probeEntityRepository.save(new ProbeEntity(
                0,
                location,
                place,
                user,
                result,
                standards,
                comment,
                createdDate,
                userRating));

        return ProbeMapper.mapEntityToModel(probe);
    }
}
