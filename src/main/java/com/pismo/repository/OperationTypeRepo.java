package com.pismo.repository;

import com.pismo.model.OperationTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationTypeRepo extends JpaRepository<OperationTypeModel, Long>{
    
}
