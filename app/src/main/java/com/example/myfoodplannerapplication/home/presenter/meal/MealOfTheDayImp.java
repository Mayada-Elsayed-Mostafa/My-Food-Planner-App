package com.example.myfoodplannerapplication.home.presenter.meal;

import com.example.myfoodplannerapplication.home.view.meal.MealOfTheDayView;
import com.example.myfoodplannerapplication.model.InspirationMealResponse;
import com.example.myfoodplannerapplication.model.MealRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealOfTheDayImp implements MealOfTheDay {

    private MealRepository mealRepository;
    private MealOfTheDayView mealOfTheDayView;
    private InspirationMealResponse inspirationMealResponse;

    public MealOfTheDayImp(MealRepository _mealRepository, MealOfTheDayView _mealOfTheDayView) {
        this.mealRepository = _mealRepository;
        this.mealOfTheDayView = _mealOfTheDayView;
    }


    @Override
    public void getMeal() {
        mealRepository.getMealFromNetwork()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<InspirationMealResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(InspirationMealResponse mealResponse) {
                        mealOfTheDayView.setData(mealResponse.getMeals());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mealOfTheDayView.showErrMsg("There are an Error : " + e.getMessage());
                    }
                });
    }


}
