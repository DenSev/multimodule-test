package com.densev.metrics.rest;

import com.densev.metrics.repository.Repository;
import com.densev.metrics.repository.elastic.ElasticsearchRepository;
import com.densev.metrics.repository.mongodb.MongoRepository;
import com.densev.metrics.services.RequesterService;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;

@Component
@Path("/")
public class RootEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(RootEndpoint.class);

    private final ElasticsearchRepository elasticsearchRepository;
    private final ElasticsearchRepository dockerElasticsearchRepository;
    private final Map<String, Repository> repositoryMap;
    private final RequesterService requesterService;

    @Autowired
    public RootEndpoint(@Qualifier("localElasticRepository") ElasticsearchRepository elasticsearchRepository,
                        @Qualifier("dockerElasticRepository") ElasticsearchRepository dockerElasticsearchRepository,
                        MongoRepository mongoRepository,
                        RequesterService requesterService) {
        this.elasticsearchRepository = elasticsearchRepository;
        this.requesterService = requesterService;
        this.dockerElasticsearchRepository = dockerElasticsearchRepository;
        repositoryMap = ImmutableMap
            .<String, Repository>builder()
            .put("elastic", elasticsearchRepository)
            .put("mongo", mongoRepository)
            .build();
    }

    @GET
    @Produces("text/plain")
    public Response home() {
        elasticsearchRepository.getResponse();
        return Response.ok().entity("Hello, all's good.").build();
    }

    @GET
    @Produces("text/plain")
    @Path("init/{db}")
    public Response init(@PathParam("db") String db) {
        Repository repository = repositoryMap.get(db);
        if (repository == null) {
            throw new NotFoundException();
        }
        //dockerElasticsearchRepository.init();

        return Response.ok().entity(repository.init()).build();
    }

    @GET
    @Produces("application/json")
    @Path("exception")
    public Response testException() {
        throw new RuntimeException(new NotFoundException("testing"));
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("search/{db}")
    public Response search(@PathParam("db") String db, String searchRequest) {

        return Response.ok()
            .entity(requesterService.search(db, searchRequest))
            .build();
    }
}
