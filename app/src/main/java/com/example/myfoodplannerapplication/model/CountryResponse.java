package com.example.myfoodplannerapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryResponse {
    @SerializedName("meals")
    private List<Country> countries;

    public CountryResponse(List<Country> _countries) {
        this.countries = _countries;
    }

    public List<Country> getCountries() {
        return countries;
    }
}
