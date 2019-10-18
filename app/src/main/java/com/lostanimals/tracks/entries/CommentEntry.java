package com.lostanimals.tracks.entries;

public class CommentEntry {
    private String mID;
    private String mPostId;
    private String mUsername;
    private String mDescription;
    private String mDate;
    private String mTime;

    public CommentEntry(String id, String postId, String username, String description, String date, String time) {
        mID = id;
        mPostId = postId;
        mUsername = username;
        mDescription = description;
        mDate = date;
        mTime = time;
    }

    public String getUsername() {
        return mUsername;
    }

    @Override
    public String toString() {
        return mDescription + "; posted on " + mDate + " at " + mDate;
    }
}