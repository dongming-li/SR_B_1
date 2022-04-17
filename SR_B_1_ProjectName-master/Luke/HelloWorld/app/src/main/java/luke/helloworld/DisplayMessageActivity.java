package luke.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class  DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);


        // Get Intent that started activity and extract string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture layout's TextView and use string for text
        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(reverseString(message));
    }

    private String reverseString(String input) {
        String reverse = "";
        for (int i = input.length() -1; i > -1; i--) {
            reverse += input.charAt(i);
        }
        return reverse;
    }
}
