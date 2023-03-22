package com.example.shoppingapplication;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList {
    private String name;
    private List<String> items;

    public ShoppingList() {}

    public ShoppingList(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
