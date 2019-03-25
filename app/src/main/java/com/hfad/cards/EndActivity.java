package com.hfad.cards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {


    private TextView scoreText;
    private int rights;
    private ImageView restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        wireWidgets();
        setOnClickListeners();

        Intent end = getIntent();
        rights = end.getIntExtra(CardPicker.SCORE_EXTRA, 0);

        scoreText.setText("You guessed " + rights + " cards!");
    }

    private void setOnClickListeners() {
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newGame = new Intent(EndActivity.this, CardPicker.class);
                startActivity(newGame);
            }
        });
    }

    private void wireWidgets() {
        scoreText = findViewById(R.id.textView_end_score);
        restart = findViewById(R.id.imageView_end_restart);
    }
}
