package com.example.myapplication;

//define our recycle elements
public class ItemModel {
    private final int imageResId;
    private final String itemName;
    private  final int originalIndex;
    public ItemModel(int imageResId, String itemName, int originalIndex) {
        this.imageResId = imageResId;
        this.itemName = itemName;
        this.originalIndex = originalIndex;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getItemName() {
        return itemName;
    }

    public  int getOriginalIndex(){
        return originalIndex;
    }
}