package com.garg.nio;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class OffHeapMap<K, V> implements Map<K, V> 
{
	private final ByteBuffer heap;
	private Map<K, Integer> handleMap;
	
	public OffHeapMap(final int capacity) 
	{
		heap = ByteBuffer.allocateDirect(capacity);
	}
	
	@Override
	public int size() 
	{
		return handleMap.size();
	}

	@Override
	public boolean isEmpty() 
	{
		return handleMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) 
	{
		return handleMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) 
	{
		// TODO
		return false;
	}

	@Override
	public V get(Object key) 
	{
		return null;
	}

	@Override
	public V put(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

}
