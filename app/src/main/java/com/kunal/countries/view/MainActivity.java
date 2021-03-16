package com.kunal.countries.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kunal.countries.R;
import com.kunal.countries.adapter.CountriesListAdapter;
import com.kunal.countries.viewmodel.CountriesViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    private CountriesViewModel countriesViewModel;
    private CountriesListAdapter adapter = new CountriesListAdapter(new ArrayList<>());

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.countriesList)
    RecyclerView countriesList;

    @BindView(R.id.listError)
    TextView listError;

    @BindView(R.id.loadingView)
    ProgressBar loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        countriesViewModel = ViewModelProviders.of(this).get(CountriesViewModel.class);
        countriesViewModel.refresh();

        countriesList.setLayoutManager(new LinearLayoutManager(this));
        countriesList.setAdapter(adapter);

        observeViewModel();
        refreshLayout.setOnRefreshListener(() -> {
            countriesList.setVisibility(View.GONE);
            listError.setVisibility(View.GONE);
            loadingView.setVisibility(View.VISIBLE);
            countriesViewModel.refreshByPassCache();
            refreshLayout.setRefreshing(false);
        });

    }

    private void observeViewModel() {
        countriesViewModel.countries.observe(this, countries -> {
            if (countries != null && countries instanceof List) {
                countriesList.setVisibility(View.VISIBLE);
                adapter.updateCountries(countries);
            }
        });

        countriesViewModel.loadError.observe(this, isError -> {
            if (isError != null && isError instanceof Boolean) {
                listError.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });

        countriesViewModel.loading.observe(this, isLoading -> {
            if (isLoading != null && isLoading instanceof Boolean)
                loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            if (isLoading) {
                listError.setVisibility(View.GONE);
                countriesList.setVisibility(View.GONE);
            }
        });
    }

}