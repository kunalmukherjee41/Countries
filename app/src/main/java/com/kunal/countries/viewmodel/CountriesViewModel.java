package com.kunal.countries.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.kunal.countries.api.CountriesApiService;
import com.kunal.countries.model.Countries;
import com.kunal.countries.model.CountriesDao;
import com.kunal.countries.model.CountriesDatabase;
import com.kunal.countries.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CountriesViewModel extends AndroidViewModel {

    public MutableLiveData<List<Countries>> countries = new MutableLiveData<List<Countries>>();
    public MutableLiveData<Boolean> loadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    private CountriesApiService apiService = new CountriesApiService();
    private CompositeDisposable disposable = new CompositeDisposable();

    private AsyncTask<List<Countries>, Void, List<Countries>> insertTask;
    private AsyncTask<Void, Void, List<Countries>> retrieveTask;

    private SharedPreferencesHelper preferencesHelper = SharedPreferencesHelper.getInstance(getApplication());
    private long refreshTime = 5 * 60 * 1000 * 1000 * 1000L;

    public CountriesViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {
        long updateTime = preferencesHelper.getUpdateTime();
        long currentTime = System.nanoTime();
        if (updateTime != 0 && currentTime - updateTime < refreshTime) {
            fetchFromDatabase();
        } else {
            fetchFromRemote();
        }
    }

    public void refreshByPassCache(){
        fetchFromRemote();
    }

    private void fetchFromDatabase() {
        loadError.setValue(false);
        loading.setValue(true);
        retrieveTask = new RetrieveCountriesTask().execute();
    }

    public void fetchFromRemote() {

        loadError.setValue(true);
        loadError.setValue(false);

        disposable.add(
                apiService.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Countries>>(){
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull List<Countries> countries) {
                        insertTask = new InsertCountriesTask().execute(countries);
//                        Toast.makeText(getApplication(), "Countries Fetch From Server", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        loadError.setValue(true);
                        loading.setValue(false);
                        e.printStackTrace();
                    }
                })
        );
    }

    private void countriesRetrieved(List<Countries> list) {
        countries.setValue(list);
        loadError.setValue(false);
        loading.setValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();

        if (insertTask != null) {
            insertTask.cancel(true);
            insertTask = null;
        }

        if (retrieveTask != null) {
            retrieveTask.cancel(true);
            insertTask = null;
        }
    }

    private class InsertCountriesTask extends AsyncTask<List<Countries>, Void, List<Countries>> {

        @Override
        protected List<Countries> doInBackground(List<Countries>... lists) {
            List<Countries> list = lists[0];
            CountriesDao countriesDao = CountriesDatabase.getInstance(getApplication()).countriesDao();
            ArrayList<Countries> newList = new ArrayList<>(list);
            List<Long> result = countriesDao.insertAllCountries(newList.toArray(new Countries[0]));
            int i = 0;
            while (i < result.size()) {
                list.get(i).uuid = result.get(i).intValue();
                ++i;
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<Countries> list) {
            countriesRetrieved(list);
            preferencesHelper.saveUpdateTime(System.nanoTime());
        }
    }

    private class RetrieveCountriesTask extends AsyncTask<Void, Void, List<Countries>> {

        @Override
        protected List<Countries> doInBackground(Void... voids) {
            return CountriesDatabase.getInstance(getApplication()).countriesDao().getAllCountries();
        }

        @Override
        protected void onPostExecute(List<Countries> list) {
            countriesRetrieved(list);
//            Toast.makeText(getApplication(), "Dogs Fetch From Database", Toast.LENGTH_SHORT).show();
        }
    }

}
