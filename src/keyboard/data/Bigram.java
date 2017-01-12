package keyboard.data;

public class Bigram {

	
	private final int rows = 26;
	private final int columns = 26;
	private long values[][];

	public Bigram(Parser parser) {
		this.values = new long[this.rows][this.columns];
		this.setValues(parser.getValues());
	}

	
	private void setValue(int row, int column, long value) {		
		this.values[row][column] = value;
	}

	private void setValues(long values[][]) {
		if(values.length != this.rows)
			System.out.println("Wrong row dimension for values in Bigram.");
		for(long byLine[] : values) {
			if(byLine.length != this.columns)
				System.out.println("Wrong column dimension for values in Bigram.");
		}
		int row = 0, column = 0;		 
		for(long byLine[] : values) {
			column=0;
			for(long value : byLine) {  
				this.setValue(row, column, value);
				column++;
			}
			row++;
		}
	}

	
	public long getValue(int row, int column) {
		long result = -1;
		if(row > 25)
			System.out.println("Wrong row index.");	
		else if(column > 25)
			System.out.println("Wrong column index.");
		else
			result = this.values[row][column];
		return result;
	}
	
	
	public String toString() {
		String result = "";
		for(long byLine[] : this.values) {
		  for(long value : byLine) { 
			int temp = 15;
			if(value!=0) temp = temp - (int)Math.log10(value);
			result = result + value; 
			for(int i = 0; i<temp; i++)
				result = result + " ";
		  }
		  result = result + "\n";
		}
		return result;
	}


}
