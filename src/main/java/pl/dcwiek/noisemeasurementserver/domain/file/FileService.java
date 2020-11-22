package pl.dcwiek.noisemeasurementserver.domain.file;

import java.io.File;
import java.io.IOException;

public interface FileService {

    File saveTemporaryFile(byte[] bytes, String fileExtension) throws IOException;
}
