package world;

import graphics.Assets;

import java.awt.image.BufferedImage;

public enum Tile {
	BOUNDS(Assets.black),
	GRASS(Assets.grass),
	TREE(Assets.tree), 
	UNKNOWN(Assets.black), 
	STAIRS_UP(Assets.stairsUp),
	STAIRS_DOWN(Assets.stairsDown);
	;
	
	BufferedImage sprite;
	
	/**
	 * Constructor
	 * @param sprite from the Assets class
	 */
	Tile(BufferedImage sprite){
		this.sprite = sprite;
	}
	
	public boolean isGround() {
		return this != TREE && this != BOUNDS;
	}
	
	public BufferedImage getSprite(){
		return sprite;
	}

	public boolean isGroundNotStairs() {
		return this == GRASS && this != STAIRS_UP && this != STAIRS_DOWN;
	}
}
