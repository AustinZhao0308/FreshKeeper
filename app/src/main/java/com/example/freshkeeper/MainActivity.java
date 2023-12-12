package com.example.freshkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private List<Item> items;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();
        itemAdapter = new ItemAdapter(items);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);

        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemDialog();
            }
        });
    }

    private void showAddItemDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_item, null);
        EditText nameEditText = dialogView.findViewById(R.id.nameEditText);
        EditText expiryDateEditText = dialogView.findViewById(R.id.expiryDateEditText);

        expiryDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(expiryDateEditText);
            }
        });

        new AlertDialog.Builder(this)
                .setTitle("Add Item")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String name = nameEditText.getText().toString();
                    String expiryDate = expiryDateEditText.getText().toString();
                    String startDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    Item newItem = new Item(name, expiryDate, startDate);
                    items.add(newItem);
                    itemAdapter.notifyDataSetChanged();
                    itemAdapter.sortItems();

                    // 添加新 Item 后执行 NotificationTask
                    new NotificationTask(getApplicationContext()).execute(items);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showDatePickerDialog(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, day) -> {
                    String selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            .format(new GregorianCalendar(year, month, day).getTime());
                    editText.setText(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    public void performSearch(View view) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_item, null);
        EditText nameEditText = dialogView.findViewById(R.id.nameEditText);
        String itemName = nameEditText.getText().toString();
        String item = itemName + "几天会过期";
        Uri searchUri = Uri.parse("https://www.google.com/search?q=" + Uri.encode(item));
        Intent intent = new Intent(Intent.ACTION_VIEW, searchUri);
        startActivity(intent);
    }

}
