package com.amb.simplequiz.ui;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amb.simplequiz.data.PreferencesImpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SplashPageViewModel extends ViewModel {

    public static final String FILE_PATH_QUIZ = "quiz.json";

    private final MutableLiveData<Boolean> _firstAccessLiveData = new MutableLiveData<>();
    private final PreferencesImpl preferences;

    public SplashPageViewModel(SharedPreferences sharedPreferences) {
        this.preferences = new PreferencesImpl(sharedPreferences);
    }

    public MutableLiveData<Boolean> firstAccessLiveData() {
        return _firstAccessLiveData;
    }

    public void getUserAccess() {
        boolean firstAccess = preferences.getFirstAccess();
        _firstAccessLiveData.postValue(firstAccess);

        if (firstAccess) {
            preferences.setFirstAccess();
        }
    }

    public void getQuizData(Context applicationContext) {
        String quizString = readJsonFromFile(applicationContext);
        preferences.saveQuizList(quizString);
    }

    public String readJsonFromFile(Context context) {
        String json = "";
        try {
            InputStream inputStream = context.getAssets().open(FILE_PATH_QUIZ);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
