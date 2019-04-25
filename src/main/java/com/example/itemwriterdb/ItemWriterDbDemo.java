package com.example.itemwriterdb;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemWriterDbDemo {

	
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("flatFileReader")
	private ItemReader<? extends Product> flatFileReader;

    @Autowired
    @Qualifier("itemwriterdb")
	private ItemWriter<? super Product> itemwriterdb;

    @Bean
    public Job itemWriterDbDemoJob(){
        return jobBuilderFactory.get("itemWriterDbDemoJob")
        		.start(itemWriterDbDemoStep1())
        		.build();
    }

    @Bean
    public Step itemWriterDbDemoStep1(){
        return stepBuilderFactory.get("itemWriterDbDemoStep1").<Product, Product>chunk(2)
        		.reader(flatFileReader).writer(itemwriterdb).build();
    }
    
	
	
}
