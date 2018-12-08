/**
 * @author Ethan Young
 * The main activity for the Maze app
 * It allows you to select a difficulty level
 * a maze building algorithm and a robot to play the maze with
 * The explore button creates a new random maze using the selected parameters
 * The revist button loads an old maze from a saved file
 */

package edu.wm.cs.cs301.EthanYoung.gui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Spinner;
//import android.widget.Toast;

import edu.wm.cs.cs301.EthanYoung.generation.MultipleMazeWriter;


public class AMazeActivity extends AppCompatActivity {

    Spinner robSelect;
    Spinner algSelect;
    SeekBar levSelect;
    Boolean load;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amaze);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.menu);

        mediaPlayer.start();

        algSelect = (Spinner) findViewById(R.id.spinnerForAlgos);
        /*ArrayAdapter<CharSequence> adapterA = ArrayAdapter.createFromResource(this, R.array.algorithms, android.R.layout.simple_spinner_item);
        adapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        algSelect.setAdapter(adapterA);*/

        robSelect = (Spinner) findViewById(R.id.spinnerForRobos);
        /*ArrayAdapter<CharSequence> adapterR = ArrayAdapter.createFromResource(this, R.array.robots, android.R.layout.simple_spinner_item);
        adapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        algSelect.setAdapter(adapterR);*/

        levSelect = (SeekBar) findViewById(R.id.seekBar);

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

        //MultipleMazeWriter mmw = new MultipleMazeWriter(this);
        //mmw.start();
    }

    /**
     * When the explore button is clicked
     * Starts the process for random maze generation
     */
    public void exploreClicked(View view) {
        Log.v("ExploreButton" , "Starting to generate a maze");
        //Toast.makeText(AMazeActivity.this, "Explore Pushed", Toast.LENGTH_SHORT).show();
        load = false;
        generateMaze();
    }

    /**
     * When the revisit button is clicked
     * Starts the process for maze generation from a file
     */
    public void revisitClicked(View view) {
        Log.v("RevisitButton" , "Loading a maze");
        //Toast.makeText(AMazeActivity.this, "Revisit Pushed", Toast.LENGTH_SHORT).show();
        load = true;
        generateMaze();
    }

    /**
     * Transitions to GeneratingActivity
     */
    private void generateMaze() {
        Intent intent = new Intent(this , GeneratingActivity.class);
        intent.putExtra("robot" , robSelect.getSelectedItem().toString() );
        intent.putExtra("algorithm", algSelect.getSelectedItem().toString());
        intent.putExtra("load", load);
        intent.putExtra("level" , levSelect.getProgress() );

        VariableStorage.mediaPlayer = mediaPlayer;
        //mediaPlayer.stop();

        startActivity(intent);
    }

}
