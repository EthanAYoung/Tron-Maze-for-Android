package edu.wm.cs.cs301.EthanYoung.generation;

import android.content.Context;
import android.util.Log;

import edu.wm.cs.cs301.EthanYoung.gui.MazeFileWriter;

public class MultipleMazeWriter {

    private Context con;

    public MultipleMazeWriter(Context c){
        con = c;
    }

    public void start(){
        writeMaze(0);
        writeMaze(1);
        writeMaze(2);
        writeMaze(3);
    }

    private void writeMaze(int level){
        MazeFactory fac = new MazeFactory(false);
        OrderHelper ord = new OrderHelper(level, Order.Builder.DFS, true);
        fac.order(ord);
        MazeBuilder mBuild = fac.getMazeBuilder();
        fac.waitTillDelivered();
        MazeConfiguration config = ord.getConfig();

        //String path = "C:/Users/Ethan/AndroidStudioProjects/AMazeByEthanYoung/app/src/main/java/edu/wm/cs/cs301/EthanYoung/generation/";
        //String path = "C:\\Users\\Ethan\\AndroidStudioProjects\\AMazeByEthanYoung\\app\\src\\main\\java\\edu\\wm\\cs\\cs301\\EthanYoung\\generation\\";

        Distance dist = config.getMazedists();
        Log.v("Dists", ""+dist.getAllDistanceValues().length);
        Log.v("Dists2", ""+dist.getAllDistanceValues()[0].length);

        MazeFileWriter.store(con, "maze" + level + ".xml", config.getWidth(), config.getHeight(), 0, mBuild.parits, mBuild.rootNode, config.getMazecells(), config.getMazedists().getAllDistanceValues(), config.getStartingPosition()[0], config.getStartingPosition()[1]);

        Log.v("Maze Written", "Level " + level + " is done");
    }
}
