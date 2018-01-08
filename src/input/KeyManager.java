package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class KeyManager implements KeyListener{
	
	private boolean[] keys;
	public boolean escape, num1, num2, num3, num4, num5, num6, num7, num8, num9, num0;
	public boolean h,j,k,l,y,u,b,n,g;
	public boolean shift, period, comma;
	
	private boolean keyDown, shiftMod;
	
	//KeyTyped adds keys to the queue
	private List<Integer> queue = new LinkedList<>();
	
	List<Integer> pressedKeys = new ArrayList<Integer>();
	Iterator<Integer> iter = pressedKeys.iterator();
	
	public KeyManager() {
		keys = new boolean[256];
	}
	
	public void update() {
		num1 = keys[KeyEvent.VK_NUMPAD1];
		num2 = keys[KeyEvent.VK_NUMPAD2];
		num3 = keys[KeyEvent.VK_NUMPAD3];
		num4 = keys[KeyEvent.VK_NUMPAD4];
		num5 = keys[KeyEvent.VK_NUMPAD5];
		num6 = keys[KeyEvent.VK_NUMPAD6];
		num7 = keys[KeyEvent.VK_NUMPAD7];
		num8 = keys[KeyEvent.VK_NUMPAD8];
		num9 = keys[KeyEvent.VK_NUMPAD9];
		h = keys[KeyEvent.VK_H];
		j = keys[KeyEvent.VK_J];
		k = keys[KeyEvent.VK_K];
		l = keys[KeyEvent.VK_L];
		y = keys[KeyEvent.VK_Y];
		u = keys[KeyEvent.VK_U];
		b = keys[KeyEvent.VK_B];
		n = keys[KeyEvent.VK_N];
		g = keys[KeyEvent.VK_G];
		
		shift = keys[KeyEvent.VK_SHIFT];
		period = keys[KeyEvent.VK_PERIOD];
		comma = keys[KeyEvent.VK_COMMA];
		
	}
	
	public boolean isKeyDown() {
		return keyDown = !pressedKeys.isEmpty();
	}
	
	public boolean isShiftMod() {
		return pressedKeys.contains(16);
	}
	
	//THIS IS FUCKING STUPID FOR A ROGUELIKE, FIGURE OUT A BETTER WAY TO MANAGE KEY PRESSES
	//TODO
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		
		//stores each pressed key's code in an ArrayList
		Integer x = new Integer(e.getKeyCode());
		if(!pressedKeys.contains(x.intValue()) && e.getKeyCode() != 16)
			pressedKeys.add(new Integer(e.getKeyCode()));
	}
	
//	public void keyPressed(KeyEvent e) {
//		queue.add(e.getKeyCode());
//		System.out.println(e.getKeyCode());
//	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		
		//when a key is released, remove it's code from the ArrayList
		for(Integer i : pressedKeys) {
			if (i.intValue() == e.getKeyCode()){
				pressedKeys.remove(i);
				break;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//TODO
	}
	

}

