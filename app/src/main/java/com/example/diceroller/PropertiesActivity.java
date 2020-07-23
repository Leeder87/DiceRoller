package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.media.MediaPlayer;

public class PropertiesActivity extends AppCompatActivity {
    private Button btnBack, btnMiniHistory;
    private SeekBar seekBarSpeed, seekBarNoR;
    private Switch switchSound;
    boolean soundOn;
    private MediaPlayer SelectSound;
    private static final String PREFS_FILE = "DicerollerPrefs";
    private static final String PREF_SOUND = "sound";
    private static final String PREF_NOR = "nor";
    private static final String PREF_SPEED = "speed";
    SharedPreferences settings;
    SharedPreferences.Editor prefEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);
        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        SelectSound = MediaPlayer.create(this, R.raw.select_menu);
        // получаем первоначальные настройки
        soundOn = settings.getBoolean(PREF_SOUND, true);
        setButtons();
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

    //Кнопка назад
    private void setButtons() {
        final Animation btnScale = AnimationUtils.loadAnimation(this, R.anim.scale);
        btnBack = findViewById(R.id.btnBack);
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

        //Кнопка Info
        btnMiniHistory = findViewById(R.id.btnMiniHistory);
        btnMiniHistory.setOnClickListener(
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

        seekBarSpeed = findViewById(R.id.seekBarSpeed);
        seekBarNoR = findViewById(R.id.seekBarNoR);
        switchSound = findViewById(R.id.switchSound);
        // получаем настройки
        boolean soundOn = settings.getBoolean(PREF_SOUND, true);
        switchSound.setChecked(soundOn);
        // В настройках время в миллисекундах, в сикбаре - в единицах.
        // При этом 1 деление сикбара соответствует 500 миллисекундам.
        long speed = settings.getLong(PREF_SPEED, 1)/500;
        seekBarSpeed.setProgress((int)speed);
        // В настройках от 1 до 5, в сикбаре от 0 до 4.
        int numberOfRolls = settings.getInt(PREF_NOR, 1)-1;
        seekBarNoR.setProgress(numberOfRolls);
    }

    @Override
    protected void onPause(){
        super.onPause();

        boolean soundOn = switchSound.isChecked();
        long speed = seekBarSpeed.getProgress()*500;
        int numberOfRolls = seekBarNoR.getProgress() + 1;

        // сохраняем в настройках
        prefEditor = settings.edit();
        prefEditor.putBoolean(PREF_SOUND, soundOn);
        prefEditor.putLong(PREF_SPEED, speed);
        prefEditor.putInt(PREF_NOR, numberOfRolls);
        prefEditor.apply();
    }

}