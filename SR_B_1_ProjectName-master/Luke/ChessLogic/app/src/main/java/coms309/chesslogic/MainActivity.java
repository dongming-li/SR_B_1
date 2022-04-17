package coms309.chesslogic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView test = (TextView) findViewById(R.id.TextView);
        // create a simple 8 by 8 board for now
        ChessBoard chessBoard = new ChessBoard(8,8);
    }
}
