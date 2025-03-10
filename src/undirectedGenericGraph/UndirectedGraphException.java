package undirectedGenericGraph;

/**
 * @author sam
 *
 */
class UndirectedGraphException extends Exception {
	/**
	 * Amennyiben az importált szomszédossági mátrix nem egy irányítatlan gráfot reprezentál, ez az exception dobható.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	protected UndirectedGraphException() {
		super("Invalid undirected graph.");
	}

}
