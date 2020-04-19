package pl.dcwiek.noisemeasurementserver.http.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.model.User;
import pl.dcwiek.noisemeasurementserver.repo.UserRepository;

import java.util.List;

@Service
public class CustomService {

    private UserRepository userRepository;

    @Autowired
    CustomService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
