package com.example.freshkeeper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import androidx.core.app.NotificationCompat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationTask extends AsyncTask<List<Item>, Void, Void> {

    private static final String CHANNEL_ID = "MyChannel";
    private static final int NOTIFICATION_ID = 1;
    private Context context;

    public NotificationTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(List<Item>... params) {
        if (params.length > 0) {
            List<Item> items = params[0];

            for (Item item : items) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date expiryDate = sdf.parse(item.getExpiryDate());
                    Calendar today = Calendar.getInstance();
                    Calendar expiryMinus3Days = Calendar.getInstance();
                    expiryMinus3Days.setTime(expiryDate);
                    expiryMinus3Days.add(Calendar.DAY_OF_YEAR, -3);

                    if (today.after(expiryMinus3Days) && today.before(expiryDate)) {
                        // 物品还有3天过期
                        sendNotification(item.getName() + "将在3天后过期");
                    } else if (today.after(expiryDate)) {
                        // 物品已过期
                        sendNotification(item.getName() + "已过期");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private void sendNotification(String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("FreshKeeper Notification")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = builder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}

