package luke.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayMessageActivityTwo extends AppCompatActivity {

    public static final String EXTRA_MESSAGE3 = "luke.helloworld.MESSAGE3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message_two);


        // Get Intent that started activity and extract string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);

        // Capture layout's TextView and use string for text
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
    }

    /** Go to this third screen if  is tapped */
    public void sendScreenThree(View view3) {
        Intent intent3 = new Intent(this, DisplayMessageActivityThree.class);
        String message3 = "Press to return to home";
        intent3.putExtra(EXTRA_MESSAGE3, message3);
        startActivity(intent3);
    }
}
