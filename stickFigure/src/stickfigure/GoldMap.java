/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stickfigure;
import java.awt.*;

/**
 *
 * @author maeda
 */
public class GoldMap {
    /** The value indicating a clear cell */
     static final int CLEAR = 0;
    /** The value indicating a blocked cell */
    private static final int GOLD = 1;
    private static final int WEAPON = 2;

    /** The width in grid cells of our map */
     static final int WIDTH = 30;
    /** The height in grid cells of our map */
    static final int HEIGHT = 30;

    /** The rendered size of the tile (in pixels) */
    public static final int TILE_SIZE = 20;

    /** The actual data for our map */
    private int[][] goldMap = new int[WIDTH][HEIGHT];
    Map map;
    int amountOfGold;
    RandomNumberGenerator i = new RandomNumberGenerator(10, 0, WIDTH-1);
    RandomNumberGenerator j = new RandomNumberGenerator(10, 0, HEIGHT-1);
    Image weapon = Toolkit.getDefaultToolkit().getImage("F:\\stickFigure\\weapon.gif");
    Image gold = Toolkit.getDefaultToolkit().getImage("F:\\stickFigure\\gold.gif");

    public GoldMap(Map inMap){
        map = inMap;
        amountOfGold = 0;
        for(int i=0; i<WIDTH; i++)
            for(int j=0; j<HEIGHT; j++)
                goldMap[i][j] = CLEAR;
        //RandomNumberGenerator i = new RandomNumberGenerator(10, 0, WIDTH-1);
        //RandomNumberGenerator j = new RandomNumberGenerator(10, 0, HEIGHT-1);
        for(int ctr=0; ctr<WIDTH; ctr++){
            int x = i.getRandomNumber();
            for(int ctr1=0; ctr1<HEIGHT; ctr1++){
                int y=j.getRandomNumber();
                if(!map.blocked(x, y)){
                    goldMap[x][y] = GOLD;
                    //amountOfGold++;
                }
            }
        }
        
        generateWeapons();
        for(int x=0; x<WIDTH; x++){
            for(int y=0; y<HEIGHT; y++){
                if(goldMap[x][y] == GOLD){
                    amountOfGold++;
                }
            }
        }
        /*goldMap[9][2] = GOLD;
        goldMap[10][2] = GOLD;
        for(int i=13; i<=21; i++)
            goldMap[i][2] = GOLD;
        for(int i=24; i<=27; i++)
            goldMap[i][3] = GOLD;
        for(int i=2; i<=11; i++)
            goldMap[i][4] = GOLD;
        for(int i=15; i<=21; i++)
            goldMap[i][4] = GOLD;
        for(int i=24; i<=28; i++)
            goldMap[i][4] = GOLD;
        goldMap[27][5] = WEAPON;
        for(int i=9; i<=12; i++)
            goldMap[i][6] = GOLD;
        for(int i=14; i<=28; i++)
            goldMap[i][6] = GOLD;
        for(int j=7; j<=29; j++)
            goldMap[1][j] = GOLD;*/

    }

    public void generateWeapons(){
        //RandomNumberGenerator i = new RandomNumberGenerator(10, 0, WIDTH-1);
        //RandomNumberGenerator j = new RandomNumberGenerator(10, 0, HEIGHT-1);
        for(int ctr=0; ctr<WIDTH; ctr++){
            int x = i.getRandomNumber();
            int y=j.getRandomNumber();
            if(!map.blocked(x, y)&&goldMap[x][y]!=GOLD){
                goldMap[x][y] = WEAPON;
            }
        }
    }

    public void paint(Graphics2D g) {
        for (int x=0;x<WIDTH;x++) {
            for (int y=0;y<HEIGHT;y++) {
                if (goldMap[x][y] == WEAPON) {
                    g.drawImage(weapon,x*TILE_SIZE,y*TILE_SIZE, null);
                }else if(goldMap[x][y] == GOLD){
                    g.drawImage(gold,x*TILE_SIZE,y*TILE_SIZE, null);
                }
            }
        }
    }

    public boolean isGold(float x, float y) {
        // look up the right cell (based on simply rounding the floating
        // values) and check the value
        return goldMap[(int) x][(int) y] == GOLD;
    }

    public boolean isWeapon(float x, float y) {
        // look up the right cell (based on simply rounding the floating
        // values) and check the value
        return goldMap[(int) x][(int) y] == WEAPON;
    }
    public void setToClear(float x, float y){
        goldMap[(int)x][(int)y] = CLEAR;
    }
    
}