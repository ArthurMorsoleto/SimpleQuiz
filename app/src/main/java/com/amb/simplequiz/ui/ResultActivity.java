package com.amb.simplequiz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amb.simplequiz.R;

import java.text.DecimalFormat;

import static com.amb.simplequiz.data.PreferencesImpl.CORRECT_ANSWERS;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        double result = getIntent().getDoubleExtra(CORRECT_ANSWERS, 0);
        TextView resultText = (TextView) findViewById(R.id.tvResult);

        resultText.setText(String.format("%s%%  de Acertos", (int) result));

        findViewById(R.id.tvRestart).setOnClickListener(view -> {
            startActivity(new Intent(this, QuizActivity.class));
        });
    }
}