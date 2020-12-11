package com.picpay.job;

import com.picpay.job.writer.RestItemWriter;
import com.picpay.user.User;
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

@EnableBatchProcessing
@Configuration
public class BatchConfiguration {

    private static final int SKIP_LIMIT = 10;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private RestItemWriter writer;

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
    public Step processBase(FlatFileItemReader<User> readBaseStep) {
        return stepBuilderFactory.get("processBase").<User, User>chunk(5)
                .reader(readBaseStep)
                .faultTolerant()
                .skipLimit(SKIP_LIMIT)
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .writer(writer)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<User> readBaseStep(@Value("#{jobParameters['tempFileLocation']}") String fileName) {
        return new FlatFileItemReaderBuilder<User>()
                .name("enrichDataItemReader")
                .resource(new FileSystemResource(fileName))
                .encoding("UTF-8")
                .linesToSkip(1)
                .delimited()
                .delimiter(",")
                .names("id", "name", "username")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(User.class);
                }})
                .build();
    }

}