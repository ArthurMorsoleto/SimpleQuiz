package com.amb.simplequiz.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.amb.simplequiz.R;
import com.amb.simplequiz.factory.SplashPageViewModelFactory;

import static com.amb.simplequiz.data.PreferencesImpl.QUIZ_APP;

public class SplashPageActivity extends AppCompatActivity {

    public static final long DELAY_MILLIS = 3000L;

    private SplashPageViewModel splashPageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        splashPageViewModel = new ViewModelProvider(this, new SplashPageViewModelFactory(getSharedPreferences(QUIZ_APP, MODE_PRIVATE))).get(SplashPageViewModel.class);
        splashPageViewModel.getUserAccess();

        observeAccessLiveData();
    }

    private void observeAccessLiveData() {
        splashPageViewModel.firstAccessLiveData().observe(this, firstAccess -> {
            if (firstAccess) {
                setContentView(R.layout.activity_splash_page);
                splashPageViewModel.getQuizData(getApplicationContext());
                new Handler().postDelayed(this::redirectToMainScreen, DELAY_MILLIS);
            } else {
                redirectToMainScreen();
            }
        });
    }

    private void redirectToMainScreen() {
        startActivity(new Intent(SplashPageActivity.this, QuizActivity.class));
    }
}