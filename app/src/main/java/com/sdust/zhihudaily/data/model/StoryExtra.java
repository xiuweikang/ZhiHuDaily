package com.sdust.zhihudaily.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kevin on 16/5/26.
 */
public class StoryExtra {

    @Expose
    @SerializedName("vote_status")
    private int voteStatus;

    @Expose
    private int popularity;

    @Expose
    @SerializedName("long_comments")
    private int longComment;

    @Expose
    @SerializedName("short_comments")
    private int shortComment;

    public int getVoteStatus() {
        return voteStatus;
    }

    public void setVoteStatus(int voteStatus) {
        this.voteStatus = voteStatus;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getLongComment() {
        return longComment;
    }

    public void setLongComment(int longComment) {
        this.longComment = longComment;
    }

    public int getShortComment() {
        return shortComment;
    }

    public void setShortComment(int shortComment) {
        this.shortComment = shortComment;
    }
}
