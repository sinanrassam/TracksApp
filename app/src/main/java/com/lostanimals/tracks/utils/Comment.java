package com.lostanimals.tracks.utils;

public class Comment {
    public String mid;
    public String mpostId;
    public String mUsername;
    public String mDescription;
    public String mDate;

    public Comment(String id, String postId, String username, String description, String date) {
        mid = id;
        mpostId = postId;
        mUsername = username;
        mDescription = description;
        mDate = date;
    }

    public String getPostId() {
        return mpostId;
    }

    @Override
    public String toString() {
        return mUsername + ": " + mDescription;
    }
}
