package com.lostanimals.tracks.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for holding the post list and map for the feed. Post entries are created here and added to the map.
 */
public class PostsContent {
    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPostEntrySAMPLE(i));
        }
    }

    public static final List<PostEntry> ITEMS = new ArrayList<>();
    public static final Map<String, PostEntry> ITEM_MAP = new HashMap<>();

    // COUNT = 5 as that's the amount of test posts were using.
    private static final int COUNT = 5;

    private ServerManager serverManager = new ServerManager(null);

    private static void addItem(PostEntry item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    // TODO: This needs to pull info from server.
    private static PostEntry createPostEntry(int position) {
        return new PostEntry("ryan", "title", "desc", String.valueOf(position), makeDetailsSAMPLE(position));
    }

    private static PostEntry createPostEntrySAMPLE(int position) {
        return new PostEntry("ryan", "title", "desc", String.valueOf(position), makeDetailsSAMPLE(position));
    }

    // TODO: Rework this with pulling the description from the server. Wont need the position variable.

    private static String setDescription(int position) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(position);

        return stringBuilder.toString();
    }

    private static String makeDetailsSAMPLE(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("details blah blah: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore blah yadda yadda.");
        }
        return builder.toString();
    }
}