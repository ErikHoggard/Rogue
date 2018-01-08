package states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import client.Game;

public abstract class State {
	
	private static State currentState = null;
	
	public void setState(State state) {
		currentState = state;
	}
	
	public static State getState() {
		return currentState;
	}
	
	//CLASS
	
	protected Game game;
	
	public State(Game game) {
		this.game = game;
	}
		
	public abstract void update();
	
	public abstract void render(Graphics g);
	
}
