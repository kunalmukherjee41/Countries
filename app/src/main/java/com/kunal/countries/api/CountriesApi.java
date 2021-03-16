package com.kunal.countries.api;

import com.kunal.countries.model.Countries;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface CountriesApi {

    @GET("rest/v2/region/asia")
    Single<List<Countries>> getCountries();

}
