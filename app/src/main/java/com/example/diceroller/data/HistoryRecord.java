package com.example.diceroller.data;

import com.example.diceroller.FateDieResult;

import java.util.Date;

// Класс, отображающий запись в историю бросков
public class HistoryRecord {
    private long id;
    private Date rollTime;              // Дата и время броска
    private String formulaRaw;          // Первоначальная формула броска
    private String formulaProcessed;    // Формула броска уже после генерации
    private String result;              // Результат
    private String area;                // Откуда бросок сделан

    // Два конструктора, один с id, другой без
    public HistoryRecord(Date rollTime, String formulaRaw, String formulaProcessed, String result, String area) {
        this.rollTime = rollTime;
        this.formulaRaw = formulaRaw;
        this.formulaProcessed = formulaProcessed;
        this.result = result;
        this.area = area;
    }
    public HistoryRecord(long id, Date rollTime, String formulaRaw, String formulaProcessed, String result, String area) {
        this.id = id;
        this.rollTime = rollTime;
        this.formulaRaw = formulaRaw;
        this.formulaProcessed = formulaProcessed;
        this.result = result;
        this.area = area;
    }

    // Отдельный конструктор для Fudge-дайсов
    public HistoryRecord(Date rollTime, FateDieResult result1, FateDieResult result2, FateDieResult result3, FateDieResult result4) {
        this.rollTime = rollTime;
        this.formulaRaw = "4dF";
        this.area = "Fate";

        this.formulaProcessed = getFudgeCharacter(result1) +
                getFudgeCharacter(result2) +
                getFudgeCharacter(result3) +
                getFudgeCharacter(result4);

        int result = getFudgeValue(result1) +
                getFudgeValue(result2) +
                getFudgeValue(result3) +
                getFudgeValue(result4);
        this.result = String.valueOf(result);
    }

    private int getFudgeValue(FateDieResult dieResult) {
        switch (dieResult) {
            case MINUS:
                return -1;
            case PLUS:
                return 1;
            default:
                return 0;
        }
    }

    private String getFudgeCharacter(FateDieResult dieResult) {
        switch (dieResult) {
            case MINUS:
                return "[-]";
            case PLUS:
                return "[+]";
            default:
                return "[ ]";
        }
    }

    // Геттер для даты броска в миллисекундах с 1970 года
    public long getRollTimeLong() {
        return rollTime.getTime();
    }

    // Далее следуют стандартные геттеры и сеттеры
    public Date getRollTime() {
        return rollTime;
    }
    public void setRollTime(Date rollTime) {
        this.rollTime = rollTime;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getFormulaRaw() {
        return formulaRaw;
    }
    public void setFormulaRaw(String formulaRaw) {
        this.formulaRaw = formulaRaw;
    }
    public String getFormulaProcessed() {
        return formulaProcessed;
    }
    public void setFormulaProcessed(String formulaProcessed) {
        this.formulaProcessed = formulaProcessed;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return this.id + ": " + this.rollTime + "; generated " + this.formulaRaw + " as "
                + this.formulaProcessed + "; result: " + this.result + ". From " + this.area;
    }
}
