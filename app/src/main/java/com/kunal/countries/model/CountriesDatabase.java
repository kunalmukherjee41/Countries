package com.kunal.countries.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Countries.class}, version = 1, exportSchema = false)
public abstract class CountriesDatabase extends RoomDatabase {

    private static CountriesDatabase mInstance = null;

    public static CountriesDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    CountriesDatabase.class,
                    "countriesdatabase"
            ).build();
        }
        return mInstance;
    }

    public abstract CountriesDao countriesDao();

}
