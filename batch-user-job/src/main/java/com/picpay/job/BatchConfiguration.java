package com.picpay.job;

import com.picpay.user.UserSummary;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@EnableBatchProcessing
@Configuration
public class BatchConfiguration {

    private static final int SKIP_LIMIT = 10;
    private static final int CHUNK_SIZE = 100;

    @Autowired
    private MongoItemWriter mongoItemWriter;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job readCSVFilesJob(Step processBase) {
        return jobBuilderFactory
                .get("readCsvBaseJob")
                .incrementer(new RunIdIncrementer())
                .start(processBase)
                .build();
    }

    @Bean
    public Step processBase(FlatFileItemReader<UserSummary> readBaseStep) {
        return stepBuilderFactory.get("processBase").<UserSummary, UserSummary>chunk(CHUNK_SIZE)
                .reader(readBaseStep)
                .faultTolerant()
                .skipLimit(SKIP_LIMIT)
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .writer(mongoItemWriter)
                .taskExecutor(taskExecutor())
                .build();
    }

    private TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor("processBaseStep");
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<UserSummary> readBaseStep(@Value("#{jobParameters['tempFileLocation']}") String fileName) {
        return new FlatFileItemReaderBuilder<UserSummary>()
                .name("enrichDataItemReader")
                .resource(new FileSystemResource(fileName))
                .encoding("UTF-8")
                .linesToSkip(1)
                .delimited()
                .delimiter(",")
                .names("id", "name", "username")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(UserSummary.class);
                }})
                .build();
    }

}
