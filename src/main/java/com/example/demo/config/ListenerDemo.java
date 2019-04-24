package com.example.demo.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.listener.MyChunkListener;
import com.example.demo.listener.MyJobListener;

@Configuration
@EnableBatchProcessing
public class ListenerDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job listnerDemoJob(){
        return jobBuilderFactory.get("listnerDemoJob")
        		.start(listnerStep1())
        		.listener(new MyJobListener())
        		.build();
    }

    @Bean
    public Step listnerStep1(){
        return stepBuilderFactory.get("listnerStep1")
        		.<String, String>chunk(2).faultTolerant()
        		.listener(new MyChunkListener())
        		.reader(read())
        		.writer(write()).build();
    }

    @Bean
	public ItemWriter<String> write() {
		
		return new ItemWriter<String>() {

			@Override
			public void write(List<? extends String> items) throws Exception {

				for (String string : items) {
					System.out.println(string);
				}
			}
		};
	}

    @Bean
	public ItemReader<String> read() {
	
		return new ListItemReader<String>(Arrays.asList("a", "b", "c", "d"));
	}
}
