package com.densev.metrics.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Metadata {

    private State state = State.COMMITED;
    private Data newData;

    public Metadata() {
    }

    public Metadata(State state) {
        this.state = state;
    }

    public Metadata(State state, Data newData) {
        this.state = state;
        this.newData = newData;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Data getNewData() {
        return newData;
    }

    public void setNewData(Data newData) {
        this.newData = newData;
    }

    public enum State {
        DIRTY,
        DIRTY_COMMITTED,
        COMMITED
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
