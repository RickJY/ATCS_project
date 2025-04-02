package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ShowPageActivity extends AppCompatActivity {
    private List<ItemModel> originalItemList;  // 原始数据列表
    private List<ItemModel> displayedItemList; // 显示的筛选后列表
    private ShowPageAdapter itemAdapter; // RecyclerView 适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_page);

        // 初始化搜索框
        SearchView searchView = findViewById(R.id.searchView);
        searchView.clearFocus(); // 防止键盘自动弹出
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // 不单独处理提交事件
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                updateFilteredList(newText); // 过滤列表
                return false;
            }
        });

        // 初始化 RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 初始化原始数据列表
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

        // 初始化显示数据列表，默认显示所有数据
        displayedItemList = new ArrayList<>(originalItemList);

        // 设置 RecyclerView 适配器
        itemAdapter = new ShowPageAdapter(displayedItemList);
        recyclerView.setAdapter(itemAdapter);
    }

    /**
     * 根据用户输入的文本筛选列表
     * @param query 用户输入的搜索关键字
     */
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

        // 如果没有匹配的结果，显示提示
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }

        // 更新适配器数据
        itemAdapter.updateList(filteredList);
    }
}
