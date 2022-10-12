package com.hemkant.batchprocessing.config;

import com.hemkant.batchprocessing.entity.OrderData;
import org.springframework.batch.item.ItemProcessor;


public class OrderProcessor implements ItemProcessor<OrderData,OrderData> {

    @Override
    public OrderData process(OrderData orderData) throws Exception {
        return orderData;
    }
}
