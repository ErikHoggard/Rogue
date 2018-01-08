package graphics;

import java.awt.image.BufferedImage;

public class Assets {

	private static final int height = 45, width = 45;
	
	public static BufferedImage player;
	public static BufferedImage fungus, bat, gob;
	public static BufferedImage black, grass, tree, fow;
	public static BufferedImage stairsUp, stairsDown;
	public static BufferedImage rock;
	
	public static BufferedImage textBox;
	
	public static void init(){
		//load character sprites
		SpriteSheet characterSheet = new SpriteSheet(ImageLoader.loadImage("/textures/CharacterSpriteSheet.png"));
		player = characterSheet.crop(0, 0, width, height);
		
		//load creature sprites
		SpriteSheet creatureSheet = new SpriteSheet(ImageLoader.loadImage("/textures/CreatureSpriteSheet.png"));
		fungus = creatureSheet.crop(0, 0, width, height);
		bat = creatureSheet.crop(width, 0, width, height);
		gob = creatureSheet.crop(width*2, 0, width, height);
		
		//load terrain sprites
		SpriteSheet terrainSheet = new SpriteSheet(ImageLoader.loadImage("/textures/TerrainSpriteSheet.png"));
		black = terrainSheet.crop(0, 0, width, height);
		fow = terrainSheet.crop(0, height, width, height);
		grass = terrainSheet.crop(width, 0, width, height);
		tree = terrainSheet.crop(width*2, 0, width, height);
		
		//load object sprites
		SpriteSheet objectSheet = new SpriteSheet(ImageLoader.loadImage("/textures/ObjectSpriteSheet.png"));
		stairsUp = objectSheet.crop(0, 0, width, height);
		stairsDown = objectSheet.crop(width, 0, width, height);
		
		//load item sprites
		SpriteSheet itemSheet = new SpriteSheet(ImageLoader.loadImage("/textures/ItemSpriteSheet.png"));
		rock = itemSheet.crop(0, 0, width, height);
		
		//load test sprites
		SpriteSheet testSheet = new SpriteSheet(ImageLoader.loadImage("/textures/test.png"));
//		tree = testSheet.crop(0, 0, width, height);
//		grass = testSheet.crop(0, 0, width, height);
		
		//load UI images
		SpriteSheet textBoxSheet = new SpriteSheet(ImageLoader.loadImage("/textures/TextBox.png"));
		textBox = textBoxSheet.crop(0, 0, 425, 135);

		
	}
	
}
