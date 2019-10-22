package com.lostanimals.tracks.entries;

public class PostEntry {
    private String mUsername;
    private String mPostTitle;
    private String mPostDesc;
    private String mPostDate;
    private String mPostTime;
    private String mId;
    private String mFound;
    private String mChipped;
    private Boolean mHasImage;
    private String mLocation;
    private boolean mFollowing;
    private boolean mStray;

    // default
    public PostEntry(String id, String postTitle, String postDesc, String username, String postDate, String postTime,
                     String found, String microChipped, String following, String hasImage, String location,
                     String stray) {
        setId(id);
        setUsername(username);
        setPostTitle(postTitle);
        setPostDesc(postDesc);
        setPostDate(postDate);
        setPostTime(postTime);
        setFound(found);
        setFollowing(following);
        setMircrochipped(microChipped);
        setHasImage(hasImage);
        setLocation(location);
        setStray(stray);
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

    public String getPostDesc() {
        return mPostDesc;
    }

    private void setPostDesc(String postDesc) {
        this.mPostDesc = postDesc;
    }

    public void setFollowing(String following) {
        mFollowing = !following.equals("0");
    }

    public boolean isFollowed() {
        return mFollowing;
    }

    public String getMircrochipped() {
        return mChipped;
    }

    public void setMircrochipped(String mircrochipped) {
        mChipped = mircrochipped;
    }

    public void setHasImage(String hasImage) {
        mHasImage = hasImage.equals("1");
    }

    public Boolean hasImage() {
        return mHasImage;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        this.mLocation = location;
    }

    public boolean isStray() {
        return mStray;
    }

    public void setStray(String stray) {
        this.mStray = !stray.equals("0");
    }

    @Override
    public String toString() {
        if (getPostDesc() != null) {
            return getPostDesc();
        }
        return "Problem loading content";
    }
}
