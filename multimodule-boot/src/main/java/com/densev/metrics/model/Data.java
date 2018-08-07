package com.densev.metrics.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Data {

    /*@JsonIgnore*/
    private String _id;
    private String userName;
    private String text;
    private int stars;
    private Metadata metadata;
    private int version = 0;


    public Data() {
    }

    private Data(Builder builder) {
        this._id = builder.id;
        this.userName = builder.userName;
        this.text = builder.text;
        this.stars = builder.stars;
        this.metadata = builder.metadata;
        this.version = builder.version;
    }

    public Data(String _id, String userName, String text, int stars, int version, Metadata metadata) {
        this._id = _id;
        this.userName = userName;
        this.text = text;
        this.stars = stars;
        this.version = version;
        this.metadata = metadata;
    }

    public String get_id() {
        return _id;
    }

    public String getUserName() {
        return userName;
    }

    public String getText() {
        return text;
    }

    public int getStars() {
        return stars;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public static Builder builder() {
        return new Builder();
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static final class Builder {
        private String id;
        private String userName;
        private String text;
        private int stars;
        private int version;
        private Metadata metadata;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder stars(int stars) {
            this.stars = stars;
            return this;
        }

        public Builder version(int version) {
            this.version = version;
            return this;
        }

        public Builder metadata(Metadata metadata) {
            this.metadata = metadata;
            return this;
        }

        public Data build() {
            return new Data(this);
        }
    }
}
