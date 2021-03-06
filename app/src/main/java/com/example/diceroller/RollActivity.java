package com.example.diceroller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.media.MediaPlayer;
import com.example.diceroller.data.DatabaseHelper;
import com.example.diceroller.data.HistoryRecord;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import java.util.Date;

public class RollActivity extends AppCompatActivity {
    private Button btnD20, btnD12, btnD10, btnD8, btnD6, btnD4, btnD100;
    private TextView text_d20, text_d12, text_d10, text_d8, text_d6, text_d4, text_d100;
    private Button btnBack, btnMiniHistory;
    private TextView resultTextView;

    SharedPreferences settings;
    private long speed;
    private int numberOfRolls;
    private static final String PREFS_FILE = "DicerollerPrefs";
    private static final String PREF_NOR = "nor";
    private static final String PREF_SPEED = "speed";
    private static final String PREF_SOUND = "sound";
    boolean soundOn;
    private MediaPlayer SelectSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll);

        // получаем первоначальные настройки
        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        speed = settings.getLong(PREF_SPEED, 1000);
        numberOfRolls = settings.getInt(PREF_NOR, 1);
        SelectSound = MediaPlayer.create(this, R.raw.select_menu);
        // получаем первоначальные настройки
        soundOn = settings.getBoolean(PREF_SOUND, true);

        setButtons(); // Вызов метода, привязывающего к кнопкам обработчики
    }

    protected void onResume() {
        super.onResume();
        // переполучаем настройки
        speed = settings.getLong(PREF_SPEED, 1000);
        numberOfRolls = settings.getInt(PREF_NOR, 1);
        soundOn = settings.getBoolean(PREF_SOUND, true);
    }

    public void soundPlay (MediaPlayer sound) {
        if(soundOn)
            sound.start();
    }

    // Метод для настройки обработчиков кнопок
    private void setButtons() {
        final Animation btnScale = AnimationUtils.loadAnimation(this, R.anim.scale);
        btnD20 = findViewById(R.id.btnD20);
        btnD12 = findViewById(R.id.btnD12);
        btnD10 = findViewById(R.id.btnD10);
        btnD8 = findViewById(R.id.btnD8);
        btnD6 = findViewById(R.id.btnD6);
        btnD4 = findViewById(R.id.btnD4);
        btnD100 = findViewById(R.id.btnD100);
        btnBack = findViewById(R.id.btnBack);
        btnMiniHistory = findViewById(R.id.btnMiniHistory);
        text_d20 = findViewById(R.id.text_d20);
        text_d12 = findViewById(R.id.text_d12);
        text_d10 = findViewById(R.id.text_d10);
        text_d8 = findViewById(R.id.text_d8);
        text_d6 = findViewById(R.id.text_d6);
        text_d4 = findViewById(R.id.text_d4);
        text_d100 = findViewById(R.id.text_d100);

        // Этот список кнопок нужен для оптимизации блокировки и разблокировки кнопок и подписей
        final View[] viewList = new View[] {btnD4, btnD6, btnD8, btnD10, btnD12, btnD20, btnD100,
                text_d4, text_d6, text_d8, text_d10, text_d12, text_d20, text_d100};
        resultTextView = findViewById(R.id.txtResultRoll);

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

        // Далее мы назначаем каждой кнопке обработчик события onClick с аналогичным функционалом.
        // Он отличается только числом граней на кубике. Это пригодится в дальнейшем.
        btnD20.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        soundPlay(SelectSound);
                        view.startAnimation(btnScale);
                        onBtnClick(viewList, view,20);
                    }
                }
        );

        btnD12.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        soundPlay(SelectSound);
                        view.startAnimation(btnScale);
                        onBtnClick(viewList, view, 12);
                    }
                }
        );

        btnD10.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        soundPlay(SelectSound);
                        view.startAnimation(btnScale);
                        onBtnClick(viewList, view, 10);
                    }
                }
        );

        btnD8.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        soundPlay(SelectSound);
                        view.startAnimation(btnScale);
                        onBtnClick(viewList, view, 8);
                    }
                }
        );

        btnD6.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        soundPlay(SelectSound);
                        view.startAnimation(btnScale);
                        onBtnClick(viewList, view, 6);
                    }
                }
        );

        btnD4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        soundPlay(SelectSound);
                        view.startAnimation(btnScale);
                        onBtnClick(viewList, view, 4);
                    }
                }
        );

        btnD100.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        soundPlay(SelectSound);
                        view.startAnimation(btnScale);
                        onBtnClick(viewList, view, 100);
                    }
                }
        );

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
    }

    // Этот метод объединяет универсальную функциональность для кнопок-дайсов.
    private void onBtnClick(final View[] viewList, View source, final int i) {
        // Заблокируем все кнопки-дайсы
        for (View view : viewList) {
            if(view != source) {
                view.setEnabled(false);
                view.setAlpha((float) 0.5);
            }
        }

        String labelId = "text_d" + i;
        int resID = getResources().getIdentifier(labelId, "id", getPackageName());
        TextView label = ((TextView) findViewById(resID));
        label.setEnabled(true);
        label.setAlpha((float) 1);

        // Генерируем криптослучайное число от 1 до i - максимального числа граней на кубике
        int result = RandomGenerator.generateInt(i);
        String strResult = String.valueOf(result);
        // Запишем в окно вывода получившееся число
        resultTextView.setText(strResult);
        if (numberOfRolls == 1)
            FinalizeRoll(strResult, i, viewList);
        else {
            int counter = 2;
            // Пауза между декоративными бросками кубика в миллисекундах.
            // Берём это время из настроек.
            final long changeTime = speed;
            // Тут нужно пояснение. Если просто делать Thread.sleep(1000), то в итоге обработчик
            // обновит поле только один раз после всех пауз. Видимо, он не может обновить поле,
            // пока не передаст управление обратно в вызывающий класс. Однако есть метод
            // postDelayed, который с помощью класса Runnable может запланировать выполнение
            // некоих действий через некоторое время (которое мы определили в changeTime).
            // Таким образом мы создаём несколько отложенных заданий.
            for (;counter < numberOfRolls; counter++) {
                resultTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int result = RandomGenerator.generateInt(i);
                        String strResult = String.valueOf(result);
                        resultTextView.setText(strResult);
                    }
                }, changeTime * (counter - 1));
            }
            // Последний отложенный бросок отличается тем,
            // что после него нужно записать историю и снова сделать кнопки доступными
            resultTextView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int result = RandomGenerator.generateInt(i);
                    String strResult = String.valueOf(result);
                    resultTextView.setText(strResult);
                    FinalizeRoll(strResult, i, viewList);
                }
            }, changeTime * (counter - 1));
        }
    }

    private void FinalizeRoll(String strResult, int i, View[] viewList) {
        // Подготавливаем запись для истории бросков
        HistoryRecord historyRecord = new HistoryRecord(new Date(), "1d" + i,
                "1d" + i, strResult, "Simple Roll");
        DatabaseHelper.insertRecord(getBaseContext(), historyRecord);

        // В последнем отложенном задании снова разблокируем кнопки
        for (View view : viewList) {
            view.setEnabled(true);
            view.setAlpha((float) 1);
        }
    }


}