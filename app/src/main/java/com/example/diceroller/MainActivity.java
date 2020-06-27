package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnRoll, btnFormula, btnSystems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setButtons(); // Вызов метода, привязывающего к кнопкам обработчики
    }

    // Метод для настройки обработчиков кнопок
    private void setButtons() {
        btnRoll = findViewById(R.id.btnRoll);
        // Следующие две кнопки пока ничего не делают, но проинициализируем их заранее
        btnFormula = findViewById(R.id.btnFormula);
        btnSystems = findViewById(R.id.btnSystems);

        btnRoll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(".RollActivity");
                        startActivity(intent);
                    }
                }
        );
        btnFormula.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(".CustomActivity");
                        startActivity(intent);
                    }
                }
        );
    }
}