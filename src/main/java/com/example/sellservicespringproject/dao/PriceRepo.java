package com.example.sellservicespringproject.dao;

import com.example.sellservicespringproject.models.entities.Price;
import com.example.sellservicespringproject.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PriceRepo extends JpaRepository<Price, Long> {

    List<Price> findAllByProduct(Product product);

    Price findByProductAndEndDateAfter(Product product, Date date);
}
