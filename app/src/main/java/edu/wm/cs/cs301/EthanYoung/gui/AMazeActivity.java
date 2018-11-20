package edu.wm.cs.cs301.EthanYoung.gui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.util.Log;
import android.content.Intent;


public class AMazeActivity extends AppCompatActivity {

    Spinner robSelect;
    Spinner algSelect;
    SeekBar levSelect;
    Boolean load;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amaze);

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
    }

    public void exploreClicked(View view) {
        Log.v("ExploreButton" , "Starting to generate a maze");
        load = false;
        generateMaze();
    }

    public void revisitClicked(View view) {
        Log.v("RevisitButton" , "Loading a maze");
        load = true;
        generateMaze();
    }

    private void generateMaze() {
        Intent intent = new Intent(this , GeneratingActivity.class);
        intent.putExtra("robot" , robSelect.getSelectedItem().toString() );
        intent.putExtra("algorithm", algSelect.getSelectedItem().toString());
        intent.putExtra("load", load);
        intent.putExtra("level" , levSelect.getProgress() );

        startActivity(intent);
    }

}
