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

public class WinningActivity extends AppCompatActivity {

    TextView pathLText;
    TextView shortPathLText;
    TextView battConsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning);

        pathLText = (TextView) findViewById(R.id.pathLenText);
        shortPathLText = (TextView) findViewById(R.id.shortPathLenText);
        battConsText = (TextView) findViewById(R.id.engConsText);

        Intent intent = getIntent();
        int ogBatt = intent.getIntExtra("ogBatt", 3000);
        int currBatt = intent.getIntExtra("currBatt", 250);
        int pathL = intent.getIntExtra("pathL", 343);
        int shortPathL = intent.getIntExtra("shortPathL", 6);
        Boolean manual = intent.getBooleanExtra("manual", true);

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
        Intent intent = new Intent(this , AMazeActivity.class);
        startActivity(intent);
    }

}