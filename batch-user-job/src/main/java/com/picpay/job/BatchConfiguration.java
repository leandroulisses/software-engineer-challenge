package com.picpay.job;

import com.picpay.user.User;
import com.picpay.user.UserRankOne;
import com.picpay.user.UserRankTwo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.sql.DataSource;

@EnableBatchProcessing
@Configuration
public class BatchConfiguration {

    private static final int SKIP_LIMIT = 10;
    private static final int CHUNK_SIZE = 1000;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job readCSVFilesJob(Step processBase, Step processListOne, Step processListTwo) {
        return jobBuilderFactory
                .get("readCsvBaseJob")
                .incrementer(new RunIdIncrementer())
                .start(processBase)
                .next(processListOne)
                .next(processListTwo)
                .build();
    }

    private TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor("processBaseStep");
        return asyncTaskExecutor;
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<User> writer() {
        return new JdbcBatchItemWriterBuilder<User>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into TB_USER(ID,NAME,USERNAME) values (:id, :name, :username)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Step processBase(FlatFileItemReader<User> readBaseStep, JdbcBatchItemWriter<User> writer) {
        return stepBuilderFactory.get("processBase").<User, User>chunk(CHUNK_SIZE)
                .reader(readBaseStep)
                .faultTolerant()
                .skipLimit(SKIP_LIMIT)
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .writer(writer)
                .taskExecutor(taskExecutor())
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

    @Bean
    @StepScope
    public FlatFileItemReader<UserRankTwo> readListTwo() {
        return new FlatFileItemReaderBuilder<UserRankTwo>()
                .name("readListTwo")
                .resource(new ClassPathResource("data/lista_relevancia_2.txt"))
                .encoding("UTF-8")
                .linesToSkip(1)
                .delimited()
                .delimiter(",")
                .names("userId")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(UserRankTwo.class);
                }})
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<UserRankOne> readListOne() {
        return new FlatFileItemReaderBuilder<UserRankOne>()
                .name("readListOne")
                .resource(new ClassPathResource("data/lista_relevancia_1.txt"))
                .encoding("UTF-8")
                .linesToSkip(1)
                .delimited()
                .delimiter(",")
                .names("userId")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(UserRankOne.class);
                }})
                .build();
    }

    @Bean
    public Step processListOne(FlatFileItemReader<UserRankOne> readListOne, JdbcBatchItemWriter<UserRankOne> rankOneWriter) {
        return stepBuilderFactory.get("processListOne").<UserRankOne, UserRankOne>chunk(CHUNK_SIZE)
                .reader(readListOne)
                .writer(rankOneWriter)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step processListTwo(FlatFileItemReader<UserRankTwo> readListTwo, JdbcBatchItemWriter<UserRankTwo> rankTwoWriter) {
        return stepBuilderFactory.get("processListTwo").<UserRankTwo, UserRankTwo>chunk(CHUNK_SIZE)
                .reader(readListTwo)
                .writer(rankTwoWriter)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<UserRankOne> rankOneWriter() {
        return new JdbcBatchItemWriterBuilder<UserRankOne>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into RELEVANCE (ID,USER_ID,RANK) values (:id, :userId, :rank)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<UserRankTwo> rankTwoWriter() {
        return new JdbcBatchItemWriterBuilder<UserRankTwo>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into RELEVANCE (ID,USER_ID,RANK) values (:id, :userId, :rank)")
                .dataSource(dataSource)
                .build();
    }

}
