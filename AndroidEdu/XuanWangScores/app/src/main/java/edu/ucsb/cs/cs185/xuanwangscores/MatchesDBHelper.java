/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 */

package edu.ucsb.cs.cs185.xuanwangscores;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xuanwang on 2/2/17.
 */

public class MatchesDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "MatchesDB.db";
    public final String TABLE_NAME = "matches";

    public MatchesDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    @Override

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create table Matches(Id integer primary key, CustomName text, OrderPrice integer, Country text);
        String sql = "create table if not exists " +
                TABLE_NAME +
                " (Id text primary key, Date text, HomeTeam text, HomeScore integer, " +
                "AwayTeam text, AwayScore integer)";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

}
