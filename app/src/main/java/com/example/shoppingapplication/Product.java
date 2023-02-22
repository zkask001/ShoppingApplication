package com.example.shoppingapplication;

public class Product {
    private String productId;
    private String productName;
    private int stockNumber;
    private float x;
    private float y;

    public Product() {
        // Default constructor required for Firebase
    }

    public Product(String productId, String productName, int stockNumber, float x, float y) {
        this.productId = productId;
        this.productName = productName;
        this.stockNumber = stockNumber;
        this.x = x;
        this.y = y;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getStockNumber() {
        return stockNumber;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}