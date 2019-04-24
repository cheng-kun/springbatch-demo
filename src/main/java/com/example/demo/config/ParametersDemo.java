package com.example.demo.config;

import java.util.Map;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParametersDemo implements StepExecutionListener {
	
	
	  @Autowired
	    private JobBuilderFactory jobBuilderFactory;

	    @Autowired
	    private StepBuilderFactory stepBuilderFactory;
	    
        private Map<String, JobParameter> parameters;

	    @Bean
	    public Job parameterDemoJob(){
	        return jobBuilderFactory.get("parameterDemoJob")
	        		.start(parastep1())
	        		.build();

	    }

	    @Bean
	    public Step parastep1(){
	        return stepBuilderFactory.get("parastep1").listener(this).tasklet(new Tasklet() {

				@Override
	            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
	                System.out.println(parameters.get("info"));
	                return RepeatStatus.FINISHED;
	            }
	        }).build();
	    }
	    

		@Override
		public void beforeStep(StepExecution stepExecution) {
				
			parameters = stepExecution.getJobParameters().getParameters();
			
		}

		@Override
		public ExitStatus afterStep(StepExecution stepExecution) {
			// TODO Auto-generated method stub
			return null;
		}
	

}
