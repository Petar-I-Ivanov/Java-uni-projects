package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button helloB;
    Button calculatorB;
    Button gameB;
    Button shoppingListB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        helloB        = findViewById(R.id.helloButton);
        calculatorB   = findViewById(R.id.calculatorButton);
        gameB         = findViewById(R.id.gameButton);
        shoppingListB = findViewById(R.id.shoppingListButton);

        helloB.setOnClickListener(onClickListener);
        calculatorB.setOnClickListener(onClickListener);
        gameB.setOnClickListener(onClickListener);
        shoppingListB.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = null;

            switch (v.getId()) {

                case R.id.helloButton:
                    intent = new Intent(MenuActivity.this, MainActivity.class);
                    break;

                case R.id.calculatorButton:
                    intent = new Intent(MenuActivity.this, CalculatorActivity.class);
                    break;

                case R.id.gameButton:
                    intent = new Intent(MenuActivity.this, GameActivity.class);
                    break;

                case R.id.shoppingListButton:
                    intent = new Intent(MenuActivity.this, ShoppingListActivity.class);
                    break;
            }

            startActivity(intent);
        }
    };
}