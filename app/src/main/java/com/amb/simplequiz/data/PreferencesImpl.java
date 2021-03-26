package com.amb.simplequiz.data;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

public class PreferencesImpl implements Preferences {

    public static final String FIRST_ACCESS = "FIRST_ACCESS";
    public static final String QUIZ_LIST = "QUIZ_LIST";
    public static final String QUIZ_APP = "QUIZ_APP";
    public static final String CORRECT_ANSWERS = "CORRECT_ANSWERS";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public PreferencesImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.editor = sharedPreferences.edit();
    }

    @Override
    public void saveQuizList(String quizString) {
        this.editor.putString(QUIZ_LIST, quizString).apply();
        this.sharedPreferences.getString(QUIZ_LIST, "");
    }

    @Override
    public boolean getFirstAccess() {
        return this.sharedPreferences.getBoolean(FIRST_ACCESS, true);
    }

    @Override
    public void setFirstAccess() {
        this.editor.putBoolean(FIRST_ACCESS, false).apply();
    }

    @Override
    public String getQuizData() {
        return this.sharedPreferences.getString(QUIZ_LIST, "");
    }
}
