package coms309.chesssuitev1;

/**
 * Created by alecl
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.app.Activity;
import java.util.ArrayList;

/**
 * Main chat screen activity.
 * This activity shows a list of chats that the client is currently in and allows them to select which chat to view.
 * @author alecl
 */
public class ChatMainActivity extends Activity {
    /**
     * A linear layout for displaying and dynamically generating buttons
     */
    LinearLayout linearlayout;
    /**
     * A scrollview for displaying and dynamically generating buttons
     */
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


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String clientName = prefs.getString("username", "");
        //basically connects to the server and grabs the info to be displayed and displays it
        Object[] params = {clientName, this};
        new GetAllListClient().execute(params);

        this.setContentView(scrollview);
    }

    //This is for loading up the chat with whoever is clicked on

    /**
     * Method for entering a chat screen view with the supplied info
     * @param view The button that was clicked
     * @param otherUserName The other person's username
     * @param chatID The chat's id
     */
    public void enterChat (View view, String otherUserName, int chatID){
        String otherUsername = otherUserName;
        int chatid = chatID;
        Intent intent = new Intent(this, ChatScreenActivity.class);
        intent.putExtra("otherName", otherUsername);
        intent.putExtra("chatid", chatid);
        startActivity(intent);
    }

    /**
     * Method for drawing buttons dynamically on the screen
     * @param otherUsernames An arraylist of NameIDPairs representing the usernames and chat ids of all the other chats the client is in
     */
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
