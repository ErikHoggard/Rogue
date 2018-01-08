package states;

import graphics.Assets;
import input.KeyManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import Factories.EntityFactory;
import world.Tile;
import world.World;
import world.CaveBuilder;
import client.Game;
import entities.Creature;
import entities.FieldOfView;

public class GameState extends State {
	
	private final int tileSize = 45;
	
	private World world;
	
	private Creature player;
	private int screenWidth;
	private int screenHeight;
	private int mapHeight = 30;   //Hardcoded map size, change this later
	private int mapWidth = 35;
	private int mapDepth = 5;
	private int left;
	private int top;
	int currentLevel = 0;
	private long lastPressProcessed = 0;
	private boolean keyDown = false;
	private boolean shiftMod = false;
	
	private List<String> messages;
	private FieldOfView fov;
	
	public GameState (Game game) {
		super(game);

		screenWidth = game.getDisplay().getFrame().getWidth()/60;
		screenHeight = game.getDisplay().getFrame().getHeight()/60;
		messages = new ArrayList<String>();
		createWorld();
		world.setClock(0.0);
		fov = new FieldOfView(world);
		
		EntityFactory ef = new EntityFactory(world);
		createItems(ef);
		createCreatures(ef);
		
		//initializes 5 blank messages to avoid indexOutOfBounds when displaying  messages
		for(int i = 0; i <= 10; i++)
			messages.add(" ");
	}
	
	private void createCreatures(EntityFactory ef) {
		player = ef.newPlayer(messages, fov);
		
//		for (int i = 0; i < 4; i++) {
//			ef.newFungus(i);
//		}
		
		for( int z = 0; z < world.getDepth(); z++) {
			for (int i = 0; i < (world.getWidth() * world.getHeight()) / 35; i++) {
				ef.newFungus(z);
			}
		}
		
//		for(int i = 0; i < 4; i++) {
//			ef.newBat(i);
//		}
		
		for( int z = 0; z < world.getDepth(); z++) {
			for (int i = 0; i < (world.getWidth() * world.getHeight()) / 35; i++) {
				ef.newBat(z);
			}
		}
	}
	
	private void createItems (EntityFactory ef) {
		for( int z = 0; z < world.getDepth(); z++) {
			for (int i = 0; i < (world.getWidth() * world.getHeight()) / 35; i++) {
				ef.newPepper(z);
			}
		}
	}
	
	private void createWorld() {
		world = new CaveBuilder(mapHeight,mapWidth,mapDepth)
			.makeForest()
			.build();
	}

	
	public int getScrollX() {
	    return Math.max(0, Math.min(player.x - screenWidth / 2, world.getWidth() - screenWidth));
	}
	public int getScrollY() {
	    return Math.max(0, Math.min(player.y - screenHeight / 2, world.getHeight() - screenHeight));
	}
		
	
	private void respondToInput() {

		//if it has been 200ms since the last press, or if no keys are currently
		//pressed down, execute the action
		//Maybe replace this with a do/while loop?
		if(System.currentTimeMillis() - lastPressProcessed > 200 ||	!keyDown) {
			if(game.getKeyManager().num6 || game.getKeyManager().l) {
				player.moveBy(1, 0, 0);	
				world.update();
			}
			if (game.getKeyManager().num4 || game.getKeyManager().h){
				player.moveBy(-1, 0, 0);
				world.update();
			}
			if(game.getKeyManager().num8 || game.getKeyManager().k){
				player.moveBy(0, -1, 0);
				world.update();
			}
			if(game.getKeyManager().num2 || game.getKeyManager().j){
				player.moveBy(0, 1, 0);
				world.update();
			}
			if(game.getKeyManager().num1 || game.getKeyManager().b){
				player.moveBy(-1, 1, 0);
				world.update();
			}
			if(game.getKeyManager().num3 || game.getKeyManager().n){
				player.moveBy(1, 1, 0);
				world.update();
			}
			if(game.getKeyManager().num7 || game.getKeyManager().y){
				player.moveBy(-1, -1, 0);
				world.update();
			}
			if(game.getKeyManager().num9 || game.getKeyManager().u){
				player.moveBy(1, -1, 0);
				world.update();
			}
			
			if(game.getKeyManager().comma || game.getKeyManager().g){
				player.pickup();
				world.update();
			}
			if(game.getKeyManager().period && game.getKeyManager().shift){
				player.moveBy(0, 0, 1);
				world.update();
			}
			if(game.getKeyManager().comma && game.getKeyManager().shift){
				player.moveBy(0, 0, -1);
				world.update();
			}
			
		lastPressProcessed = System.currentTimeMillis();
		}
		
		
		
		//checks if a key is being held down, excluding modifiers {shift}
		keyDown = game.getKeyManager().isKeyDown();
	}
	
	
//	public void respondToInput(KeyEvent key) {
//		switch (key.getKeyCode()) {
//		case KeyEvent.VK_LEFT:
//        case KeyEvent.VK_H: player.moveBy(-1, 0,0); world.update(); break;
//        case KeyEvent.VK_RIGHT:
//        case KeyEvent.VK_L: player.moveBy(1, 0,0); world.update(); break;
//        case KeyEvent.VK_UP:
//        case KeyEvent.VK_K: player.moveBy( 0,-1,0); world.update(); break;
//        case KeyEvent.VK_DOWN:
//        case KeyEvent.VK_J: player.moveBy( 0, 1,0); world.update(); break;
//        case KeyEvent.VK_Y: player.moveBy(-1,-1,0); world.update(); break;
//        case KeyEvent.VK_U: player.moveBy( 1,-1,0); world.update(); break;
//        case KeyEvent.VK_B: player.moveBy(-1, 1,0); world.update(); break;
//        case KeyEvent.VK_N: player.moveBy( 1, 1,0); world.update(); break;
//		}
//	}
	
