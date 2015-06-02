package com.garg.prep.graphs;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.garg.prep.graphs.DiGraph.Edge;
import com.garg.prep.graphs.DiGraph.Vertex;

/**
 * To be used only once for a DFS traversal.
 */
public class Bfs
{
	/**
	 *  A light container of Vertex to aid DFS traversal. 
	 */
	private static class BfsVertex
	{
		/** The actual vertex */
		private final Vertex v;
		
		// Meta-data.
		/** visited can be simulated in practice by checking preTime > 0 */
		private boolean visited = false;
		/** Aids in backtracking path from dest back to source in a dfs search */
		private BfsVertex parent;
		
		public BfsVertex(Vertex vertex) 
		{
			v = vertex; 
		}
		
		@Override
		public boolean equals(Object obj) 
		{
			if (obj instanceof BfsVertex) {
				return ((BfsVertex) obj).v.equals(v);
			}
			return false;
		}
		
		@Override
		public int hashCode() 
		{
			return v.hashCode();
		}
		
		@Override
		public String toString() 
		{
			String parentStr = parent == null ? "<none>" : parent.v.toString(); 
			return String.format("%s parent:%s", v, parentStr);
		}
		
		public void reset() 
		{
			visited = false;
			parent = null;
		}
	}
	
	private Map<Vertex, BfsVertex> vertices = new HashMap<Vertex, BfsVertex>();
	
	public Bfs(DiGraph G)
	{
		for (Vertex v : G.getVertices()) {
			vertices.put(v, new BfsVertex(v));
		}
	}
	
	/**
	 * Initiate a bfs starting with vertex s.
	 */
	public void bfs(Vertex ss)
	{
		for (BfsVertex vertex : vertices.values()) {
			vertex.reset();
		}
		
		BfsVertex s = vertices.get(ss);
		
		Queue<BfsVertex> queue = new LinkedList<BfsVertex>();
		queue.offer(s);
		
		while (! queue.isEmpty()) {
			BfsVertex v = queue.poll();
			v.visited = true;
			for (Edge e : v.v.outgoingEdges) {
				BfsVertex u = vertices.get(e.end);
				if (!u.visited) {
					queue.offer(u);
					u.parent = v; 
				}
			}
		}
	}
	
	/**
	 * Return path (in the form of a list from the source to dest).
	 * Null is returned if no path exists.
	 * Note that this path is the shortest path if the weight of each edge
	 * is unity.
	 */
	public List<Vertex> path(Vertex source, Vertex dest)
	{
		bfs(source);
		BfsVertex d = vertices.get(dest);
		if (d.parent == null) {
			// d lies in another connected component and no path exists.
			return Collections.emptyList();
		}
		
		List<Vertex> path = new LinkedList<DiGraph.Vertex>();
		path.add(dest);
		while (d.parent != null) {
			d = d.parent;
			path.add(0, d.v);
		}
		return path;
	}
}