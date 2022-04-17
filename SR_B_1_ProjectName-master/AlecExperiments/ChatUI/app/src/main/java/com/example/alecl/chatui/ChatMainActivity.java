package com.example.alecl.chatui;

/**
 * Created by alecl
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.app.Activity;

import java.util.ArrayList;

public class ChatMainActivity extends Activity {
    LinearLayout linearlayout;
    ScrollView scrollview;

    @Override
    /**
     * Creates the screen and calls GetAllListClient
     * @param savedInstanceState - the instance state
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scrollview = new ScrollView(this);
        linearlayout = new LinearLayout(this);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        scrollview.addView(linearlayout);


        String clientName = "Alpha";
        //basically connects to the server and grabs the info to be displayed and displays it
        Object[] params = {clientName, this};
        new GetAllListClient().execute(params);

        this.setContentView(scrollview);
    }

    //This is for loading up the chat with whoever is clicked on

    /**
     * Detects when a button is clicked and opens the chat screen with that person
     * @param view
     */
    public void enterChat (View view, String otherUserName, int chatID){
        String otherUsername = otherUserName;
        int chatid = chatID;
        Intent intent = new Intent(this, ChatScreenActivity.class);
        intent.putExtra("otherName", otherUsername);
        intent.putExtra("chatid", chatid);
        startActivity(intent);
    }

    public void draw(ArrayList<NameIDPair> otherUsernames){
        Button b;
        for(int i = 0; i < otherUsernames.size() ;i++)
        {
            LinearLayout linear1 = new LinearLayout(this);
            linear1.setOrientation(LinearLayout.HORIZONTAL);

            linearlayout.addView(linear1);
            b = new Button(this);
            b.setText(otherUsernames.get(i).getName());
            b.setWidth(1500000000);
            b.setHeight(200);
            b.setId(otherUsernames.get(i).getID());
            b.setTextSize(10);
            b.setPadding(8, 8, 8, 8);
            b.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            linear1.addView(b);

            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Button v2 = (Button) v;
                    enterChat(v, v2.getText().toString(), v.getId());
                }
            });
        }
    }
}
