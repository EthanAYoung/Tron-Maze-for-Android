package edu.wm.cs.cs301.EthanYoung.gui;

import android.graphics.Bitmap;
import android.media.MediaPlayer;

import edu.wm.cs.cs301.EthanYoung.generation.MazeConfiguration;

public class VariableStorage {

    public static MediaPlayer mediaPlayer;
    //static Controller cont;
    static Bitmap bMap;
    private static MazePanel panel;
    static MazeConfiguration config;

    static MazeConfiguration config0;
    static MazeConfiguration config1;
    static MazeConfiguration config2;
    static MazeConfiguration config3;


    public static MazePanel getMazePanel() {
        return panel;
    }

    public Bitmap getBMap(){
        return bMap;
    }

    /*public void setCont(Controller c){
        cont = c;
    }*/

    public void setBMap(Bitmap b) {
        bMap = b;
    }

    private static final VariableStorage holder = new VariableStorage();

    public static VariableStorage getInstance(){
        return holder;
    }

}
