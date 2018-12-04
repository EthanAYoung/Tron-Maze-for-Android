package edu.wm.cs.cs301.EthanYoung.gui;

import android.graphics.Bitmap;

public class VariableStorage {

    gui.Controller cont;
    Bitmap bMap;
    private static MazePanel panel;

    public static MazePanel getMazePanel() {
        return panel;
    }

    public Bitmap getBMap(){
        return bMap;
    }

    public void setCont(gui.Controller c){
        cont = c;
    }

    public void setBMap(Bitmap b) {
        bMap = b;
    }

    private static final VariableStorage holder = new VariableStorage();

    public static VariableStorage getInstance(){
        return holder;
    }

}
