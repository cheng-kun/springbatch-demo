package com.example.itemreaderdb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;


@Configuration
public class ItemReaderDbDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("jdbcWriter")
	private ItemWriter<? super User> jdbcWriter;

    @Bean
    public Job itemReaderDbDemoJob(){
        return jobBuilderFactory.get("itemReaderDbDemoJob")
        		.start(itemReaderDbStep())
        		.build();
    }

    @Bean
    public Step itemReaderDbStep(){
        return stepBuilderFactory.get("itemReaderDbStep").<User, User>chunk(2)
        		.reader(read())
        		.writer(jdbcWriter)
        		.build();
    }
    
    @Bean
    @StepScope
    public JdbcPagingItemReader<User> read() {
    	JdbcPagingItemReader<User> jdbcPagingItemReader = new JdbcPagingItemReader<User>();
    	jdbcPagingItemReader.setDataSource(dataSource);
    	jdbcPagingItemReader.setFetchSize(2);
    	
    	jdbcPagingItemReader.setRowMapper(new RowMapper<User>() {
			
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getInt(1));
				user.setPassword(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setAge(rs.getInt(4));
				
				return user;
			}
		});
    	
    	MySqlPagingQueryProvider mySqlPagingQueryProvider = new MySqlPagingQueryProvider();
    	mySqlPagingQueryProvider.setSelectClause("id, username, password, age");
    	mySqlPagingQueryProvider.setFromClause("from user");
    	
    	Map<String, Order> map = new HashMap<String, Order>();
    	map.put("id", Order.ASCENDING);
    	
    	mySqlPagingQueryProvider.setSortKeys(map);
    	
    	jdbcPagingItemReader.setQueryProvider(mySqlPagingQueryProvider);
    	
    	return jdbcPagingItemReader;
	}
	
}
