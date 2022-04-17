package com.example.alecl.test4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button b;
    ScrollView scrollview;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        scrollview = new ScrollView(this);
        LinearLayout linearlayout = new LinearLayout(this);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        scrollview.addView(linearlayout);

        for(int i = 0; i<5;i++)
        {
            Toast.makeText(getApplicationContext(), this.getClass().toString(), Toast.LENGTH_SHORT).show();
            LinearLayout linear1 = new LinearLayout(this);
            linear1.setOrientation(LinearLayout.HORIZONTAL);
            linearlayout.addView(linear1);
            b = new Button(this);
            b.setText("Button no "+i);
            b.setWidth(1500000000);
            b.setHeight(200);
            b.setId(i);
            b.setTextSize(10);
            b.setPadding(8, 8, 8, 8);
            //b.setTypeface(Typeface.SERIF,Typeface.BOLD_ITALIC);
            b.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));

            linear1.addView(b);

            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Toast.makeText(getApplicationContext(), "Yipee.."+ v.getId(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        this.setContentView(scrollview);
    }
}