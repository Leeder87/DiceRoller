package com.example.diceroller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnRoll, btnFormula, btnSystems, btnProperties, btnExit;
    private MediaPlayer SelectSound;
    private static final String PREFS_FILE = "DicerollerPrefs";
    private static final String PREF_SOUND = "sound";
    boolean soundOn;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SelectSound = MediaPlayer.create(this, R.raw.select_menu);
        // получаем первоначальные настройки
        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        soundOn = settings.getBoolean(PREF_SOUND, true);
        setButtons(); // Вызов метода, привязывающего к кнопкам обработчики
    }

    protected void onResume() {
        super.onResume();
        // переполучаем настройки
        soundOn = settings.getBoolean(PREF_SOUND, true);
        /*Toast.makeText(
                MainActivity.this,
                "soundOn: " + String.valueOf(soundOn),
                Toast.LENGTH_SHORT
        ).show();*/
    }

    public void soundPlay (MediaPlayer sound) {
        if(soundOn)
            sound.start();
    }

    // Метод для настройки обработчиков кнопок
    private void setButtons() {
        final Animation btnScale = AnimationUtils.loadAnimation(this, R.anim.scale);
        btnRoll = findViewById(R.id.btnRoll);
        btnFormula = findViewById(R.id.btnFormula);
        btnSystems = findViewById(R.id.btnSystems);
        btnProperties = findViewById(R.id.btnProperties);
        btnExit = findViewById(R.id.btnExit);

        btnRoll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        soundPlay(SelectSound); //это строчка является шаблоном для воспроизведения звука
                        Intent intent = new Intent(".RollActivity");
                        view.startAnimation(btnScale);
                        startActivity(intent);
                    }
                }
        );

        btnFormula.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        soundPlay(SelectSound);
                        Intent intent = new Intent(".CustomActivity");
                        view.startAnimation(btnScale);
                        startActivity(intent);
                    }
                }
        );

        btnSystems.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        soundPlay(SelectSound);
                        Intent intent = new Intent(".SystemsActivity");
                        view.startAnimation(btnScale);
                        startActivity(intent);
                    }
                }
        );

        btnProperties.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        soundPlay(SelectSound);
                        Intent intent = new Intent(".PropertiesActivity");
                        view.startAnimation(btnScale);
                        startActivity(intent);
                    }
                }
        );

        btnExit.setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View view) {
                        soundPlay(SelectSound);
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                        view.startAnimation(btnScale);
                        a_builder.setMessage("Are you sure you want to quit?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        soundPlay(SelectSound);
                                        finish();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        soundPlay(SelectSound);
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