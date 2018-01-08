package states;

import java.awt.Graphics;

import client.Game;
import states.State;

public class LoseState extends State {

	private Game game;
	private int screenWidth, screenHeight;
	
	public LoseState(Game game) {
		super(game);
		this.game = game;
	}
	
	@Override
	public void update() {

		screenWidth = game.getDisplay().getFrame().getWidth();
		screenHeight = game.getDisplay().getFrame().getHeight();
		
		
	}

	@Override
	public void render(Graphics g) {

		g.drawString("YOULOSE", screenWidth - (screenWidth/2), screenHeight - (screenHeight/2));
		
	}

}
