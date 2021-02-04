package stickfigure;

/*
 * MyFrame.java
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import javax.swing.*;
import java.applet.*;
import java.io.*;
import sun.audio.*;
/**
 * meada hanafi
 */
public class MyFrame extends JFrame implements ActionListener{
    //private Canvas C;
    final int MAPSIZEX = 800;
    final int MAPSIZEY = 800;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    boolean move = false;
    Map map;
    Sprite player;
    GoldMap itemMap;
    Graphics2D g ;
    /** The buffered strategy used for accelerated rendering */
    private BufferStrategy strategy;
    int delay = 1000;
    Timer timer = new Timer(delay, new TimerListener());
    Timer spriteKillTimer = new Timer(delay, new TimerListener());
    int fightCounter = 0;
    SecurityGuard guard1, guard2, guard3, guard4, guard5;
    Image buffer;
    RandomNumberGenerator m = new RandomNumberGenerator(10, 10, 30);
    private Image lifeImage = Toolkit.getDefaultToolkit().getImage("F:\\stickFigure\\life.gif");
    boolean gameRunning;
    boolean deductLife = false;
    int xCaught, yCaught;
    //AudioStream ac;
    //ContinuousAudioDataStream cas;


    public MyFrame( String title ) throws IOException {
        super(title);
        initComponents();
        setSize(MAPSIZEX,MAPSIZEY);
        setResizable(false);
        
        setVisible( true );
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        map = new Map();
        
        itemMap = new GoldMap(map);
        player = new Sprite( map, itemMap);
        releaseGuards();
        //playMusic();
        xCaught = (int) player.getX();
        yCaught = (int) player.getY();
        this.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e) {
		// check the keyboard and record which keys are pressed
		if (e.getKeyCode() == KeyEvent.VK_LEFT) 
			left = true;
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			up = true;
		}
                move = true;
            }
            public void keyReleased(KeyEvent e) {
		// check the keyboard and record which keys are released
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			up = false;
		}
                move = false;
	}
        });
        timer.start();
        gameStart();
    }

    public void releaseGuards(){
        guard1 = new SecurityGuard( map, itemMap, player, this, m.getRandomNumber());
        guard2 = new SecurityGuard( map, itemMap, player, this, 2, 28);
        guard3 = new SecurityGuard( map, itemMap, player, this,28, 2);
        guard4 = new SecurityGuard( map, itemMap, player, this,28, 20);
        guard5 = new SecurityGuard( map, itemMap, player, this,15, 15);
        guard1.start();
        guard2.start();
        guard3.start();
        guard4.start();
        guard5.start();
    }

    public void playMusic() throws IOException{
        /*InputStream in = new FileInputStrem("F:\\stickFigure\\ringin.wav");
        ac = new AudioStream(in);
        cas = new ContinuousAudioDataStream(ac.getData());
        AudioPlayer.player.start(cas);*/
    }

    class TimerListener implements ActionListener {
       // Handle ActionEvent 
        public void actionPerformed(ActionEvent e) {
            if(gameRunning){
                if(guard1.imageNumber==0){
                    guard1.imageNumber = 1;
                    guard2.imageNumber = 1;
                    guard3.imageNumber = 1;
                    guard4.imageNumber = 1;
                    guard5.imageNumber = 1;
                }else if(guard1.imageNumber==1){
                    guard1.imageNumber = 0;
                    guard2.imageNumber = 0;
                    guard3.imageNumber = 0;
                    guard4.imageNumber = 0;
                    guard5.imageNumber = 0;
                }
                if(!player.canFight ){
                    fightCounter = 0;
                    if(player.imageNumber==0)
                        player.imageNumber = 1;
                    else if(player.imageNumber==1)
                        player.imageNumber = 0;
                    player.paint(g);
                }else if(player.canFight ){
                    fightCounter ++;
                    if(fightCounter==20){
                        player.canFight = false;
                        fightCounter = 0;
                    }
                    player.paint(g);
                }

            }
        }
    }

    public void gameStart(){
         gameRunning = true;
        long last = System.nanoTime();

        // keep looking while the game is running
        while (gameRunning) {
            g = (Graphics2D) strategy.getDrawGraphics();

            // clear the screen
            g.setColor(Color.GREEN);
            g.setColor(g.getColor().darker());
            g.fillRect(0,0,MAPSIZEX,MAPSIZEY);
            g.setColor(Color.black);
            g.setFont( new Font( "Serif", Font.BOLD, 20 ) );
            g.drawString("SCORE: "+player.score, 350, 50);
            g.setFont( new Font( "Serif", Font.BOLD, 15 ) );
            g.drawString("Life:", 20, 45);
            for(int i=1; i<=player.life; i++){
                g.drawImage(lifeImage, 20*i, 50, null);
            }
            // render our game objects
            g.translate(100,100);
            map.paint(g);
            itemMap.paint(g);
            
            
            if(!guard1.kill)
                guard1.paint(g);
            else if(guard1.kill){
                guard1.bang(g);
            }
            if(!guard2.kill)
                guard2.paint(g);
            else if(guard2.kill){
                guard2.bang(g);
            }
            if(!guard3.kill)
                guard3.paint(g);
            else if(guard3.kill){
                guard3.bang(g);
            }
            if(!guard4.kill)
                guard4.paint(g);
            else if(guard4.kill){
                guard4.bang(g);
            }
            if(!guard5.kill)
                guard5.paint(g);
            else if(guard5.kill){
                guard5.bang(g);
            }
            player.paint(g);
            // flip the buffer so we can see the rendering
            g.dispose();
            strategy.show();

            if(guard1.kill && guard2.kill && guard3.kill && guard4.kill && guard5.kill){
                releaseGuards();
                itemMap.generateWeapons();
            }
            // pause a bit so that we don't choke the system
            try { Thread.sleep(4); } catch (Exception e) {};
            long delta = (System.nanoTime() - last) / 1000000;
            last = System.nanoTime();
            // now this needs a bit of explaining. The amount of time
            // passed between rendering can vary quite alot. If we were
            // to move our player based on the normal delta it would
            // at times jump a long distance (if the delta value got really
            // high). So we divide the amount of time passed into segments
            // of 5 milliseconds and update based on that
            for (int i=0; i<delta/5; i++) {
                logic(5);
            }
            // after we've run through the segments if there is anything
            // left over we update for that
            if ((delta % 5) != 0) {
                logic(delta % 5);
            }
            if(itemMap.isGold(player.getX(), player.getY())){
                player.eatGold();
            }else if(itemMap.isWeapon(player.getX(), player.getY())){
                fightCounter = 0;
                player.useWeapon();
                
            }
            
            if((xCaught!=(int)player.getX() || yCaught !=(int)player.getY()) && deductLife==true){
                deductLife=false;
            }
            if(!deductLife){
                guard1.checkCoor();
                guard2.checkCoor();
                guard3.checkCoor();
                guard4.checkCoor();
                guard5.checkCoor();
            }
            if(itemMap.amountOfGold<=0){
                
                player.win=true;
                gameRunning = false;
            }
        }

        g = (Graphics2D) strategy.getDrawGraphics();

       // clear the screen
        g.setColor(Color.GREEN);
        g.setColor(g.getColor().darker());
        g.fillRect(0,0,MAPSIZEX,MAPSIZEY);
        g.setColor(Color.black);
        g.setFont( new Font( "Serif", Font.BOLD, 20 ) );
        g.drawString("SCORE: "+player.score, 350, 50);
        g.setFont( new Font( "Serif", Font.BOLD, 15 ) );
        g.drawString("Life:", 20, 45);
        for(int i=1; i<=player.life; i++){
            g.drawImage(lifeImage, 20*i, 50, null);
        }
        if(player.life<=-1){
            //display lose message
            g.setColor(Color.black);
            g.setFont( new Font( "SansSerif", Font.BOLD, 70 ) );
            g.drawString( "Game Over" ,250, 90 );
            player.bang(g);

        }else if(player.win){
            g.setColor(Color.black);
            g.setFont( new Font( "SansSerif", Font.BOLD, 70 ) );
            g.drawString( "Success!" ,250, 90 );
        }
        // render our game objects
        g.translate(100,100);
        map.paint(g);
        itemMap.paint(g);

        player.paint(g);
        if(!guard1.kill)
            guard1.paint(g);
        else if(guard1.kill){
            guard1.bang(g);
        }
        if(!guard2.kill)
            guard2.paint(g);
        else if(guard2.kill){
            guard2.bang(g);
        }
        if(!guard3.kill)
            guard3.paint(g);
        else if(guard3.kill){
            guard3.bang(g);
        }
        if(!guard4.kill)
            guard4.paint(g);
        else if(guard4.kill){
            guard4.bang(g);
        }
        if(!guard5.kill)
            guard5.paint(g);
        else if(guard5.kill){
            guard5.bang(g);
        }

         // flip the buffer so we can see the rendering
        g.dispose();
        strategy.show();
        //AudioPlayer.player.start(cas);
    }

    /**
     * Our game logic method - for this example purpose this is very
     * simple. Check the keyboard, and attempt to move the player
     *
     * @param delta The amount of time to update for (in milliseconds)
     */
    public void logic(long delta) {
        // check the keyboard and record which way the player
        // is trying to move this loop
        float dx = 0;
        float dy = 0;
        if (left) {
            dx -= 1;
        }
        if (right) {
            dx += 1;
        }
        if (up) {
            dy -= 1;
        }
        if (down) {
            dy += 1;
        }

        // if the player needs to move attempt to move the entity
        // based on the keys multiplied by the amount of time thats
        // passed
        if ((dx != 0) || (dy != 0)) {
            player.move(dx  *delta * 0.003f,
                        dy * delta * 0.003f);
        }
    }


  


    public void actionPerformed( ActionEvent e) {
        // Your code goes here
        repaint();
    }

    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

 }
