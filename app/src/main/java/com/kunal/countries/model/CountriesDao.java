package com.kunal.countries.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CountriesDao {

    @Insert
    List<Long> insertAllCountries(Countries...countries);

    @Query("SELECT * FROM Countries")
    List<Countries> getAllCountries();

    @Query("DELETE FROM Countries")
    void deleteAllCountries();

}
