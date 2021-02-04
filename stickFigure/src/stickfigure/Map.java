package stickfigure;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author maeda
 */
public class Map implements TileBasedMap {
    /** The value indicating a clear cell */
    private static final int CLEAR = 0;
    /** The value indicating a blocked cell */
    private static final int BLOCKED = 1;

    /** The width in grid cells of our map */
     static final int WIDTH = 30;
    /** The height in grid cells of our map */
     static final int HEIGHT = 30;

    /** The rendered size of the tile (in pixels) */
    public static final int TILE_SIZE = 20;

    /** The actual data for our map */
    private int[][] wallMap = new int[WIDTH][HEIGHT];
    /** Indicator if a given tile has been visited during the search */
    private boolean[][] visited = new boolean[WIDTH][HEIGHT];


    /**
     * Create a new map with some default contents
     */
    public Map(){
        for(int i=0; i<WIDTH; i++){
            for(int j=0; j<WIDTH; j++){
                wallMap[i][j] = CLEAR;
            }
        }
        for (int j=0;j<HEIGHT;j++){
            wallMap[0][j] = BLOCKED;
            wallMap[29][j] = BLOCKED;
        }
        for (int i=0;i<HEIGHT;i++){
            wallMap[i][0] = BLOCKED;
            wallMap[i][29] = BLOCKED;
        }
        for(int i=2; i<=5; i++)
            wallMap[i][2] = BLOCKED;
        for(int i=9; i<=12; i++)
            wallMap[i][2] = BLOCKED;
        for(int i=16; i<=19; i++)
            wallMap[i][2] = BLOCKED;
        for(int i=23; i<=27; i++)
            wallMap[i][2] = BLOCKED;
        for(int j=1; j<=3; j++)
            wallMap[7][j] = BLOCKED;
        for(int j=1; j<=2; j++)
            wallMap[14][j] = BLOCKED;
        for(int j=1; j<=3; j++)
            wallMap[21][j] = BLOCKED;
        for(int i=2; i<=13; i++)
            wallMap[i][4] = BLOCKED;
        for(int i=15; i<=27; i++)
            wallMap[i][4] = BLOCKED;
        for(int j=5; j<=9; j++)
            wallMap[2][j] = BLOCKED;
        for(int j=5; j<=9; j++)
            wallMap[27][j] = BLOCKED;
        for(int j=5; j<=7; j++)
            wallMap[18][j] = BLOCKED;
        for(int j=5; j<=7; j++)
            wallMap[20][j] = BLOCKED;
        for(int j=6; j<=7; j++)
            wallMap[10][j] = BLOCKED;
         for(int j=6; j<=7; j++)
            wallMap[20][j] = BLOCKED;
        for(int i=11; i<=16; i++)
            wallMap[i][6] = BLOCKED;
        for(int j=7; j<=8; j++)
            wallMap[14][j] = BLOCKED;
        for(int i=4; i<=25; i++)
            wallMap[i][9] = BLOCKED;
        //M
        for(int j=11; j<=17; j++)
            wallMap[2][j] = BLOCKED;
        for(int j=11; j<=14; j++)
            wallMap[4][j] = BLOCKED;
        for(int j=11; j<=17; j++)
            wallMap[6][j] = BLOCKED;
        for(int i=2; i<=6; i++)
            wallMap[i][11] = BLOCKED;

        //a
        for(int j=11; j<=17; j++)
            wallMap[8][j] = BLOCKED;
        for(int j=11; j<=17; j++)
            wallMap[11][j] = BLOCKED;
        for(int i=8; i<=11; i++)
            wallMap[i][11] = BLOCKED;
        wallMap[9][14] = BLOCKED;
        
        //E
        for(int j=11; j<=17; j++)
            wallMap[13][j] = BLOCKED;
        for(int i=13; i<=16; i++)
            wallMap[i][11] = BLOCKED;
        for(int i=13; i<=16; i++)
            wallMap[i][17] = BLOCKED;
        wallMap[14][14] = BLOCKED;

        //d
        for(int j=11; j<=17; j++)
            wallMap[18][j] = BLOCKED;
        for(int i=18; i<=21; i++)
            wallMap[i][11] = BLOCKED;
        for(int i=18; i<=21; i++)
            wallMap[i][17] = BLOCKED;
        wallMap[21][11] = BLOCKED;
        wallMap[22][11] = BLOCKED;
        wallMap[22][14] = BLOCKED;
        wallMap[22][15] = BLOCKED;
        wallMap[21][16] = BLOCKED;

        //a
        for(int j=11; j<=17; j++)
            wallMap[8+16][j] = BLOCKED;
        for(int j=11; j<=17; j++)
            wallMap[11+16][j] = BLOCKED;
        for(int i=8+16; i<=11+16; i++)
            wallMap[i][11] = BLOCKED;
        wallMap[9+16][14] = BLOCKED;

        for(int i=4; i<=25; i++)
            wallMap[i][19] = BLOCKED;
        for(int j=19; j<=24; j++)
            wallMap[2][j] = BLOCKED;
        for(int j=19; j<=24; j++)
            wallMap[27][j] = BLOCKED;
        for(int j=19; j<=22; j++)
            wallMap[8][j] = BLOCKED;
        for(int j=19; j<=22; j++)
            wallMap[21][j] = BLOCKED;
        for(int j=21; j<=23; j++)
            wallMap[10][j] = BLOCKED;
        for(int j=21; j<=23; j++)
            wallMap[19][j] = BLOCKED;
         for(int i=3; i<=6; i++)
            wallMap[i][21] = BLOCKED;
        for(int i=12; i<=17; i++)
            wallMap[i][21] = BLOCKED;
        for(int i=23; i<=27; i++)
            wallMap[i][21] = BLOCKED;
        for(int i=4; i<=8; i++)
            wallMap[i][23] = BLOCKED;
        for(int i=10; i<=19; i++)
            wallMap[i][23] = BLOCKED;
        for(int i=21; i<=25; i++)
            wallMap[i][23] = BLOCKED;
        for(int i=2; i<=14; i++)
            wallMap[i][25] = BLOCKED;
        for(int i=15; i<=27; i++)
            wallMap[i][25] = BLOCKED;
        for(int i=2; i<=5; i++)
            wallMap[i][27] = BLOCKED;
        for(int i=9; i<=12; i++)
            wallMap[i][27] = BLOCKED;
        for(int i=16; i<=19; i++)
            wallMap[i][27] = BLOCKED;
        for(int i=23; i<=27; i++)
            wallMap[i][27] = BLOCKED;
        for(int j=26; j<=28; j++)
            wallMap[7][j] = BLOCKED;
        for(int j=27; j<=28; j++)
            wallMap[14][j] = BLOCKED;
        for(int j=26; j<=28; j++)
            wallMap[21][j] = BLOCKED;
        wallMap[14][25]=CLEAR;
        for(int i=1; i<=28; i++)
            wallMap[i][1]=CLEAR;
        for(int i=1; i<=28; i++)
            wallMap[i][28]=CLEAR;
        /*wallMap[2][1] = BLOCKED;
        wallMap[6][1] = BLOCKED;
        for(int i=14; i<=20; i++){
            wallMap[i][1] = BLOCKED;
            wallMap[i][3] = BLOCKED;
        }
        wallMap[4][2] = BLOCKED;
        wallMap[8][2] = BLOCKED;
        for(int i=11; i<=12; i++)
            wallMap[i][2] = BLOCKED;
        for(int i=22; i<=26; i++)
            wallMap[i][2] = BLOCKED;
        wallMap[28][2] = BLOCKED;
        for(int i=1; i<=11; i++)
            wallMap[i][3] = BLOCKED;
        wallMap[23][3] = BLOCKED;
        wallMap[28][3] = BLOCKED;
        wallMap[2][4] = BLOCKED;
        wallMap[23][4] = BLOCKED;
        for(int i=2; i<=28; i++)
            wallMap[i][5] = BLOCKED;
        wallMap[8][5] = CLEAR;
        wallMap[14][5] = CLEAR;
        wallMap[27][5] = CLEAR;
        wallMap[13][6] = BLOCKED;
        for(int i=2; i<=28; i++)
            wallMap[i][7] = BLOCKED;
        for(int j=8; j<=25; j++)
            wallMap[2][j] = BLOCKED;
        for(int i=3; i<=12; i++)
            wallMap[i][22] = BLOCKED;
        for(int i=15; i<=28; i++)
            wallMap[i][22] = BLOCKED;
        wallMap[10][23] = BLOCKED;
        wallMap[3][24] = BLOCKED;
        for(int i=5; i<=27; i++)
            wallMap[i][24] = BLOCKED;
        wallMap[7][25] = BLOCKED;
        wallMap[11][25] = BLOCKED;
        wallMap[25][25] = BLOCKED;
        for(int i=13; i<=25; i++)
            wallMap[i][26] = BLOCKED;
        for(int i=2; i<=13; i++)
            wallMap[i][27] = BLOCKED;
        for(int j=26; j<=28; j++)
            wallMap[27][j] = BLOCKED;
        wallMap[16][27] = BLOCKED;
        wallMap[20][27] = BLOCKED;
        wallMap[22][27] = BLOCKED;
        wallMap[18][28] = BLOCKED;
        wallMap[25][28] = BLOCKED;
        for(int i=4; i<=8; i++)
            wallMap[i][10] = BLOCKED;
        for(int i=10; i<=13; i++)
            wallMap[i][10] = BLOCKED;
        for(int i=15; i<=18; i++)
            wallMap[i][10] = BLOCKED;
        for(int i=20; i<=22; i++)
            wallMap[i][10] = BLOCKED;
        for(int i=25; i<=28; i++)
            wallMap[i][10] = BLOCKED;
        for(int j=10; j<=19; j++){
            wallMap[4][j] = BLOCKED;
            wallMap[8][j] = BLOCKED;
            wallMap[10][j] = BLOCKED;
            wallMap[13][j] = BLOCKED;
            wallMap[15][j] = BLOCKED;
            wallMap[20][j] = BLOCKED;
            wallMap[23][j] = BLOCKED;
            wallMap[25][j] = BLOCKED;
            wallMap[28][j] = BLOCKED;
        }
        for(int j=10; j<=18; j++)
            wallMap[6][j] = BLOCKED;
        wallMap[11][14] = BLOCKED;
        wallMap[16][14] = BLOCKED;
        wallMap[26][14] = BLOCKED;
        for(int i=16; i<=22; i++)
            wallMap[i][19] = BLOCKED;
        wallMap[23][19] = CLEAR;
        wallMap[23][14] = CLEAR;
        wallMap[23][10] = CLEAR;
        wallMap[23][15] = CLEAR;
        wallMap[19][19] = CLEAR;*/
    }

