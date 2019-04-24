package com.example.multireader;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.validation.BindException;

import com.example.itemreaderfile.ItemReaderFileDemo;
import com.example.itemreaderfile.Product;


@Configuration
public class MultiItemReaderDemo {
	
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("classpath:/file*.txt")
	private Resource[] resources;

    @Bean
    public Job multiItemReaderDemoJob(){
        return jobBuilderFactory.get("multiItemReaderDemoJob")
        		.start(multiItemReaderDemoStep())
        		.build();
    }

    @Bean
    public Step multiItemReaderDemoStep(){
        return stepBuilderFactory.get("multiItemReaderDemoStep").<Product, Product>chunk(2)
        		.reader(multiFileReader())
        		.writer(null)
        		.build();
    }
    
    @Bean
    @StepScope
    public MultiResourceItemReader<Product> multiFileReader() {
    
    	MultiResourceItemReader<Product> reader = new MultiResourceItemReader<>();
    	reader.setDelegate(read());
    	
    	reader.setResources(resources);
    	
    	
    	return reader;
    	
	}
    
    @Bean
    @StepScope
    public FlatFileItemReader<Product> read() {
    	FlatFileItemReader<Product> reader = new FlatFileItemReader<>();
    	reader.setResource(new ClassPathResource("products.txt"));
    	reader.setLinesToSkip(1);
    	
    	DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
    	tokenizer.setNames(new String[]{"id", "name", "description", "price"} );
    	
    	DefaultLineMapper<Product> mapper = new DefaultLineMapper<Product>();
    	mapper.setFieldSetMapper(new FieldSetMapper<Product>() {
			
			@Override
			public com.example.itemreaderfile.Product mapFieldSet(FieldSet fieldSet) throws BindException {
				Product product = new Product();
				product.setId(fieldSet.readInt("id"));
				product.setName(fieldSet.readString("name"));
				product.setDescription(fieldSet.readString("description"));
				product.setPrice(fieldSet.readDouble("price"));
				
				return product;
			}
		});
    	
    	mapper.afterPropertiesSet();
    	reader.setLineMapper(mapper);
    	
    	return reader;
	}
	
	
}
