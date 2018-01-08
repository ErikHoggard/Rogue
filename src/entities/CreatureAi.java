package entities;

import world.Line;
import world.Point;
import world.Tile;

public class CreatureAi {
	
	protected Creature creature;
	
	private double currentTime;
	private double previousTime;
	private double timeElapsed;
	
	public CreatureAi(Creature creature) {
		this.creature = creature;
		this.creature.setCreatureAi(this);
		previousTime = 0.0;
		currentTime = 0.0;
		timeElapsed = 0.0;
	}
	
	public void onEnter(int x, int y, int z, Tile tile){
		if(tile.isGround()) {
			creature.x = x;
			creature.y = y;
			creature.z = z;
		} else {
			creature.doAction("bump into a wall");
		}
	}
	
	
	public void wander() {
		while(timeElapsed >= creature.getMoveSpeed()) {	
			int mx = (int)(Math.random() * 3) -1;
			int my = (int)(Math.random() * 3) -1;
			
			Creature other = creature.creature(creature.x + mx, creature.y + my, creature.z);
				
					
	        // possible to change movement to stop creatures from moving into walls
	        // also implement moving up and down stairs if the player is moving
	        // up or down stairs
	        
	        // prevents a creature from fighting another with the same sprite.
	        // can be changed to != @ to only allow creatures to attack the player
	        // or probably come up with another way to do this, such as adding a hostile
	        // boolean value to the creature constructor or something
	        if (other != null && other.getSprite() == creature.getSprite())
	            return;
	        else
	        creature.moveBy(mx, my, 0);
	        
	        timeElapsed -= creature.getMoveSpeed();
		}
	}
	
	
	public void onUpdate() {
		previousTime = currentTime;
		currentTime = creature.getWorld().getClock();
		timeElapsed += currentTime - previousTime;
	}
	
	public void onNotify(String message){
		
	}
	
	public boolean canSee (int wx, int wy, int wz) {
		if (creature.z != wz)
			return false;
		
		if ((creature.x-wx)*(creature.x-wx) + (creature.y-wy)*(creature.y-wy)
				> creature.getVisionRadius()*creature.getVisionRadius())
			return false;
		
		for (Point p : new Line(creature.x, creature.y, wx, wy)) {
			if (creature.tile(p.x, p.y, wz).isGround() || p.x == wx && p.y == wy)
				continue;
			return false;
		}
		return true;
	}


	
	
}





























