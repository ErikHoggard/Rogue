package entities;

import Factories.EntityFactory;

public class FungusAi extends CreatureAi{
	
	private EntityFactory factory;
	private int spreadcount;

	
	public FungusAi(Creature creature, EntityFactory factory) {
		super(creature);
		this.factory = factory;
	}
	
	public void onUpdate() {
		if(spreadcount < 5 && Math.random() < 0.02)
			spread();
	}
	
	private void spread() {
		int x = creature.x + (int)(Math.random() * 11) - 5;
		int y = creature.y + (int)(Math.random() * 11) - 5;
		
		if (!creature.canEnter(x, y, this.creature.z))
			return;
		
		Creature child = factory.newFungus(this.creature.z);
		child.x = x;
		child.y = y;
		child.z = this.creature.z;
		spreadcount++;
		
		this.creature.doAction("spread");
	}

}
