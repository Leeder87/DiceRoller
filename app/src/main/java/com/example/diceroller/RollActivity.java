package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.diceroller.data.DatabaseHelper;
import com.example.diceroller.data.HistoryRecord;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import java.security.SecureRandom;
import java.util.Date;

public class RollActivity extends AppCompatActivity {
    private Button btnD20, btnD12, btnD10, btnD8, btnD6, btnD4, btnD100;
    private Button btnBack;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll);

        setButtons(); // Вызов метода, привязывающего к кнопкам обработчики
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
        // Этот список кнопок нужен для оптимизации блокировки и разблокировки кнопок
        final Button[] btnList = new Button[] {btnD4, btnD6, btnD8, btnD10, btnD12, btnD20, btnD100};
        resultTextView = findViewById(R.id.txtResultRoll);

        btnBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                        view.startAnimation(btnScale);
                        onBtnClick(btnList, (Button) view,20);
                    }
                }
        );

        btnD12.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.startAnimation(btnScale);
                        onBtnClick(btnList, (Button) view, 12);
                    }
                }
        );

        btnD10.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.startAnimation(btnScale);
                        onBtnClick(btnList, (Button) view, 10);
                    }
                }
        );

        btnD8.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.startAnimation(btnScale);
                        onBtnClick(btnList, (Button) view, 8);
                    }
                }
        );

        btnD6.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.startAnimation(btnScale);
                        onBtnClick(btnList, (Button) view, 6);
                    }
                }
        );

        btnD4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.startAnimation(btnScale);
                        onBtnClick(btnList, (Button) view, 4);
                    }
                }
        );

        btnD100.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.startAnimation(btnScale);
                        onBtnClick(btnList, (Button) view, 100);
                    }
                }
        );

    }

    // Этот метод объединяет универсальную функциональность для кнопок-дайсов.
    private void onBtnClick(final Button[] btnList, Button source, final int i) {
        // Заблокируем все кнопки-дайсы
        for (Button btn : btnList) {
            if(btn != source) {
                btn.setEnabled(false);
                btn.setAlpha((float) 0.5);
            }
        }

        // Генерируем криптослучайное число от 1 до i - максимального числа граней на кубике
        int result = RandomGenerator.generateInt(i);
        String strResult = String.valueOf(result);
        // Запишем в окно вывода получившееся число
        resultTextView.setText(strResult);

        // 1000 миллисекунд - пауза между декоративными бросками кубика.
        // В итоге надо будет брать это время из настроек.
        final long changeTime = 1000L;
        // Тут нужно пояснение. Если просто делать Thread.sleep(1000), то в итоге обработчик
        // обновит поле только один раз после всех пауз. Видимо, он не может обновить поле,
        // пока не передаст управление обратно в вызывающий класс. Однако есть метод
        // postDelayed, который с помощью класса Runnable может запланировать выполнение
        // некоих действий через некоторое время (которое мы определили в changeTime).
        // Таким образом мы создаём несколько отложенных заданий.
        resultTextView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int result = RandomGenerator.generateInt(i);
                String strResult = String.valueOf(result);
                resultTextView.setText(strResult);
            }
        }, changeTime);
        resultTextView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int result = RandomGenerator.generateInt(i);
                String strResult = String.valueOf(result);
                resultTextView.setText(strResult);

                // Подготавливаем запись для истории бросков
                HistoryRecord historyRecord = new HistoryRecord(new Date(), "1d" + i,
                        "1d" + i, strResult, "Simple Roll");
                DatabaseHelper.insertRecord(getBaseContext(), historyRecord);

                // В последнем отложенном задании снова разблокируем кнопки
                for (Button btn : btnList) {
                    btn.setEnabled(true);
                    btn.setAlpha((float) 1);
                }
            }
        }, changeTime * 2);
    }


}