package com.example.call_spam_blocker;

import android.Manifest;
import android.app.Application;

public class Config extends Application {
    private String[] permissionString = new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.ANSWER_PHONE_CALLS};

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
