package coms309.chesssuitev1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * This class handles user settings preferences
 */
public class SettingsActivity extends AppCompatActivity {
    public static final String
            KEY_PREF_EXAMPLE_SWITCH = "sounds_pref";
    public static  final String KEY_PREF_BOARD_COLOR = "board_colors";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
