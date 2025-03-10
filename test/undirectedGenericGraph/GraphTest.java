package undirectedGenericGraph;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class GraphTest<T> {
	Graph<T> graph;
	Graph<T> graph2;
	Graph<T> graph3;

	public GraphTest() {
		// undirected graph only
		try {
			int[][] matrix = { { 0, 1, 1 }, { 1, 0, 1 }, { 1, 1, 0 } };
			int[][] matrix2 = { { 0, 4, 7, 2 }, { 4, 0, 3, 5 }, { 7, 3, 0, 9 }, { 2, 5, 9, 0 } };
			int[][] matrix3 = { { 0, 0, 0, 0 }, { 0, 0, 3, 5 }, { 0, 3, 0, 9 }, { 0, 5, 9, 0 } };
			graph = new Graph<T>(matrix);
			graph2 = new Graph<T>(matrix2);
			graph3 = new Graph<T>(matrix3);
		} catch (AssertionError e) {
			e.getStackTrace();
		}
	}

	@Test
	public void testGetNumberOfEdges() {
		Assert.assertTrue(graph.getNumberOfEdges() == 3);
		Assert.assertTrue(graph2.getNumberOfEdges() == 6);
		Assert.assertTrue(graph3.getNumberOfEdges() == 3);
	}

	@Test
	public void testIsConnected() {
		Assert.assertTrue(graph.isConnected());
		Assert.assertTrue(graph2.isConnected());
		Assert.assertFalse(graph3.isConnected());
	}

	@Test
	public void testIsComplete() {
		Assert.assertTrue(graph.isComplete());
		Assert.assertTrue(graph2.isComplete());
		Assert.assertFalse(graph3.isComplete());
	}

	@Test
	public void testBreadthFirstSearch() {
		// undirected graph only
		try {
			int[][] matrix4 = { { 0, 0, 2, 0, 8 }, { 0, 0, 3, 5, 0 }, { 2, 3, 0, 0, 0 }, { 0, 5, 0, 0, 1 },
					{ 8, 0, 0, 1, 0 } };
			Graph<T> graph4 = new Graph<T>(matrix4);
			graph4.sortEdgeList("weigth");
			ArrayList<Integer> weightPath = new ArrayList<>(Arrays.asList(0, 2, 4, 1, 3));
			Assert.assertEquals(graph4.BreadthFirstSearch(0), weightPath);

			graph4.sortEdgeList("id");
			ArrayList<Integer> idPath = new ArrayList<>(Arrays.asList(0, 2, 4, 1, 3));
			Assert.assertEquals(graph4.BreadthFirstSearch(0), idPath);
		} catch (AssertionError e) {
			e.getStackTrace();
		}
	}
	
	@Test
	public void testAddEdges() {
		String edgeListString = "[0-1:4, 0-2:7, 0-3:2]\n[1-0:4, 1-2:3, 1-3:5]\n[2-0:7, 2-1:3, 2-3:9]\n[3-0:2, 3-1:5, 3-2:9]\n";
		Assert.assertEquals(graph2.getEdgeList(), edgeListString);
	}
	

}
