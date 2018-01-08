package world;

public class Forest {

	private int height;
	private int width;
	Tile[][] forest;
	
	public Forest(int width, int height) {
		this.width = width;
		this.height = height;
		createForest();
	}
	
	public void createForest() {
		forest = new Tile[width][height];
			
		for (int h = 0; h <= height; h++) {
			for(int w = 0; w <= width; w++) {
				if(Math.random() >= .9) {
					forest[w][0] = Tile.TREE;
				} else {
					forest[w][0] = Tile.GRASS;
				}
			}
		}
	}
}
