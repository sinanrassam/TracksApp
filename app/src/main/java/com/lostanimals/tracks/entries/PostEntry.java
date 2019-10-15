package com.lostanimals.tracks.entries;

public class PostEntry {
	private String mUsername;
	private String mPostTitle;
	private String mPostDesc;
	private String mPostDate;
	private String mPostTime;
	private String mId;
	private String mFound;
	private String mImageLocation;
	
	// default
	public PostEntry(String id, String postTitle, String postDesc, String username, String postDate, String postTime, String found) {
		setId(id);
		setUsername(username);
		setPostTitle(postTitle);
		setPostDesc(postDesc);
		setPostDate(postDate);
		setPostTime(postTime);
		setFound(found);
	}
	
	public String getUsername() {
		return mUsername;
	}
	
	public void setUsername(String username) {
		this.mUsername = username;
	}
	
	public String getPostTitle() {
		return "" + mPostTitle;
	}
	
	private void setPostTitle(String postTitle) {
		this.mPostTitle = postTitle;
	}
	
	public String getPostTime() {
		return "" + mPostTime;
	}
	
	public void setPostTime(String postTime) {
		mPostTime = postTime;
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
	
	public String getFound() {
		return mFound;
	}
	
	public void setFound(String found) {
		this.mFound = found;
	}
	
	@Override
	public String toString() {
		if (getPostDesc() != null) {
			return getPostDesc();
		}
		return "Problem loading content";
	}
	
	public String getPostDesc() {
		return mPostDesc;
	}
	
	private void setPostDesc(String postDesc) {
		this.mPostDesc = postDesc;
	}
}
