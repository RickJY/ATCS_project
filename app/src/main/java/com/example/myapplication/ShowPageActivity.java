package com.example.myapplication;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ShowPageActivity extends AppCompatActivity {
    private List<ItemModel> originalItemList;
    private List<ItemModel> displayedItemList;
    private ShowPageAdapter itemAdapter;
    private static final String CHANNEL_ID = "ATCS_test_channel";
    private static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_page);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                updateFilteredList(newText);
                return false;
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        originalItemList = new ArrayList<>();
        originalItemList.add(new ItemModel(R.drawable.develop0, "T-shirt", 0));
        originalItemList.add(new ItemModel(R.drawable.develop1, "Shoe", 1));
        originalItemList.add(new ItemModel(R.drawable.develop2, "Shoe", 2));
        originalItemList.add(new ItemModel(R.drawable.develop3, "shoe", 3));
        originalItemList.add(new ItemModel(R.drawable.develop4, "shoe", 4));
        originalItemList.add(new ItemModel(R.drawable.develop5, "shoe", 5));
        originalItemList.add(new ItemModel(R.drawable.develop6, "badminton racket", 6));
        originalItemList.add(new ItemModel(R.drawable.develop7, "badminton", 7));
        originalItemList.add(new ItemModel(R.drawable.develop8, "Football", 8));
        originalItemList.add(new ItemModel(R.drawable.develop9, "Basketball", 9));

        displayedItemList = new ArrayList<>(originalItemList);

        itemAdapter = new ShowPageAdapter(displayedItemList);
        recyclerView.setAdapter(itemAdapter);

        createNotificationChannel();
        requestNotificationPermission();
    }

    private void updateFilteredList(String query) {
        List<ItemModel> filteredList = new ArrayList<>();
        if (query.isEmpty()) {
            filteredList.addAll(originalItemList);
        } else {
            for (ItemModel item : originalItemList) {
                if (item.getItemName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }

        itemAdapter.updateList(filteredList);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ATCS_test_channel";
            String description = "ATCS_test_channel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }

    public void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Notification")
                .setContentText("This is a notification from ATCS_test app.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setDefaults(0);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.e("ShowPageActivity", "Notification permission not granted");
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}