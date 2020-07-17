package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class PropertiesActivity extends AppCompatActivity {
    private Button btnBack, btnInfo;

    @Override
        //назовем это телом
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);

        setButtons();
    }
        //Кнопка назад
    private void setButtons() {
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }
        );
    //Кнопка Info
        btnInfo = findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(".InfoActivity");
                        startActivity(intent);
                    }
                }
        );
    }

}