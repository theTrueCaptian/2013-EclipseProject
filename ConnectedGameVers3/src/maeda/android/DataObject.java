package maeda.android;

public class DataObject {
	private int indicator;
	private int[] index;
	private int score;
	private int chance;
	
	public DataObject(int indicator, int[] index, int score, int chance){
		this.indicator = indicator;
		this.score = score;
		this.chance = chance;
		this.index = index.clone();
	}
	
	public int getIndicator(){
		return indicator;
	}
	
	public int[] getIndex(){
		return index.clone();
	}
	
	public int getScore(){
		return score;
	}
	
	public int getChance(){
		return chance;
	}
}
