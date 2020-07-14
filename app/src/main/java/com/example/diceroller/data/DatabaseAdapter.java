package com.example.diceroller.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Класс-адаптер для базы данных, предоставляющий
// абстрактный интерфейс работы с БД
public class DatabaseAdapter {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_ROLL_TIME, DatabaseHelper.COLUMN_FORMULA_RAW,
                DatabaseHelper.COLUMN_FORMULA_PROCESSED, DatabaseHelper.COLUMN_RESULT, DatabaseHelper.COLUMN_AREA};
        return  database.query(DatabaseHelper.TABLE, columns, null, null, null, null, null);
    }

    public List<HistoryRecord> getAllRecords(){
        ArrayList<HistoryRecord> historyRecords = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                Date rollTime = new Date(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ROLL_TIME)));
                String formulaRaw = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FORMULA_RAW));
                String formulaProcessed = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FORMULA_PROCESSED));
                String result = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_RESULT));
                String area = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_AREA));
                historyRecords.add(new HistoryRecord(id, rollTime, formulaRaw, formulaProcessed, result, area));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return historyRecords;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE);
    }

    public HistoryRecord getRecord(long id){
        HistoryRecord historyRecord = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            Date rollTime = new Date(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ROLL_TIME)));
            String formulaRaw = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FORMULA_RAW));
            String formulaProcessed = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FORMULA_PROCESSED));
            String result = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_RESULT));
            String area = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_AREA));
            historyRecord = new HistoryRecord(id, rollTime, formulaRaw, formulaProcessed, result, area);
        }
        cursor.close();
        return historyRecord;
    }

    public long insert(HistoryRecord historyRecord){

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_ROLL_TIME, historyRecord.getRollTimeLong());
        cv.put(DatabaseHelper.COLUMN_FORMULA_RAW, historyRecord.getFormulaRaw());
        cv.put(DatabaseHelper.COLUMN_FORMULA_PROCESSED, historyRecord.getFormulaProcessed());
        cv.put(DatabaseHelper.COLUMN_RESULT, historyRecord.getResult());
        cv.put(DatabaseHelper.COLUMN_AREA, historyRecord.getArea());

        return  database.insert(DatabaseHelper.TABLE, null, cv);
    }

    public long delete(long recordId){

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(recordId)};
        return database.delete(DatabaseHelper.TABLE, whereClause, whereArgs);
    }

    public long update(HistoryRecord historyRecord){

        String whereClause = DatabaseHelper.COLUMN_ID + "=" + historyRecord.getId();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_ROLL_TIME, historyRecord.getRollTimeLong());
        cv.put(DatabaseHelper.COLUMN_FORMULA_RAW, historyRecord.getFormulaRaw());
        cv.put(DatabaseHelper.COLUMN_FORMULA_PROCESSED, historyRecord.getFormulaProcessed());
        cv.put(DatabaseHelper.COLUMN_RESULT, historyRecord.getResult());
        cv.put(DatabaseHelper.COLUMN_AREA, historyRecord.getArea());
        return database.update(DatabaseHelper.TABLE, cv, whereClause, null);
    }
}