package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShowPageAdapter extends RecyclerView.Adapter<ShowPageAdapter.ViewHolder> {
    private List<ItemModel> displayedItems; // 当前显示的列表

    public ShowPageAdapter(List<ItemModel> itemList) {
        this.displayedItems = new ArrayList<>(itemList); // 复制列表，避免直接修改原始数据
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemModel item = displayedItems.get(position);

        holder.imageView.setImageResource(item.getImageResId());
        holder.textView.setText(item.getItemName());

        // 按钮点击事件
        holder.button.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            String[] toasts = context.getResources().getStringArray(R.array.option_toasts);

            int originalIndex = item.getOriginalIndex();
            String toastMessage = (originalIndex < toasts.length) ? toasts[originalIndex] : context.getString(R.array.option_toasts);

            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return displayedItems.size();
    }

    /** 更新适配器数据 **/
    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<ItemModel> newList) {
        displayedItems = new ArrayList<>(newList); // 重新赋值
        notifyDataSetChanged(); // 刷新界面
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemImage);
            textView = itemView.findViewById(R.id.itemText);
            button = itemView.findViewById(R.id.itemButton);
        }
    }
}
