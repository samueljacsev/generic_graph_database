package undirectedGenericGraph;

import org.junit.Assert;
import org.junit.Test;

public class EdgeTest<T> {
	Node<T> nodeFrom;
	Node<T> nodeTo;
	int weigth = 5;
	Edge<T> edge;
	
	
	public EdgeTest() {
		nodeFrom = new Node<T>(0);
		nodeTo = new Node<T>(1);
		edge = new Edge<T>(nodeFrom, nodeTo, weigth);
	}

	@Test
	// a getTo(), getFrom() valamint getWeigth() metódusok tesztelése is egyben.
	public void testToString() {
		Assert.assertEquals("0-1:5", String.valueOf(edge.getFrom().getID())+"-"+String.valueOf(edge.getTo().getID())+":"+String.valueOf(edge.getWeight()));
	}

}