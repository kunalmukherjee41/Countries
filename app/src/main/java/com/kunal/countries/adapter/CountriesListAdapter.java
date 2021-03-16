package com.kunal.countries.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kunal.countries.R;
import com.kunal.countries.databinding.CountriesListBinding;
import com.kunal.countries.model.Countries;

import java.util.ArrayList;
import java.util.List;

public class CountriesListAdapter extends RecyclerView.Adapter<CountriesListAdapter.CountriesViewHolder> {

    ArrayList<Countries> countries;

    public CountriesListAdapter(ArrayList<Countries> countries) {
        this.countries = countries;
    }

    public void updateCountries(List<Countries> countriesList) {
        countries.clear();
        countries.addAll(countriesList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CountriesListBinding view = DataBindingUtil.inflate(inflater, R.layout.countries_list, parent, false);
        return new CountriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesViewHolder holder, int position) {
        holder.listBinding.setCountries(countries.get(position));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class CountriesViewHolder extends RecyclerView.ViewHolder {

        public CountriesListBinding listBinding;

        public CountriesViewHolder(@NonNull CountriesListBinding itemView) {
            super(itemView.getRoot());

            listBinding = itemView;
        }
    }
}
