package undirectedGenericGraph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Egy gráf csúcsait meghatározó objektum.
 * Egy csúcs létrejöttekor az alábbiakat inicializálódnak: edgeList: a csúcsból
 * elérhető többi csúcsba futó Edge=élek listűja; dataBank: generikus tároló.
 * Objektumok tárolására alkalmas; visited: különböző algoritmusokhoz
 * felhasználhato Boolean érték. A Node osztályt alapvetően szomszédossági
 * mátrixból kinyert adatokkal alkalmazzuk. A szomszédossági mátrix egyes sorait
 * a nodeID=id azonosítja 0...(n-1)-ig.
 * 
 * @author sam
 *
 * @param <T> Gráf létrehozásakor a szomszédossági mátrixban megadott pontokat
 *            Node=csúcsok reprezentálják.
 */
class Node<T> implements Serializable {
	/**
	 * A Gráf osztály szerializálható, így a Node osztály is.
	 */
	private static final long serialVersionUID = -5302772571222708220L;
	private ArrayList<T> dataBank;
	private ArrayList<Edge<T>> edgeList = new ArrayList<>();
	private boolean visited;
	private int id;

	/**
	 * @param id csúcs azonosító. A szomszédossági mátrix sorának 0-tól számolt
	 *           értéke.
	 */
	Node(int id) {
		this.id = id;
		visited = false;
		dataBank = new ArrayList<T>();
	}

	/**
	 * Az id String értékével tér vissza
	 * 
	 * @return String id.
	 */
	public String toString() {
		return String.valueOf(this.id);
	}

	/**
	 * A csúcs id-jával tér vissza
	 * 
	 * @return int id.
	 */
	public int getID() {
		return id;
	}

	/**
	 * A dataBank-el tér vissza
	 * 
	 * @return ArrayList, adatbank
	 */
	protected ArrayList<T> getData() {
		return dataBank;
	}

	/**
	 * A megfelelő paraméterú adatot helyezi el a csúcs adattárolójában
	 * 
	 * @param d = data.
	 */
	protected void addData(T d) {
		dataBank.add(d);
	}

	/**
	 * A paraméterként megadott adatot eltávolítja az adattárolóból. A Data osztály
	 * removeData() metódusával működik.
	 * 
	 * @param data a törölni kívánt adat.
	 */
	protected void removeData(T data) {
		dataBank.remove(data);
	}

	/**
	 * Az adattároló összes adatának kiírására alkalmas metódus.
	 */
	protected void listData() {
		for (int d = 0; d < dataBank.size(); d++) {
			System.out.println(id + ": " + dataBank.get(d));
		}
	}

	/**
	 * A csúcs éllistáját adja vissza ArrayList formátumban.
	 * 
	 * @return ArrayList&lt;Edge&lt;T&gt;&gt; edgeList
	 */
	protected ArrayList<Edge<T>> getEdges() {
		return edgeList;
	}

	/**
	 * Az éllistához élet hozzáadó metódus
	 * 
	 * @param edge él
	 */
	protected void addEdge(Edge<T> edge) {
		edgeList.add(edge);
	}

	/**
	 * A visited boolean értékét a paraméter értékére állítja
	 * 
	 * @param state, boolean bejárva állapot
	 */
	protected void setVisited(boolean state) {
		visited = state;
	}

	/**
	 * A visited boolean értékét adja vissza.
	 * 
	 * @return boolean visited
	 */
	protected boolean getVisited() {
		return visited;
	}

	/**
	 * Az éllista Edge objektumait rendezi növekvő sorrendbe az él végpontja (Node)
	 * id-ja alapján. Az éllistát felhasználva futtatott algoritmusok lefutási
	 * sorrendjét befolyásolhatja.
	 */
	protected void sortEdgeListByToId() {
		Collections.sort(edgeList, new NodeToIdComparator<T>());
	}

	/**
	 * Az éllista Edge objektumait rendezi a súlyozás alapján. Az éllistát
	 * felhasználva futtatott algoritmusok lefutási sorrendjét befolyásolhatja.
	 */
	protected void sortEdgeListByWeight() {
		Collections.sort(edgeList, new EdgeWeightComparator<T>());
	}

}
