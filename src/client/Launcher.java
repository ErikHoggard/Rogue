package client;

public class Launcher {

	//window size is updated dynamically in the Game class update loop
	public static int windowWidth = 1260, windowHeight = 720; 
	
	public static void main(String[] args){
		
		Game game = new Game("TestGame", windowWidth, windowHeight);
		game.start();
		
	}
	
}
