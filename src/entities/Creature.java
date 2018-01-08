package entities;

import java.awt.image.BufferedImage;

import world.Tile;
import world.World;

public class Creature {
	
	private World world;
	
	public int x, y, z;
	
	//Creature stats
	private BufferedImage sprite;
	private int maxHp;
	private int hp;
	private int strength;
	private int dex;
	private int intel;
	private int ac;
	private double moveSpeed;
	private double attackSpeed;
	private String name;
	private int visionRadius;
	
	private boolean justMoved;
	private boolean justAttacked;
	
	private Inventory inventory;
	private Item weapon;
	private Item armor;
	
	public Creature(World world, String name, BufferedImage sprite, int maxHp,
			int strength, int dex, int intel, int ac, double moveSpeed, double attackSpeed, int visionRadius) {
		this.world = world;
		this.name = name;
		this.sprite = sprite;
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.strength = strength;
		this.dex = dex;
		this.intel = intel;
		this.ac = ac;
		this.moveSpeed = moveSpeed;
		this.attackSpeed = attackSpeed;
		this.visionRadius = visionRadius;
		this.inventory = new Inventory(20);
	}

	
	
	private CreatureAi ai;
	public void setCreatureAi(CreatureAi ai) {
		this.ai = ai;
	}
	
	
	private void notify(String message, Object ... params) {
		ai.onNotify(String.format(message, params));
	}
	
	
	
	public boolean canSee(int wx, int wy, int wz) {
		return ai.canSee(wx, wy, wz);
	}
	
	
	
