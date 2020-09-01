package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.media.MediaPlayer;
import android.widget.ImageView;

public class FateActivity extends AppCompatActivity {
    private Button btnBack, btnRoll;
    private MediaPlayer SelectSound;
    private static final String PREFS_FILE = "DicerollerPrefs";
    private static final String PREF_SOUND = "sound";
    boolean soundOn;
    SharedPreferences settings;
    private long speed;
    private static final String PREF_SPEED = "speed";
    private ImageView imgFirst, imgSecond, imgThird, imgFourth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fate);

        SelectSound = MediaPlayer.create(this, R.raw.select_menu);
        // получаем первоначальные настройки
        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        soundOn = settings.getBoolean(PREF_SOUND, true);
        speed = settings.getLong(PREF_SPEED, 500);

        setButtons(); // Вызов метода, привязывающего к кнопкам обработчики
    }

    protected void onResume() {
        super.onResume();
        // переполучаем настройки
        soundOn = settings.getBoolean(PREF_SOUND, true);
        speed = settings.getLong(PREF_SPEED, 500);
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

        btnRoll = findViewById(R.id.btnRoll);

        btnRoll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rollFudgeDice(view);
                    }
                }
        );
    }

    private void rollFudgeDice(View view) {
        final Animation btnScale = AnimationUtils.loadAnimation(this, R.anim.scale);
        soundPlay(SelectSound);
        view.startAnimation(btnScale);

        imgFirst = findViewById(R.id.imgFirst);
        imgSecond = findViewById(R.id.imgSecond);
        imgThird = findViewById(R.id.imgThird);
        imgFourth = findViewById(R.id.imgFourth);
        imgFirst.setImageResource(R.drawable.fate_empty);
        imgSecond.setImageResource(R.drawable.fate_empty);
        imgThird.setImageResource(R.drawable.fate_empty);
        imgFourth.setImageResource(R.drawable.fate_empty);

        FateDieResult firstDieResult = RandomGenerator.generateFudge();
        SetFudgePicture(imgFirst, firstDieResult);

        // Два варианта: мгновенное отображение или постепенное, с анимацией
        if (speed == 0) {
            FateDieResult secondDieResult = RandomGenerator.generateFudge();
            FateDieResult thirdDieResult = RandomGenerator.generateFudge();
            FateDieResult fourthDieResult = RandomGenerator.generateFudge();

            SetFudgePicture(imgSecond, secondDieResult);
            SetFudgePicture(imgThird, thirdDieResult);
            SetFudgePicture(imgFourth, fourthDieResult);

        } else {
            btnRoll.setEnabled(false);
            btnRoll.setAlpha((float) 0.5);

            final long changeTime = speed;
            int counter = 1;
            // Создаём несколько отложенных заданий.
            btnRoll.postDelayed(new Runnable() {
                    @Override
                public void run() {
                    FateDieResult secondDieResult = RandomGenerator.generateFudge();
                    SetFudgePicture(imgSecond, secondDieResult);
                }
            }, changeTime);
            btnRoll.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FateDieResult thirdDieResult = RandomGenerator.generateFudge();
                    SetFudgePicture(imgThird, thirdDieResult);
                }
            }, changeTime * 2);
            btnRoll.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FateDieResult fourthDieResult = RandomGenerator.generateFudge();
                    SetFudgePicture(imgFourth, fourthDieResult);
                    btnRoll.setEnabled(true);
                    btnRoll.setAlpha((float) 1);
                }
            }, changeTime * 3);

        }

    }

    private void SetFudgePicture(ImageView img, FateDieResult dieResult) {
        switch (dieResult) {
            case MINUS:
                img.setImageResource(R.drawable.fate_minus);
                break;

            case EMPTY:
                img.setImageResource(R.drawable.fate_empty);
                break;

            case PLUS:
                img.setImageResource(R.drawable.fate_plus);
                break;
        }
    }

}