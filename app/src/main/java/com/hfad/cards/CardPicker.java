package com.hfad.cards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

// app:srcCompat="@android:drawable/ic_delete"
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CardPicker extends AppCompatActivity {

    private Button genCard;
    private ImageView card;
    private Button finalAnswer;
    private ImageView cardback;
    private EditText choice;
    private Card currentCard;
    private TextView help;
    private TextView fails;
    private int numFails = 20;
    private int numRight = 0;

    private ImageView up;
    private ImageView down;
    private ImageView rightImage;

    public static final String SCORE_EXTRA = "score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_picker);

        wireWidgets();
        setOnClickListeners();
        cardback.setVisibility(View.INVISIBLE);
        rightImage.setVisibility(View.INVISIBLE);

        choice.setEnabled(false);
        finalAnswer.setEnabled(false);

    }

    private void setOnClickListeners() {
        genCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardback.setVisibility(View.VISIBLE);
                getCard();
                genCard.setEnabled(false);
                choice.setEnabled(true);
                finalAnswer.setEnabled(true);
                rightImage.setVisibility(View.INVISIBLE);
            }
        });

        finalAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choice.getText().toString().equals("")) {
                    Toast.makeText(CardPicker.this, "You have to make a guess!", Toast.LENGTH_SHORT).show();
                }
                else{
                    checkAnswer();
                    updateScore();
                }

            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent help = new Intent(CardPicker.this, HelpActivity.class);
                startActivity(help);
            }
        });
    }

    private void updateScore() {

    }

    private void checkAnswer() {

        if(choice.getText().equals(null)){
            Toast.makeText(this, "You have to make a guess!", Toast.LENGTH_SHORT).show();
        }
        else {
            String userAnswer = choice.getText().toString();
            String realAnswer = currentCard.getCode().toString();

            if(userAnswer.length() == 3 && userAnswer.substring(0, 2).equals("10")){
                userAnswer = userAnswer.substring(1, 3);
            }
            if (userAnswer.equals(realAnswer)) {
                Toast.makeText(this, "CORRECT!", Toast.LENGTH_SHORT).show();
                up.setVisibility(View.INVISIBLE);
                down.setVisibility(View.INVISIBLE);
                rightImage.setVisibility(View.VISIBLE);
                finalAnswer.setEnabled(false);
                genCard.setEnabled(true);
                cardback.setVisibility(View.INVISIBLE);
                numRight++;
            } else {
                if (numFails == 0) {
                    Intent endGame = new Intent(CardPicker.this, EndActivity.class);
                    endGame.putExtra(SCORE_EXTRA, numRight);
                    startActivity(endGame);
                } else {
                    if(userAnswer.substring(0, 1).equals("A") || userAnswer.substring(0, 1).equals("K") || userAnswer.substring(0, 1).equals("Q") || userAnswer.substring(0, 1).equals("J")
                                    || userAnswer.substring(0, 1).equals("0") || userAnswer.substring(0, 1).equals("9") || userAnswer.substring(0, 1).equals("8") || userAnswer.substring(0, 1).equals("7")
                                    || userAnswer.substring(0, 1).equals("6") || userAnswer.substring(0, 1).equals("5") || userAnswer.substring(0, 1).equals("4") || userAnswer.substring(0, 1).equals("3")
                                    || userAnswer.substring(0, 1).equals("2"))
                    {
                        if (userAnswer.substring(1).equals("S") || userAnswer.substring(1).equals("C") || userAnswer.substring(1).equals("D") || userAnswer.substring(1).equals("H")){
                            numFails--;
                            fails.setText("Failure points: " + numFails);
                            checkHighLow();
                            Toast.makeText(this, "WRONG!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(this, "Invalid Entry!", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(this, "Invalid Entry!", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        }

    }

    //if(userAnswer.substring(0, 1).equals("A") || userAnswer.substring(0, 1).equals("K") || userAnswer.substring(0, 1).equals("Q") || userAnswer.substring(0, 1).equals("J")
    //                || userAnswer.substring(0, 1).equals("10") || userAnswer.substring(0, 1).equals("9") || userAnswer.substring(0, 1).equals("8") || userAnswer.substring(0, 1).equals("7")
    //                || userAnswer.substring(0, 1).equals("6") || userAnswer.substring(0, 1).equals("5") || userAnswer.substring(0, 1).equals("4") || userAnswer.substring(0, 1).equals("3")
    //                || userAnswer.substring(0, 1).equals("2")) {
    //            if (userAnswer.substring(1).equals("S") || userAnswer.substring(1).equals("C") || userAnswer.substring(1).equals("D") || userAnswer.substring(1).equals("H")) {

    private void checkHighLow() {
        String userAnswer = choice.getText().toString();
        String realAnswer = currentCard.getCode().toString();

        if(userAnswer.length() == 3 && userAnswer.substring(0, 2).equals("10")){
            userAnswer = userAnswer.substring(1, 3);
        }
        int chosenValue = 0;
        int realValue = 0;




        if(userAnswer.length() < 2 || (userAnswer.substring(0,1).equals("1") && userAnswer.length() == 2) || userAnswer.length() > 3) {
            Toast.makeText(this, "Invalid Entry", Toast.LENGTH_SHORT).show();
        }

        else if(currentCard.getSuit().substring(0, 1).equals(userAnswer.substring(1)) || currentCard.getSuit().substring(0, 1).equals(userAnswer.substring(2))){
            if(currentCard.getValue().equals("ACE")){
                realValue = 14;
            }
            else if(currentCard.getValue().equals("JACK")){
                realValue = 11;
            }
            else if(currentCard.getValue().equals("QUEEN")){
                realValue = 12;
            }
            else if(currentCard.getValue().equals("KING")){
                realValue = 13;
            }
            else if(realAnswer.substring(0, 1).equals("0")){
                realValue = 10;
            }
            else {
                realValue = Integer.parseInt(currentCard.getValue());
            }

            if(userAnswer.substring(0, 1).equals("A")){
                chosenValue = 14;
            }
            else if(userAnswer.substring(0, 1).equals("J")){
                chosenValue = 11;
            }
            else if(userAnswer.substring(0, 1).equals("Q")){
                chosenValue = 12;
            }
            else if(userAnswer.substring(0, 1).equals("K")){
                chosenValue = 13;
            }
            else if(userAnswer.substring(0, 1).equals("0")){
                chosenValue = 10;
            }


            else{
                try {
                    chosenValue = Integer.parseInt(userAnswer.substring(0, 1));
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid Entry", Toast.LENGTH_SHORT).show();
                }
            }


            if(chosenValue < 0) {
                down.setVisibility(View.INVISIBLE);
                up.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Invalid Entry", Toast.LENGTH_SHORT).show();
            }

            else if(chosenValue < realValue){
                down.setVisibility(View.INVISIBLE);
                up.setVisibility(View.VISIBLE);
            }
            else if(chosenValue > realValue){
                up.setVisibility(View.INVISIBLE);
                down.setVisibility(View.VISIBLE);
            }
        }
        else if (currentCard.getSuit().substring(0, 1).equals("S")){
            down.setVisibility(View.INVISIBLE);
            up.setVisibility(View.VISIBLE);
        }
        else if ((currentCard.getSuit().substring(0, 1).equals("H")) && (userAnswer.substring(1)).equals("S")){
            down.setVisibility(View.VISIBLE);
            up.setVisibility(View.INVISIBLE);
        }
        else if((currentCard.getSuit().substring(0, 1).equals("H")) && (userAnswer.substring(1)).equals("D") || (userAnswer.substring(1)).equals("C")){
            down.setVisibility(View.INVISIBLE);
            up.setVisibility(View.VISIBLE);
        }
        else if((currentCard.getSuit().substring(0, 1).equals("D")) && (userAnswer.substring(1)).equals("S") || (userAnswer.substring(1)).equals("H")){
            down.setVisibility(View.VISIBLE);
            up.setVisibility(View.INVISIBLE);
        }
        else if((currentCard.getSuit().substring(0, 1).equals("D")) && (userAnswer.substring(1)).equals("C")){
            down.setVisibility(View.INVISIBLE);
            up.setVisibility(View.VISIBLE);
        }
        else if(currentCard.getSuit().substring(0, 1).equals("C")){
            down.setVisibility(View.VISIBLE);
            up.setVisibility(View.INVISIBLE);
        }




    }

    private void wireWidgets() {
        genCard = findViewById(R.id.button_cardPicker_pickButton);
        card = findViewById(R.id.imageView_cardPicker_card);
        finalAnswer = findViewById(R.id.button_cardPicker_finalAnswer);
        cardback = findViewById(R.id.imageView_cardPicker_cardback);
        choice = findViewById(R.id.editText_cardPicker_choice);
        help = findViewById(R.id.textView_cardPicker_help);

        up = findViewById(R.id.imageView_cardPicker_up);
        down = findViewById(R.id.imageView_cardPicker_down);
        rightImage = findViewById(R.id.imageView_cardPicker_right);

        fails = findViewById(R.id.textView_cardPicker_failpoints);
    }

    private void getCard() {
        // need GSON and converter-gson libraries for this step
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://deckofcardsapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CardService service =
                retrofit.create(CardService.class);

        // temporarily hardcode our food query
        // TODO make edittexts to enter the search terms
        Call<CardResponse> cardResponseCall =
                service.search();

        cardResponseCall.enqueue(new Callback<CardResponse>() {
            @Override
            public void onResponse(Call<CardResponse> call,
                                   Response<CardResponse> response) {
                List<Card> cards = response.body().getCards();
                Log.d("ENQUEUE", "onResponse: " + cards.get(0).toString());
                Picasso.get().load(cards.get(0).getImage()).into(card);
                currentCard = cards.get(0);
            }

            @Override
            public void onFailure(Call<CardResponse> call,
                                  Throwable t) {
                Log.d("ENQUEUE", "onFailure: " + t.getMessage());
            }
        });
    }
}
