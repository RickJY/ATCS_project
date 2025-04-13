package com.example.myapplication;

import android.content.Context;
import android.util.Log;
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
    private List<ItemModel> displayedItems;

    public ShowPageAdapter(List<ItemModel> itemList) {
        this.displayedItems = new ArrayList<>(itemList);
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

        holder.button.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            String[] toasts = context.getResources().getStringArray(R.array.option_toasts);

            int originalIndex = item.getOriginalIndex();
            String toastMessage = (originalIndex < toasts.length) ? toasts[originalIndex] : context.getString(R.string.app_name);

            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();

            if (context instanceof ShowPageActivity) {
                ((ShowPageActivity) context).showNotification();
                Log.d("ShowPageAdapter", "showNotification() called");
            } else {
                Log.e("ShowPageAdapter", "Context is not ShowPageActivity");
            }
        });
    }

    @Override
    public int getItemCount() {
        return displayedItems.size();
    }

    public void updateList(List<ItemModel> newList) {
        displayedItems = new ArrayList<>(newList);
        notifyDataSetChanged();
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