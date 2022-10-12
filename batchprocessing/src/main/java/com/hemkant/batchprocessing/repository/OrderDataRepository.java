package com.hemkant.batchprocessing.repository;

import com.hemkant.batchprocessing.entity.OrderData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDataRepository extends JpaRepository<OrderData,Integer> {
}
