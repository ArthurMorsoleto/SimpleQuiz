package com.amb.simplequiz.ui;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amb.simplequiz.data.PreferencesImpl;
import com.amb.simplequiz.data.Quiz;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class QuizViewModel extends ViewModel {

    private final MutableLiveData<Quiz> _quizListLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _quizMaxCountLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _currentQuestionAnswerLiveData = new MutableLiveData<>();
    private final PreferencesImpl preferences;

    private ArrayList<Quiz> quizList = new ArrayList<>();
    private int quizCount = 0;
    private int quizSize = 0;
    private int correctAnswersCount = 0;

    public MutableLiveData<Quiz> quizLiveData() {
        return _quizListLiveData;
    }

    public MutableLiveData<Boolean> quizMaxCountLiveData() {
        return _quizMaxCountLiveData;
    }

    public MutableLiveData<Boolean> currentQuestionAnswerLiveData() {
        return _currentQuestionAnswerLiveData;
    }

    public QuizViewModel(SharedPreferences sharedPreferences) {
        this.preferences = new PreferencesImpl(sharedPreferences);
    }

    public void getQuizData() {
        String quizString = preferences.getQuizData();
        quizList = getQuizFromJson(quizString);
        quizSize = quizList.size();

        Collections.shuffle(quizList);
        _quizListLiveData.postValue(quizList.get(quizCount));
    }

    public double getCorrectAnswers() {
        return ((double) correctAnswersCount * 100 / quizSize);
    }

    public void onButtonClick(boolean answer) {
        if (quizCount == quizSize) {
            _quizMaxCountLiveData.postValue(true);
        } else {
            Quiz currentQuestion = quizList.get(quizCount);
            _currentQuestionAnswerLiveData.postValue(answer == currentQuestion.getAnswer());
            _quizListLiveData.postValue(currentQuestion);

            if (answer == currentQuestion.getAnswer()) correctAnswersCount++;
            quizCount++;
        }
    }

    private ArrayList<Quiz> getQuizFromJson(String todoJson) {
        Type todoListType = new TypeToken<ArrayList<Quiz>>() {
        }.getType();
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(todoJson, todoListType);
    }
}
