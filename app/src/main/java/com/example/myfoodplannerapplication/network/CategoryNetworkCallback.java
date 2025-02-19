package com.example.myfoodplannerapplication.network;

import com.example.myfoodplannerapplication.model.Category;

import java.util.List;

public interface CategoryNetworkCallback {

    void onSuccess(List<Category> categoryList);

    void onFailure(String errorMessage);
}
