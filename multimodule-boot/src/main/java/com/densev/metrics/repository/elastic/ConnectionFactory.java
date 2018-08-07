package com.densev.metrics.repository.elastic;

import org.apache.http.HttpHost;
import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.sniff.Sniffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

/**
 * Created on 20.01.2017.
 */
public class ConnectionFactory implements ClientProvider {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionFactory.class);

    private final String address;
    private final int connectionPort;
    private RestClient basicClient;
    private RestHighLevelClient client;
    private Sniffer sniffer;

    public ConnectionFactory(final String address, final int connectionPort) {
        this.address = address;
        this.connectionPort = connectionPort;
    }

    @PostConstruct
    private void init() throws IOException {
        RestClientBuilder builder = RestClient.builder(new HttpHost(address, connectionPort, "http"));
        this.client = new RestHighLevelClient(builder);
        this.basicClient = this.client.getLowLevelClient();
        this.sniffer = Sniffer.builder(basicClient).build();

        MainResponse response = this.client.info();

        LOG.info("Started Elasticsearch connection with {} at {}:{}", response.getClusterName(), address, connectionPort);
    }

    @PreDestroy
    public void tearDown() throws IOException {
        LOG.info("Calling tearDown method for ConnectionFactory: {}:{}", address, connectionPort);
        if (this.basicClient != null) {
            this.basicClient.close();
        }
        if (this.sniffer != null) {
            this.sniffer.close();
        }
    }

    @Override
    public RestHighLevelClient getClient() {
        return this.client;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public int getPort() {
        return connectionPort;
    }
}
