package com.example.itemreaderxml;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component("flatXmlWriter")
public class FlatFileWriter implements ItemWriter<Product> {

	@Override
	public void write(List<? extends Product> items) throws Exception {
		for (Product product : items) {
			System.out.println(product);
		}	
	}

}
