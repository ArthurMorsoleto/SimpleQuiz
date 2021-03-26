package com.amb.simplequiz.factory;

import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.amb.simplequiz.ui.SplashPageViewModel;

public class SplashPageViewModelFactory implements ViewModelProvider.Factory {
    private final SharedPreferences mSharedPreferences;

    public SplashPageViewModelFactory(SharedPreferences sharedPreferences) {
        this.mSharedPreferences = sharedPreferences;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new SplashPageViewModel(mSharedPreferences);
    }
}
