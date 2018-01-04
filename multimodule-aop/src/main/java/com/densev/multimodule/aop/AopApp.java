package com.densev.multimodule.aop;

import org.h2.tools.Server;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Dzianis_Sevastseyenk on 06/27/2017.
 */
@Component
public class AopApp {

    private static final Logger LOG = LoggerFactory.getLogger(AopApp.class);

    private static SessionFactory factory;
    @Autowired
    LogContainer logContainer;
    @Autowired
    private RepositoryTest repositoryTest;

    public static void main(String... args) {
        try (ServerWrapper server = new ServerWrapper(Server.createTcpServer(args).start())) {
            ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
            AopApp application = context.getBean(AopApp.class);
            application.run();
            server.status();
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
            factory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();


            TestEntity te = new TestEntity("test", 1);

            saveEntity(te);
            listEmployeesEntity();

            factory.close();
        } catch (Exception exception) {
            LOG.error("Caught exception:", exception);
        }

    }


    public void run() {
        LOG.info(repositoryTest.wrapperSearch("something something something test"));
        logContainer.log();
    }

    public static class ServerWrapper implements AutoCloseable {

        private static final Logger LOG = LoggerFactory.getLogger(ServerWrapper.class);
        private final Server server;

        public ServerWrapper(Server server) {
            this.server = server;
        }

        public void status() {
            LOG.info("URL: {}", server.getURL());
            LOG.info("H2 server is running at port: {}, with status: {}", server.getPort(), server.getStatus());
        }

        @Override
        public void close() throws Exception {
            LOG.debug("Stopping server...");
            server.stop();
            LOG.debug("Server stopped.");
        }

        public int getPort() {
            return server.getPort();
        }

        public String getStatus() {
            return server.getStatus();
        }
    }

    public static void doSqlQuery(String query) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.createQuery(query);
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public static Integer saveEntity(TestEntity testEntity){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer id = null;

        try {
            tx = session.beginTransaction();
            id = (Integer) session.save(testEntity);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    /* Method to READ all the employees using Entity Query */
    public static void listEmployeesEntity( ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String sql = "SELECT * FROM testEntity";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(TestEntity.class);
            List employees = query.list();

            for (Iterator iterator = employees.iterator(); iterator.hasNext();){
                TestEntity employee = (TestEntity) iterator.next();
                System.out.println(employee.toString());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
