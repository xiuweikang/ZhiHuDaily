package com.sdust.zhihudaily.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.w3c.dom.Comment;

import java.util.List;

/**
 * Created by Kevin on 16/5/26.
 */
public class Comments {

    @Expose
    @SerializedName("comments")
    private List<Comment> commentList;

}
