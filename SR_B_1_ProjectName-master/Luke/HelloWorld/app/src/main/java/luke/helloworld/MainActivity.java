package luke.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "luke.helloworld.MESSAGE";
    public static final String EXTRA_MESSAGE2 = "luke.helloworld.MESSAGE2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView im = (ImageView)findViewById(R.id.imageView);
        TextView test = (TextView) findViewById(R.id.editText2);
        test.setText("X  X  X  X  X  X  X  X\nX  X  X  X  X  X  X  X\nX  X  X  X  X  X  X  X\nX  X  X  X  X  X  X  X\nX  X  X  X  X  X  X  X\nX  X  X  X  X  X  X  X\nX  X  X  X  X  X  X  X\nX  X  X  X  X  X  X  X");
    }

    /** Gets called when the "send" button is tapped */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    /** Go to this other screen if Send2 is tapped */
    public void sendScreenTwo(View view2) {
        Intent intent2 = new Intent(this, DisplayMessageActivityTwo.class);
        String message2 = "String2";
        intent2.putExtra(EXTRA_MESSAGE2, message2);
        startActivity(intent2);
    }
}
