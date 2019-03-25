package com.hfad.cards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HelpActivity extends AppCompatActivity {

    private TextView helpText1;
    private ImageView cancel;
    private ImageView next;
    private TextView helpText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        wireWidgets();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(helpText1.getVisibility() == View.VISIBLE){
                    helpText1.setVisibility(View.INVISIBLE);
                    helpText2.setVisibility(View.VISIBLE);
                    next.setRotation(180);
                }

                else if(helpText2.getVisibility() == View.VISIBLE){
                    helpText2.setVisibility(View.INVISIBLE);
                    helpText1.setVisibility(View.VISIBLE);
                    next.setRotation(0);
                }

            }
        });
    }

    private void wireWidgets() {
        helpText1 = findViewById(R.id.textView_help_text1);
        helpText2 = findViewById(R.id.textView_help_text2);
        cancel = findViewById(R.id.imageView_help_back);
        next = findViewById(R.id.imageView_help_next);

    }
}
