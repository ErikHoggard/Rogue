package world;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import entities.Creature;
import entities.Item;

public class World {
	
	private List<Region> regions;
	
	private Tile[][][] tiles;
	
	private int width;
	private int height;
	private int depth;
	private int left;
	private int top;
	
	private Double clock;
	
	private int[][][] cursorPosition;

	private List<Creature> creatures;

	private Item[][][] items;
	
	public World(Tile[][][] tiles){
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.depth = tiles[0][0].length;
		System.out.println(depth);
		this.creatures = new ArrayList<Creature>();
		this.items = new Item[width][height][depth];
	}
	
	public Item item(int x, int y, int z) {
		return items[x][y][z];
	}
	
	//returns a creature at a given coordinate if there is one 
	public Creature creature(int x, int y, int z){
		for (Creature c : creatures) {
			if (c.x == x && c.y == y && c.z == z)
				return c;
		}
		return null;
	}
	
	//returns the tile at a specific coordinate
	public Tile tile(int x, int y, int z){
		if ( x < 0 || x >= width || y < 0 || y >= height || z < 0 || z >= depth) 
			return Tile.BOUNDS;
		else
			return tiles[x][y][z];
	}
	
	//return the sprite of a creature or item at a coordinate.
	//If there is nothing there, the terrain sprite is returned
	public BufferedImage sprite(int x, int y, int z) {
		Creature creature = creature(x, y, z);
		if (creature != null)
			return creature.getSprite();
		if (item(x,y,z) != null)
			return item(x,y,z).getSprite();
		return tile(x,y,z).getSprite();
	}
	
	
	//spawns a creature at a random empty location
	public void addAtEmptyLocation(Creature creature, int z) {
		int x, y;
		
		do {
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
		}
		while (!tile(x,y,z).isGround() || creature(x,y,z) != null);
		
		creature.x = x;
		creature.y = y;
		creature.z = z;
		creatures.add(creature);
	}
	
	//spawns an item at an empty location
	public void addAtEmptyLocation(Item item, int depth){
		int x, y;
		
		do {
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
		}
		while (!tile(x,y,depth).isGroundNotStairs() || item(x,y,depth) != null);
		
		items[x][y][depth] = item;
	}
	
	public boolean addAtEmptySpace(Item item, int x, int y, int z) {
		//Add code for dropping items here
		return false;
	}
	
	
	//check PlayerAI update method for more info
	public void update() {
		List<Creature> toUpdate = new ArrayList<Creature>(creatures);
		for (Creature c : toUpdate) {
				c.update();
		}
	}
	
	public void remove(Creature other) {
		creatures.remove(other);
	}
	
	public void remove(int x, int y, int z) {
		items[x][y][z] = null;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public Tile[][][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][][] tiles) {
		this.tiles = tiles;
	}
	

	public int[][][] getCursorPosition() {
		return cursorPosition;
	}

	public void setCursorPosition(int[][][] cursorPosition) {
		this.cursorPosition = cursorPosition;
	}

	
	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}
	
	public double getClock() {
		return clock;
	}

	public void setClock(double clock) {
		this.clock = clock;
	}

	public List<Creature> getCreatures() {
		return creatures;
	}

	public void setCreatures(List<Creature> creatures) {
		this.creatures = creatures;
	}

	public void render(Graphics g) {
		
	}

	public BufferedImage getBackgroundTile() {
		return Tile.GRASS.getSprite();
	}
	
	
}



























