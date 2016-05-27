package com.sdust.zhihudaily.data.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Kevin on 16/5/26.
 */
public class Reply {
    @Expose
    private String cotent;

    @Expose
    private String status;

    @Expose
    private String id;

    @Expose
    private String author;

    public String getCotent() {
        return cotent;
    }

    public void setCotent(String cotent) {
        this.cotent = cotent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
