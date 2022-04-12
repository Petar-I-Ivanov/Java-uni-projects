package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ShoppingListActivity extends AppCompatActivity {

    RecyclerView shoppingListRV;
    MyCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        Product[] products = new Product[] {
                new Product("Кока-кола", 10),
                new Product("Ракия", 0.7),
                new Product("Краставици", 3),
                new Product("Домати", 2.5),
                new Product("Бира", 12),
                new Product("Сирене", 1.2),
                new Product("Кашкавал", 0.5),
                new Product("Чипс", 2),
                new Product("Мед", 1),
                new Product("Хляб", 1),
                new Product("Яйца", 12),
                new Product("Маслини", 0.33),
                new Product("Газирана вода", 4),
        };

        shoppingListRV = findViewById(R.id.shoppingListRecyclerView);
        adapter = new MyCustomAdapter(products);
        shoppingListRV.setHasFixedSize(true);
        shoppingListRV.setLayoutManager(new LinearLayoutManager(this));
        shoppingListRV.setAdapter(adapter);
    }
}