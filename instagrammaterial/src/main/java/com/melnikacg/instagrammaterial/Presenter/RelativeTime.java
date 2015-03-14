package com.melnikacg.instagrammaterial.Presenter;

public class RelativeTime {

    public static String getRelativeTime(String createdTime) {

        long createdTimeSeconds = Long.parseLong(createdTime);
        long currentTimeSeconds = System.currentTimeMillis() / 1000;
        long elapsedSeconds = currentTimeSeconds - createdTimeSeconds;

        if (elapsedSeconds < 60) { // less than a minute
            return String.format("%.0fs", elapsedSeconds);
        } else if (elapsedSeconds < 3600) { // less than an hour
            return String.format("%.0fm", Math.floor(elapsedSeconds / 60));
        } else if (elapsedSeconds < 86400) { // less than a day
            return String.format("%.0fh", Math.floor(elapsedSeconds / 3600));
        } else {
            return String.format("%.0fd", Math.floor(elapsedSeconds / 86400));
        }
    }


}
