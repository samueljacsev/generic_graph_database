package undirectedGenericGraph;

import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NodeTest {
	Node<String> node;
	ArrayList<String> testList;
	
	@Before
	public void setUp(){
		node = new Node<String>(0);
		testList = new ArrayList<>();
		String data = "cool";
		testList.add(data);
		node.addData(data);
	}
	
	@Test
	public void testAddData() {
		Assert.assertEquals(node.getData(), testList);
	}

	@Test
	// egymást teszteli a két metódus. A @Before -ban van az addData().
	public void testGetData() {
		Assert.assertEquals(node.getData(), testList);
	}
	
	@Test
	public void testRemoveData() {
		String data = "cool";
		node.removeData(data);
		Assert.assertNotEquals(node.getData(), testList);
	}
	
	@Test
	public void testGetVisited() {
		// visited is false by default
		Assert.assertTrue(node.getVisited() == false);
	}
	
	@Test
	public void testSetVisited() {
		node.setVisited(true);
		Assert.assertTrue(node.getVisited() == true);
	}
	
}
