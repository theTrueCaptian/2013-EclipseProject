
package stickfigure;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;


/**
 *
 * @author maeda
 */
public class SecurityGuard extends Thread{
    /** The x position of this entity in terms of grid cells */
    float x;
    /** The y position of this entity in terms of grid cells */
    float y;
    /** The image to draw for this entity */
    private Image[] frames;
    /** The map which this entity is wandering around */
    Map map;
    /** The angle to draw this entity at */
    private float ang;
    /** The size of this entity, this is used to calculate collisions with walls */
    private float size = 0.3f;
    int imageNumber = 0;
    GoldMap goldMap;
    CloseCombat combat;
    Sprite sprite;
    boolean up = false, down = false, left= false, right = false;
    PathFinder finder;
    MyFrame game ;
       
    Timer timer = new Timer(150, new TimerListener());
    Path path;
    int ctr = 0;
    int pathCounter=0;
    boolean kill = false;
    boolean bloodSplatter=false;
    int bloodSplatterCtr = 0;
    Image blood;
    long last = System.nanoTime();
    /**
     * Create a new entity in the game
     *
     * @param image The image to represent this entity (needs to be 32x32)
     * @param map The map this entity is going to wander around
     * @param x The initial x position of this entity in grid cells
     * @param y The initial y position of this entity in grid cells
     */
    public SecurityGuard( Map map, GoldMap inGMAP, Sprite inSprite, MyFrame game, int seed) {
        loadImages();
        this.map = map;
        this.game = game;
        goldMap = inGMAP;
        sprite = inSprite;
        getStartingPoint(seed);
        //timer.start();
        newPath((int)sprite.getX(), (int)sprite.getY());
    }

    public SecurityGuard( Map map, GoldMap inGMAP, Sprite inSprite, MyFrame game, int x, int y) {
        loadImages();
        this.map = map;
        this.game = game;
        goldMap = inGMAP;
        sprite = inSprite;
        this.x = x;
        this.y = y;
        newPath((int)sprite.getX(), (int)sprite.getY());
    }

    public void run(){
        System.out.println("run guard!");
        timer.start();
    }
    class TimerListener implements ActionListener {
       // Handle ActionEvent
        public void actionPerformed(ActionEvent e) {
            long delta = (System.nanoTime() - last) / 1000000;
            last = System.nanoTime();
            if(!sprite.canFight){
                
                if(pathCounter==10){
                    pathCounter=0;
                    newPath((int)sprite.getX(), (int)sprite.getY());
                }else {
                    pathCounter++;
                    proceedNextStep(delta % 5);
                }
            }else if(sprite.canFight){
                //runaway

                if(pathCounter==10){
                    pathCounter=0;
                    newRunAwayPath();
                }else {
                    pathCounter++;
                   proceedNextStep(delta % 5);
                }
                
            }

        }
    }

    public void loadImages(){
        //Toolkit toolkit = getToolkit();
        frames = new Image[5];
        // Load animation frames
        for(int k=1; k<=3; k++){
            //frames[k-1] = Toolkit.getDefaultToolkit().getImage("F:\\stickFigure\\enemy"+(k-1)+".gif");
            frames[k-1] = Toolkit.getDefaultToolkit().getImage("enemy"+(k-1)+".gif");

        }
        //blood = Toolkit.getDefaultToolkit().getImage("F:\\stickFigure\\bang.gif");
        blood = Toolkit.getDefaultToolkit().getImage("bang.gif");

    }

    public void getStartingPoint(int seed){
        RandomNumberGenerator i = new RandomNumberGenerator(seed, 0, goldMap.WIDTH-1);
        RandomNumberGenerator j = new RandomNumberGenerator(seed, 0, goldMap.HEIGHT-1);
        do{
            x = i.getRandomNumber();
            y = j.getRandomNumber();

        }while(map.blocked((int)x, (int)y) && x!=sprite.getX() && y!=sprite.getY());
    }

    public void newPath(int tx, int ty){
        ctr=0;
        map.clearVisited();
        finder = new AStarPathFinder(map, 30, false);
       
        //find path
        path = finder.findPath((int)x, (int)y, tx, ty);
    }

