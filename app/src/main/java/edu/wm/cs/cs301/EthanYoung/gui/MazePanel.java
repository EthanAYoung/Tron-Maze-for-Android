package edu.wm.cs.cs301.EthanYoung.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import edu.wm.cs.cs301.EthanYoung.generation.Seg;


/**
 * Add functionality for double buffering to an AWT Panel class.
 * Used for drawing a maze.
 *
 * @author Peter Kemper
 * includes methods by Peter Kemper from other classes
 * methods moved here by Ethan Young
 *
 * newColor, colorsEqual, setGcToG, drawHelper,
 * setLineColor, and additional constructor by Ethan Young
 *
 */
public class MazePanel extends View {
    /* Panel operates a double buffer see
     * http://www.codeproject.com/Articles/2136/Double-buffer-in-standard-Java-AWT
     * for details
     */
    // bufferImage can only be initialized if the container is displayable,
    // uses a delayed initialization and relies on client class to call initBufferImage()
    // before first use
    //private Image bufferImage;
    //private Graphics2D graphics; // obtained from bufferImage,
    // graphics is stored to allow clients to draw on the same graphics object repeatedly
    // has benefits if color settings should be remembered for subsequent drawing operations
    Bitmap bMap;
    Canvas can;
    Paint paint;

    /**
     * Constructor. Object is not focusable.
     */
    public MazePanel(Context context) {
        super(context);
        System.out.println("Super check");
        Log.v("super Check" , "******** Super Check ********");
        bMap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        VariableStorage.getInstance().setBMap(bMap);
        can = new Canvas(bMap);
        paint = new Paint();
        paint.setColor(Color.RED);
        System.out.println("variable check");
        Log.v("variable Check" , "******** Variable Check ********");
        //setFocusable(false);
        System.out.println("focusable check");
        Log.v("focusable Check" , "******** focusable Check ********");
        //bufferImage = null; // bufferImage initialized separately and later
        //graphics = null;	// same for graphics
    }

