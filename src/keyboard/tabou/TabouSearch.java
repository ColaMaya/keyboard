package keyboard.tabou;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import keyboard.data.Bigram;
import keyboard.data.Keyboard;
import keyboard.data.Population;

public class TabouSearch {


	private Bigram bigram;
	private Keyboard solution;
	private Double cost;
	private ArrayList<Keyboard> keyboards;
	private ArrayList<Double> costs;
	private ArrayList<Keyboard> tabous;
	private int keyboardsSize;
	private int tabousSize;
	private int iterations;

	public TabouSearch(Bigram bigram, Population population, int keyboardsSize, int tabousSize, int iterations) {
		this.bigram = bigram;
		this.keyboards = population.getKeyboards();
		this.costs = this.costs();
		this.solution = this.findSolution();
		this.cost = this.cost();
		this.tabous = this.keyboards;
		this.keyboardsSize = keyboardsSize;
		this.tabousSize = tabousSize;	
		this.iterations = iterations;
	}

	public Keyboard getSolution() {
		return this.solution;
	}

	public Double getCost() {
		return this.cost;
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

	private Double cost() {
		return this.solution.totalCost(this.bigram);
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

	private Keyboard findSolution() {
		this.sortByCost();
		Keyboard result = new Keyboard();
		result = this.keyboards.get(0);
		this.keyboards.remove(0);
		this.costs.remove(0);
		return result;
	}

	public void execute() {

		for(int iteration = 0; iteration < 1; iteration++) {
			this.keyboards = new ArrayList<Keyboard>();
			for(int i = 0; i < keyboardsSize; i++) {
				Keyboard movment = new Keyboard();
				do {
					movment = this.solution.mutateOnce();
				} while(tabous.contains(movment));
				this.keyboards.add(movment);			
			}
			this.sortByCost();
			this.solution = this.findSolution();
			if(this.tabous.size() >= tabousSize)
				for(int i = 0; i < keyboardsSize; i++)
					this.tabous.remove(i);
			this.tabous.addAll(this.keyboards);
		}
	}



}
