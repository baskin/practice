/*
 * BetterProgrammerTask.java
 *
 * $Header$
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

package com.garg.concepts.algos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public class BetterProgrammerTask
{
    public static class CompoundIterator<E> implements Iterator<E>
    {
        /**
         * Iterator over myIteratorsList.  Note that this will be null
         * until iteration begins.
         */
        private Iterator<Iterator<? extends E>> myIteratorsItr = null;

        /**
         * Current Iterator from myIteratorsList.  We initialize this to an
         * empty iterator to begin with because it simplifies the remainder
         * of the code.
         */
        private Iterator<? extends E> myItr =
            Collections.<E>emptyList().iterator();

        private final List<Iterator<? extends E>> myIteratorsList =
            new ArrayList<Iterator<? extends E>>();

        /**
         * Adds an iterator to the CompoundIterator. If iteration is in
         * progress a ConcurrentModificationException is thrown.
         *
         * @param iterator an Iterator to add to this CompoundIterator
         */
        public void addIterator(Iterator<? extends E> iterator)
        {
            if (myIteratorsItr != null) {
                throw new ConcurrentModificationException(
                    "Cannot add iterator after iteration begins");
            }

            myIteratorsList.add(iterator);
        }

        /**
         * {@inheritDoc}
         */
        public boolean hasNext()
        {
            advanceIfNecessary();

            return myItr.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        public E next()
        {
            advanceIfNecessary();

            return myItr.next();
        }

        /**
         * {@inheritDoc}
         *
         * <p>Note that this method must be called immediately after
         * {@link #next()} in order to work properly.  That is,
         * if you call {@link #hasNext()} after calling {@link #next()} but
         * before calling this method, it may fail.
         */
        public void remove()
        {
            myItr.remove();
        }

        /**
         * Initialize iteration if necessary by advancing myIteratorsItr
         * to an Iterator where an element can be found.
         */
        private void advanceIfNecessary()
        {
            if (myIteratorsItr == null) {
                myIteratorsItr = myIteratorsList.iterator();
            }

            // If we don't have an individual iterator yet, or we've finished
            // with it, advance to the next one where data is available.
            while (myIteratorsItr.hasNext() && ! myItr.hasNext()) {
                myItr = myIteratorsItr.next();
            }
        }
    }

    public static <T> Iterator<T> getIterator(
        final Collection<? extends Collection<? extends T>> cc)
    {
        /*
          Please implement this method to
          return the Iterator of the Collection of the Collections
          It should iterate over all elements in the inner collections.
          Please make remove() method throw UnsupportedOperationException
         */

        CompoundIterator<T> itr = new CompoundIterator<T>();

        for (Collection<? extends T> c : cc) {
            itr.addIterator(c.iterator());
        }

        return itr;

    }
    
    public static int countWords(String s) 
    {
        /*
          Please implement this method to
          return the word count in a given String.
          Assume that the parameter String can only contain spaces and alphanumeric characters.
         */
    	
    	if (s == null) {
    		return 0;
    	}
    	String str = s.trim();
    	if (str.isEmpty()) {
    		return 0;
    	}
    	String[] split = str.split("\\s");
    	if (split == null) {
    		return 0;
    	}
    	int count = 0;
    	for (String s1 : split) {
    		if (s1.trim().isEmpty()) {
    			continue;
    		}
    		count ++;
    	}
    	return count;
    }

    public static void main(String... strings) 
    {
    	System.out.println(countWords(" hi 456 577uu   6uuju     "));
    	
    	///////////
    	String s1 = new String(new char[] { 'a', 'b', 'c', 'd' });
    	String s2 = new String(new char[] { 'a', 'b', 'c', 'd' });
        String s3 = new String(new char[] { 'j', 'a', 'v', 'a' });
        

        System.out.println(System.identityHashCode(s1));
        System.out.println(System.identityHashCode(s2));
        System.out.println(System.identityHashCode(s3));
        
    }
}
