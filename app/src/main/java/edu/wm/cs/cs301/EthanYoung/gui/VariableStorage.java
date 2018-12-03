package edu.wm.cs.cs301.EthanYoung.gui;

import android.graphics.Bitmap;

public class VariableStorage {

    //Controller cont;
    Bitmap bMap;

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
