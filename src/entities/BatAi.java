package entities;

import Factories.EntityFactory;

public class BatAi extends CreatureAi{

	private EntityFactory factory;
	private Creature creature;
	
	public BatAi(Creature creature, EntityFactory factory){
		super(creature);
		this.factory = factory;
	}
	
	public void onUpdate() {
		super.onUpdate();
		wander();
	}

	
}
