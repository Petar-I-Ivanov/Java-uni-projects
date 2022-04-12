package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity {

    TextView  resultTV;
    EditText  firstNumberET;
    EditText  secondNumberET;
    Button    plusB;
    Button    minusB;
    Button    divideB;
    Button    multiplyB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        resultTV        = findViewById(R.id.resultTextView);
        firstNumberET   = findViewById(R.id.firstNumberEditText);
        secondNumberET  = findViewById(R.id.secondNumberEditText);
        plusB           = findViewById(R.id.plusButton);
        minusB          = findViewById(R.id.minusButton);
        multiplyB       = findViewById(R.id.multiplyButton);
        divideB         = findViewById(R.id.divideButton);

        plusB.setOnClickListener(onClickListener);
        minusB.setOnClickListener(onClickListener);
        multiplyB.setOnClickListener(onClickListener);
        divideB.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(        firstNumberET.getText().length() == 0
                    || secondNumberET.getText().length() == 0
                    || firstNumberET.getText().toString().equals(".")
                    || secondNumberET.getText().toString().equals(".")
            )  return;

            double first   = Double.parseDouble(firstNumberET.getText().toString());
            double second  = Double.parseDouble(secondNumberET.getText().toString());
            double result  = 0;

            switch (v.getId()) {

                case R.id.plusButton:
                    result = first + second;
                    break;

                case R.id.minusButton:
                    result = first - second;
                    break;

                case R.id.multiplyButton:
                    result = first * second;
                    break;

                case R.id.divideButton:
                    if(second != 0)  result = first / second;
                    else  result = Double.NaN;
                    break;
            }

/*            switch (((Button)v).getText().toString()){
                case "+":
                    result = first + second;
                    break;
                case "-":
                    result = first - second;
                    break;
                case "*":
                    result = first * second;
                    break;

                case "/":
                    result = first / second;
                    break;
            }

 */

            resultTV.setText("Result: " + result);
        }
    };
}