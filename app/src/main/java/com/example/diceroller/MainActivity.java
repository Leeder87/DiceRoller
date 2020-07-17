package com.example.diceroller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnRoll, btnFormula, btnSystems, btnProperties, btnExit;
    private MediaPlayer SelectSound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SelectSound = MediaPlayer.create(this, R.raw.select);

        setButtons(); // Вызов метода, привязывающего к кнопкам обработчики
    }

    public void soundPlay (MediaPlayer sound) {
        sound.start();
    }

    // Метод для настройки обработчиков кнопок
    private void setButtons() {
        btnRoll = findViewById(R.id.btnRoll);
        btnFormula = findViewById(R.id.btnFormula);
        btnSystems = findViewById(R.id.btnSystems);
        btnProperties = findViewById(R.id.btnProperties);
        btnExit = findViewById(R.id.btnExit);

        btnRoll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //soundPlay(SelectSound); //это строчка является шаблоном для воспроизведения звука
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
        btnSystems.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(".SystemsActivity");
                        startActivity(intent);
                    }
                }
        );
        btnProperties.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(".PropertiesActivity");
                        startActivity(intent);
                    }
                }
        );

        btnExit.setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                        a_builder.setMessage("Are you sure you want to quit?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("Close application");
                        alert.show();
                    }
                }
        );
    }
}