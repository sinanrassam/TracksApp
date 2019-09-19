package com.lostanimals.tracks.utils;

import java.util.ArrayList;

public class PostsUtility {

    public static ArrayList<PostEntry> postEntryArray;

    public static ArrayList<PostEntry> getPostEntryArray() {
        return postEntryArray;
    }

    public static void addPostEntry(int index, PostEntry newPost) {
        if (postEntryArray == null) {
            postEntryArray = new ArrayList<>();
        }
        PostsUtility.postEntryArray.add(index, newPost);
    }

    public static PostEntry getPostEntry(int index) {
        return PostsUtility.postEntryArray.get(index);
    }

    public static void setPostEntryArray(final ArrayList<PostEntry> postEntryArray) {
        PostsUtility.postEntryArray = postEntryArray;
    }

    public static void clear() {
        if (postEntryArray != null) {
            postEntryArray.clear();
        }
    }
}