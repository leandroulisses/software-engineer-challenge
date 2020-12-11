package com.picpay.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Path;

@SpringBootTest
class FileTransferManagerTest {

    private static final String URL_BASE = "https://s3.amazonaws.com/careers-picpay/users.csv.gz";
    private static final String FILE_NAME = "base.csv";

    @Autowired
    private FileTransferManager fileTransferManager;

    @Test
    void should_download_file() {
        DownloadFileRequest downloadFileRequest = new DownloadFileRequest(FILE_NAME, URL_BASE);

        Path path = fileTransferManager.download(downloadFileRequest);
        File file = path.toFile();

        Assertions.assertTrue(file.length() > 0);
        file.deleteOnExit();
    }

}