package com.example.freshkeeper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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


}
