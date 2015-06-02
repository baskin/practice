package com.garg.websites.codeeval;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The AddressCache has a max age for the elements it's storing, an add method 
 * for adding elements, a remove method for removing, a peek method which 
 * returns the most recently added element, and a take method which removes 
 * and returns the most recently added element.
 * 
 * Assumptions, which were not clear from the question.
 * - peek() and take() return most recently added element and not the most
 * recently accessed.
 * - the ttl is strict enough that the cache won't run out of memory. i.e. there is no explcit
 *  bounds check. 
 * 
 * @author BhupinderGarg
 */
public class AddressCache 
{
	private long maxAge = -1;
	private TimeUnit ageUnit = null;
	
	// The actual cache for O(1) lookup.
	private final Map<InetAddress, ContainerAddress> cache = 
		new HashMap<InetAddress, AddressCache.ContainerAddress>();
	
	// Cache ordered by insertion time, with most recently one being at the head
	// of the linked list.
	private ContainerAddress first;
	
	// Background thread to cleanup expired cache entries.
	private final ScheduledExecutorService ttlEnforcer = 
		Executors.newScheduledThreadPool(1);
	
	
	/**
	 * Container for the address. Doubles as a node having pointers to containers next and prev
	 * in line in terms of creation time.
	 */
	private static class ContainerAddress
	{
		private InetAddress address;
		private long creationTimeMs;
		
		private ContainerAddress next = null;
		private ContainerAddress prev = null;
		
		public ContainerAddress(final InetAddress address) 
		{
			this.address = address;
			this.creationTimeMs = System.currentTimeMillis();
		}
		
		public InetAddress getAddress() 
		{
			return address;
		}
		
		public long getCreationTimeMs() 
		{
			return creationTimeMs;
		}

		public ContainerAddress getNext() {
			return next;
		}

		public void setNext(final ContainerAddress next) 
		{
			this.next = next;
		}

		public ContainerAddress getPrev() 
		{
			return prev;
		}

		public void setPrev(final ContainerAddress prev) 
		{
			this.prev = prev;
		}
	}
	
	/**
	 * Task which cleans up the cache off expired entries.
	 */
	private class CacheCleaner implements Runnable
	{
		@Override
		public void run() 
		{
			// Complexity can be improved if we maintain end of list too.
			// For now start from 'first' of the list and cleanup.
			ContainerAddress current = first;
			while (current != null) {
				if (!isValid(current)) {
					remove(current.getAddress());
				}
			}
		}
	}
	
	public AddressCache(final long maxAge, final TimeUnit unit) 
	{
		this.maxAge = maxAge;
		this.ageUnit = unit;
		
		this.ttlEnforcer.scheduleWithFixedDelay(
			new CacheCleaner(), 5, 60 * 5, TimeUnit.SECONDS);
	}
	
	/**
	 * add() method must store unique elements only (existing elements must be ignored). 
	 * This will return true if the element was successfully added. 
	 * 
	 * Complexity: O(1)
	 * 
	 * @param address the cache entry to be added
	 * @return true if addition was successful
	 */

	public synchronized boolean add(final InetAddress address) 
	{
		if (address == null) {
			return false;
		}
		
		boolean wasEmpty = first == null;
		ContainerAddress addressContainer = new ContainerAddress(address);
		ContainerAddress oldItem = cache.put(address, addressContainer);
		
		if (oldItem != null) {
			removeAddressFromList(oldItem);
		}
		
		// insert the new item at the head of the list.
		addressContainer.setNext(first);
		if (first != null) {
			first.setPrev(addressContainer);
		}
		first = addressContainer;
		
		if (wasEmpty) {
			// notify one of the threads potentially waiting on take()
			notify();
		}
		
		return true;
	}

	/**
	 * remove() method will return true if the address was successfully removed
	 * @param address
	 * @return
	 */
	public synchronized boolean remove(final InetAddress address) 
	{
		ContainerAddress removedAddress = cache.remove(address);
		if (removedAddress == null) {
			return false;
		}
		// remove the old item from the ordered list in O(1)
		removeAddressFromList(removedAddress);
		return true;
	}

	/**
	 * Remove the given address from the ordered list in O(1).
	 */
	private synchronized void removeAddressFromList(ContainerAddress address) 
	{
		assert address != null;
		
		ContainerAddress prev = address.getPrev();
		ContainerAddress next = address.getNext();
		if (prev != null) {
			prev.setNext(next);
		}
		if (next != null) {
			next.setPrev(prev);
		}
		if (prev == null) {
			first = next;
		}
		address = null;
	}

	/**
	 * The peek() method will return the most recently added element, 
	 * null if no element exists.
	 */
	public synchronized InetAddress peek() 
	{
		if (isValid(first)) {
			return first.getAddress();
		}
		if (first != null) {
			// first has expired => all elements have expired since first was the most recently added
			first = null;
		}
		return null;
 	}

	/**
	 * take() method retrieves and removes the most recently added element 
	 * from the cache and waits if necessary until an element becomes available.
	 */
	public synchronized InetAddress take() throws InterruptedException 
	{
		// wait for notification by add() or for interruption by a thread.
		while (peek() == null) {
			wait();
		}
		InetAddress address = peek();
		remove(address);
		return address;
	}
	
	/**
	 * Is the address entry valid (null, ttl check).
	 */
	private boolean isValid(final ContainerAddress address)
	{
		return address != null && 
			(address.getCreationTimeMs() + 
				TimeUnit.MILLISECONDS.convert(maxAge, ageUnit) > System.currentTimeMillis());
	}
	
	public static void main(final String args[]) 
		throws UnknownHostException, InterruptedException 
	{
		// Test normal add() take() peek()
		AddressCache cache = new AddressCache(10, TimeUnit.MINUTES);
		cache.add(InetAddress.getByName("134.23.23.65"));
		cache.add(InetAddress.getByName("134.23.23.64"));
		cache.add(InetAddress.getByName("134.23.23.61"));
		//  Test duplicates()
		cache.add(InetAddress.getByName("134.23.23.61"));
		System.out.println(cache.peek());
		System.out.println(cache.take());
		System.out.println(cache.take());
		
		System.out.println("---------");
		
		final AddressCache cache2 = new AddressCache(10, TimeUnit.SECONDS);
		cache2.add(InetAddress.getByName("134.23.23.65"));
		cache2.add(InetAddress.getByName("134.23.23.64"));
		// Let all expire.
		Thread.sleep(1000 * 12);
		System.out.println(cache2.peek());
		// Test empty()
		System.out.println("---------");
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("Sleeping in new thread. Will add new entry");
					Thread.sleep(1000 * 5);
					cache2.add(InetAddress.getByName("134.23.23.55"));
				} 
				catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		// Test take().
		t.start();
		System.out.println(cache2.take());
	}
	
}
