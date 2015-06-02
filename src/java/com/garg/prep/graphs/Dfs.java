package com.garg.prep.graphs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.garg.prep.graphs.DiGraph.Edge;
import com.garg.prep.graphs.DiGraph.EdgeType;
import com.garg.prep.graphs.DiGraph.Vertex;

/**
 * To be used only once for a DFS traversal.
 */
public class Dfs
{
	/**
	 *  A light container of Vertex to aid DFS traversal. 
	 */
	static class DfsVertex
	{
		/** The actual vertex */
		private final Vertex v;
		
		// Meta-data.
		
		/** visited can be simulated in practice by checking preTime > 0 */
		private boolean visited = false;
		private int preTime     = 0;
		private int postTime    = 0;
		/** connected component label.*/
		private int cclabel     = 0;
		
		/** Aids in backtracking path from dest back to source in a dfs search */
		private DfsVertex parent;
		
		public DfsVertex(Vertex vertex) 
		{
			v = vertex; 
		}
		
		@Override
		public boolean equals(Object obj) 
		{
			if (obj instanceof DfsVertex) {
				return ((DfsVertex) obj).v.equals(v);
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
			return String.format("%s(%d) [%d, %d] parent:%s", v, cclabel,
					preTime, postTime, parentStr);
		}

		public void reset() 
		{
			visited = false;
			preTime = postTime = 0;
			cclabel = 0;
			parent = null;
		}
	}
	
	private int cclabel = 0;
	private int clock   = 0;
	Map<Vertex, DfsVertex> vertices = new HashMap<Vertex, DfsVertex>();
	List<Vertex> topologicalOrder = new LinkedList<Vertex>();
	
	public Dfs(DiGraph G)
	{
		for (Vertex v : G.getVertices()) {
			vertices.put(v, new DfsVertex(v));
		}
	}
	
	/**
	 * Returns the list containing the roots of the dfs forest.
	 */
	public List<DfsVertex> dfs()
	{
		// prepare for another dfs traversal.
		// reset cclabel and clock.
		// note that this 'cclabel' algo works ONLY FOR undirected graphs.
		// we are showing here just for representation.
		cclabel = 0;  clock = 0;
		topologicalOrder = new LinkedList<Vertex>();
		for (DfsVertex vertex : vertices.values()) {
			vertex.reset();
		}
		
		List<DfsVertex> dfsForest = new LinkedList<DfsVertex>(); 
		for (DfsVertex vertex : vertices.values()) {
			if (! vertex.visited) {
				// new label for each dfs tree.
				cclabel++;
				dfsForest.add(vertex);
				exploreIterative(vertex);
			}
		}
		return dfsForest;
	}

	/** Explore the vertex s in a depth first fashion. */
	private void explore(DfsVertex s)
	{
		previsit(s);
		visit(s);
		for (Edge e : s.v.outgoingEdges) {
			DfsVertex u = vertices.get(e.end);
			// If one of the adj vertices is 'being explored'
			// => u is an ancestor of s
			// => back edge exists u->s
			if (u.preTime > 0 && u.postTime <= 0) {
				// Cycle detected!
			}
			if (! u.visited) {
				u.parent = s;
				explore(u);
			}
		}
		postvisit(s);
	}
	
	/** Explore the vertex s in a depth first fashion using stack. */
	private void exploreIterative(DfsVertex s)
	{
		Stack<DfsVertex> stack = new Stack<DfsVertex>();
		stack.push(s);
		
		while (!stack.isEmpty()) {
			DfsVertex vertex = stack.pop();
			previsit(vertex);
			visit(vertex);
			for (Edge e : vertex.v.outgoingEdges) {
				DfsVertex u = vertices.get(e.end);
				if (! u.visited) {
					u.parent  = vertex;
					stack.push(u);
				}
			}
			postvisit(vertex);
		}
	}

	private void previsit(DfsVertex s) 
	{
		clock++;
		s.preTime = clock;
	}
	
	private void visit(DfsVertex s) 
	{
		s.visited = true;	
		s.cclabel = cclabel;
	}
	
	private void postvisit(DfsVertex s) 
	{
		clock++;
		s.postTime = clock;
		
		// Insert the vertex to the beginning of the list.
		topologicalOrder.add(0, s.v);
	}
	
	/**
	 * Return edge type of e : {u,v}
	 * 
	 * <ul>Types of Edges
	 * <li>Tree edges      : part of the DFS forest.
	 * <li>Non-tree edges  : all other edges.
	 * </ul>
	 * <ul>Types of non-tree edges
	 * <li>Forward edges: node -> non-child descendant in DFS tree.
	 * <li>Back edge       : node -> ancestor in DFS tree.
	 * <li>Cross edge      : node -> non-ancestor non-descendant 
     *                   (a node which has been completely explored).
     * </ul>
	 */
	public EdgeType edgeType(Edge e) 
	{
		if (clock <= 0) {
			throw new AssertionError("dfs() has not been invoked");
		}
		
		DfsVertex parent = vertices.get(e.end).parent;
		if (parent != null && parent.v == e.start) {
			return EdgeType.Tree;
		}

		// Ancestor descendant relationship as well as edge types can be
		// inferred
		// from pre, post nums
		DfsVertex uU = vertices.get(e.start);
		DfsVertex vV = vertices.get(e.end);

		// [[]]
		if (uU.preTime < vV.preTime && vV.postTime < uU.postTime) {
			return EdgeType.Forward;
		}

		// [[]]
		if (vV.preTime < uU.preTime && uU.postTime < vV.postTime) {
			return EdgeType.Back;
		}

		// [][]
		if (uU.preTime > vV.postTime) {
			return EdgeType.Cross;
		}
		
		return EdgeType.None;
	}
}