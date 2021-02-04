package stickfigure;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 *
 * @author maeda
 */
public class Sprite {
    /** The x position of this entity in terms of grid cells */
    private float x;
    /** The y position of this entity in terms of grid cells */
    private float y;
    /** The image to draw for this entity */
    private Image[] frames;
    private Image spriteFight;
    /** The map which this entity is wandering around */
    private Map map;
    /** The angle to draw this entity at */
    private float ang;
    /** The size of this entity, this is used to calculate collisions with walls */
    private float size = 0.3f;
    int imageNumber = 0;
    int score=0;
    GoldMap goldMap;
    boolean canFight = false;
    int life = 3;
    boolean bloodSplatter=false;
    Image blood;
    boolean win = false;
    


    /**
     * Create a new entity in the game
     *
     * @param image The image to represent this entity (needs to be 32x32)
     * @param map The map this entity is going to wander around
     * @param x The initial x position of this entity in grid cells
     * @param y The initial y position of this entity in grid cells
     */
    public Sprite( Map map, GoldMap inGMAP) {
        //image1 = Toolkit.getDefaultToolkit().getImage("F:\\stickFigure\\sprite1.gif");
        //image2 = Toolkit.getDefaultToolkit().getImage("F:\\stickFigure\\sprite2.gif");
        loadImages();
        this.map = map;
        goldMap = inGMAP;
        x = 1.5f;
        y =1.5f;


    }
    public void loadImages(){
        //Toolkit toolkit = getToolkit();
        frames = new Image[5];
        // Load animation frames
        for(int k=1; k<=3; k++){
            //frames[k-1] = Toolkit.getDefaultToolkit().getImage("F:\\stickFigure\\sprite"+(k-1)+".gif");
            frames[k-1] = Toolkit.getDefaultToolkit().getImage("sprite"+(k-1)+".gif");

        }
        //spriteFight = Toolkit.getDefaultToolkit().getImage("F:\\stickFigure\\spriteWithSword.gif");
        //blood = Toolkit.getDefaultToolkit().getImage("F:\\stickFigure\\bang.gif");
        spriteFight = Toolkit.getDefaultToolkit().getImage("spriteWithSword.gif");
        blood = Toolkit.getDefaultToolkit().getImage("bang.gif");

    }



    /**
     * Move this entity a given amount. This may or may not succeed depending
     * on collisions
     *
     * @param dx The amount to move on the x axis
     * @param dy The amount to move on the y axis
     * @return True if the move succeeded
     */
    public boolean move(float dx, float dy) {
        // work out what the new position of this entity will be
        float nx = x + dx;
        float ny = y + dy;

        // check if the new position of the entity collides with
        // anything
        if (validLocation(nx, ny)) {
            // if it doesn't then change our position to the new position
            x = nx;
            y = ny;

            // and calculate the angle we're facing based on our last move
            ang = (float) (Math.atan2(dy, dx) - (Math.PI / 2));
            return true;
        }

        // if it wasn'n't do anything apart from
        // tell the caller
        return false;
    }

    /**
     * Check if the entity would be at a valid location if its position
     * was as specified
     *
     * @param nx The potential x position for the entity
     * @param ny The potential y position for the entity
     * @return True if the new position specified would be valid
     */
    public boolean validLocation(float nx, float ny) {
        // here we're going to check some points at the corners of
        // the player to see whether we're at an invalid location
        // if any of them are blocked then the location specified
        // isn't valid
        if (map.blocked((int)(nx - size), (int)(ny - size))) {
            return false;
        }
        if (map.blocked((int)(nx + size), (int)(ny - size))) {
            return false;
        }
        if (map.blocked((int)(nx - size), (int)(ny + size))) {
            return false;
        }
        if (map.blocked((int)(nx + size), (int)(ny + size))) {
            return false;
        }

        // if all the points checked are unblocked then we're in an ok
        // location
        return true;
    }

    public void eatGold(){
        goldMap.amountOfGold--;
        goldMap.setToClear(x,y);
        score++;
    }
    public void useWeapon(){
        goldMap.setToClear(x,y);
        canFight=true;
    }
    public float getX(){
        return  x;
    }
    public float getY(){
        return  y;
    }

    /**
     * Draw this entity to the graphics context provided.
     *
     * @param g The graphics context to which the entity should be drawn
     */
    public void paint(Graphics2D g) {
        // work out the screen position of the entity based on the
        // x/y position and the size that tiles are being rendered at. So
        // if we'e'd render on screen
        // at 15,15.
        int xp = (int) (Map.TILE_SIZE * x );
        int yp = (int) (Map.TILE_SIZE * y );


        // rotate the sprite based on the current angle and then
        // draw it
        g.rotate(ang, xp, yp);
        if(life>=0){
            if(canFight){
                 g.drawImage(spriteFight, (int) (xp - 8), (int) (yp - 8 ), null);
            }else{
                if( imageNumber==0){g.drawImage(frames[0], (int) (xp - 8 ), (int) (yp - 8), null);  }
                else if( imageNumber==1){g.drawImage(frames[1], (int) (xp - 8), (int) (yp - 8 ), null); }
            }
        }else{
            bang(g);
        }
        g.rotate(-ang, xp, yp);
        //System.out.println("x: "+xp +", y: "+ yp);
        
    }
     public void bang(Graphics2D g){
       int xp = (int) (Map.TILE_SIZE * x );
        int yp = (int) (Map.TILE_SIZE * y );
         g.drawImage(blood, (int) (xp - 8), (int) (yp - 8 ), null);
   }
}


