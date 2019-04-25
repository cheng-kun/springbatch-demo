package com.example.itemwriterdb;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.BindException;

@Configuration
public class FlatFileReaderConfig {

	
    @Bean
    public FlatFileItemReader<Product> flatFileReader() {
    	FlatFileItemReader<Product> reader = new FlatFileItemReader<>();
    	reader.setResource(new ClassPathResource("product_new.txt"));
    	
    	DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
    	tokenizer.setNames(new String[]{"id", "name", "description", "price"} );
    	
    	DefaultLineMapper<Product> mapper = new DefaultLineMapper<Product>();
    	mapper.setFieldSetMapper(new FieldSetMapper<Product>() {
			
			@Override
			public Product mapFieldSet(FieldSet fieldSet) throws BindException {
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
