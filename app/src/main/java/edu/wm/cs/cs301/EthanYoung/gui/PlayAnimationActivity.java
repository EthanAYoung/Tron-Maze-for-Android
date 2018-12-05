/**
 * @author Ethan Young
 * This activity is shown when a driver is automatically playing the game
 * The progress bar shows how much energy is left
 * The Show Map button shows the local map
 * The Solution button shows the maze solution
 * The Full Map button shows the whole map
 * The pause button pauses the game
 * The Go2Winning button wins the game
 * The Go2Loosing button loses the game
 * the back button returns you to AMazeActivity
 */

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
import android.widget.Toast;

import edu.wm.cs.cs301.EthanYoung.generation.MazeConfiguration;

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
    MazeConfiguration config;

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

        config = VariableStorage.config;

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

    /**
     * Shows the local map
     */
    public void showM(View view) {
        Log.v("LocalMapButton" , "Toggling local map");
        Toast.makeText(PlayAnimationActivity.this, "LocalMapButton Pushed", Toast.LENGTH_SHORT).show();
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

    /**
     * Shows the maze solution
     */
    public void showS(View view) {
        Log.v("SolutionButton" , "Toggling solution");
        Toast.makeText(PlayAnimationActivity.this, "SolutionButton Pushed", Toast.LENGTH_SHORT).show();
        //cont.keyDown(Constants.UserInput.ToggleSolution, 0);
    }

    /**
     * Shows the full maze
     */
    public void showF(View view) {
        Log.v("FullMapButton" , "Toggling full map");
        Toast.makeText(PlayAnimationActivity.this, "FullMapButton Pushed", Toast.LENGTH_SHORT).show();
        //cont.keyDown(Constants.UserInput.ToggleFullMap, 0);
    }

    /**
     * Pauses the game
     */
    public void pause(View view) {
        Log.v("PauseButton" , "Toggling Pause");
        Toast.makeText(PlayAnimationActivity.this, "Pause Pushed", Toast.LENGTH_SHORT).show();
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

    /**
     * Wins the game
     * Transitions to WinningActivity
     */
    public void winNow(View view) {
        Log.v("WinButton" , "Winning now");
        Toast.makeText(PlayAnimationActivity.this, "Go2Winning Pushed", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this , WinningActivity.class);
        intent.putExtra("ogBatt" , 3000);
        intent.putExtra("currBatt", 50);
        intent.putExtra("pathL" , 32);
        intent.putExtra("shortPathL" , 4);
        intent.putExtra("manual" , false);

        startActivity(intent);
    }

    /**
     * Loses the game
     * Transitions to LosingActivity
     */
    public void loseNow(View view) {
        Log.v("LoseButton" , "Losing now");
        Toast.makeText(PlayAnimationActivity.this, "Go2Loosing Pushed", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this , LosingActivity.class);
        intent.putExtra("ogBatt" , 3000);
        intent.putExtra("currBatt", 50);
        intent.putExtra("pathL" , 32);
        intent.putExtra("shortPathL" , 4);
        intent.putExtra("manual" , false);
        intent.putExtra("reason" , "crashed");

        startActivity(intent);
    }

    /**
     * Returns to AMazeActivity
     */
    public void returnToTitle(View view) {
        Log.v("BackButton" , "Returning to title");
        Toast.makeText(PlayAnimationActivity.this, "BackButton Pushed", Toast.LENGTH_SHORT).show();
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

    /**
     * A helper method to pause the program
     * useful to see what decisions the driver is making
     */
    public void timeDelay(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {}
    }

}
