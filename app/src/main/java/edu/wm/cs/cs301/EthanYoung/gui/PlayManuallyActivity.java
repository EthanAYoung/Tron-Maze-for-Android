package edu.wm.cs.cs301.EthanYoung.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//import gui.Constants;
//import gui.Controller;
//import gui.ManualDriver;

//import edu.wm.cs.cs301.EthanYoung.generation.VariableStorage;

public class PlayManuallyActivity extends AppCompatActivity {

    //Controller cont;
    //ManualDriver dri;
    Boolean seeButts;
    Boolean paused;
    Button pB;
    Button mB;
    Button sB;
    Button fB;
    Button uB;
    Button rB;
    Button lB;
    TextView pMsg;
    String rob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_manually);

        pB = (Button) findViewById(R.id.pauseButt);
        mB = (Button) findViewById(R.id.mapButt);
        sB = (Button) findViewById(R.id.solButt);
        fB = (Button) findViewById(R.id.fullButt);
        uB = (Button) findViewById(R.id.upButt);
        rB = (Button) findViewById(R.id.rightButt);
        lB = (Button) findViewById(R.id.leftButt);
        pMsg = (TextView) findViewById(R.id.pauseMsg);

        seeButts = false;
        paused = false;

        Intent intent = getIntent();
        rob = intent.getStringExtra("robot");

        //cont = VariableStorage.controller;
        //dri = (ManualDriver) cont.getDriver();


        /*(Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

    public void go(View view) {
        Log.v("ForwardButton" , "Going forward one space");
        //dri.go();
    }

    public void turnR(View view) {
        Log.v("RightButton" , "Turning to the right");
        //dri.rotateR();
    }

    public void turnL(View view) {
        Log.v("LeftButton" , "Turning to the left");
        //dri.rotateL();
    }

    public void showM(View view) {
        Log.v("LocalMapButton" , "Toggling local map");
        //cont.keyDown(Constants.UserInput.ToggleLocalMap, 0);
        if(seeButts){
            seeButts = false;
            //sB.setVisibility(View.INVISIBLE);
            //fB.setVisibility(View.INVISIBLE);
        }
        else{
            seeButts = true;
            sB.setVisibility(View.VISIBLE);
            fB.setVisibility(View.VISIBLE);
        }
    }

    public void showS(View view) {
        Log.v("SolutionButton" , "Toggling solution");
        //cont.keyDown(Constants.UserInput.ToggleSolution, 0);
    }

    public void showF(View view) {
        Log.v("FullMapButton" , "Toggling full map");
        //cont.keyDown(Constants.UserInput.ToggleFullMap, 0);
    }

    public void pause(View view) {
        Log.v("PauseButton" , "Toggling Pause");
        if(paused){
            //dri.pause = false;
            paused = false;
            pMsg.setVisibility(View.INVISIBLE);
        }
        else{
            //dri.pause = ture;
            paused = true;
            pMsg.setVisibility(View.VISIBLE);
        }
    }

    public void winNow(View view) {
        Log.v("WinButton" , "Winning now");
        Intent intent = new Intent(this , WinningActivity.class);
        intent.putExtra("ogBatt" , 3000);
        intent.putExtra("currBatt", 50);
        intent.putExtra("pathL" , 32);
        intent.putExtra("shortPathL" , 4);
        intent.putExtra("manual" , true);

        startActivity(intent);
    }

    public void loseNow(View view) {
        Log.v("LoseButton" , "Losing now");
        Intent intent = new Intent(this , LosingActivity.class);
        intent.putExtra("ogBatt" , 3000);
        intent.putExtra("currBatt", 50);
        intent.putExtra("pathL" , 32);
        intent.putExtra("shortPathL" , 4);
        intent.putExtra("manual" , false);
        intent.putExtra("reason" , "crashed");

        startActivity(intent);
    }

    public void returnToTitle(View view) {
        Log.v("BackButton" , "Returning to title");
        Intent intent = new Intent(this , AMazeActivity.class);
        startActivity(intent);
    }
}
