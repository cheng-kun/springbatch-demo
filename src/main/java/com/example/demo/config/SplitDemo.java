package com.example.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
public class SplitDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job splitDemoJob(){
        return jobBuilderFactory.get("splitDemoJob")
        		.start(splitDemoFlow1())
        		.split(new SimpleAsyncTaskExecutor())
        		.add(splitDemoFlow2()).end().build();
    }

    @Bean
    public Step splitDemostep1(){
        return stepBuilderFactory.get("splitDemostep1").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("splitDemostep1!");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }
    
    @Bean
    public Step splitDemostep2(){
        return stepBuilderFactory.get("splitDemostep2").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("splitDemostep2!");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }
    
    @Bean
    public Step splitDemostep3(){
        return stepBuilderFactory.get("splitDemostep3").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("splitDemostep3!");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }
    
    @Bean
    public Flow splitDemoFlow1() {
    	return new FlowBuilder<Flow>("splitDemoFlow1")
    			.start(splitDemostep1())
    			.next(splitDemostep2())
    			.build();
    }
    
    @Bean
    public Flow splitDemoFlow2() {
    	return new FlowBuilder<Flow>("splitDemoFlow2")
    			.start(splitDemostep1())
    			.next(splitDemostep3())
    			.build();
    }
	
}
