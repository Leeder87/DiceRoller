package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.diceroller.data.DatabaseAdapter;
import com.example.diceroller.data.HistoryRecord;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private Button btnBack;
    private ListView recordList;
    ArrayAdapter<HistoryRecord> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setButtons(); // Вызов метода, привязывающего к кнопкам обработчики
    }

    // Метод для настройки обработчиков кнопок
    private void setButtons() {
        final Animation btnScale = AnimationUtils.loadAnimation(this, R.anim.scale);
        btnBack = findViewById(R.id.btnBack);
        recordList = findViewById(R.id.list);

        btnBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.startAnimation(btnScale);
                        finish();
                    }
                }
        );

        recordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*User user =arrayAdapter.getItem(position);
                if(user!=null) {
                    Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                    intent.putExtra("id", user.getId());
                    intent.putExtra("click", 25);
                    startActivity(intent);
                }*/
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();

        List<HistoryRecord> historyRecords = adapter.getAllRecords();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyRecords);
        recordList.setAdapter(arrayAdapter);
        adapter.close();
    }
}