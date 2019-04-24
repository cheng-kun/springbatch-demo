package com.example.itemreaderfile;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.BindException;

@Configuration
public class ItemReaderFileDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    @Qualifier("flatFileWriter")
	private ItemWriter<? super Product> flatFileWriter;

    @Bean
    public Job itemReaderFileDemoJob(){
        return jobBuilderFactory.get("itemReaderFileDemoJob")
        		.start(itemReaderFileStep())
        		.build();
    }

    @Bean
    public Step itemReaderFileStep(){
        return stepBuilderFactory.get("itemReaderFileStep").<Product, Product>chunk(2)
        		.reader(read())
        		.writer(flatFileWriter)
        		.build();
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
