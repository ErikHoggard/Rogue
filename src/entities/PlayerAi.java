package entities;

import java.util.List;

import world.Tile;

public class PlayerAi extends CreatureAi {
	
	private List<String> messages;
	private FieldOfView fov;
	
	public PlayerAi(Creature creature, List<String> messages, FieldOfView fov) {
		super(creature);
		this.messages = messages;
		this.fov = fov;
	}
	
	@Override
	public void onEnter(int x, int y, int z, Tile tile) {
		if (tile.isGround()) {
			creature.x = x;
			creature.y = y;
			creature.z = z;
		}
	}
	
	@Override
	public boolean canSee(int wx, int wy, int wz) {
		return fov.isVisible(wx, wy, wz);
	}
	
	@Override
	public void onNotify(String message) {
		messages.add(message);
	}

	public void onUpdate() {
		//update to check whether or not player moved
		if(creature.isJustMoved() && !creature.isJustAttacked()) {
			creature.getWorld().setClock(creature.getWorld().getClock() + creature.getMoveSpeed());
			creature.setJustMoved(false);
		} else if(creature.isJustAttacked()){
			creature.getWorld().setClock(creature.getWorld().getClock() + creature.getAttackSpeed());
			creature.setJustAttacked(false);
		}
	}
}
