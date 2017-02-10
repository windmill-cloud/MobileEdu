/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.xuanwangreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.IntegerRes;

/**
 * Created by xuanwang on 2/9/17.
 */

public class ReminderDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "ReminderDB.db";
    public final String TABLE_NAME = "reminders";

    public static final int UUID = 0;
    public static final int TITLE = 1;
    public static final int DAYS = 2;
    public static final int HOUR = 3;
    public static final int MINUTE = 4;
    public static final int DETAILS = 5;

    public ReminderDBHelper(Context context) {
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
                " (id text primary key, " +
                "title text, days integer, " +
                "hour integer, minute integer," +
                "details text)";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public boolean insertReminder (ReminderContent.Reminder reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", reminder.id);
        contentValues.put("title", reminder.title);
        contentValues.put("days", reminder.days);
        contentValues.put("hour", reminder.hour);
        contentValues.put("minute", reminder.minute);
        contentValues.put("details", reminder.details);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id, null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateReminder (ReminderContent.Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", reminder.id);
        contentValues.put("title", reminder.title);
        contentValues.put("days", reminder.days);
        contentValues.put("hour", reminder.hour);
        contentValues.put("minute", reminder.minute);
        contentValues.put("details", reminder.details);
        db.update(TABLE_NAME, contentValues, "id='" + reminder.id + "'", null);
        return true;
    }

    public Integer deleteReminder(ReminderContent.Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id='" + reminder.id + "'", null);
    }

    public void populateReminders() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            String uuid = res.getString(UUID);
            String title = res.getString(TITLE);
            int days = Integer.parseInt(res.getString(DAYS));
            int hour = Integer.parseInt(res.getString(HOUR));
            int minute = Integer.parseInt(res.getString(MINUTE));
            String details = res.getString(DETAILS);

            ReminderContent.Reminder reminder =
                    new ReminderContent.Reminder(uuid, title, days, hour, minute, details);

            ReminderContent.addItem(reminder);

            res.moveToNext();
        }
    }
}