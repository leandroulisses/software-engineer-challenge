package com.picpay.job;

import com.picpay.file.DownloadFileRequest;
import com.picpay.file.FileTransferManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.UUID;

@Service
public class ImportJobService {

    private static final Logger LOG = LoggerFactory.getLogger(ImportJobService.class);
    private final FileTransferManager fileTransferManager;
    private final JobLauncher jobLauncher;
    private final Job job;

    public ImportJobService(FileTransferManager fileTransferManager, JobLauncher jobLauncher, Job job) {
        this.fileTransferManager = fileTransferManager;
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    public void execute(String fileName, String url) {
        try {
            DownloadFileRequest downloadFileRequest = new DownloadFileRequest(fileName, url);
            Path path = fileTransferManager.download(downloadFileRequest);
            String executionId = UUID.nameUUIDFromBytes(path.toString().getBytes()).toString();
            LOG.info("Starting execution for file : {}", path);
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString(JobParameterKey.IMPORT_ID.getValue(), executionId)
                    .addString(JobParameterKey.TEMP_FILE_LOCATION.getValue(), path.toString(), Boolean.FALSE)
                    .toJobParameters();

            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            String errorMessage = "Error on launching Job Process: ";
            LOG.error(errorMessage, e.getMessage());
        }
    }

}
