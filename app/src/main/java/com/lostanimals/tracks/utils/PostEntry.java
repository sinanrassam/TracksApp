package com.lostanimals.tracks.utils;

public class PostEntry {
    public String mUsername;
    public String mPostTitle;
    public String mPostDesc;
    public String mPostDate;
    public String mId;
    public String mContent;

    // extra con for testing
    public PostEntry(String username, String postTitle, int id) {
        setUsername(username);
        setPostTitle(postTitle);
        setPostDesc(null);
        setPostDate(null);
        setId(String.valueOf(id));
        setContent(null);
    }

    // default
    public PostEntry(String username, String postTitle, String postDesc, String postDate, String id, String content) {
        setUsername(username);
        setPostTitle(postTitle);
        setPostDesc(postDesc);
        setPostDate(postDate);
        setId(id);
        setContent(content);
    }

    // override for no date
    public PostEntry(String username, String postTitle, String postDesc, String id, String content) {
        setUsername(username);
        setPostTitle(postTitle);
        setPostDesc(postDesc);
        setPostDate("1970");
        setId(id);
        setContent(content);
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

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    @Override
    public String toString() {
        if (getContent() != null) {
            return getContent();
        }
        return "Problem loading content";
    }
}
