package com.densev.metrics.repository.mongodb;

import com.densev.metrics.model.Data;
import com.densev.metrics.model.Metadata;
import com.densev.metrics.repository.Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static com.densev.metrics.model.Metadata.State;

@Component
public class MongoRepository implements Repository {

    private static final Logger LOG = LoggerFactory.getLogger(MongoRepository.class);

    private MongoClient client;
    @Autowired
    private ObjectMapper mapper;
    private String address;
    private int port;

    private final ExecutorService executorService;

    public MongoRepository() {
        this.address = "localhost";
        this.port = 27017;
        this.client = new MongoClient(address, port);
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @PreDestroy
    public void terminate() {
        try {
            LOG.info("Called terminate on mongoDB connection: {}:{}", address, port);
            client.close();
            executorService.awaitTermination(50, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public String search(String query) {
        BasicDBObject dbObject = BasicDBObject.parse(query);

        MongoDatabase database = client.getDatabase("test");
        MongoCollection collection = database.getCollection("posts");

        FindIterable<Document> result = collection.<Document>find(dbObject);

        List<String> response = new ArrayList<>();
        result.map(Document::toJson)
            .forEach((Consumer<String>) response::add);

        return response.toString();
    }

    @Override
    public String init() {

        List<Data> dataList = Lists.newArrayList(Data.builder()
                .id("5b56dcf3b8b5852a4c7304e7")
                .userName("abc")
                .text("it's shit")
                .stars(0)
                .build(),
            Data.builder()
                .id("5b56dcf3b8b5852a4c7304e8")
                .userName("def")
                .text("it's good")
                .stars(5)
                .build(),
            Data.builder()
                .id("5b56dcf3b8b5852a4c7304e9")
                .userName("ghi")
                .text("it's meh")
                .stars(3)
                .build()
        );
        for (Data data : dataList) {
            save(data);
        }

        return "Success";
    }

    public void save(Data data) {
        if (data == null) {
            return;
        }
        try {
            BasicDBObject idQuery = new BasicDBObject();
            idQuery.put("_id",data.get_id());

            Data foundData = getObject(idQuery);

            if (foundData != null && foundData.getMetadata() != null) {
                if (State.COMMITED.equals(foundData.getMetadata().getState())
                    //what about metadata version?
                    /*&& data.getMetadata().getVersion() == foundData.getMetadata().getVersion()*/) {
                    data.setVersion(foundData.getVersion() + 1);

                    BasicDBObject idQueryUpdated = new BasicDBObject();
                    idQueryUpdated.put("_id",  data.get_id());
                    idQueryUpdated.put("version", foundData.getVersion());

                    saveDirty(idQueryUpdated, foundData);
                    saveDirtyCommitted(idQueryUpdated, data, foundData);
                    saveCommittedData(idQueryUpdated, data, foundData);


                }
            } else {
                //save new committed
                saveCommittedData(null, data, null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Document getDBObject(Data data) {
        try {
            Document document = Document.parse(mapper.writeValueAsString(data));
            Document doc = new Document("$set", document);
            return doc;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void saveCommittedData(Bson query, Data newData, Data oldData) {
        try {
            MongoDatabase database = client.getDatabase("test");
            MongoCollection collection = database.getCollection("posts");
            if (oldData != null) {
                newData.setMetadata(new Metadata(State.COMMITED, null));
                Object obj = collection.findOneAndUpdate(query, getDBObject(newData));

            } else {
                newData.setMetadata(new Metadata());
                collection.insertOne(Document.parse(mapper.writeValueAsString(newData)));
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void saveDirtyCommitted(Bson query, Data newData, Data oldData) {

        MongoDatabase database = client.getDatabase("test");
        MongoCollection collection = database.getCollection("posts");
        
        newData.setMetadata(null);
        
        oldData.setMetadata(new Metadata(State.DIRTY_COMMITTED, newData));
        Object obj = collection.findOneAndUpdate(query, getDBObject(oldData));
    }

    private void saveDirty(Bson query, Data existingData) {
        MongoDatabase database = client.getDatabase("test");
        MongoCollection collection = database.getCollection("posts");
        existingData.setMetadata(new Metadata(State.DIRTY, null));
        Object obj = collection.findOneAndUpdate(query, getDBObject(existingData));
    }

    private Data getObject(BasicDBObject query) {
        try {
            MongoDatabase database = client.getDatabase("test");
            MongoCollection collection = database.getCollection("posts");

            Object found = collection.find(query).first();

            if (found != null) {
                return mapper.readValue(((Document) found).toJson(), Data.class);
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public ExecutorService getExecutor() {
        return this.executorService;
    }
}
