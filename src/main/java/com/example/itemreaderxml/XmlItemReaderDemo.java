package com.example.itemreaderxml;


import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;


@Configuration
public class XmlItemReaderDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    @Qualifier("flatXmlWriter")
	public ItemWriter<? super Product> flatXmlWriter;

    @Bean
    public Job xmlItemReaderDemoJob(){
        return jobBuilderFactory.get("xmlItemReaderDemoJob")
        		.start(itemReaderXmlStep())
        		.build();
    }

    @Bean
    public Step itemReaderXmlStep(){
        return stepBuilderFactory.get("itemReaderXmlStep").<Product, Product>chunk(2)
        		.reader(read())
//        		.writer(flatXmlWriter)
        		.writer(null)
        		.build();
    }
    
    @Bean
    @StepScope
    public StaxEventItemReader<Product> read() {
    	StaxEventItemReader<Product> reader = new StaxEventItemReader<>();
    	reader.setResource(new ClassPathResource("products.xml"));
    	reader.setFragmentRootElementName("product");
    	
    	XStreamMarshaller uMarshaller = new XStreamMarshaller();
    	
    	Map<String, Class> map = new HashMap<>();
    	map.put("product", Product.class);
    	uMarshaller.setAliases(map);
    	
    	reader.setUnmarshaller(uMarshaller);
    	
    	return reader;
    	
	}
}
