/*
 * Test.java
 *
 * $HeadURL: https://bhupi-practice.googlecode.com/svn/trunk/src/java/com/garg/test/ReferenceTest.java $
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

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * The garbage collector will always collect weakly referenced objects, but 
 * will only collect softly referenced objects when its algorithms decide that 
 * memory is low enough to warrant it.
 * 
 *  
 */
public class ReferenceTest
{
	
	private static class Student 
	{
		int id;
		public Student(int id) {
			this.id = id;
		}
		@Override
        public String toString() {
			return "[id=" + id + "]";
		}
		@Override
		public boolean equals(Object obj) {
			return (obj instanceof Student) && ((Student) obj).id == id;
		}
	}
	
	public static void main(String[] args) throws InterruptedException 
	{
		Map<Student, String> weakMap = new WeakHashMap<Student, String>();
		Student s1 = new Student(1);
		{
			Student s2 = new Student(1);
			weakMap.put(s2, "bhupi");
			Reference<Student> wr = new WeakReference<Student>(s2);
			System.out.println(wr.get());
			System.out.println(weakMap.get(s1));
			s2 = null;
			System.gc();
			System.out.println(wr.get());
		}
		System.out.println(weakMap.get(s1));
	}
}
