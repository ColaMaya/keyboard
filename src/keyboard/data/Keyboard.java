package keyboard.data;

import java.util.ArrayList;
import java.util.Random;

public class Keyboard {


	private final int rows = 4;
	private final int columns = 10;
	private int values[][];

	public Keyboard() {
		this.values = new int[this.rows][this.columns];
		for(int row = 0; row < this.rows; row++) {
			for(int column = 0; column < this.columns; column++) {
				this.values[row][column] = -1;
			}
		}
	}


	public void setValue(int row, int column, int value) {
		if(row > 3 || column > 9)
			System.out.println("Wrong index.");
		else	 
			this.values[row][column] = value;	
	}
	
	public void setValues(int[][] values) {
		this.values = values;
	}


	public int getValue(int row, int column) {
		int result = -1;
		if(row > 3 || column > 9)
			System.out.println("Wrong index.");
		else
			result = this.values[row][column];
		return result;
	}

	public int rowOf(int value) {
		for(int row = 0; row < this.rows; row++) {
			for(int column = 0; column < this.columns; column++) {
				if(this.values[row][column] == value)
					return row;
			}
		}
		return -1;
	}

	public int columnOf(int value) {
		for(int row = 0; row < this.rows; row++) {
			for(int column = 0; column < this.columns; column++) {
				if(this.values[row][column] == value)
					return column;
			}
		}
		return -1;
	}


	private double distance(int value1, int value2) {
		if(value1 == value2)
			return 0;
		else {
			int row1 = this.rowOf(value1);
			int row2 = this.rowOf(value2);
			int column1 = this.columnOf(value1);
			int column2 = this.columnOf(value2);
			if(row1 == -1 || row2 == -1 || column1 == -1 || column2 == -1)
				return -1;
			else
				return Math.sqrt(Math.pow(row1-row2, 2) + Math.pow(column1-column2, 2));
		}	
	}

	private double cost(int value1, int value2, Bigram bigram) {
		double distance = this.distance(value1, value2);
		long frequency = bigram.getValue(value1, value2);
		double result = -1;
		if(distance < 0) 
			System.out.println("These values aren't in the keyboard.");
		else
			result = distance*frequency;
		return result;
	}

	public double totalCost(Bigram bigram) {
		double result = 0;
		for(int value1 = 0; value1 < 25; value1++) {
			for(int value2 = 0; value2 < 25; value2++) {
				double cost = this.cost(value1, value2, bigram);
				if(cost == -1)
					return -1;
				else
					result += cost;
			}
		}
		return result;
	}


	public void mutate(int iterations) {
		new Random();
		for(int i = 0; i < iterations; i++) {
			int row1 = (int)(Math.random() * 4);
			int column1 = (int)(Math.random() * 10);
			int value1 = this.getValue(row1, column1);
			int row2, column2, value2;
			do {
				row2 = (int)(Math.random() * 4);
				column2 = (int)(Math.random() * 10);
				value2 = this.getValue(row2, column2);
			} while(value1 == value2);
			this.setValue(row2, column2, value1);
			this.setValue(row1, column1, value2);
		}
	}

	public Keyboard mutateOnce() {
		int[][] result = new int[rows][columns];
		for(int row = 0; row < this.rows; row++) {
			for(int column = 0; column < this.columns; column++) {
				result[row][column] = this.getValue(row, column);
			}
		}
		new Random();
		int row1 = (int)(Math.random() * 4);
		int column1 = (int)(Math.random() * 10);
		int value1 = this.getValue(row1, column1);
		int row2, column2, value2;
		do {
			row2 = (int)(Math.random() * 4);
			column2 = (int)(Math.random() * 10);
			value2 = this.getValue(row2, column2);
		} while(value1 == value2);
		result[row2][column2] =  value1;
		result[row1][column1] = value2;
		Keyboard keyboard = new Keyboard();
		keyboard.setValues(result);
		return keyboard;

	}


	public boolean isValid() {
		ArrayList<Integer> exist = new ArrayList<Integer>();
		for(int byLine[] : this.values) {
			for(int value : byLine) {
				if(value != -1) {
					if(exist.contains(value)) 
						return false;
					exist.add(value);
				}
			}
		}
		if (exist.size() != 26)
			return false;
		else
			return true;
	}

	public boolean isEqual(Keyboard keyboard) {
		int row = 0;
		int column = 0;
		for(int byLine[] : this.values) {
			column = 0;
			for(int value : byLine) { 
				if(value != keyboard.getValue(row, column)) 
					return false;
				column++;
			}
			row++;
		}
		return true;
	}


	public String toString() {
		String result = "";
		for(int byLine[] : this.values) {
			for(int value : byLine) { 
				result = result + toChar(value) + " "; 
			}
			result = result + "\n";
		}
		return result;
	}

	private String toChar(int value) {
		switch(value) {
		case -1 :
			return " ";
		case 0 :
			return "A";
		case 1 :
			return "B";
		case 2 :
			return "C";
		case 3 :
			return "D";
		case 4 :
			return "E";
		case 5 :
			return "F";
		case 6 :
			return "G";
		case 7 :
			return "H";
		case 8 :
			return "I";
		case 9 :
			return "J";
		case 10 :
			return "K";
		case 11 :
			return "L";
		case 12 :
			return "M";
		case 13 :
			return "N";
		case 14 :
			return "O";
		case 15 :
			return "P";
		case 16 :
			return "Q";
		case 17 :
			return "R";
		case 18 :
			return "S";
		case 19 :
			return "T";
		case 20 :
			return "U";
		case 21 :
			return "V";
		case 22 :
			return "W";
		case 23 :
			return "X";
		case 24 :
			return "Y";
		case 25 :
			return "Z";
		default :
			return "error";
		}
	}


}
