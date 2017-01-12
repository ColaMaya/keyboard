package keyboard.data;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Parser {

	
	private Path path;
	private final static Charset ENCODING = StandardCharsets.UTF_8; 
	private long values[][] = new long[26][26];

	public Parser(){
		path = Paths.get("data.txt");	
	}

	
	public final void processLineByLine() throws IOException {
		try (Scanner scanner =  new Scanner(path, ENCODING.name())){
			int row = 0;
			while (scanner.hasNextLine()){
				String line = scanner.nextLine();
				if(!line.contains("#")) {
					processLine(line, row);
					row++;
				}			
			}      
		}
	}

	private void processLine(String line, int row){
		Scanner scanner = new Scanner(line);
		if (scanner.hasNext()) {
			for(int column=0; column < 26; column++) {  
				values[row][column] = Long.parseLong(scanner.next());
			}
		}
		else
			log("Empty or invalid line. Unable to process.");
		scanner.close();
	}

	private static void log(Object aObject){
		System.out.println(String.valueOf(aObject));
	}
	
	
	public long[][] getValues() {
		return this.values;
	}
	
	
} 
