package keyboard.data;

import java.util.ArrayList;
import java.util.Random;

public class Population {

	private int size;
	private ArrayList<Keyboard> keyboards;
	
	public Population(int size) {
		this.size = size;
		this.keyboards = new ArrayList<Keyboard>();		
	}
	
	public void generateRandomly() {
		new Random();
		for(int i = 0; i < this.size; i++) {
			Keyboard keyboard = new Keyboard();
			int loop = 0;
			while(loop < 26) {
				int row = (int)(Math.random() * 4);
				int column = (int)(Math.random() * 10);
				if(keyboard.getValue(row, column) == -1) {
					keyboard.setValue(row, column, loop);
					loop++;
				}
			}
			
			this.keyboards.add(keyboard);		
		}	
	}
	
	public int getSize() {
		return this.size;
	}
	
	public ArrayList<Keyboard> getKeyboards() {
		return this.keyboards;
	}
	
	public Keyboard getKeyboard(int index) {
		if(index >= this.size)
			return null;
		else 
			return this.keyboards.get(index);
	}
	
	
	
	
	
}
