package com.garg.concepts.ds.graphs;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Directed weighted graph.
 * 
 * G = (V, E)
 */
public interface DiGraph
{
	public enum EdgeType{
		None,
		Tree,
		// Non-tree
		Forward,
		Back,
		Cross,
	}
	/**
	 * Vertex in a Digraph. 
	 */
	public static class Vertex
	{
		int vertexId;
		Set<Edge> outgoingEdges = new LinkedHashSet<Edge>();
		Set<Edge> incomingEdges = new LinkedHashSet<Edge>();
		
		public Vertex(char c) 
		{
			vertexId = c - 'a';
		}
		
		@Override
		public boolean equals(Object obj) 
		{
			if (obj instanceof Vertex) {
				return ((Vertex) obj).vertexId == vertexId;
			}
			return false;
		}
		
		@Override
		public int hashCode() 
		{
			return vertexId;
		}
		
		@Override
		public String toString() 
		{
			return String.valueOf((char)(vertexId + 'a'));
		}
	}
	
	/**
	 * A directed edge from n1 to n2 with weight w. 
	 */
	public static class Edge
	{
		/** The Node from which the edge starts. */
		Vertex start;
		/** The Node into which the edge ends. */
		Vertex end;
		int w = 1;
		
		@Override
		public boolean equals(Object obj) 
		{
			if (obj instanceof Edge) {
				Edge e = (Edge) obj;
				return start.equals(e.start) && end.equals(e.end);
			}
			return false;
		}
		
		@Override
		public int hashCode() 
		{
			return start.hashCode() * 31 + end.hashCode();
		}
		
		@Override
		public String toString() 
		{
			return String.format("%s-(%d)->%s", start, w, end);
		}
	}
	
	public int getVertexCount();
	
	public int getEdgeCount();
	
	public boolean edgeExists(Vertex v, Vertex u);
	
	public int inDegree(Vertex v);
	
	public int outDegree(Vertex v);
	
	public Set<Vertex> getVertices();
	
	public Set<Edge> getEdges();
	
	/**
	 * Add a directed edge b/w vertex v --(w)--> u, with weight w.
	 * Also adds the vertices to the graph if they're not already present. 
	 * Returns the edge. If the edge already existed returns null.
	 */
	public Edge addEdge(Vertex v, Vertex u, int w);

	/**
	 * Add the Vertex to this graph. 
	 * Returns null of the vertex was already present. 
	 */
	public Vertex addNode(Vertex v);
	
	public void reverse();
}