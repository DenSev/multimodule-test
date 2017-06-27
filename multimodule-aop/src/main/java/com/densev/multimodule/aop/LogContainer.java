package com.densev.multimodule.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dzianis_Sevastseyenk on 06/27/2017.
 */
@Component
public class LogContainer {

    private static final Logger LOG = LoggerFactory.getLogger(LogContainer.class);

    private List<LogInfo> logCache;

    public void add(LogInfo logInfo) {
        if (logCache == null) {
            logCache = new ArrayList<>();
        }
        logCache.add(logInfo);
    }

    public void log() {
        this.logCache
            .forEach(item -> LOG.info("log item: {}", item.toString()));
    }
}
