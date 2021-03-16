package com.kunal.countries.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Countries {

    // name, capital, flag(display image in app), region,
    //subregion, population, borders & languages

    public String name;
    public String capital;
    public String flag;
    public String region;
    public String subregion;
    public String population;
    @PrimaryKey(autoGenerate = true)
    public int uuid;

    public Countries(String name, String capital, String flag, String region, String subregion, String population) {
        this.name = name;
        this.capital = capital;
        this.flag = flag;
        this.region = region;
        this.subregion = subregion;
        this.population = population;
    }
}
