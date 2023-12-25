package com.example.freshkeeper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// Item.java
public class Item {
    private String name;
    private String expiryDate;
    private String startDate;

    public Item(String name, String expiryDate, String startDate) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getStartDate() {
        return startDate;
    }

    // 添加到数据库
    public long insertIntoDatabase(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("expiry_date", expiryDate);
        values.put("start_date", startDate);

        return db.insert("items", null, values);
    }

    // 从数据库读取所有项
    public static List<Item> readAllItemsFromDatabase(SQLiteDatabase db) {
        List<Item> itemList = new ArrayList<>();
        Cursor cursor = db.query("items", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String expiryDate = cursor.getString(cursor.getColumnIndex("expiry_date"));
            @SuppressLint("Range") String startDate = cursor.getString(cursor.getColumnIndex("start_date"));

            Item item = new Item(name, expiryDate, startDate);
            itemList.add(item);
        }
        return itemList;
    }

}
