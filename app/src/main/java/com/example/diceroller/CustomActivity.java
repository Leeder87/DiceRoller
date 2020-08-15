package com.example.diceroller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import java.util.prefs.Preferences;

public class CustomActivity extends AppCompatActivity {
    private Button btnBack, btnRoll, btnInfo, btnMiniHistory, btnMem1, btnMem2, btnMem3;
    private EditText editFormula;
    private MediaPlayer SelectSound;
    private static final String PREFS_FILE = "DicerollerPrefs";
    private static final String PREF_SOUND = "sound";
    boolean soundOn;
    SharedPreferences settings;
    SharedPreferences thisSettings;
    SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        setButtonRoll();
        SelectSound = MediaPlayer.create(this, R.raw.select_menu);
        // получаем первоначальные настройки
        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        thisSettings = getPreferences(MODE_PRIVATE);
        soundOn = settings.getBoolean(PREF_SOUND, true);
        setButtons(); // Вызов метода, привязывающего к кнопкам обработчики
    }
    protected void onResume() {
        super.onResume();
        // переполучаем настройки
        soundOn = settings.getBoolean(PREF_SOUND, true);
        // при перезагрузке активити надо обновить надписи на кнопках
        setMemoryButtons(btnMem1);
        setMemoryButtons(btnMem2);
        setMemoryButtons(btnMem3);
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
        editFormula = findViewById(R.id.editFormula);
        btnBack = findViewById(R.id.btnBack);
        btnInfo = findViewById(R.id.btnInfo);
        btnMiniHistory = findViewById(R.id.btnMiniHistory);
        btnMem1 = findViewById(R.id.btnMem1);
        btnMem2 = findViewById(R.id.btnMem2);
        btnMem3 = findViewById(R.id.btnMem3);

        btnMem1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setMemoryButtons(btnMem1);
                        String formula = btnMem1.getText().toString();
                        if (!formula.equals("Memory"))
                            StartParse(view, formula);
                    }
                }
        );
        btnMem1.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        flushMemoryButton(btnMem1);
                        return true;
                    }
                }
        );

        btnMem2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setMemoryButtons(btnMem2);
                        String formula = btnMem2.getText().toString();
                        if (!formula.equals("Memory"))
                            StartParse(view, formula);
                    }
                }
        );
        btnMem2.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        flushMemoryButton(btnMem2);
                        return true;
                    }
                }
        );

        btnMem3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setMemoryButtons(btnMem3);
                        String formula = btnMem3.getText().toString();
                        if (!formula.equals("Memory"))
                            StartParse(view, formula);
                    }
                }
        );
        btnMem3.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        flushMemoryButton(btnMem3);
                        return true;
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

        btnInfo.setOnClickListener(
                new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View view) {

                        soundPlay(SelectSound);
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(CustomActivity.this, R.style.AlertDialogStyle);
                        view.startAnimation(btnScale);
                        a_builder.setMessage("You can use XdY blocks (where X and Y - positive integers) " +
                                "and arithmetic signs \"+\", \"-\" and \"*\" . \n\n" +
                                "Spaces around the signs can be used at will. \n\n" +
                                "Example: 2d20-1d6*4 + 13d643")
                                .setCancelable(false)
                                .setNegativeButton("Ok:)", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        soundPlay(SelectSound);
                                        dialogInterface.cancel();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("Info");
                        alert.show();
                    }
                }
        );
    }

    // Настраиваем обработчик кнопки Roll
    private void setButtonRoll() {
        btnRoll = findViewById(R.id.btnRoll);

        btnRoll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String formula = editFormula.getText().toString();
                        StartParse(view, formula);
                    }
                }
        );
    }

    private void StartParse(View view, String formula) {
        final TextView txtResult = findViewById(R.id.txtResultRoll);
        final Animation btnScale = AnimationUtils.loadAnimation(this, R.anim.scale);
        soundPlay(SelectSound);
        view.startAnimation(btnScale);
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

    // Настраиваем обработчики кнопок Memory
    private void setMemoryButtons(Button btn) {
        // Получаем настройку из настроек
        String btnMemText = thisSettings.getString(btn.getResources().getResourceEntryName(btn.getId()), "");
        assert btnMemText != null;
        if (btnMemText.isEmpty()) {
            // Если настройка пустая, то получаем текст из строки формулы
            String formula = editFormula.getText().toString();
            if (!formula.isEmpty()) {
                // если строка формулы не пустая, то
                // меняем текст на кнопке и сохраняем в настройках
                btn.setText(formula);
                prefEditor = thisSettings.edit();
                prefEditor.putString(btn.getResources().getResourceEntryName(btn.getId()), formula);
                prefEditor.apply();
            }
        }
        else {
            btn.setText(btnMemText);
            // Тут нельзя сразу парсить, потому что при перезагрузке страницы
            // сразу будет новый результат. Поэтому этот вызов пришлось перенести
            // в обработчик нажатия на каждую из кнопок.
            //StartParse(btn, btnMemText);
        }
    }

    // Сбрасываем обработчики и текст кнопок Memory
    private void flushMemoryButton(Button btn) {
        final Button btn1 = btn;
        AlertDialog.Builder a_builder = new AlertDialog.Builder(CustomActivity.this, R.style.AlertDialogStyle);
        a_builder.setMessage("Are you sure you want to clean this memory button?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        soundPlay(SelectSound);
                        btn1.setText(R.string.memorize);
                        prefEditor = thisSettings.edit();
                        prefEditor.remove(btn1.getResources().getResourceEntryName(btn1.getId()));
                        prefEditor.apply();
                        dialogInterface.cancel();
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
        alert.setTitle("Erase memory button");
        alert.show();
    }
}