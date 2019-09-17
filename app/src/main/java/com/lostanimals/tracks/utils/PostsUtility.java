package com.lostanimals.tracks.utils;

import java.util.ArrayList;

public class PostsUtility {

    public static ArrayList<PostEntry> postEntryArray = new ArrayList<>();

    public static ArrayList<PostEntry> getPostEntryArray() {
        return postEntryArray;
    }

    public static void setPostEntryArray(final ArrayList<PostEntry> postEntryArray) {
        PostsUtility.postEntryArray = postEntryArray;
    }

}
