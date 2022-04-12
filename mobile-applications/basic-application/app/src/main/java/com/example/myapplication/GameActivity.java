package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    int wins;
    int loses;

    TextView   triesTV;
    ImageView  statusIV;
    TextView   guessesTV;
    EditText   guessET;
    Button     guessB;
    Button     restartB;
    TextView   winsTV;
    TextView   losesTV;

    final int  LIVES = 5;
    int        tries;
    int        computerNumber;
    Random random = new Random();

    private void restartGame() {

        computerNumber = random.nextInt(101);
        tries = 0;
        triesTV.setText("0/" + LIVES);
        guessesTV.setText("");
        guessB.setEnabled(true);

        //Log.i("cheat", computerNumber + "");
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(v.getId() == R.id.restartButton) {
                restartGame();
                return;
            }

            if(guessET.getText().length() == 0) {
                Toast.makeText(GameActivity.this, "Please enter a guess!", Toast.LENGTH_LONG).show();
                return;
            }

            tries++;
            triesTV.setText(tries + "/" + LIVES);

            int userGuess = Integer.parseInt(guessET.getText().toString());
            guessET.setText("");
            guessesTV.setText(guessesTV.getText() + " " + userGuess);

            if (userGuess > computerNumber) {
                statusIV.setImageResource(R.drawable.down);
            }
            else if (userGuess < computerNumber) {
                statusIV.setImageResource(R.drawable.up);
            }
            else {
                statusIV.setImageResource(R.drawable.winner);
                guessB.setEnabled(false);

                wins++;
                editor.putInt("wins", wins);
                editor.apply();
                updateOldScore();

                return;
            }

            if (tries == LIVES) {
                statusIV.setImageResource(R.drawable.loser);
                guessB.setEnabled(false);

                loses++;
                editor.putInt("loses", loses);
                editor.apply();
                updateOldScore();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        triesTV    = findViewById(R.id.triesTextView);
        statusIV   = findViewById(R.id.hintImageView);
        guessesTV  = findViewById(R.id.guessesTextView);
        guessET    = findViewById(R.id.guessEditText);
        guessB     = findViewById(R.id.guessButton);
        restartB   = findViewById(R.id.restartButton);
        winsTV     = findViewById(R.id.winsTextView);
        losesTV    = findViewById(R.id.losesTextView);

        guessB.setOnClickListener(onClickListener);
        restartB.setOnClickListener(onClickListener);
        restartGame();

        pref = getSharedPreferences("scoreStats", Context.MODE_PRIVATE);
        editor = pref.edit();

        wins = pref.getInt("wins", 0);
        loses = pref.getInt("loses", 0);

        updateOldScore();
    }

    private void updateOldScore() {
        winsTV.setText("wins: " + wins);
        losesTV.setText("losses: " + loses);
    }
}