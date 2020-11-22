package pl.dcwiek.noisemeasurementserver.infrastructure.file;

import org.springframework.stereotype.Service;
import pl.dcwiek.noisemeasurementserver.domain.file.FileService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
class FileServiceImpl implements FileService {


    @Override
    public File saveTemporaryFile(byte[] bytes, String fileExtension) throws IOException {
        String temporaryFilePath = createTemporaryFilePath(fileExtension);
        File tmpFile = new File(temporaryFilePath);
        tmpFile.deleteOnExit();

        OutputStream fileOutputStream = new FileOutputStream(tmpFile);
        fileOutputStream.write(bytes);
        return tmpFile;
    }

    private String createTemporaryFilePath(String fileExtension) {
        final String tmpDir = System.getProperty("java.io.tmpdir");
        StringBuilder filePathBuilder = new StringBuilder();
        filePathBuilder.append(tmpDir);
        filePathBuilder.append("/");
        filePathBuilder.append(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
        filePathBuilder.append(".");
        filePathBuilder.append(fileExtension);
        return filePathBuilder.toString();
    }
}
