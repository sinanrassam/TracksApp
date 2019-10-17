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

    /**
     * @param index
     * @param REPORT_TYPE the type of report. If 0, clear the reports.
     */
    public static void setReportCount(int index, final int REPORT_TYPE) {
        final int REPORT_OTHER = 1;
        final int REPORT_ABUSE = 2;
        final int REPORT_VIOLATE = 3;

        if (REPORT_TYPE == 0) {
            postEntryArray.get(index).setReported(false);
        } else {
            postEntryArray.get(index).setReported(true);
        }

        switch (REPORT_TYPE) {
            case 0:
                postEntryArray.get(index).setReportCount(new int[3]);
                break;
            case REPORT_OTHER:
                if (postEntryArray.get(index).getReportCount(REPORT_OTHER) > 10) {
                    postEntryArray.get(index).setReportCount(REPORT_OTHER);
                }
                break;
            case REPORT_ABUSE:
                if (postEntryArray.get(index).getReportCount(REPORT_ABUSE) > 10) {
                    postEntryArray.get(index).setReportCount(REPORT_ABUSE);
                }
                break;
            case REPORT_VIOLATE:
                if (postEntryArray.get(index).getReportCount(REPORT_VIOLATE) > 10) {
                    postEntryArray.get(index).setReportCount(REPORT_VIOLATE);
                }
                break;
        }
    }
}