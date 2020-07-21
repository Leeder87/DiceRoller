package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class InfoActivity extends AppCompatActivity {
    private Button btnHistory,btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        setButtons(); // Вызов метода, привязывающего к кнопкам обработчики
    }

    // Метод для настройки обработчиков кнопок
    private void setButtons() {
        final Animation btnScale = AnimationUtils.loadAnimation(this, R.anim.scale);
        btnHistory = findViewById(R.id.btnHistory);
        btnBack = findViewById(R.id.btnBack);

        btnHistory.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.startAnimation(btnScale);
                        Intent intent = new Intent(".HistoryActivity");
                        startActivity(intent);
                    }
                }
        );

        btnBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.startAnimation(btnScale);
                        finish();
                    }
                }
        );
    }
}