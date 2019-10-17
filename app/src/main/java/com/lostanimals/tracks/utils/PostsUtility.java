package com.lostanimals.tracks.utils;

import com.lostanimals.tracks.entries.PostEntry;

import java.util.ArrayList;

public class PostsUtility {
	
	private static ArrayList<PostEntry> postEntryArray;
	
	public static void addPostEntry(int index, PostEntry newPost) {
		if (postEntryArray == null) {
			postEntryArray = new ArrayList<>();
		}
		PostsUtility.postEntryArray.add(index, newPost);
	}
	
	public static PostEntry getPostEntry(int index) {
		return PostsUtility.postEntryArray.get(index);
	}
	
	public static void clear() {
		if (postEntryArray != null) {
			postEntryArray.clear();
		}
	}

	public static void reportPost(int index, int type) {
		if (type == 0) {
			PostsUtility.getPostEntry(index).setReported(false);
		} else {
			PostsUtility.getPostEntry(index).setReported(true);
		}
		PostsUtility.getPostEntry(index).setReportCount(type);
	}
}