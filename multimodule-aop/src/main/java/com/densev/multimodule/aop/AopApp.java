package com.densev.multimodule.aop;

import org.h2.tools.Server;
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

/**
 * Created by Dzianis_Sevastseyenk on 06/27/2017.
 */
@Component
public class AopApp {

    private static final Logger LOG = LoggerFactory.getLogger(AopApp.class);

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
            SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();


            TestEntity te = new TestEntity("test", 1);
            Session session = sessionFactory.openSession();
            session.save(te);
            session.close();

            Session session2 = sessionFactory.openSession();
            Transaction tx = session2.beginTransaction();
            Query query = session2.createQuery("from TestEntity t where t.id=1");
            Iterator it = session2.createQuery("from TestEntity").list().iterator();
            LOG.info("iterator: {}", it.hasNext());
            while (it.hasNext()) {
                TestEntity tt = (TestEntity) it.next();

                LOG.info("{}", tt);
            }
            tx.commit();
            session2.close();
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
}
