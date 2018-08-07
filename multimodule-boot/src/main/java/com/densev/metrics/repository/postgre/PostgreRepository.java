package com.densev.metrics.repository.postgre;

import com.densev.metrics.model.Data;
import com.densev.metrics.repository.Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.ResultSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PreDestroy;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@org.springframework.stereotype.Repository
public class PostgreRepository implements Repository {

    private static final Logger LOG = LoggerFactory.getLogger(PostgreRepository.class);

    private String address;
    private int port;

    private final ExecutorService executorService;
    private final Connection connection;
    private final ObjectMapper mapper;


    private final ResultSetHandler<Data[]> handler = rs -> {
        if (!rs.next()) {
            return null;
        }

        ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();
        Data[] result = new Data[cols];

        for (int i = 0; i < cols; i++) {
            result[i] = rs.getObject(i + 1, Data.class);
        }

        return result;
    };


    public PostgreRepository(@Autowired ObjectMapper mapper) {
        this.mapper = mapper;
        this.address = "localhost";
        this.port = 32768;
        String db = "test";

        String url = String.format("jdbc:postgresql://%s:%s/%s", address, port, db);
        String user = "postgres";
        String password = "";
        try {
            this.connection = DriverManager.getConnection(url, user, password);
            this.executorService = Executors.newSingleThreadExecutor();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @PreDestroy
    public void terminate() {
        try {
            LOG.info("Called terminate on postgreSQL connection: {}:{}", address, port);
            DbUtils.close(connection);
            executorService.awaitTermination(50, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public String search(String query) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            /*while (resultSet.next()) {
                resultSet.
            }*/
            Data[] data = handler.handle(resultSet);


            return mapper.writeValueAsString(data);
        } catch (SQLException | JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    @Override
    public String init() {
        return null;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public ExecutorService getExecutor() {
        return this.executorService;
    }
}
