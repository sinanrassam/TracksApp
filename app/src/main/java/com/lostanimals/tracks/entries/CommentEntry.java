package com.lostanimals.tracks.entries;

public class CommentEntry {
	private String mID;
	private String mPostId;
	private String mUsername;
	private String mDescription;
	private String mDate;
	
	public CommentEntry(String id, String postId, String username, String description, String date) {
		mID = id;
		mPostId = postId;
		mUsername = username;
		mDescription = description;
		mDate = date;
	}
	
	public String getPostId() {
		return mPostId;
	}
	
	@Override
	public String toString() {
		return mUsername + ": " + mDescription;
	}
}