package template;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * The map holds the data about game area. In this case its responsible
 * for both rendering the map and check collision against the grid cells
 * within.
 *
 * Our map is a simple WIDTHxHEIGHT grid containing value 0 to indicate
 * a clear cell and 1 to incidate a wall.
 *
 * @author Kevin Glass
 */
public class Map {
	/** The value indicating a clear cell */
	private static final int CLEAR = 0;
	/** The value indicating a blocked cell */
	private static final int BLOCKED = 1;

	/** The width in grid cells of our map */
	/*private static final int WIDTH = 15;
	/** The height in grid cells of our map *
	private static final int HEIGHT = 15;

	/** The rendered size of the tile (in pixels) *
	public static final int TILE_SIZE = 20;

	/** The actual data for our map *
	private int[][] data = new int[WIDTH][HEIGHT];

	/**
	 * Create a new map with some default contents
	 *
	public Map() {
		// create some default map data - it would be way
		// cooler to load this from a file and maybe provide
		// a map editor of some sort, but since we're just doing
		// a simple tutorial here we'll manually fill the data
		// with a simple little map
		for (int y=0;y<HEIGHT;y++) {
			data[0][y] = BLOCKED;
			data[2][y] = BLOCKED;
			data[7][y] = BLOCKED;
			data[11][y] = BLOCKED;
			data[WIDTH-1][y] = BLOCKED;
		}
		for (int x=0;x<WIDTH;x++) {
			if ((x > 0) && (x < WIDTH-1)) {
				data[x][10] = CLEAR;
			}

			if (x > 2) {
				data[x][9] = BLOCKED;
			}
			data[x][0] = BLOCKED;
			data[x][HEIGHT-1] = BLOCKED;
		}

		data[4][9] = CLEAR;
		data[7][5] = CLEAR;
		data[7][4] = CLEAR;
		data[11][7] = CLEAR;
	}*/
        private static final int WIDTH = 30;
	/** The height in grid cells of our map */
	private static final int HEIGHT = 30;

	/** The rendered size of the tile (in pixels) */
	public static final int TILE_SIZE = 20;

	/** The actual data for our map */
	private int[][] wallMap = new int[WIDTH][HEIGHT];

	/**
	 * Create a new map with some default contents
	 */
	public Map(){
		for (int j=0;j<HEIGHT;j++){
                    wallMap[0][j] = BLOCKED;
                    wallMap[29][j] = BLOCKED;
                }
                for (int i=0;i<HEIGHT;i++){
                    wallMap[i][0] = BLOCKED;
                    wallMap[i][29] = BLOCKED;
                }
                wallMap[2][1] = BLOCKED;
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
                wallMap[19][19] = CLEAR;
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
	public boolean blocked(float x, float y) {
		// look up the right cell (based on simply rounding the floating
		// values) and check the value
		return wallMap[(int) x][(int) y] == BLOCKED;
	}
}
