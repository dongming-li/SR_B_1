package com.acer.android.actionbar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements TextView.OnEditorActionListener {

    private MenuItem myActionMenuItem;
    private EditText myActionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_bar, menu);


        myActionMenuItem = menu.findItem(R.id.my_action);
        View actionView = myActionMenuItem.getActionView();


        if(actionView != null) {
            myActionEditText = (EditText) actionView.findViewById(R.id.myActionEditText);

            if(myActionEditText != null) {

                myActionEditText.setOnEditorActionListener(this);
            }
        }


        MenuItemCompat.setOnActionExpandListener(myActionMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                if(myActionEditText != null) {
                    myActionEditText.setText("");
                }

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            toggleActionBar();
        }

        return true;
    }

    private void toggleActionBar() {
        ActionBar actionBar = getActionBar();

        if(actionBar != null) {
            if(actionBar.isShowing()) {
                actionBar.hide();
            }
            else {
                actionBar.show();
            }
        }
    }

    public void openSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:

                Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_record:
                Toast.makeText(this, "Record clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_save:
                Toast.makeText(this, "Save clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_label:
                Toast.makeText(this, "Label clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_play:
                Toast.makeText(this, "Play clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if(keyEvent != null) {

            if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                CharSequence textInput = textView.getText();

                Toast.makeText(this, textInput, Toast.LENGTH_SHORT).show();
                MenuItemCompat.collapseActionView(myActionMenuItem);
            }
        }
        return false;
    }
}