	public Tile tile(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz);
	}
	
	
	
	public void pickup() {
		Item item = world.item(x, y, z);
		
		if (inventory.isFull()) {
			notify("Your inventory is full");
		} else if ( item == null ){
			notify("There is nothing to pick up");
		} else {
			doAction("pick up a %s", item.getName());
			world.remove(x,y,z);
			inventory.add(item);
		}
	}
	
	
	
	public void drop (Item item){
		if (world.addAtEmptySpace(item, x,y,z)){
			doAction("drop a " + item.getName());
			inventory.remove(item);
			unequip(item);
			unequip(item);
		} else {
			notify("There's no room to drop anything");
		}
	}
	
	
	
    public void unequip(Item item){
        if (item == null)
            return;
        
        if (item == armor) {
            doAction("remove the " + item.getName());
            armor = null;
        } else if (item == weapon) {
            doAction("unequip the " + item.getName());
            weapon = null;
        }
    }
    
    
    
    public void equip(Item item){
        if (item.getAttackValue() == 0 && item.getDefenseValue() == 0)
          return;
   
        if (item.getAttackValue() >= item.getDefenseValue()){
            unequip(weapon);
            doAction("wield the " + item.getName());
            weapon = item;
        } else {
            unequip(armor);
            doAction("put on the " + item.getName());
            armor = item;
      }
    }
    
    
    
    public void moveBy(int mx, int my, int mz){
    	Tile tile = world.tile(x+mx, y+my, z+mz);
    	
    	
    	if (mz == -1){
    		if (tile == Tile.STAIRS_DOWN) {
    			doAction("walk up the stairs");
    			justMoved = true;
    		} else {
    			doAction("cannot go up here");
    			return;
    		}
    	} else if (mz == 1) {
    		if (tile == Tile.STAIRS_UP) {
    			doAction("walk down the stairs");
    		} else {
    			doAction("cannot go down here");
    			return;
    		}
    	}
    	
    	if (mx == 0 && my == 0 && mz == 0)
    		return;
    	
    	Creature other = world.creature(x+mx, y+my, z+mz);
    		justMoved = true;
    	
    	if (other == null)
    		ai.onEnter(x+mx, y+my, z+mz, tile);
    	else
    		attack(other);
    }
    
    
    
    public Creature creature(int wx, int wy, int wz){
    	return world.creature(wx, wy, wz);
    }
    
    
    // attack and  modifyHp, console output used for debugging
    public void attack(Creature other) {
        // attack is equal to attacker's damage, minus defender's defense
        // if the result is <0, the attack instead does 0 damage
        System.out.println(this.name + " attacks " + other.name );
        
        int amount = Math.max(0, getStrength() - other.getAc());
        System.out.println("raw attack number: " + amount);
        
        //attack # is then multiplied by a random number from 0-1 and +1
        amount = (int)(Math.random()*amount) + 1;
        System.out.println("damage dealt: " + amount);
        
        notify("You attack the " + other.name + " for " + amount + " damage");
        other.notify("The " + this.name + " attacks you for " + amount + " damage" );
        
        other.modifyHp(-amount);
        System.out.println("hp remaining: " + other.hp + "\n");
        
        justAttacked = true;
    }
    
    public void wait(double d) {
    	
    }
    
    public void modifyHp(int amount) {
        hp += amount;
        
        if (hp < 1) {
            doAction("die");
            world.remove(this);
        }
    }
    
    
 // allows messages to be created when a creature performs an action
    public void doAction(String message, Object ... params) {
        int r = 10;
        for (int ox = -r; ox < r+1; ox++) {
            for (int oy = -r; oy < r+1; oy++) {
                if (ox*ox + oy*oy > r * r)
                    continue;

                Creature other = world.creature(x+ox, y+oy, z);

                if (other == null)
                    continue;

                if (other == this)
                    other.notify("You " + message + ".", params);
                else if (other.canSee(this.x, this.y, this.z))
                    other.notify(String.format("The %s %s.", name, makeSecondPerson(message)), params);
            }
        }
    }
 // makeSecondPerson makes adds an 's' to the end the first word in a string
    private String makeSecondPerson(String text){
        String[] words = text.split(" ");
        words[0] = words[0] + "s";
        
        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            builder.append(" ");
            builder.append(word);
        }
        return builder.toString().trim();
    }
    
    
    public void update() {
    	ai.onUpdate();
    }
    
    // checks if the tile in question is both a ground tile,
    //and does not contain a creature
    public boolean canEnter(int wx, int wy, int wz) {
        return world.tile(wx, wy, wz).isGround() && world.creature(wx, wy, wz) == null;
    }
    
    // same as the canEnter method, but only returns true for empty floors
    public boolean canEnterFloorOnly(int wx, int wy, int wz) {
        return world.tile(wx, wy, wz).isGroundNotStairs() && world.creature(wx, wy, wz) == null;
    }
    
    
	
	//getters/setters
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getDex() {
		return dex;
	}

	public void setDex(int dex) {
		this.dex = dex;
	}

	public int getIntel() {
		return intel;
	}

	public void setIntel(int intel) {
		this.intel = intel;
	}

	public int getAc() {
		return ac;
	}

	public void setAc(int ac) {
		this.ac = ac;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVisionRadius() {
		return visionRadius;
	}

	public void setVisionRadius(int visionRadius) {
		this.visionRadius = visionRadius;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Item getWeapon() {
		return weapon;
	}

	public void setWeapon(Item weapon) {
		this.weapon = weapon;
	}

	public Item getArmor() {
		return armor;
	}

	public void setArmor(Item armor) {
		this.armor = armor;
	}


	public double getMoveSpeed() {
		return moveSpeed;
	}


	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}	
	
	public boolean isJustMoved() {
		return justMoved;
	}

	public void setJustMoved(boolean justMoved) {
		this.justMoved = justMoved;
	}


	public boolean isJustAttacked() {
		return justAttacked;
	}


	public void setJustAttacked(boolean justAttacked) {
		this.justAttacked = justAttacked;
	}


	public double getAttackSpeed() {
		return attackSpeed;
	}


	public void setAttackSpeed(double attackSpeed) {
		this.attackSpeed = attackSpeed;
	}


	public void setMoveSpeed(double moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
	
	

}
