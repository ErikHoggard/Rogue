package states;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import client.Game;
import entities.Creature;
import entities.Item;


public abstract class InventoryBasedState extends State {

	Game game;
	Creature player;
	private String letters;
	
	protected abstract String getVerb();
	protected abstract boolean isAcceptable(Item item);
	protected abstract State use(Item item);
	
	public InventoryBasedState(Game game, Creature player) {
		super(game);
		this.player = player;
		this.letters = "abcdefghijklmnopqrstuvwxyz";
	}

	private ArrayList<String> getList() {
		ArrayList<String> lines = new ArrayList<String>();
		Item[] inventory = player.getInventory().getItems();
		
		for (int i = 0; i < inventory.length; i++) {
			Item item = inventory[i];
			if (item == null || !isAcceptable(item))
				continue;
			String line = letters.charAt(i) + " - " + item.getName();
			
			lines.add(line);
		}
		return lines;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		ArrayList<String> lines = getList();
		
		
		int y = 23 - lines.size();
		int x = 4;
		
		if(lines.size() > 0)
			g2d.string
			
		
	}
	
}
