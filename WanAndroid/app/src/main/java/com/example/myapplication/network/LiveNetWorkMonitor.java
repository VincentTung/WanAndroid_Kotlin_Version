package com.example.myapplication.network;

import android.content.Context;

public class LiveNetWorkMonitor implements NetWorkMonitor {

    private final Context applicationContext;

    public LiveNetWorkMonitor(Context context) {
        applicationContext = context.getApplicationContext();
    }

    @Override
    public boolean isConnected() {
        return NetWorkSkill.isNetConnected(applicationContext);
    }
}

