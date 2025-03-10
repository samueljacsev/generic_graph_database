package undirectedGenericGraph;

import java.util.Comparator;

/**
 * Egy éllista Edge=éleket tartalmaz. Minden él kezdő -és végpontja egyNode=csúcs.
 * A NodeToIdComparator az Edge -ből kimenő Node végpontjainak összehasonlítására szolgál, azok id-ja alapján. 
 * 
 * @author sam
 * @param <T> generikus
 */
class NodeToIdComparator<T> implements Comparator<Edge<T>> {
	
	/**
	 * Az összehasonlítást végző metódus.
	 */
	public int compare(Edge<T> e1, Edge<T> e2) {
		return Integer.compare(e1.getTo().getID(), e2.getTo().getID());
	}
}
