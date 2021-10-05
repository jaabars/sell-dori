package com.example.sellservicespringproject.dao;

import com.example.sellservicespringproject.models.entities.OperationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationDetailRepo extends JpaRepository<OperationDetail, Long> {
}