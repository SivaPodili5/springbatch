package com.example.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirstItemWriter implements ItemWriter<Long> {

    @Override
    public void write(Chunk<? extends Long> item) throws Exception {
        System.out.println("Inside Item Writer");
        item.getItems().forEach(System.out::println);
    }

}
