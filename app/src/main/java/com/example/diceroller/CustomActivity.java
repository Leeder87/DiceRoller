package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        setButtonRoll();
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
                                parser.result = "Ошибка";
                            }
                        }
                        else
                            parser.result = "Введите формулу броска:";
                        txtResult.setText(parser.result);
                    }
                }
        );
    }
}