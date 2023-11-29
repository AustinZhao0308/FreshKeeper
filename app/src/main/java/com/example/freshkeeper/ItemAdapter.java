package com.example.freshkeeper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> items;

    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView expiryDateTextView;
        public TextView daysLeftTextView;
        public Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            expiryDateTextView = itemView.findViewById(R.id.expiryDateTextView);
            daysLeftTextView = itemView.findViewById(R.id.daysLeftTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.nameTextView.setText(item.getName());
        holder.expiryDateTextView.setText("Expires on: " + item.getExpiryDate());

        // 计算还有几天过期
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date expiryDate = sdf.parse(item.getExpiryDate());
            long daysLeft = getDaysDifference(Calendar.getInstance().getTime(), expiryDate);
            holder.daysLeftTextView.setText("Days left: " + daysLeft);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 处理删除按钮点击事件
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取点击按钮所在的列表项的位置，并从列表中移除
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    items.remove(clickedPosition);
                    notifyItemRemoved(clickedPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private long getDaysDifference(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }

    // 实现排序功能
    public void sortItems() {
        Collections.sort(items, (item1, item2) -> {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date1 = sdf.parse(item1.getExpiryDate());
                Date date2 = sdf.parse(item2.getExpiryDate());
                return date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0; // 如果解析日期失败，返回默认值
            }
        });
        notifyDataSetChanged();
    }
}
