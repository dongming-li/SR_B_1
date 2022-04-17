package com.experiment.colton.parrotapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ParrotMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parrot_message);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        if(message.toLowerCase().contains("cracker")){
            message = "Polly wants a cracker!!!";
        }
        else if(message.toLowerCase().contains("name") && !message.toLowerCase().contains("my")){
            message = "My name is Polly!!";
        }
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
    }

    public void backToMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
