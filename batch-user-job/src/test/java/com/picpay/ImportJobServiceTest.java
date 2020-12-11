package com.picpay;

import com.picpay.file.FileTransferManager;
import com.picpay.job.ImportJobService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Path;

@SpringBootTest
class ImportJobServiceTest {

    private static final String URL_BASE = "https://s3.amazonaws.com/careers-picpay/users.csv.gz";
    private static final String FILE_NAME = "base.csv";

    @Value("classpath:users.csv")
    Resource resourceFile;

    @Autowired
    private ImportJobService jobService;

    @MockBean
    private FileTransferManager manager;

    @Test
    void should_execute_job() throws IOException {
        Path path = resourceFile.getFile().toPath();
        Mockito.when(manager.download(Mockito.any())).thenReturn(path);
        jobService.execute(FILE_NAME, URL_BASE);
    }
}