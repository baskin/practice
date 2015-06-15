package com.garg.scratchpad;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class MarkovChain
{
	private final String trainingFileName;
	private final int order;

	public MarkovChain(String trainingFileName, int order) 
	{
		this.trainingFileName = trainingFileName;
		this.order = order;
	}
	
	public MarkovChain(String trainingFileName) 
	{
		this(trainingFileName, 2);
	}
	

	public void process() throws FileNotFoundException
	{
		FileReader reader = new FileReader(trainingFileName); 
	}
	
	public void writeEssay()
	{
		
	}
	
	public void writeEssay(int wordLimit)
	{
		
	}
	
	public static void main(String[] args) throws FileNotFoundException 
	{
		MarkovChain chain = new MarkovChain(args[0]);
		chain.process();
		chain.writeEssay();
	}

}
