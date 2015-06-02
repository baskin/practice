package com.garg.prep.graphs;

import java.util.HashSet;
import java.util.Set;

/**
 * G = (V, E)
 */
public class AdjacencyList implements DiGraph
{
	private Set<Vertex> nodes = new HashSet<Vertex>();
	private int edgeCount;
	
	public AdjacencyList()
	{
	}

	@Override
	public int getVertexCount()
	{
		return nodes.size();
	}

	@Override
	public int getEdgeCount()
	{
		return edgeCount;
	}

	@Override
	public boolean edgeExists(Vertex v, Vertex u)
	{
		if (v != null) {
			return v.outgoingEdges.contains(u);
		}
		return false;
	}

	@Override
	public int outDegree(Vertex v)
	{
		return v.outgoingEdges.size();
	}

	@Override
	public int inDegree(Vertex v) 
	{
		return v.incomingEdges.size();
	}

	@Override
	public Edge addEdge(Vertex v, Vertex u, int w) 
	{
		Edge e = null;
		if (! v.outgoingEdges.contains(u)) {
			e  = new Edge();
			nodes.add(v); nodes.add(u);
			e.start = v; e.end = u; e.w  = w;
			v.outgoingEdges.add(e);
			u.incomingEdges.add(e);
			edgeCount++;
		}
		return e;
	}

	@Override
	public Vertex addNode(Vertex v) 
	{
		if (! nodes.contains(v)) {
			nodes.add(v);
			return v;
		}
		return null;
	}

	@Override
	public Set<Vertex> getVertices() 
	{
		return nodes;
	}
	
	@Override
	public Set<Edge> getEdges() 
	{
		Set<Edge> edges = new HashSet<DiGraph.Edge>();
		for (Vertex v : nodes) {
			edges.addAll(v.outgoingEdges);
		}
		return edges;
	}
	
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		for (Vertex v : nodes) {
			sb.append(v);
			sb.append(" { out: ").append(v.outgoingEdges);
			sb.append(" in : ").append(v.incomingEdges).append(" }\n");
		}
		return sb.toString();
	}
	
	@Override
	public void reverse()
	{
		for (Vertex v : nodes) {
			// Swap incoming and outgoing edge list references.
			Set<Edge> incoming = v.incomingEdges;
			v.incomingEdges = v.outgoingEdges;
			v.outgoingEdges = incoming;
			
			for (Edge e : v.outgoingEdges) {
				Vertex tmp = e.start;
				e.start = e.end;
				e.end = tmp;
			}
		}
	}
}