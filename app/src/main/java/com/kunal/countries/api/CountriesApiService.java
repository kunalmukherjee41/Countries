package com.kunal.countries.api;

import com.kunal.countries.model.Countries;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountriesApiService {

    private static final String BASE_URL = "https://restcountries.eu/";

    private CountriesApi api;

    public CountriesApiService() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(CountriesApi.class);
    }

    public Single<List<Countries>> getCountries() {
        return api.getCountries();
    }

}
