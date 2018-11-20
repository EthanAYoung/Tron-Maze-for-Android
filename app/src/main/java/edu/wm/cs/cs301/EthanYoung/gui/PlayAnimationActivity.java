package edu.wm.cs.cs301.EthanYoung.gui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

//import gui.Controller;
//import gui.RobotDriver;

//import edu.wm.cs.cs301.EthanYoung.generation.VariableStorage;

public class PlayAnimationActivity extends AppCompatActivity {

    Boolean seeButts;
    Boolean paused;
    Button pB;
    Button mB;
    Button sB;
    Button fB;
    TextView pMsg;
    ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_animation);

        pB = (Button) findViewById(R.id.pauseButt);
        mB = (Button) findViewById(R.id.mapButt);
        sB = (Button) findViewById(R.id.solButt);
        fB = (Button) findViewById(R.id.fullButt);
        pMsg = (TextView) findViewById(R.id.pauseMsg);
        pBar = (ProgressBar) findViewById(R.id.progressBar);

        seeButts = false;
        paused = false;

        new batteryTracker().execute();

        /*Controller cont = VariableStorage.controller;
        RobotDriver dri = cont.getDriver();
        try {
            dri.drive2Exit();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

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
        intent.putExtra("manual" , false);

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

    class batteryTracker extends AsyncTask<Context, Integer, String> {

        @Override
        protected String doInBackground(Context...params) {
            for(int i = 0; i < 10; i++) {
                //pBar.incrementProgressBy(percent);
                publishProgress();
                timeDelay(1000);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pBar.incrementProgressBy(-10);
        }
    }

    public void timeDelay(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {}
    }

}
