package com.picpay.file;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;

@Component
public class FileTransferManager {
    private static final Logger LOG = LoggerFactory.getLogger(FileTransferManager.class);
    private static final String GZ_EXTENSION = ".gz";

    public Path download(DownloadFileRequest downloadFileRequest) {
        String gzipFileName = buildGzipFileName(downloadFileRequest);
        File gzipFile = new File(gzipFileName);
        try {
            LOG.info("Starting download..");
            FileUtils.copyURLToFile(
                    downloadFileRequest.getUrl(),
                    gzipFile);
            LOG.info("Completed download..");
            return unGZipFile(gzipFile, downloadFileRequest.getFileName());
        } catch (IOException e) {
            throw new RuntimeException("Error while downloading base to import...");
        }
    }

    private String buildGzipFileName(DownloadFileRequest downloadFileRequest) {
        return downloadFileRequest.getFileName().concat(GZ_EXTENSION);
    }

    public Path unGZipFile(File gzFile, String decompressedFile) throws IOException {
        File outputFile = new File(decompressedFile);
        byte[] buffer = new byte[1024];
        LOG.info("Start unzip file!");
        FileInputStream fileIn = new FileInputStream(gzFile);

        GZIPInputStream gZIPInputStream = new GZIPInputStream(fileIn);

        FileOutputStream fileOutputStream = new FileOutputStream(decompressedFile);

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
