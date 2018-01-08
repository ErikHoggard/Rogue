package Factories;

import java.awt.image.BufferedImage;
import java.util.List;

import entities.BatAi;
import entities.Creature;
import entities.FieldOfView;
import entities.FungusAi;
import entities.Item;
import entities.PlayerAi;
import graphics.Assets;
import world.World;

public class EntityFactory {

	private World world;
	
	public EntityFactory(World world) {
		this.world = world;
	}
	
	public Creature newPlayer(List<String> messages, FieldOfView fov) {
		Creature player = new Creature(world, "Player", Assets.player, 50,
			5, 5, 5, 0, 1.0, .9, 6);
		world.addAtEmptyLocation(player, 0);
		new PlayerAi(player, messages, fov);
		return player;
	}
	
	public Creature newFungus(int depth) {
		Creature fungus = new Creature(world, "Fungus", Assets.fungus, 5,
				1, 1, 1, 0, 5.0, 1.0, 0);
		world.addAtEmptyLocation(fungus, depth);
		new FungusAi(fungus, this);
		return fungus;
	}
	
	public Creature newBat(int depth) {
		Creature bat = new Creature(world, "Bat", Assets.bat, 10,
				1, 1, 1, 0, .5, 1.0, 0);
		world.addAtEmptyLocation(bat, depth);
		new BatAi(bat, this);
		return bat;
	}
	
	
	
	public Item newPepper(int depth) {
		Item pepper = new Item(Assets.rock, "pepper");
		world.addAtEmptyLocation(pepper, depth);
		return pepper;
	}
}
