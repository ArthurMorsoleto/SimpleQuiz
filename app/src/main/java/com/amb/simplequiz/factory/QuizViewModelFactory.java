package com.amb.simplequiz.factory;

import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.amb.simplequiz.ui.QuizViewModel;

public class QuizViewModelFactory implements ViewModelProvider.Factory {
    private final SharedPreferences mSharedPreferences;

    public QuizViewModelFactory(SharedPreferences sharedPreferences) {
        this.mSharedPreferences = sharedPreferences;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new QuizViewModel(mSharedPreferences);
    }
}
