/**
 * @author Ethan Young
 * This activity is shown when a driver is automatically playing the game
 * The Show Map button shows the local map
 * The Solution button shows the maze solution
 * The Full Map button shows the whole map
 * The pause button pauses the game
 * The Go2Winning button wins the game
 * The Go2Loosing button loses the game
 * the back button returns you to AMazeActivity
 * The Up button moves the robot forward
 * The Left button turns the robot left
 * The Right button turns the robot right
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        Log.v("manual play check", "manual play check");
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

    /**
     * tells the robot to go forward
     */
    public void go(View view) {
        Log.v("ForwardButton" , "Going forward one space");
        Toast.makeText(PlayManuallyActivity.this, "Forward Pushed", Toast.LENGTH_SHORT).show();
        //dri.go();
    }

    /**
     * tells the robot to turn right
     */
    public void turnR(View view) {
        Log.v("RightButton" , "Turning to the right");
        Toast.makeText(PlayManuallyActivity.this, "Right Pushed", Toast.LENGTH_SHORT).show();
        //dri.rotateR();
    }

    /**
     * tells the robot to turn left
     */
    public void turnL(View view) {
        Log.v("LeftButton" , "Turning to the left");
        Toast.makeText(PlayManuallyActivity.this, "Left Pushed", Toast.LENGTH_SHORT).show();
        //dri.rotateL();
    }

    /**
     * Shows the local map
     */
    public void showM(View view) {
        Log.v("LocalMapButton" , "Toggling local map");
        Toast.makeText(PlayManuallyActivity.this, "LocalMapButton Pushed", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(PlayManuallyActivity.this, "SolutionButton Pushed", Toast.LENGTH_SHORT).show();
        //cont.keyDown(Constants.UserInput.ToggleSolution, 0);
    }

    /**
     * Shows the full maze
     */
    public void showF(View view) {
        Log.v("FullMapButton" , "Toggling full map");
        Toast.makeText(PlayManuallyActivity.this, "FullMapButton Pushed", Toast.LENGTH_SHORT).show();
        //cont.keyDown(Constants.UserInput.ToggleFullMap, 0);
    }

    /**
     * Pauses the game
     */
    public void pause(View view) {
        Log.v("PauseButton" , "Toggling Pause");
        Toast.makeText(PlayManuallyActivity.this, "Pause Pushed", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(PlayManuallyActivity.this, "Go2Winning Pushed", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this , WinningActivity.class);
        intent.putExtra("ogBatt" , 3000);
        intent.putExtra("currBatt", 50);
        intent.putExtra("pathL" , 32);
        intent.putExtra("shortPathL" , 4);
        intent.putExtra("manual" , true);

        startActivity(intent);
    }

    /**
     * Loses the game
     * Transitions to LosingActivity
     */
    public void loseNow(View view) {
        Log.v("LoseButton" , "Losing now");
        Toast.makeText(PlayManuallyActivity.this, "Go2Loosing Pushed", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(PlayManuallyActivity.this, "BackButton Pushed", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this , AMazeActivity.class);
        startActivity(intent);
    }
}
