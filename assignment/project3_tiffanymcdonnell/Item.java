package com.assignment.project3_tiffanymcdonnell;

public class Item {
    private String itemName;
    private String itemDesc;
    private int count;

    public Item(String itemName, String itemDesc, int count) {
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.count = count;
    }

    public String getItemName() {
        return itemName;
    }
    public String getItemDesc() {
        return itemDesc;
    }
    public int getCount() {
        return count;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public void setCount(int count) {
        this.count = count;
    }
}