    /**
     * Render the map to the graphics context provided. The rendering
     * is just simple fill rectangles
     *
     * @param g The graphics context on which to draw the map
     */
    public void paint(Graphics2D g) {
        // loop through all the tiles in the map rendering them
        // based on whether they block or not
        for (int x=0;x<WIDTH;x++) {
            for (int y=0;y<HEIGHT;y++) {

                // so if the cell is blocks, draw a light grey block
                // otherwise use a dark gray
                g.setColor(Color.darkGray);
                if (wallMap[x][y] == BLOCKED) {
                    g.setColor(Color.gray);
                }

                // draw the rectangle with a dark outline
                g.fillRect(x*TILE_SIZE,y*TILE_SIZE,TILE_SIZE,TILE_SIZE);
                g.setColor(g.getColor().darker());
                g.drawRect(x*TILE_SIZE,y*TILE_SIZE,TILE_SIZE,TILE_SIZE);
            }
        }
    }

    /**
     * Check if a particular location on the map is blocked. Note
     * that the x and y parameters are floating point numbers meaning
     * that we can be checking partially across a grid cell.
     *
     * @param x The x position to check for blocking
     * @param y The y position to check for blocking
     * @return True if the location is blocked
     */
    public boolean blocked(int x, int y) {
        // look up the right cell (based on simply rounding the floating
        // values) and check the value
        return wallMap[ x][ y] == BLOCKED;
    }
    /**
     * Get the width of the tile map. The slightly odd name is used
     * to distiguish this method from commonly used names in game maps.
     *
     * @return The number of tiles across the map
     */
    public int getWidthInTiles(){
        return WIDTH;
    }

    /**
     * Get the height of the tile map. The slightly odd name is used
     * to distiguish this method from commonly used names in game maps.
     *
     * @return The number of tiles down the map
     */
    public int getHeightInTiles(){
        return HEIGHT;
    }
    /**
     * @see TileBasedMap#getCost(Mover, int, int, int, int)
     */
    public float getCost(int sx, int sy, int tx, int ty) {
            return 1;
    }

    /**
     * Clear the array marking which tiles have been visted by the path
     * finder.
     */
    public void clearVisited() {
            for (int x=0;x<getWidthInTiles();x++) {
                    for (int y=0;y<getHeightInTiles();y++) {
                            visited[x][y] = false;
                    }
            }
    }

    /**
     * @see TileBasedMap#visited(int, int)
     */
    public boolean visited(int x, int y) {
            return visited[x][y];
    }

    /**
     * @see TileBasedMap#pathFinderVisited(int, int)
     */
    public void pathFinderVisited(int x, int y) {
            visited[x][y] = true;
    }


}
