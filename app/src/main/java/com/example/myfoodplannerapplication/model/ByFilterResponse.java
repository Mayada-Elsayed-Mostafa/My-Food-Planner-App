package com.example.myfoodplannerapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ByFilterResponse {

    @SerializedName("meals")
    private List<FilterBy> byFilters;

    public ByFilterResponse(List<FilterBy> _byFilters) {
        this.byFilters = _byFilters;
    }

    public List<FilterBy> getByFilters() {
        return byFilters;
    }
}
