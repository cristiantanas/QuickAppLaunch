package com.ctanas.android.applaunch.adapter;


import android.content.Intent;
import android.graphics.drawable.Drawable;

import java.util.Comparator;

public class AppInfo {

    private String      applicationName;
    private Drawable    applicationIcon;
    private Intent      launchingIntent;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Drawable getApplicationIcon() {
        return applicationIcon;
    }

    public void setApplicationIcon(Drawable applicationIcon) {
        this.applicationIcon = applicationIcon;
    }

    public Intent getLaunchingIntent() {
        return launchingIntent;
    }

    public void setLaunchingIntent(Intent launchingIntent) {
        this.launchingIntent = launchingIntent;
    }

    public static class ApplicationNameComparator implements Comparator<AppInfo> {

        @Override
        public int compare(AppInfo lhs, AppInfo rhs) {
            return lhs.getApplicationName().compareToIgnoreCase( rhs.getApplicationName() );
        }
    }
}
