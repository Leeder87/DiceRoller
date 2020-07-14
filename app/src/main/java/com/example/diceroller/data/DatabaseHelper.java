package com.example.diceroller.data;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "diceroller.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "history"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ROLL_TIME = "roll_time";
    public static final String COLUMN_FORMULA_RAW = "formula_raw";
    public static final String COLUMN_FORMULA_PROCESSED = "formula_processed";
    public static final String COLUMN_RESULT = "result";
    public static final String COLUMN_AREA = "area";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ROLL_TIME + " INTEGER, "
                + COLUMN_FORMULA_RAW + " TEXT, "
                + COLUMN_FORMULA_PROCESSED + " TEXT, "
                + COLUMN_RESULT + " TEXT, "
                + COLUMN_AREA + " TEXT);"
        );
        // добавление начальных данных
        //db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_ROLL_TIME + ", " + COLUMN_FORMULA_RAW  + ", "
        //      + COLUMN_FORMULA_PROCESSED + ", " + COLUMN_RESULT  + ", "
        //      + COLUMN_AREA + ") VALUES (12315256541, '1d20+5', '16+5', '21', 'Simple Roll');");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    // Статический метод для вставки записи в таблицу истории бросков
    public static void insertRecord(Context baseContext, HistoryRecord historyRecord) {
        DatabaseAdapter adapter = new DatabaseAdapter(baseContext);
        adapter.open();
        adapter.insert(historyRecord);
        adapter.close();
    }
}