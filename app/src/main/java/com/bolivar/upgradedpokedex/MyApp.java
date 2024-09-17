package com.bolivar.upgradedpokedex;

import android.app.Application;

import net.zetetic.database.sqlcipher.SQLiteDatabase;

import com.bolivar.upgradedpokedex.sqlite.helper.AppDbHelper;

public class MyApp extends Application {

    static {
        System.loadLibrary("sqlcipher");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppDbHelper.initialize(this);
    }
}
