package pl.dcwiek.noisemeasurementserver.web.util;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailUtils {

    public static final Pattern EMAIL_PATTERN = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");


    public boolean isEmail(String allegedEmail) {
        Matcher mat = EMAIL_PATTERN.matcher(allegedEmail);
        return mat.matches();
    }
}
