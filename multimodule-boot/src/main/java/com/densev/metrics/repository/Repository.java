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

    ExecutorService getExecutor();

    default QueryResult query(String query) {
        try {
            Future<QueryResult> queryResult = getExecutor().submit(() -> {
                Stopwatch innerStopwatch = Stopwatch.createStarted();
                String response = search(query);
                long elapsed = innerStopwatch.elapsed(TimeUnit.NANOSECONDS);
                DecimalFormat formatter = new DecimalFormat("###,###,###");

                QueryResult result = new QueryResult();
                result.setElapsed(formatter.format(elapsed) + " " + TimeUnit.NANOSECONDS);
                result.setResponse(response);
                result.setAddress(getAddress());
                result.setPort(getPort());

                return result;
            });

            return queryResult.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
