package com.picpay.file;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.zip.GZIPInputStream;

@Component
public class FileTransferManager {
    private static final Logger LOG = LoggerFactory.getLogger(FileTransferManager.class);
    private static final String GZ_EXTENSION = ".gz";
    private static final String CSV_EXTENSION = ".csv";

    public Path download(DownloadFileRequest downloadFileRequest) {
        try {
            Path tempDir = Files.createTempDirectory(UUID.randomUUID().toString());
            File gzipFile = Files.createTempFile(tempDir, downloadFileRequest.getFileName(), GZ_EXTENSION).toFile();
            LOG.info("Starting download..");
            FileUtils.copyURLToFile(
                    downloadFileRequest.getUrl(),
                    gzipFile);
            LOG.info("Completed download..");
            Path unGZipFile = unGZipFile(gzipFile, downloadFileRequest.getFileName());

            Files.delete(gzipFile.toPath());
            Files.delete(tempDir);

            return unGZipFile;
        } catch (IOException e) {
            throw new RuntimeException("Error while downloading base to import...", e);
        }
    }

    public Path unGZipFile(File gzFile, String decompressedFile) throws IOException {
        Path tempDir = Files.createTempDirectory(UUID.randomUUID().toString());
        File outputFile = Files.createTempFile(tempDir, decompressedFile, CSV_EXTENSION).toFile();
        byte[] buffer = new byte[1024];
        LOG.info("Start unzip file!");
        FileInputStream fileIn = new FileInputStream(gzFile);

        GZIPInputStream gZIPInputStream = new GZIPInputStream(fileIn);

        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

        int bytes_read;

        while ((bytes_read = gZIPInputStream.read(buffer)) > 0) {
            fileOutputStream.write(buffer, 0, bytes_read);
        }

        gZIPInputStream.close();
        fileOutputStream.close();
        gzFile.deleteOnExit();
        LOG.info("The file was decompressed successfully!");
        return outputFile.toPath();
    }

}
