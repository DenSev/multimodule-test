package com.densev.multimodule.aop;

import java.util.List;

/**
 * Created by Dzianis_Sevastseyenk on 06/27/2017.
 */
public class LogInfo {

    private final List<Object> parameters;
    private final Object response;

    public LogInfo(List<Object> parameters, Object response) {
        this.parameters = parameters;
        this.response = response;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public Object getResponse() {
        return response;
    }


    @Override
    public String toString() {
        return "LogInfo{" +
            "parameters=" + parameters +
            ", response=" + response +
            '}';
    }
}
