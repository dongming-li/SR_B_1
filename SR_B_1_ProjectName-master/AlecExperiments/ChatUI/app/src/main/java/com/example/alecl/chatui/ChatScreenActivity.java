package com.example.alecl.chatui;

/**
 * Created by alecl
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity for the chat screen itself
 */
public class ChatScreenActivity extends AppCompatActivity {

    /**
     * On the creation of the screen, create the layout
     * @param savedInstanceState - instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent temp = this.getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
    }

    /**
     * When the activity loads to the forground, grab an update the from the server
     */
    @Override
    protected void onResume(){
        super.onResume();
        Intent temp = this.getIntent();
        String chatID = temp.getExtras().get(("chatid")).toString();
        Object[] params = {chatID, false, "", this};
        new ChatScreenClient().execute(params);
    }

    /**
     * Handles sending a message when the send button is clicked
     */
    public void sendMessage (View v){
        EditText editText = (EditText) findViewById(R.id.editText);
        //grabs the message to send
        String message = editText.getText().toString();
        //clears the text field
        editText.setText("");
        //our username
        Intent temp = this.getIntent();
        String chatID = temp.getExtras().get(("chatid")).toString();
        //starting up threads and stuffz
        Object[] params = {chatID, true, message, this};
        new ChatScreenClient().execute(params);
        Object[] params2 = {chatID, false, "", this};
        new ChatScreenClient().execute(params2);
    }

    public void draw(String messageHistory){
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(messageHistory);
    }

}
