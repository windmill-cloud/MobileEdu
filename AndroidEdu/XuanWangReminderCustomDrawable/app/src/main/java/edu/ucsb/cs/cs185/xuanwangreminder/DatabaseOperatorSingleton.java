/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs185.xuanwangreminder;

import android.content.Context;

/**
 * Created by xuanwang on 2/9/17.
 */

public class DatabaseOperatorSingleton {

    private static DatabaseOperatorSingleton mInstance;
    private static ReminderDBHelper mDatabaseOperator;

    private static Context mCtx;

    private DatabaseOperatorSingleton(Context context) {
        mCtx = context;
        mDatabaseOperator = getDBOperator();
    }

    public static synchronized DatabaseOperatorSingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseOperatorSingleton(context);
        }
        return mInstance;
    }

    public ReminderDBHelper getDBOperator() {
        if (mDatabaseOperator == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mDatabaseOperator = new ReminderDBHelper(mCtx.getApplicationContext());
        }
        return mDatabaseOperator;
    }

}
