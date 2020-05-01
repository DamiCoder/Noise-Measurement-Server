package pl.dcwiek.noisemeasurementserver.security.service;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class ShaService {

    public static final String DEFAULT_INSTANCE = "SHA-256";

    public String hashPassword(String plainString) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance(DEFAULT_INSTANCE);
        byte[] digest = sha.digest(plainString.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(digest);
    }

}
