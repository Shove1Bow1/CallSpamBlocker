package com.example.call_spam_blocker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BlockedList extends SQLiteOpenHelper {
    private static int DATABASE_VERSION=1;
    private static String DATABASE_NAME="blocked_list.db";
    private static String DATABASE_BUILDER="";
    public BlockedList(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
