package com.densev.metrics.repository.elastic;

import com.densev.metrics.model.Data;
import com.densev.metrics.repository.Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ElasticsearchRepository implements Repository {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticsearchRepository.class);

    private RestHighLevelClient client;
    private ObjectMapper mapper;
    private String address;
    private int port;
    private final ExecutorService requestExecutor;

    public ElasticsearchRepository(RestHighLevelClient client,
                                   ObjectMapper mapper,
                                   String address,
                                   int port) {
        this.client = client;
        this.mapper = mapper;
        this.address = address;
        this.port = port;
        this.requestExecutor = Executors.newSingleThreadExecutor();
    }

    @PreDestroy
    public void terminate() {
        try {
            LOG.info("Called terminate on Elasticsearch connection: {}:{}", address, port);
            requestExecutor.awaitTermination(50, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void getResponse() {
        try {
            SearchRequest searchRequest = new SearchRequest();
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            searchRequest.source(searchSourceBuilder);

            SearchResponse response = client.search(searchRequest);
            LOG.info("Took {}", response.getTook());
        } catch (IOException e) {
            LOG.error("Something went wrong", e);
        }
    }

    public String search(String requestString) {
        try {
            HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory consumerFactory =
                new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024);

            Response response = this.client.getLowLevelClient().performRequest(
                "POST",
                "/_search",
                new HashMap<String, String>(),
                new NStringEntity(requestString, ContentType.APPLICATION_JSON),
                consumerFactory);

            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public String init() {
        try {
            LOG.info("Preparing to index items");
            BulkRequest request = new BulkRequest();

            List<Data> dataList = Lists.newArrayList(
                Data.builder()
                    .id("id1")
                    .userName("abc")
                    .text("it's shit")
                    .stars(0)
                    .build(),
                Data.builder()
                    .id("id2")
                    .userName("def")
                    .text("it's good")
                    .stars(5)
                    .build(),
                Data.builder()
                    .id("id3")
                    .userName("ghi")
                    .text("it's meh")
                    .stars(3)
                    .build()
            );

            dataList.forEach((item) -> {
                try {
                    byte[] data = mapper.writeValueAsBytes(item);

                    request.add(
                        new IndexRequest("posts", "doc")
                            .source(data, XContentType.JSON)
                            .id(item.get_id())
                    );
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            });

            BulkResponse bulkResponse = client.bulk(request);
            return !bulkResponse.hasFailures() ? "No failures" : bulkResponse.buildFailureMessage();
        } catch (IOException e) {
            LOG.error("Something went wrong", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public ExecutorService getExecutor() {
        return requestExecutor;
    }
}
