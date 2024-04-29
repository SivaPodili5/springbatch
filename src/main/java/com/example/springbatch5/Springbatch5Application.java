package com.example.springbatch5;

import org.springframework.aop.SpringProxy;
import org.springframework.aop.framework.Advised;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.DecoratingProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.*;

@SpringBootApplication
@ComponentScan({"com.example.config", "com.example.reader",
        "com.example.writer", "com.example.processor", "com.example.listener"})
public class Springbatch5Application implements CommandLineRunner {

    /*static class Hints  implements RuntimeHintsRegistrar{

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.proxies().registerJdkProxy(JobOperator.class, SpringProxy.class, Advised.class, DecoratingProxy.class);

        }
    }*/

    private JobLauncher jobLauncher;

    private Job job;

    public static void main(String[] args) {
        
        SpringApplication.run(Springbatch5Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        invokeJob(job);
    }

    public void invokeJob(Job job) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        Map<String, JobParameter<?>> parameters = new HashMap<>();
        parameters.put("timsStamp", new JobParameter<>(System.currentTimeMillis(), Long.class));
        JobParameters jobParameters = new JobParameters(parameters);
        jobLauncher.run(job, jobParameters);
    }

/*
    @Bean
    ApplicationRunner runner(JobLauncher jobLauncher, Job job){
        return args -> {
            var JobParameters= new JobParametersBuilder()
                  //  .addString("uuid", UUID.randomUUID().toString())
                    .addDate("date", new Date())
                    .toJobParameters();
            var run=jobLauncher.run(job, JobParameters);
            var instanceId=run.getJobInstance().getInstanceId();
            System.out.println("Instance Id: "+instanceId);
        };
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @StepScope
    Tasklet tasklet(@Value("#{jobParameters['date']}") Date date) {
        return ((contribution, chunkContext) ->{
            System.out.println("date is:" +date);
              return   RepeatStatus.FINISHED;
        });
    }

    @Bean
    Job job(JobRepository jobRepository, Step step){
        return new JobBuilder("job", jobRepository)
                .start(step)
                .build();
    }

    @Bean()
    Step step1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, Tasklet tasklet){
        return new StepBuilder("step1", jobRepository)
                .tasklet(tasklet, platformTransactionManager)

                .build();
    }

    @Bean
    Step csvToDb(JobRepository  jobRepository, PlatformTransactionManager  platformTransactionManager){
        return new StepBuilder("csvToDb", jobRepository)
                .<String, String>chunk(2,platformTransactionManager)
                .reader(new ItemReader<String>() {
                    @Override
                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        return null;
                    }
                })
                .writer(new ItemWriter<String>() {

                    @Override
                    public void write(Chunk<? extends String> chunk) throws Exception {
                    var onehundredRecords=  chunk.getItems();
                    System.out.println(onehundredRecords);
                    }
                })
                .build();
    }
*/

}
