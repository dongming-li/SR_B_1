package luke.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayMessageActivityThree extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message_three);

        // Get Intent that started activity and extract string
        Intent intent = getIntent();
        String message = intent.getStringExtra(DisplayMessageActivityTwo.EXTRA_MESSAGE3);

        // Capture layout's TextView and use string for text
//        TextView textView = (TextView) findViewById(R.id.textView);
//        textView.setText(message);
    }

    /** Go to start screen if Home is tapped */
    public void goHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
