package com.example.diceroller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Класс для обработки формулы CustomRoll, простой инетрпретатор
public class FormulaParser {
    String formula;                 // Собственно сама формула
    List<String> parts;             // Список элементов без арифметических операторов
    List<String> signs;             // Список арифметических операторов
    List<String> processedParts;    // Список для обработки данных
    String prettyFormula;           // Красивая формула броска, если нужно будет её показывать
    String result;                  // Результат вычислений

    // Конструктор, создаёт большинство полей класса
    public FormulaParser(String formula) {
        this.formula = formula.trim();
        this.processedParts = new ArrayList<>();
        this.signs = new ArrayList<>();
        this.parts = new ArrayList<>();
    }

    // Основная функциональность здесь
    public void parse() throws Exception {
        // Парсим формулу, получаем список операндов без пробелов
        String[] partsArray = formula.split("[+*-]");
        for (String part:partsArray)
            parts.add(part.trim());

        // Снова парсим формулу, получаем список арифметических действий (знаков *, + и -)
        Matcher m = Pattern.compile("[+*-]").matcher(formula);
        while (m.find())
            signs.add(m.group());

        // Прочёсываем список операндов, и если находим символ "d", то генерим случайное число
        for (String str : this.parts) {
            if( str.contains("d")) {
                String diceToRoll = str.trim();
                String[] diceSplitted = diceToRoll.split("d");
                int numberOfDice = Integer.parseInt(diceSplitted[0]);
                int diceType = Integer.parseInt(diceSplitted[1]);
                int result = 0;

                for(int i = 0; i < numberOfDice; i++)
                    result += RandomGenerator.generateInt(diceType);

                str = String.valueOf(result);
            }
            // Результат генерации (или сам операнд, если генерить в нём нечего)
            // записывается в новый список обработанных элементов
            this.processedParts.add(str);
        }

        // Создаём строку для вывода красивой формулы броска
        StringBuilder newFormula = new StringBuilder();
        for (int i = 0; i < signs.size(); i++) {
            newFormula.append(processedParts.get(i));
            newFormula.append(" ");
            newFormula.append(signs.get(i));
            newFormula.append(" ");
        }
        newFormula.append(processedParts.get(processedParts.size()-1));
        this.prettyFormula = newFormula.toString();

        // Начинаем обрабатывать операнды, применяя к ним необходимые арифметические действия
        while(signs.contains("*")) {
            DoMultiply(processedParts, signs);
        }
        while(signs.contains("+") || signs.contains("-")) {
            DoPlusMinus(processedParts, signs);
        }

        // В результате в списке данных для обработки должен остаться только один элемент
        // Если это так, то вот он результат. Если нет, то кидаем ошибку
        if(processedParts.size() != 1) {
            throw new Exception("Ambiguous result");
        }
        else {
            result = processedParts.get(0);
        }
    }

    // Метод для обработки действия умножения. Умножает одно число на другое, уменьшает списки
    private void DoMultiply(List<String> processedParts, List<String> signs) {
        int index = signs.indexOf("*");
        signs.remove(index);

        processedParts.set(index, String.valueOf(Integer.parseInt(processedParts.get(index)) * Integer.parseInt(processedParts.get(index + 1))));
        processedParts.remove(index + 1);
    }

    // Метод для обработки действий сложения и вычитания. Складывает или вычитает два числа, уменьшает списки
    private void DoPlusMinus(List<String> processedParts, List<String> signs) {
        String sign = signs.get(0);
        switch (sign) {
            case "+":
                processedParts.set(0, String.valueOf(Integer.parseInt(processedParts.get(0)) + Integer.parseInt(processedParts.get(1))));
                break;
            case "-":
                processedParts.set(0, String.valueOf(Integer.parseInt(processedParts.get(0)) - Integer.parseInt(processedParts.get(1))));
                break;
        }
        signs.remove( 0);
        processedParts.remove( 1);
    }
}
