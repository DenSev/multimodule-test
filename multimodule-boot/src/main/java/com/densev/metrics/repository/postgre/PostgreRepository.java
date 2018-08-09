package com.densev.metrics.repository.postgre;

import com.densev.metrics.app.ConfigProvider;
import com.densev.metrics.app.ConnectionProperties;
import com.densev.metrics.model.Data;
import com.densev.metrics.repository.Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.ResultSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PreDestroy;
import java.sql.*;
import java.util.List;

@org.springframework.stereotype.Repository
public class PostgreRepository implements Repository {

    private static final Logger LOG = LoggerFactory.getLogger(PostgreRepository.class);

    private String address;
    private int port;

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

        rs.close();

        return result;
    };


    public PostgreRepository(@Autowired ObjectMapper mapper,
                             ConfigProvider configProvider) {

        List<ConnectionProperties> connections = configProvider.getConnectionsForDataSource(ConnectionProperties.DataSource.POSTGRE);

        if (CollectionUtils.isNotEmpty(connections)) {
            ConnectionProperties connectionProperties = connections.get(0);
            this.mapper = mapper;
            this.address = connectionProperties.getUrl();
            this.port = connectionProperties.getPort();

            String url = String.format("jdbc:postgresql://%s:%s/%s", address, port, connectionProperties.getDbName());
            String user = connectionProperties.getUser();
            String password = connectionProperties.getPassword();
            try {
                this.connection = DriverManager.getConnection(url, user, password);

            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        } else {
            throw new RuntimeException("No configuration for POSTGRE data source type found");
        }

    }

    @PreDestroy
    public void terminate() {
        try {
            LOG.info("Called terminate on postgreSQL connection: {}:{}", address, port);
            DbUtils.close(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void save(Data data) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO public.data VALUES(?, ?, ?)")) {
            connection.setAutoCommit(false);
            statement.setString(1, data.getUserName());
            statement.setString(2, data.getText());
            statement.setInt(3, data.getStars());

            int i = statement.executeUpdate();
            connection.commit();
            LOG.debug("Rows updated: {}", i);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new RuntimeException(e.getMessage(), e);
            }
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    @Override
    public String search(String query) {
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

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

}
