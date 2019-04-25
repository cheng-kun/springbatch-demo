package com.example.itemwriterdb;

import javax.sql.DataSource;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemWriterDbConfig {

	@Autowired
	private DataSource dataSource;

	@Bean
	public JdbcBatchItemWriter<Product> itemwriterdb() {
		
		JdbcBatchItemWriter<Product> writer = new JdbcBatchItemWriter<Product>();
		writer.setDataSource(dataSource);
		writer.setSql("Insert into product (id, name, description, price) values (:id, :name, :description, :price)");
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		
		return writer;
	}
	
}
