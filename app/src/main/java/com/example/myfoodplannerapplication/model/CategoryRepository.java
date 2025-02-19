package com.example.myfoodplannerapplication.model;

import com.example.myfoodplannerapplication.network.CategoryNetworkCallback;
import com.example.myfoodplannerapplication.network.CategoryRemoteDataSource;

public class CategoryRepository {

    private static CategoryRepository categoryRepository = null;

    private CategoryRemoteDataSource categoryRemoteDataSource;

    private CategoryRepository(CategoryRemoteDataSource remoteDataSource) {
        this.categoryRemoteDataSource = remoteDataSource;
    }

    public static CategoryRepository getInstance(CategoryRemoteDataSource categoryRemoteDataSource) {
        if (categoryRepository == null) {
            categoryRepository = new CategoryRepository(categoryRemoteDataSource);
        }
        return categoryRepository;
    }

    public void getAllData(CategoryNetworkCallback networkCallBack) {
        categoryRemoteDataSource.getCategoryOverNetwork(networkCallBack);
    }

}
