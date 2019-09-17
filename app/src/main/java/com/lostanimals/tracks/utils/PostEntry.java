package com.lostanimals.tracks.utils;

public class PostEntry {
    public String mUsername;
    public String mPostTitle;
    public String mPostDesc;
    public String mPostDate;
    public String mId;
    public String mTime;

    // extra con for testing
    public PostEntry(String username, String postTitle) {
        setUsername(username);
        setPostTitle(postTitle);
        setPostDesc("TEST");
        setPostDate(null);
        setId(null);
        setTime(null);
    }

    // extra con for testing
    public PostEntry(String username, String postTitle, String desc, int id) {
        setUsername(username);
        setPostTitle(postTitle);
        setPostDesc(desc);
        setPostDate(null);
        setId(String.valueOf(id));
        setTime(null);
    }

    // default
    public PostEntry(String id, String postTitle, String postDesc, String username,  String postDate, String postTime) {
        setUsername(username);
        setPostTitle(postTitle);
        setPostDesc(postDesc);
        setPostDate(postDate);
        setId(id);
        setTime(postTime);
    }

    // override for no date
    public PostEntry(String username, String postTitle, String postDesc, String id, String content) {
        setUsername(username);
        setPostTitle(postTitle);
        setPostDesc(postDesc);
        setPostDate("1970");
        setId(id);
        setTime(content);
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

    private void setPostTitle(String postTitle) {
        this.mPostTitle = postTitle;
    }

    public String getPostDesc() {
        return mPostDesc;
    }

    private void setPostDesc(String postDesc) {
        this.mPostDesc = postDesc;
    }

    public String getPostDate() {
        return mPostDate;
    }

    private void setPostDate(String mPostDate) {
        this.mPostDate = mPostDate;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        this.mTime = time;
    }

    @Override
    public String toString() {
        if (getPostDesc() != null) {
            return getPostDesc();
        }
        return "Problem loading content";
    }
}
