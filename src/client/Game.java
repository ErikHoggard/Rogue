package client;

import graphics.Assets;
import input.KeyManager;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import entities.Creature;
import states.GameState;
import states.MenuState;
import states.State;
import states.StateManager;


public class Game implements Runnable {

	private Display display;
	public int width, height;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	
	//States
	private State gameState;
	private State menuState;
	
	//input
	private KeyManager keyManager;
	

	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		keyManager = new KeyManager();	}
	
	
	private void init(){
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		Assets.init();
		
		gameState = new GameState(this);
		menuState = new MenuState(this);
		StateManager.setState(gameState);
	}
	
	private void update(){
		
		keyManager.update();
		
		if (StateManager.getState() != null) {
			StateManager.getState().update();
		}
	}
	
	private void render(){
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//clear screen
		g.clearRect(0, 0, width, height);
		//begin draw
		if (StateManager.getState() != null)
			StateManager.getState().render(g);
		
		//end draw
		
		bs.show();
		g.dispose();
	}
	
	public void run() {
		
		init();
		
		//framerate cap
		int fps = 25;
		double timePerUpdate = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int updates = 0;
		
		while(running){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerUpdate;
			timer += now - lastTime;
			lastTime = now;
			
			if (delta >= 1) {
				update();
				render();
				updates++;
				delta--;
			}
			
			if (timer >= 1000000000){
//				System.out.println("Updates and Frames: " + updates);
				updates = 0;
				timer = 0;
			}
		}
		stop();
	}
	
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this); //run "this" class under the new thread
		thread.start(); //calls the run method
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public KeyManager getKeyManager() {
		return keyManager;
	}


	public Display getDisplay() {
		return display;
	}


	public void setDisplay(Display display) {
		this.display = display;
	}
	
	
}



































