package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;

public class PropertiesActivity extends AppCompatActivity {
    private Button btnBack, btnInfo;
    private SeekBar seekBarSpeed, seekBarNoR;
    private Switch switchSound;

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

        seekBarSpeed = findViewById(R.id.seekBarSpeed);
        seekBarNoR = findViewById(R.id.seekBarNoR);
        switchSound = findViewById(R.id.switchSound);
        // получаем настройки
        boolean soundOn = settings.getBoolean(PREF_SOUND, true);
        switchSound.setChecked(soundOn);
    }

    @Override
    protected void onPause(){
        super.onPause();

        //EditText nameBox = (EditText) findViewById(R.id.nameBox);
        //String name = nameBox.getText().toString();

        boolean soundOn = switchSound.isChecked();
        // сохраняем в настройках
        prefEditor = settings.edit();
        prefEditor.putBoolean(PREF_SOUND, soundOn);
        prefEditor.apply();
    }

}