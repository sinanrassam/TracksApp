package com.lostanimals.tracks.utils;

import java.util.Date;

public class PostEntry {
    public String mUsername;
    public String mPostTitle;
    public String mPostDesc;
    public Date mPostDate;

    public PostEntry(String username, String postTitle, String postDesc, Date postDate) {
        setUsername(username);
        setPostTitle(postTitle);
        setPostDesc(postDesc);
        setPostDate(postDate);
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getPostTitle() {
        return mPostTitle;
    }

    public void setPostTitle(String postTitle) {
        this.mPostTitle = postTitle;
    }

    public String getPostDesc() {
        return mPostDesc;
    }

    public void setPostDesc(String postDesc) {
        this.mPostDesc = postDesc;
    }

    public Date getPostDate() {
        return mPostDate;
    }

    public void setPostDate(Date mPostDate) {
        this.mPostDate = mPostDate;
    }
}
