package com.example.diceroller.data;

import java.util.Date;

public class HistoryRecord {
    private long id;
    private Date rollTime;
    private String formulaRaw;
    private String formulaProcessed;
    private String result;
    private String area;

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

    public Date getRollTime() {
        return rollTime;
    }

    public long getRollTimeLong() {
        return rollTime.getTime();
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
