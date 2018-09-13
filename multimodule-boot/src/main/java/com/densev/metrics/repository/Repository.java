package com.densev.metrics.repository;

import com.densev.metrics.model.QueryResult;
import com.google.common.base.Stopwatch;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface Repository {

    String search(String query);

    String init();

    String getAddress();

    int getPort();

}
