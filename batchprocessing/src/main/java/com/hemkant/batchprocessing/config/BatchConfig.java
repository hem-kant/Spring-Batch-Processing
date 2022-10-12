package com.hemkant.batchprocessing.config;

import com.hemkant.batchprocessing.entity.OrderData;
import com.hemkant.batchprocessing.repository.OrderDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;


@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfig {
    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private OrderDataRepository orderDataRepository;

    @Bean
    public FlatFileItemReader<OrderData> reader(){
        FlatFileItemReader<OrderData> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/batch-processing.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());

        return itemReader;
    }

    private LineMapper<OrderData> lineMapper() {
        DefaultLineMapper<OrderData> lineMapper=new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer=new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id","name","cost","contact","vendor");

        BeanWrapperFieldSetMapper<OrderData> fieldSetMapper=new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(OrderData.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return  lineMapper;
    }

    @Bean
    public  OrderProcessor processor(){
        return  new OrderProcessor();
    }

    @Bean
    public RepositoryItemWriter<OrderData> writer(){
        RepositoryItemWriter<OrderData> writer= new RepositoryItemWriter<>();
        writer.setRepository(orderDataRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step step1(){
    return  stepBuilderFactory.get("Order-data-step").<OrderData,OrderData>chunk(10)
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .build();
    }

    @Bean
    public Job orderJob(){
        return jobBuilderFactory.get("ImportOrderData")
                .flow(step1()).end().build();
    }

}
