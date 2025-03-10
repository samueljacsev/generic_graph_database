package undirectedGenericGraph;

import java.io.Serializable;

/**
 * A csúcsokat élek kötik össze.
 * Minden esetben két különböző csúcsot között fut él.
 * Egyetlen saját értékük a weight=súly, amely valamilyen egész számot tartalmaz 1..9 -ig
 *
 * @author sam
 * @param <T> generikus osztály. A gráf létrehozásakor meghatározott paraméter.
 */
class Edge<T> implements Serializable {
	/**
	 * A Gráf osztály szerializálással elmenthető, így az Edge osztály is.
	 */
	private static final long serialVersionUID = 4965031824601693884L;
	private Node<T> from;
	private Node<T> to;
	private int weight;

	/**
	 * @param from az él kiindulási pontja. Node osztályba tartozó csúcs objektum.
	 * @param to az él végpontja. Node osztályba tartozó csúcs objektum.
	 * @param weight élsúly. Reprezentálha bátmilyen mérhető mennyiséget pl km, perc stb.
	 */
	Edge(Node<T> from, Node<T> to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	/**
	 * Az él kezdő-és végpontját, valamint az él súlyát adja vissza Stringben.
	 * @return String
	 */
	public String toString() {
		return from + "-" + to + ":" + weight;
	}

	/**
	 * Visszaadja az él kiindulási pontját.
	 * @return Node
	 */
	public Node<T> getFrom() {
		return from;
	}

	/**
	 * Visszaadja az él végpontját.
	 * @return Node
	 */
	public Node<T> getTo() {
		return to;
	}

	/**
	 * Visszaadja az él súlyát.
	 * @return int weight
	 */
	public int getWeight() {
		return weight;
	}
}
