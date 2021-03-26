package com.amb.simplequiz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.amb.simplequiz.R;
import com.amb.simplequiz.factory.QuizViewModelFactory;

import static com.amb.simplequiz.data.PreferencesImpl.CORRECT_ANSWERS;
import static com.amb.simplequiz.data.PreferencesImpl.QUIZ_APP;

public class QuizActivity extends AppCompatActivity {

    private QuizViewModel quizViewModel;

    private TextView questionText;
    private CardView cardView;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quizViewModel = new ViewModelProvider(this, new QuizViewModelFactory(getSharedPreferences(QUIZ_APP, MODE_PRIVATE))).get(QuizViewModel.class);
        quizViewModel.getQuizData();
        observeQuizLiveData();
        initViews();
    }

    private void initViews() {
        TextView falseButton = (TextView) findViewById(R.id.tvFalse);
        TextView trueButton = (TextView) findViewById(R.id.tvTrue);
        questionText = (TextView) findViewById(R.id.tvQuestion);
        cardView = (CardView) findViewById(R.id.cardView);
        loading = (ProgressBar) findViewById(R.id.loading);

        cardView.setVisibility(View.GONE);

        falseButton.setOnClickListener(view -> {
            handleLoading(true);
            new Handler().postDelayed(() -> quizViewModel.onButtonClick(false), 500);
        });

        trueButton.setOnClickListener(view -> {
            handleLoading(true);
            new Handler().postDelayed(() -> quizViewModel.onButtonClick(true), 500);
        });
    }

    private void observeQuizLiveData() {
        quizViewModel.quizLiveData().observe(this, currentQuestion -> {
            handleLoading(false);

            questionText.setText(currentQuestion.getQuestion());
        });

        quizViewModel.currentQuestionAnswerLiveData().observe(this, answer -> {
            String toastText;
            if (answer) {
                toastText = "Certa Resposta";
            } else {
                toastText = "Resposta Errada";
            }
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
        });

        quizViewModel.quizMaxCountLiveData().observe(this, maxCount -> {
            if (maxCount) {
                double correctAnswers = quizViewModel.getCorrectAnswers();
                startActivity(new Intent(this, ResultActivity.class).putExtra(CORRECT_ANSWERS, correctAnswers));
            }
        });
    }

    private void handleLoading(Boolean showLoading) {
        if (showLoading) {
            loading.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.GONE);
        } else {
            loading.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);
        }
    }
}