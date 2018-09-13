package com.densev.metrics.services;


import com.densev.metrics.repository.Repository;
import com.densev.metrics.repository.mongodb.MongoRepository;
import com.densev.metrics.repository.postgre.PostgreRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
//@RequestScope
public class RequesterService {

    private static final Logger LOG = LoggerFactory.getLogger(RequesterService.class);


    private final Stopwatch stopwatch = Stopwatch.createStarted();
    private final Map<String, List<Repository>> repositoryMap;
    private final ObjectMapper mapper;

    @Autowired
    public RequesterService(MongoRepository mongoRepository,
                            PostgreRepository postgreRepository,
                            ObjectMapper mapper) {

        this.mapper = mapper;
        this.repositoryMap = ImmutableMap
            .<String, List<Repository>>builder()
            .put("mongo", Lists.newArrayList(mongoRepository))
            .put("postgre", Lists.newArrayList(postgreRepository))
            .build();
    }

    public String search(String db, String query) {

        try {
            List<Repository> repositoryList = repositoryMap.get(db);
            if (repositoryList == null) {
                throw new NotFoundException("No such repository");
            }
            List<String> results = new ArrayList<>();
            for (Repository repository : repositoryList) {
                results.add(mapper.writeValueAsString(repository.search(query)));
            }

            return results.toString();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    @PreDestroy
    public void destroy() {
        LOG.info("lifetime is: {}", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}
