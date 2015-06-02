/*
 * Test.java
 *
 * $HeadURL: https://bhupi-practice.googlecode.com/svn/trunk/src/java/com/garg/test/MarkovChain.java $
 */

/*
 * Copyright (c) 2010 D. E. Shaw & Co., L.P. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of D. E. Shaw & Co., L.P. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with D. E. Shaw & Co., L.P.
 */

package com.garg.test;

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
