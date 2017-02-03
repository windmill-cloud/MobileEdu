/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 */

package edu.ucsb.cs.cs185.xuanwangscores;

import android.content.Context;

/**
 * Created by xuanwang on 2/2/17.
 */

public class DatabaseOperatorSingleton {

    private static DatabaseOperatorSingleton mInstance;
    private static MatchesDBHelper mDatabaseOperator;

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

    public MatchesDBHelper getDBOperator() {
        if (mDatabaseOperator == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mDatabaseOperator = new MatchesDBHelper(mCtx.getApplicationContext());
        }
        return mDatabaseOperator;
    }

}
