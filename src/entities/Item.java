package entities;

import java.awt.image.BufferedImage;

public class Item {
	
	private BufferedImage sprite;
	private String name;
	private int strMod;
	private int dexMod;
	private int intMod;
	private int ACMod;
	private int attackValue;
	private int defenseValue;
	
	public Item (BufferedImage sprite, String name) {
		this.sprite = sprite;
		this.name = name;
	}
	
	
	

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStrMod() {
		return strMod;
	}

	public void setStrMod(int strMod) {
		this.strMod = strMod;
	}

	public int getDexMod() {
		return dexMod;
	}

	public void setDexMod(int dexMod) {
		this.dexMod = dexMod;
	}

	public int getIntMod() {
		return intMod;
	}

	public void setIntMod(int intMod) {
		this.intMod = intMod;
	}

	public int getAttackValue() {
		return attackValue;
	}

	public void setAttackValue(int attackValue) {
		this.attackValue = attackValue;
	}

	public int getDefenseValue() {
		return defenseValue;
	}

	public void setDefenseValue(int defenseValue) {
		this.defenseValue = defenseValue;
	}

	public int getACMod() {
		return ACMod;
	}

	public void setACMod(int aCMod) {
		ACMod = aCMod;
	}
	
	
	
}
