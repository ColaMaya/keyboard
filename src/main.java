import java.io.IOException;

import keyboard.data.Bigram;
import keyboard.data.Keyboard;
import keyboard.data.Parser;
import keyboard.data.Population;
import keyboard.genetic.GeneticAlgorithme;

public class main {

	public static void main(String[] args) throws IOException {
		Parser parser = new Parser();
		parser.processLineByLine();
		Bigram bigram = new Bigram(parser);
		Population population = new Population(100);//(int)Integer.parseInt(args[0]));
		population.generateRandomly();		
		for(int i = 0; i < 10; i ++)
			System.out.println(population.getKeyboard(i).toString());
		GeneticAlgorithme algo = new GeneticAlgorithme(bigram, population, 10000, 5 );
		algo.execute();
		int size = algo.getKeyboards().size();
		for(int i = 0; i < size; i++) {
		System.out.println(algo.getKeyboards().get(i).toString());
		System.out.println(algo.getCosts().get(i));
		}
		
		

		


	}

}
