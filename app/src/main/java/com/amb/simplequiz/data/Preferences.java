package com.amb.simplequiz.data;

public interface Preferences {

    boolean getFirstAccess();

    void saveQuizList(String quizString);

    void setFirstAccess();

    String getQuizData();
}
