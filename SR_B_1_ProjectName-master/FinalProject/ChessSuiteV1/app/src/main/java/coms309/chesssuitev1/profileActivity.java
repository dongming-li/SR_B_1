package coms309.chesssuitev1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 *this class is responsible for showing up user's profile info.
 */
public class profileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //IDs
        TextView wins = (TextView) findViewById(R.id.wins_num);
        TextView losses = (TextView) findViewById(R.id.losses_num);
        TextView draws = (TextView) findViewById(R.id.draws_num);
        TextView win_loss_ratio = (TextView)findViewById(R.id.win_loss_ratio);
        
        //to get username and email
        SharedPreferences pref2 = PreferenceManager.getDefaultSharedPreferences(this);
        String winsValue = pref2.getString("wins", "");
        String lossesValue = pref2.getString("losses", "");
        String drawsValue = pref2.getString("draws", "");
        String win_loss_ratioValue = pref2.getString("win_loss_ratio","");

        Log.d("winsValue",winsValue);
        Log.d("lossesValue",lossesValue);
        Log.d("drawsValue",drawsValue);
        Log.d("win_loss_ratioValue", win_loss_ratioValue);

        //set Values
        wins.setText(winsValue);
        losses.setText(lossesValue);
        draws.setText(drawsValue);
        win_loss_ratio.setText(win_loss_ratioValue);
    }

}
