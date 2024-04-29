package com.example.config;

import com.example.listener.FirstJobListener;
import com.example.processor.FirstItemProcessor;
import com.example.reader.FirstItemReader;
import com.example.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.ThreadPoolExecutor;

import static ch.qos.logback.classic.spi.ThrowableProxyVO.build;

@Configuration
public class SpringBatchConfiguration {

    @Autowired
    private FirstItemReader firstItemReader;

    @Autowired
    private FirstItemProcessor firstItemProcessor;

    @Autowired
    private FirstItemWriter firstItemWriter;

    @Autowired
    private FirstJobListener firstJobListener;


   /* @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor threadPoolExecutor= new ThreadPoolTaskExecutor();
        threadPoolExecutor.setMaxPoolSize(10);
        threadPoolExecutor.setThreadNamePrefix("spring-batch");
        threadPoolExecutor.initialize();

        return threadPoolExecutor;
    }*/

    @Bean
    public Job firstJob(JobRepository jobRepository, Step step){
        return new JobBuilder("firstJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .listener(firstJobListener)
                .build();

    }


    @Bean
    public Step firstStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("firstStep", jobRepository)
                .<Integer, Long>chunk(2, platformTransactionManager)
                .reader(firstItemReader)
                .processor(firstItemProcessor)
                .writer(firstItemWriter)
                //.taskExecutor(taskExecutor());
                .build();
    }
}
