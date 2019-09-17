package com.lostanimals.tracks.utils;

import java.util.ArrayList;

public class PostsHandler {

    public static ArrayList<PostEntry> postEntryArray = new ArrayList<>();

    public static ArrayList<PostEntry> getPostEntryArray() {
        return postEntryArray;
    }

    public static void setPostEntryArray(final ArrayList<PostEntry> postEntryArray) {
        PostsHandler.postEntryArray = postEntryArray;
    }

}
