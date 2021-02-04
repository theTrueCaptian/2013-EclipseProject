package spaceshooters;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import it.randomtower.engine.World;

public class GameWorld extends World {
	
	private Player player;
	private Monster monster; 
	//keeps track of time
	long last = System.nanoTime();
	
	public GameWorld(int id, GameContainer container) throws SlickException {
		super(id, container);
		// TODO Auto-generated constructor stub
		
		player = new Player(container.getWidth()/ 2, container.getHeight() / 2, getWidth(), getHeight());
		add(player);
		
		
		
		/*monster = new Monster(300,0);
		add(monster);
		Monster monster2 = new Monster(200, 0);
		Monster monster3 = new Monster(100, 0);
		Monster monster4 = new Monster(400,0);
		Monster monster5 = new Monster(500, 0);
		Monster monster6 = new Monster(0, 0);
		add(monster2);
		add(monster3);
		add(monster4);
		add(monster5);
		add(monster6);
		*/
	}
	private int getRandomXCoordinate(){
		Random rand = new Random();
		return rand.nextInt(width-50);
	}
	
	@Override
	public void init(GameContainer containter, StateBasedGame game) throws SlickException{
		super.init(containter, game);
		for(int i=0; i<6; i++){
			int x = getRandomXCoordinate();
			System.out.println(i+" "+x);
			Monster monster = new Monster(x,0);
			add(monster);
			//try { Thread.sleep(getRandomXCoordinate()); } catch (Exception e) {};
		}
		
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		super.update(container, game, delta);
		long now = System.nanoTime();
		long dif = (now-last)/1000000;
		if(dif>getRandomXCoordinate()*5000){
			int x = getRandomXCoordinate();
			System.out.println(" "+x);
			Monster monster = new Monster(x,0);
			add(monster);
		}
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		super.render(container, game, g);
	}

}