	@Override
	public void update() {

		screenWidth = game.getDisplay().getFrame().getWidth()/tileSize;
		screenHeight = game.getDisplay().getFrame().getHeight()/tileSize;
		
		respondToInput();

		//update the player's field of view
		fov.update(player.x, player.y, player.z, player.getVisionRadius());
		
		//update the top left screen position.  Modify these values to change player position centering
		left = getScrollX();
		top = getScrollY();		
		
		if(player.getHp() < 1)
			StateManager.setState(new LoseState(game));
	}

	private void displayMessages(Graphics g, List<String> messages) {
		int top = screenHeight - (messages.size());
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setFont(new Font("Serif", Font.BOLD, 20));
		g2d.setColor(Color.BLACK);
		
		//Display the last 5 messages. The outer loop adds a drop shadow to the text
		for(int j = 0; j <= 1; j++) {
			for(int i = messages.size()-1; i >= messages.size() - 5; i--) {
				g2d.drawString(messages.get(i), 20 + j, screenHeight*tileSize - ((messages.size()-i)*20) - j);
			}
			g2d.setColor(Color.WHITE);
		}
	}

	@Override
	public void render(Graphics g) {

		Graphics2D g2d = (Graphics2D)g;
		
		for (int x = 0; x <= screenWidth; x++){
			for (int y = 0; y <= screenHeight; y++){
				int wx =  x + left;
				int wy = y + top;

				if(player.canSee(wx, wy, player.z)) {
					g.drawImage(world.getBackgroundTile(), x*tileSize, y*tileSize, null);
					g.drawImage(world.tile(wx, wy, player.z).getSprite(), x*tileSize, y*tileSize, null);
					if(world.item(wx, wy, player.z) != null)
						g.drawImage(world.item(wx, wy, player.z).getSprite(),x*tileSize,y*tileSize,null);
				}
				else {
					//draw the bg behind structures/items, then the stuff, then fog of war
					g.drawImage(world.getBackgroundTile(), x*tileSize, y*tileSize, null);
					g.drawImage(fov.tile(wx, wy, player.z).getSprite(), x*tileSize, y*tileSize, null);
					g.drawImage(Assets.fow, x*tileSize, y*tileSize, null);
				}

				
		
			}
		}
		for(Creature c : world.getCreatures()) {
			if(player.canSee(c.x, c.y, c.z))
				g.drawImage(c.getSprite(), (c.x - left)*tileSize, (c.y-top)*tileSize, null);
		}
//		g.drawImage(Assets.textBox, 5, screenHeight*60 - 135, null);
		displayMessages(g, messages);
		g.drawString(Double.toString(world.getClock()), 50, 50);
	}
}
























