package com.pg.alldemo;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.pg.alldemo.activity.bean.User;
import com.pg.alldemo.activity.bean.UserDetail;

/**
 * Created by test on 12/3/18.
 */

@Database(entities = {User.class, UserDetail.class}, version = 1)
public abstract class DataBaseHelper extends RoomDatabase {
    private static String DB_NAME = "UserData.db";
    private static volatile DataBaseHelper instance;

    public abstract OperationManager getData();

    public static DataBaseHelper create(final Context context) {
        return Room.databaseBuilder(context, DataBaseHelper.class, DB_NAME).allowMainThreadQueries().build();
    }

    public static synchronized DataBaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
