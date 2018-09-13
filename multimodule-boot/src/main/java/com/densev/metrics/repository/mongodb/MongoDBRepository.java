package com.densev.metrics.repository.mongodb;

import com.densev.metrics.model.Data;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoDBRepository extends MongoRepository<Data, String> {

    List<Data> findByUserName(String userName);



}
