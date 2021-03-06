package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.media.MediaPlayer;

public class InfoActivity extends AppCompatActivity {
    private Button btnHistory,btnBack;
    private MediaPlayer SelectSound;
    private static final String PREFS_FILE = "DicerollerPrefs";
    private static final String PREF_SOUND = "sound";
    boolean soundOn;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
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
        btnHistory = findViewById(R.id.btnHistory);
        btnBack = findViewById(R.id.btnBack);

        btnHistory.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        soundPlay(SelectSound);
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
                        soundPlay(SelectSound);
                        view.startAnimation(btnScale);
                        finish();
                    }
                }
        );
    }
}