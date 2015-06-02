package com.garg.concepts.ds.graphs;

import java.util.List;

import com.garg.concepts.ds.graphs.DiGraph.Edge;
import com.garg.concepts.ds.graphs.DiGraph.EdgeType;
import com.garg.concepts.ds.graphs.DiGraph.Vertex;

/**
 * Graph utility methods.
 */
public class GraphUtil
{
	/**
	 * A di-graph is cyclic iff its dfs reveals a back edge.
	 */
	public static boolean hasCycle(DiGraph graph)
	{
		Dfs dfs = new Dfs(graph);
		dfs.dfs();
		for (Edge e : graph.getEdges()) {
			if (dfs.edgeType(e) == EdgeType.Back) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Linearized form of the graph. Graph vertices sorted by decreasing order
	 * of their postvisit times. In such an order, each edge goes from 
	 * list.get(i) -> list.get(j), where j > i. 
	 */
	public static List<Vertex> topologicalSort(DiGraph graph)
	{
		Dfs dfs = new Dfs(graph);
		dfs.dfs();
		return dfs.topologicalOrder;
	}
	
	public static void main(String[] args) 
	{
	}
}