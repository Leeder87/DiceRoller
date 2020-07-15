package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.diceroller.data.DatabaseAdapter;
import com.example.diceroller.data.DatabaseHelper;
import com.example.diceroller.data.HistoryRecord;

import java.util.Date;

public class CustomActivity extends AppCompatActivity {
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        setButtonRoll();
        setButtons(); // Вызов метода, привязывающего к кнопкам обработчики
    }

    // Метод для настройки обработчиков кнопок
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
    }

    // Настраиваем обработчик кнопки Roll
    private void setButtonRoll() {
        Button btnRoll = findViewById(R.id.btnRoll);
        final EditText editFormula = findViewById(R.id.editFormula);
        final TextView txtResult = findViewById(R.id.txtResultRoll);

        btnRoll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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