package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.diceroller.data.DatabaseAdapter;
import com.example.diceroller.data.DatabaseHelper;
import com.example.diceroller.data.HistoryRecord;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.media.MediaPlayer;

import java.util.Date;

public class CustomActivity extends AppCompatActivity {
    private Button btnBack, btnRoll;
    private MediaPlayer SelectSound;
    private static final String PREFS_FILE = "DicerollerPrefs";
    private static final String PREF_SOUND = "sound";
    boolean soundOn;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        setButtonRoll();
        setButtons(); // Вызов метода, привязывающего к кнопкам обработчики
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
    }

    // Настраиваем обработчик кнопки Roll
    private void setButtonRoll() {
        final EditText editFormula = findViewById(R.id.editFormula);
        final TextView txtResult = findViewById(R.id.txtResultRoll);
        final Animation btnScale = AnimationUtils.loadAnimation(this, R.anim.scale);
        btnRoll = findViewById(R.id.btnRoll);

        btnRoll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        soundPlay(SelectSound);
                        view.startAnimation(btnScale);
                        String formula = editFormula.getText().toString();
                        // Создаём парсер
                        FormulaParser parser = new FormulaParser(formula);
                        if (!formula.equals("")) {
                            try {
                                // Парсим, в случае исключения перехватываем его и показываем ошибку
                                parser.parse();
                            } catch (Exception ex) {
                                // Тут надо будет залогировать исключение
                                parser.result = "Error";
                            }
                        }
                        else
                            parser.result = "Enter a throw formula:";

                        txtResult.setText(parser.result);

                        // Подготавливаем запись для истории бросков
                        HistoryRecord historyRecord = new HistoryRecord(new Date(), parser.formula,
                                parser.prettyFormula, parser.result, "Custom");
                        DatabaseHelper.insertRecord(getBaseContext(), historyRecord);
                    }
                }
        );
    }
}