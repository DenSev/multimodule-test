package com.densev.multimodule.aop;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

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
            LOG.info("H2 server is running at port: {}, with status: {}", server.getPort(), server.getStatus());
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
