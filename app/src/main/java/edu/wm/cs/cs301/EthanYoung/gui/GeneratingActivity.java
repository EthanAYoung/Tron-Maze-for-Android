/**
 * @author Ethan Young
 * This activity is where the maze is generated from scratch
 * or loaded from a saved file
 * the progress bar shows how far along the generation is
 * the back button returns you to AMazeActivity
 */

package edu.wm.cs.cs301.EthanYoung.gui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;

//import edu.wm.cs.cs301.EthanYoung.generation.VariableStorage;
//import gui.Constants;
//import gui.Controller;
//import gui.RobotDriver;


public class GeneratingActivity extends AppCompatActivity {

    ProgressBar pBar;
    //private Handler mHandler = new Handler();
    String algo;
    String rob;
    int level;
    //int algorithmNum;
    //Button btn;
    public Thread buildThread;
    File savedFile;
    //MazeFileReader mfr;
    Boolean load;
    //Controller cont;
    //RobotDriver dri;
    Boolean wentBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating);
        pBar = (ProgressBar) findViewById(R.id.progressBar);
        Intent intent = getIntent();
        algo = intent.getStringExtra("algorithm");
        rob = intent.getStringExtra("robot");
        level = intent.getIntExtra("level", 0);
        load = intent.getBooleanExtra("genMode" , false);
        //VariableStorage.mostRecentSkill = level;
        //VariableStorage.stepsTaken = 0;
        //VariableStorage.energyConsumed = 0;
        wentBack = false;


        new MazeGenerator().execute();

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
     * Handles the generation of the maze in the background
     * Also updates the progress bar
     */
    class MazeGenerator extends AsyncTask<Context, Integer, String> {

        @Override
        protected String doInBackground(Context...params) {
            if(load) {
                int percent = 0;
                for(int i = 0; i < 10; i++){
                    //pBar.incrementProgressBy(percent);
                    publishProgress();
                    percent += 10;
                    timeDelay(1000);
                }
            }
            else {
                int percent = 0;
                for(int i = 0; i < 10; i++){
                    //pBar.incrementProgressBy(percent);
                    publishProgress();
                    percent += 10;
                    timeDelay(1000);
                }
                /*cont = createController(algo);
                if (rob == "Manual") {
                    cont.driverType = 0;
                }
                else if (rob == "Wizard") {
                    cont.driverType = 1;
                }
                else if (rob == "Wall Follower") {
                    cont.driverType = 2;
                }
                else if (rob == "Explorer") {
                    cont.driverType = 3;
                }
                else {
                    cont.driverType = 0;
                }
                VariableStorage.controller = cont;
                cont.start();
                cont.keyDown(Constants.UserInput.Start, level);*/
            }
            if(wentBack == false) {
                if (rob.equals("Manual")) {
                    moveOn(true);
                }
                else {
                    moveOn(false);
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pBar.incrementProgressBy(10);
        }
    }

    /**
     * Instantiates a controller with settings according to the given parameter.
     * @param //parameter can identify a generation method (Prim, Kruskal, Eller)
     * or a filename that contains a generated maze that is then loaded,
     * or can be null
     * @return the newly instantiated and configured controller
     */
    /*Controller createController(String parameter) {
        // need to instantiate a controller to return as a result in any case
        Controller result = new Controller() ;
        String msg = null; // message for feedback
        // Case 1: no input
        if (parameter == null) {
            msg = "MazeApplication: maze will be generated with a randomized algorithm.";
        }
        // Case 2: Prim
        else if ("Prim".equalsIgnoreCase(parameter))
        {
            msg = "MazeApplication: generating random maze with Prim's algorithm.";
            result.setBuilder(generation.Order.Builder.Prim);
        }
        // Case 3 a and b: Eller, Kruskal or some other generation algorithm
        else if ("Kruskal".equalsIgnoreCase(parameter))
        {
            // TODO: for P2 assignment, please add code to set the builder accordingly
            msg = "MazeApplication: generating random maze with Kruskal's algorithm.";
            result.setBuilder(generation.Order.Builder.Kruskal);
        }
        else if ("Eller".equalsIgnoreCase(parameter))
        {
            // TODO: for P2 assignment, please add code to set the builder accordingly
            msg = "MazeApplication: generating random maze with Eller's algorithm.";
            result.setBuilder(generation.Order.Builder.Eller);
        }
        else if("noG".equalsIgnoreCase(parameter)) {
            msg = "MazeApplication: generating random maze with no graphics.";
            result.turnOffGraphics();
        }
        // Case 4: a file
        else {
            File f = new File(parameter) ;
            if (f.exists() && f.canRead())
            {
                msg = "MazeApplication: loading maze from file: " + parameter;
                result.setFileName(parameter);
                return result;
            }
            else {
                // None of the predefined strings and not a filename either:
                msg = "MazeApplication: unknown parameter value: " + parameter + " ignored, operating in default mode.";
            }
        }
        // controller instanted and attributes set according to given input parameter
        // output message and return controller
        System.out.println(msg);
        return result;
    }*/

    /**
     * Transitions to PlayManuallyActivity or PlayAnimationActivity
     */
    private void moveOn(Boolean manual){
        Intent intent;
        if(manual){
            intent = new Intent(this , PlayManuallyActivity.class);
        }
        else {
            intent = new Intent(this , PlayAnimationActivity.class);
            intent.putExtra("robot" , rob);
        }
        startActivity(intent);
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

    /**
     * Returns to AMazeActivity
     */
    public void returnToTitle(View view) {
        Log.v("BackButton" , "Returning to title");
        Toast.makeText(GeneratingActivity.this, "BackButton Pushed", Toast.LENGTH_SHORT).show();
        wentBack = true;
        Intent intent = new Intent(this , AMazeActivity.class);
        startActivity(intent);
    }

}
