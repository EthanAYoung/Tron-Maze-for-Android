/**
 * @author Ethan Young
 * This activity is shown when you lose the maze game
 * Shows the energy consumption
 * Shows the length of the path you took
 * Shows the length of the shortest path
 * Shows the reason you lost
 * the back button returns you to AMazeActivity
 */

package edu.wm.cs.cs301.EthanYoung.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LosingActivity extends AppCompatActivity {

    TextView pathLText;
    TextView shortPathLText;
    TextView battConsText;
    TextView reasonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_losing);

        pathLText = (TextView) findViewById(R.id.pathLenText);
        shortPathLText = (TextView) findViewById(R.id.shortPathLenText);
        battConsText = (TextView) findViewById(R.id.engConsText);
        reasonText = (TextView) findViewById(R.id.stopReason);

        Intent intent = getIntent();
        int ogBatt = intent.getIntExtra("ogBatt", 3000);
        int currBatt = intent.getIntExtra("currBatt", 250);
        int pathL = intent.getIntExtra("pathL", 343);
        int shortPathL = intent.getIntExtra("shortPathL", 6);
        Boolean manual = intent.getBooleanExtra("manual", true);
        String reason = intent.getStringExtra("reason");

        String temp;
        if(manual){
            battConsText.setVisibility(View.INVISIBLE);
        }
        else{
            battConsText.setVisibility(View.VISIBLE);
            temp = "Energy Consumed: " + (ogBatt - currBatt);
            battConsText.setText(temp);
        }
        pathLText.setText("Your Path Length: " + pathL);
        shortPathLText.setText("Shortest Path Length: " + shortPathL);
        reasonText.setText("Reason for Stopping: " + reason);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void returnToTitle(View view) {
        Log.v("BackButton" , "Returning to title");
        Toast.makeText(LosingActivity.this, "BackButton Pushed", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this , AMazeActivity.class);
        startActivity(intent);
    }

}
