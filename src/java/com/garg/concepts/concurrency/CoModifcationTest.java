/*
 * CoModifcationTest.java
 *
 * $Header: /proj/infra/repository/desmake/etc/deshaw_jalopy.xml,v 1.4 2007/09/12 10:24:53 lalam Exp $
 */

/*
 * Copyright (c) 2008 D. E. Shaw & Co., L.P. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of D. E. Shaw & Co., L.P. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with D. E. Shaw & Co., L.P.
 */

package com.garg.concepts.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CoModifcationTest
{
    private final Map<String, Integer> myMap;

    public CoModifcationTest()
    {
        myMap = makeMap();

        for (Integer i = 0; i < 100000; i++) {
            myMap.put(i.toString(), i);
        }
    }

    protected Map<String, Integer> makeMap()
    {
        return new HashMap<String, Integer>();
    }

    public void access()
    {
        while (true) {
            for (String key : myMap.keySet()) {
                // Just access.
                try {
                    Thread.sleep(10);
                }
                catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
        }
    }

    public void modify()
    {
        while (true) {
            for (String key : myMap.keySet()) {
                Integer value = myMap.get(key);
                myMap.put(key, value + 1);

                try {
                    Thread.sleep(5);
                }
                catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
        }
    }

    private static class TestObject 
	{
		int myInt = 0;
		public TestObject(int i) {myInt = i;}
		void setInt(int i) {myInt = i;}    		
	}
    
    public static void main(String... args)
    {
//        final CoModifcationTest testObject = new CoModifcationTest();
//        new Thread(new Runnable() {
//                @Override
//                public void run()
//                {
//                    testObject.access();
//                }
//            }, "Acessor1").start();
//
//        new Thread(new Runnable() {
//                @Override
//                public void run()
//                {
//                    testObject.modify();
//                }
//            }, "Modifier1").start();
    	
    	
    	Collection<TestObject> coll = new ArrayList<TestObject>();
    	for (int i = 0; i < 10000; i++)
    		coll.add(new TestObject(i));
    	Random rdm = new Random();
    	for (TestObject obj : coll) {
    		obj.setInt(rdm.nextInt(1000));
    		System.out.println(obj.myInt);
    	}
    }

}
