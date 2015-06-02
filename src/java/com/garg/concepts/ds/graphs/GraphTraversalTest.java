package com.garg.concepts.ds.graphs;

import com.garg.concepts.ds.graphs.DiGraph.Edge;
import com.garg.concepts.ds.graphs.DiGraph.Vertex;

/**
 * Test Dfs class.
 */
public class GraphTraversalTest
{
	public static void main(String[] args) 
	{
		DiGraph graph = new AdjacencyList();
		Vertex a = new Vertex('a');
		Vertex b = new Vertex('b');
		Vertex c = new Vertex('c');
		Vertex d = new Vertex('d');
		graph.addEdge(a, b, 1);
		graph.addEdge(a, d, 1);
		graph.addEdge(b, c, 1);
		graph.addEdge(b, d, 1);
		graph.addEdge(c, a, 1);
		graph.addEdge(d, c, 1);
		
		Vertex e = new Vertex('e');
		Vertex f = new Vertex('f');
		Vertex g = new Vertex('g');
		graph.addEdge(e, f, 1);
		graph.addEdge(e, g, 1);
		
		// print the graph.
		System.out.println("GRAPH\n" + graph);
		graph.reverse();
		// print the graph.
		System.out.println("GRAPH\n" + graph);
		
		// Some DFS operations.
		Dfs dfs = new Dfs(graph);
		dfs.dfs();
		System.out.println("TOPOLOGICAL ORDER");
		for (Vertex v : dfs.topologicalOrder) {
			System.out.println(dfs.vertices.get(v));
		}
		System.out.println();

		System.out.println("EDGE TYPES");
		for (Edge edge : graph.getEdges()) {
			System.out.println(edge + ": " + dfs.edgeType(edge));
		}
		System.out.println();
		
		System.out.println("HAS CYCLE:" + GraphUtil.hasCycle(graph));
		System.out.println();
		
		// Some Bfs operations.
		Bfs bfs = new Bfs(graph);
		
		System.out.println("BFS PATH");
		System.out.println("a->c: " +  bfs.path(a, c));
		System.out.println("b->g: " +  bfs.path(b, g));
	}
}