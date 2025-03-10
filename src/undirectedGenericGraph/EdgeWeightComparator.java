package undirectedGenericGraph;

import java.util.Comparator;

/**
 * Éleket hasonlít össze élsúly alapján.
 * 
 * @author sam
 * @param <T> generikus
 */
class EdgeWeightComparator<T> implements Comparator<Edge<T>> {
	
	/**
	 *Az élek összehasonlítását végző metódus
	 */
	public int compare(Edge<T> e1, Edge<T> e2) {
		return Integer.compare(e1.getWeight(), e2.getWeight());
	}
}
