package com.lostanimals.tracks.entries;

public class PostEntry {
	private String mUsername;
	private String mPostTitle;
	private String mPostDesc;
	private String mPostDate;
	private String mPostTime;
	private String mId;
	private String mFound;
	private String mMircrochipped;
	
	// default
	public PostEntry(String id, String postTitle, String postDesc, String username, String postDate, String postTime,
					 String found, String microchipped) {
		setId(id);
		setUsername(username);
		setPostTitle(postTitle);
		setPostDesc(postDesc);
		setPostDate(postDate);
		setPostTime(postTime);
		setFound(found);
		setMircrochipped(microchipped);
	}
	
	public String getUsername() {
		return mUsername;
	}

	public void setPostTime(String postTime) {
		mPostTime = postTime;
	}
	
	public void setUsername(String username) {
		this.mUsername = username;
	}

	public String getPostTitle() {
		return "" + mPostTitle;
	}

	public String getPostTime() {
		return "" + mPostTime;
	}
	
	private void setPostTitle(String postTitle) {
		this.mPostTitle = postTitle;
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

	public String getMircrochipped() {
		return mMircrochipped;
	}

	public void setMircrochipped(String mircrochipped) {
		mMircrochipped = mircrochipped;
	}
}
