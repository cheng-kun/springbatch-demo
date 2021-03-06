package com.example.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class ChildDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job childDemoJob1(){
        return jobBuilderFactory.get("childDemoJob1")
        		.start(job1Step1())
        		.build();
    }

    @Bean
    public Step job1Step1(){
        return stepBuilderFactory.get("job1Step1").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("job1Step1!");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }
    
    @Bean
    public Step job1Step2(){
        return stepBuilderFactory.get("job1Step2").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("job1Step2!");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }
    
    @Bean
    public Step job1Step3(){
        return stepBuilderFactory.get("job1Step3").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("job1Step3!");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }
	
	
}


@Configuration
@EnableBatchProcessing
class ChildDemo2 {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job childDemoJob2(){
        return jobBuilderFactory.get("childDemoJob2")
        		.start(job2Step2())
        		.next(job2Step3())
        		.build();
    }

    @Bean
    public Step job2Step1(){
        return stepBuilderFactory.get("job2Step1").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("job2Step1!");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }
    
    @Bean
    public Step job2Step2(){
        return stepBuilderFactory.get("job2Step2").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("job2Step2!");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }
    
    @Bean
    public Step job2Step3(){
        return stepBuilderFactory.get("job2Step3").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("job2Step3!");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }
	
	
}


