package com.example.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class ParentDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    private Job childDemoJob1;
    
    @Autowired
    private Job childDemoJob2;
    
    @Autowired
    private JobLauncher launcher;

    @Bean
    public Job parentJobDemoJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return jobBuilderFactory.get("parentJobDemoJob")
        		.start(childJob1(jobRepository, platformTransactionManager))
        		.next(childJob2(jobRepository, platformTransactionManager))
        		.build();
    }

	private Step childJob2(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
		return new JobStepBuilder(new StepBuilder("childJob2()"))
				.job(childDemoJob2).launcher(launcher).repository(jobRepository)
				.transactionManager(platformTransactionManager).build();
			
	}

	private Step childJob1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
		return new JobStepBuilder(new StepBuilder("childJob1()"))
				.job(childDemoJob1).launcher(launcher).repository(jobRepository)
				.transactionManager(platformTransactionManager).build();
	}
	
}