    public void proceedNextStep(long delta) {
        float dx = 0;
        float dy = 0;
        System.out.println(ctr);
      
        if(path!=null && ctr<path.getLength() && ctr>-1){
           if(path.getX(ctr)-x>0 ){//move right
               System.out.println("right");
               dx += 1;
            }
           if(path.getX(ctr)-x<0){//move left
               System.out.println("left");
                dx -= 1;
            }
            if(path.getY(ctr)-y>0 ){//move down
                System.out.println("down on map");
                dy += 1;
            }
            if(path.getY(ctr)-y<0){//move up
                System.out.println("upu");
                dy -= 1;
            }
           //dx=dx*delta;
           //dy=dy*delta;
            ang = (float) (Math.atan2(dy/2, dx/2) - (Math.PI / 2));
            if(!map.blocked((int)(x+dx/2), (int)(y+dy/2))){
                x = x+dx/2;
                y = y+dy/2;
            }
            //x = path.getX(ctr);
            //y = path.getY(ctr);
            ctr ++;
        }
        
    }

    public void newRunAwayPath(){
        int xDest=(int) x, yDest=(int) y;
        RandomNumberGenerator i = new RandomNumberGenerator(10, 0,(int)Math.abs(goldMap.WIDTH-1 - x));
        RandomNumberGenerator j = new RandomNumberGenerator(10, 0, (int)Math.abs(goldMap.HEIGHT-1-y));
        do{
            do{
                //generate delta
                int xDelta = i.getRandomNumber();
                int yDelta = j.getRandomNumber();
                System.out.println("xd:"+xDelta+", yd:"+yDelta);
                //delta +guard location= guard destination
                if(sprite.getX()-x>0 ){
                    xDest=(int) (xDelta + x);
                }else if(sprite.getX()-x<0 ){
                    xDest=(int) (-xDelta + x);
                }
                if(sprite.getY()-y>0 ){
                    yDest=(int) (-yDelta + x);
                }else if(sprite.getY()-y<0 ){
                    yDest=(int) (yDelta + x);
                }
            }while(xDest<0 || xDest>=goldMap.WIDTH || yDest<0 || yDest>=goldMap.WIDTH);
        }while(map.blocked((int)xDest, (int)yDest));

        newPath(xDest, yDest);
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
        // cycle through the tiles in the map drawing the appropriate
        // image  units where appropriate
        for (int x=0;x<map.getWidthInTiles();x++) {
            for (int y=0;y<map.getHeightInTiles();y++) {
                if (path != null) {
                    //if (path.contains(x, y)) {
                    //    g.setColor(Color.blue);
                    //    g.fillRect((x*Map.TILE_SIZE)+4, (y*Map.TILE_SIZE)+4,7,7);
                    //}
                }
            }
        }

        // work out the screen position of the entity based on the
        // x/y position and the size that tiles are being rendered at. So
        // if we'e'd render on screen
        // at 15,15.
        int xp = (int) (Map.TILE_SIZE * x );
        int yp = (int) (Map.TILE_SIZE * y );
        //g.rotate(ang, xp+, yp);
        if( imageNumber==0){g.drawImage(frames[0], (int) (xp), (int) (yp), null);  }
        else if( imageNumber==1){g.drawImage(frames[1], (int) (xp), (int) (yp), null); }
        //g.rotate(-ang, xp, yp);
    }
    
   public void bang(Graphics2D g){
       if(bloodSplatterCtr>0 && bloodSplatterCtr<100){
           int xp = (int) (Map.TILE_SIZE * x );
           int yp = (int) (Map.TILE_SIZE * y );
           System.out.println("security guardBLOOD!!!!!");
            g.drawImage(blood, (int) (xp - 8), (int) (yp - 8 ), null);
            bloodSplatterCtr++;
       }else {
           bloodSplatterCtr=0;
       }
   }

   public void checkCoor(){
       if((int)sprite.getX()==(int)x && (int)sprite.getY()==(int)y){
            game.xCaught = (int) sprite.getX();
            game.yCaught = (int) sprite.getY();
            if(!kill)
                if(sprite.canFight){
                    //kill guard
                    timer.stop();
                    kill=true;
                    sprite.score=sprite.score+20;
                    bloodSplatterCtr =1;
                }else{
                    //deduct life
                    sprite.life--;
                    if(sprite.life==-1){
                        game.gameRunning = false;
                    }
                    game.deductLife=true;
                    sprite.score=sprite.score-20;
                    sprite.bloodSplatter = true;

                }
        }
   }


}