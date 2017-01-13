package keyboard.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import keyboard.data.Bigram;
import keyboard.data.Population;
import keyboard.data.Keyboard;

public class GeneticAlgorithme {

	private Bigram bigram;
	private ArrayList<Keyboard> keyboards;
	private ArrayList<Double> costs;
	private int stopCriterium;
	private int mutationProbability;

	public GeneticAlgorithme(Bigram bigram, Population population, int stopCriterium, int mutationProbability) {
		this.bigram = bigram;
		this.keyboards = population.getKeyboards();
		this.costs = this.costs();
		this.stopCriterium = stopCriterium;
		this.mutationProbability = mutationProbability;
	}

	public ArrayList<Double> getCosts() {
		return this.costs;
	}

	public ArrayList<Keyboard> getKeyboards() {
		return this.keyboards;
	}


	private ArrayList<Double> costs() {
		ArrayList<Double> costs = new ArrayList<Double>();
		Iterator<Keyboard> iterator = this.keyboards.iterator();
		while (iterator.hasNext()) {
			Double temp = iterator.next().totalCost(this.bigram);
			if(temp >= 0) costs.add(temp);
			else System.out.println("Cost not found.");
		}
		if(costs.size() == this.keyboards.size())
			return costs;
		else
			return null;
	}

	private void sortByCost() {
		this.costs = this.costs();
		ArrayList<Double> sortedCosts = this.costs;
		ArrayList<Keyboard> sortedKeyboards = new ArrayList<Keyboard>();
		Collections.sort(sortedCosts);
		Iterator<Double> iterator = sortedCosts.iterator();
		while(iterator.hasNext()) {
			int index = this.costs.indexOf(iterator.next());
			sortedKeyboards.add(this.keyboards.get(index));
		}
		if(sortedKeyboards.size() == this.keyboards.size())
			this.keyboards = sortedKeyboards;
		else 
			System.out.println("Error during sorting.");
	}

	public void execute() {
		for(int i = 0; i < this.stopCriterium; i++) {
			this.sortByCost();
			int size;
			if(this.keyboards.size() > 3)
				size = (int) this.keyboards.size() / 2 + 1;
			else 
				size = this.keyboards.size();
			ArrayList<Keyboard> temp = new ArrayList<Keyboard>();
			for (int index = 0; index < size; index++) {
				temp.add(this.keyboards.get(index));
			}
			this.keyboards = temp;
			temp = new ArrayList<Keyboard>();
			for(int index = 0; index < size - 1; index++) {
				temp.addAll(this.crossOver(index, index + 1));
			}
			this.keyboards = temp;
			for(int index = 0; index < this.keyboards.size(); index++) {
				this.mutate(index);
			}		
		}
		this.sortByCost();
		int size = (int) this.keyboards.size() / 2;	
		ArrayList<Keyboard> temp = new ArrayList<Keyboard>();
		for (int index = 0; index < size; index++) {
			temp.add(this.keyboards.get(index));
		}
		this.keyboards = temp;
	}

	public void mutate(int index) {
		new Random();
		int temp = (int)(Math.random() * 11);
		if(temp <= this.mutationProbability)
			keyboards.get(index).mutate(5);		
	}

	public ArrayList<Keyboard> crossOver(int index1, int index2) {
		ArrayList<Keyboard> result = new ArrayList<Keyboard>();
		Keyboard child1;
		Keyboard child2;
		Keyboard parent1 = this.keyboards.get(index1);
		Keyboard parent2 = this.keyboards.get(index2);
		int point = 13;
		do {
			child1 = new Keyboard();
			child2 = new Keyboard();
			for (int value = 0; value < point; value++) {
				int row = parent1.rowOf(value);
				int column = parent1.columnOf(value);
				child1.setValue(row, column, value);
			}
			for (int value = point; value < 26; value++) {
				int row = parent2.rowOf(value);
				int column = parent2.columnOf(value);
				child1.setValue(row, column, value);
			}
			for (int value = 0; value < point; value++) {
				int row = parent2.rowOf(value);
				int column = parent2.columnOf(value);
				child2.setValue(row, column, value);
			}
			for (int value = point; value < 26; value++) {
				int row = parent1.rowOf(value);
				int column = parent1.columnOf(value);
				child2.setValue(row, column, value);
			}
			point++;
		} while(!(child1.isValid() && child2.isValid()));
		result.add(child1);
		result.add(child2);		
		return result;
	}


}
