package com.example.itemReader;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemReaderDemo {
	
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobDemoJob(){
        return jobBuilderFactory.get("jobDemoJob")
        		.start(step1())
        		.build();
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1").<String, String>chunk(2)
        		.reader(read())
        		.writer(item -> {
        			for (String string : item) {
						System.out.println(string + "...");
					}
        		}).build();
    }
    
    @Bean
    public MyReader read() {
    	List<String> data = Arrays.asList("cat", "dog","fox");
    	
    	return new MyReader(data);
	}

}