    public MazePanel(Context context, AttributeSet attributes){
        super(context, attributes);
        bMap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        can = new Canvas(bMap);
        paint = new Paint();
        paint.setColor(Color.RED);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bMap,0,0, null);
        Log.v("onDraw Check" , "******** onDraw Check ********");
        //drawSomeShapes();
        drawCorners();
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec){

        int defaultH = 300;
        int defaultW = 300;

        int width = measureHelper(defaultW, widthMeasureSpec);
        int height = measureHelper(defaultH, heightMeasureSpec);

        setMeasuredDimension(width, height);

    }

    private int measureHelper(int value, int measureSpec){
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            value = size;
        } else if (mode == MeasureSpec.AT_MOST) {
            value = Math.min(value, size);
        }
        return value;
    }

    protected void drawSomeShapes(){
        paint.setColor(Color.YELLOW);
        drawRect(0, 100, 100, 200, Color.YELLOW);
        paint.setColor(Color.GREEN);
        can.drawCircle(160, 100, 50, paint);
        paint.setColor(Color.BLUE);
        drawRect(200, 200, 200, 50, Color.BLUE);
        drawRect(250, 150, 50, 100, Color.BLUE);
        paint.setColor(Color.BLACK);
        can.drawLine(350, 30, 400, 400, paint);
        can.drawLine(320, 0, 380, 380, paint);

    }

    protected void drawCorners(){
        can.drawRect(0,0,100,100, paint);
        can.drawRect(300,0,400,100, paint);
        can.drawRect(0,300,100,400, paint);
        can.drawRect(300,300,400,400, paint);
    }

    /**
     * draws a rectangle with current color
     * @param x is the x coordinate of the top left corner
     * @param y is the y coordinate of the top left corner
     * @param w is the width of the rectangle
     * @param h is the height of the rectangle
     */
    private void drawRect(int x, int y, int w, int h){
        paint.setStyle(Paint.Style.FILL);
        can.drawRect(new Rect(x, y, w+x, h+y), paint);
    }

    /**
     * draws a rectangle with custom color
     * @param x is the x coordinate of the top left corner
     * @param y is the y coordinate of the top left corner
     * @param w is the width of the rectangle
     * @param h is the height of the rectangle
     * @param color is the color of the rectangle
     */
    private void drawRect(int x, int y, int w, int h, int color){
        paint.setStyle(Paint.Style.FILL);
        int temp = col;
        col = color;
        can.drawRect(new Rect(x, y, w+x, h+y), paint);
        col = temp;
        //canvas.drawRect(new Rect(x,y, w, h), paint);
    }

    /*public void update(Graphics g) {
        //paint(g);
    }*/
    public void update() {
        //paint(g);
        invalidate();
    }

    /**
     * Method to draw the buffer image on a graphics object that is
     * obtained from the superclass.
     * Warning: do not override getGraphics() or drawing might fail.
     */
    /*public void update() {
        paint(getGraphics());
    }*/

    /**
     * Draws the buffer image to the given graphics object.
     * This method is called when this panel should redraw itself.
     * The given graphics object is the one that actually shows
     * on the screen.
     */
    public void paint() {
        /*if (null == g) {
            System.out.println("MazePanel.paint: no graphics object, skipping drawImage operation");
        }
        else {
            g.drawImage(bufferImage,0,0,null);
        }*/
    }

    /**
     * Obtains a graphics object that can be used for drawing.
     * This MazePanel object internally stores the graphics object
     * and will return the same graphics object over multiple method calls.
     * The graphics object acts like a notepad where all clients draw
     * on to store their contribution to the overall image that is to be
     * delivered later.
     * To make the drawing visible on screen, one needs to trigger
     * a call of the paint method, which happens
     * when calling the update method.
     * @return graphics object to draw on, null if impossible to obtain image
     */
    public void getBufferGraphics() { //not sure what to return for this
        // if necessary instantiate and store a graphics object for later use
       /* if (null == graphics) {
            if (null == bufferImage) {
                bufferImage = createImage(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
                if (null == bufferImage)
                {
                    System.out.println("Error: creation of buffered image failed, presumedly container not displayable");
                    return null; // still no buffer image, give up
                }
            }
            graphics = (Graphics2D) bufferImage.getGraphics();
            if (null == graphics) {
                System.out.println("Error: creation of graphics for buffered image failed, presumedly container not displayable");
            }
            else {
                // System.out.println("MazePanel: Using Rendering Hint");
                // For drawing in FirstPersonDrawer, setting rendering hint
                // became necessary when lines of polygons
                // that were not horizontal or vertical looked ragged
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            }
        }
        return graphics;*/
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FirstPersonDrawer

    /**
     * The drawing is performed on a Graphics object. Storing it makes
     * its access easier for code that traverses the tree of BSP nodes
     * to draw segments. Drawing is performed in a piecemeal manner on
     * a buffer image, such that updating the panel that is on screen
     * with the current buffer image is the responsibility of
     * the StatePlaying class.
     */
    //public Graphics2D gc;

    /**
     * Draws a black and a grey rectangle to provide a background.
     * Note that this also erases previous drawings of maze or map.
     */
    private void drawBackground(int viewWidth, int viewHeight) {
        paint.setColor(Color.BLACK);
        drawRect(0, 0, viewWidth, viewHeight/2);
        paint.setColor(Color.GREEN);
        drawRect(0, 0, viewWidth, viewHeight/2);

        /*// black rectangle in upper half of screen
        graphics.setColor(Color.black);
        graphics.drawRect(0, 0, viewWidth, viewHeight/2);
        // grey rectangle in lower half of screen
        graphics.setColor(Color.darkGray);
        graphics.drawRect(0, viewHeight/2, viewWidth, viewHeight/2);*/
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //MapDrawer

    /*public void setLineColor(Cells seenCells, CardinalDirection dir, int x, int y) {
        // TODO Auto-generated method stub
        tempG.setColor(seenCells.hasWall(x,y, dir) ? Color.white : Color.gray);
    }*/

    public void setLineColor() {
        // TODO Auto-generated method stub
        //tempG.setColor(seenCells.hasWall(x,y, dir) ? Color.white : Color.gray);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //Seg

    /**
     * color of segment, only set by constructor and file reader.
     */
    public int col;




    /**
     * @return the color
     */
    public int getColor() {
        return col;
    }

    /**
     * @param color
     *            the color to set
     */
    public void setColor(int color) {
        /*
         * for debugging: use random color settings such that all segments look
         * different
         * int r = SingleRandom.getRandom().nextIntWithinInterval(20,240) ;
         * int g = SingleRandom.getRandom().nextIntWithinInterval(20,240) ;
         * int b = SingleRandom.getRandom().nextIntWithinInterval(20,240) ;
         * this.col = new Color(r,g,b); return ;
         */

        col = color;
        paint.setColor(color);
    }

    /**
     * checks if two colors are equal
     * one color is this panel's col
     * and the other color is from a different
     * seg object's panel's col
     */
    public boolean colorsEqual(Seg o) {
        //return (col == o.panel.getColor());
        Seg a = o;
        return col == o.col;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //New methods for refactoring

    /**
     * creates a new color object with an integer
     * @param val determines the new color
     * @return the newly created color
     */
    /*public int newColor(int val) {
        return val;
    }*/

    /**
     * creates a new color object with a string
     * @param val determines the new color
     * @return the newly created color
     */
    public int newColor(String val) {
        switch(val.toLowerCase()) {
            case "white":
                return Color.WHITE;
            case "yellow":
                return Color.YELLOW;
            case "red":
                return Color.RED;
        }
        return Color.MAGENTA;
    }

    /**
     * empty constructor so seg objects can access panel methods
     */
    /*public MazePanel(boolean forSeg, Context context) {
        super(context);

    }*/

    /**
     * a temporary Graphics object
     * helps MapDrawer and FirstPersonDrawer not have to
     * directly use Graphics objects
     */
    //public Graphics tempG;

    /**
     * sets gc to tempG
     * helper for FirstPersonDrawer
     */
    public void setGcToG() {
        // TODO Auto-generated method stub
        //gc = (Graphics2D) tempG ;
    }

    /**
     * helper for FirstPersonDrawer's draw method
     * @param viewWidth is FirstPersonDrawer's viewWidth
     * @param viewHeight is FirstPersonDrawer's viewHeight
     */
    public void drawHelper(int viewWidth, int viewHeight) {
        // update graphics
        // draw background figure: black on bottom half, grey on top half
        //drawBackground(viewWidth, viewWidth);
        // set color to white and draw what ever can be seen from the current position
        //tempG.setColor(Color.WHITE);

    }

}
