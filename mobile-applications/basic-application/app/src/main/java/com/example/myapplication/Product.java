package com.example.myapplication;

public class Product {

    public int id;
    public String name;
    public double quantity;
    public double price;
    public boolean bought;

    public Product(String name, double quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public void switchStatus() {
        bought = !bought;
    }
}
