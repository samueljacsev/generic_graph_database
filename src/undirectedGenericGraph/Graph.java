package undirectedGenericGraph;

import java.io.BufferedReader;

import java.util.LinkedList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Queue;
import java.util.Scanner;

/**
 * Gráf osztály, csúcsokkal és azokat összekötő élekkel. Az alábbi osztályokat
 * használja: Node, Edge. Egy gráfban a csúcsok száma > 0. A csúcsokat Edge=él
 * objektumok kötik össze. Az élek kiindulási és végpontjai Node objektumok. Az
 * Edge ezenfelül tartalmaz élsúlyt=weight. Minden csúcshoz tartozik egy
 * dataBank generikus ArrayList tároló. A dataBank adattárolóban elhelyezhetünk
 * és törölhetünk objektumokat. A Gráf osztály az alábbi tárolókkal renedlkezik:
 * int nodes[]: Node objektumokat tárol; int AdjacencyMatrix[][]: a
 * szomszédissági mátrixszot tárolja; int numberOfNodes: csúcsok száma.
 * 
 * @author sam
 * 
 * @param <T> a Gráf létrehozásakor meghatározott osztály.
 */
public class Graph<T> implements Serializable {

	/**
	 * A teljes gráf tartalma szerializálással elmenthető.
	 */
	private static final long serialVersionUID = -6317631815600480864L;
	/**
	 * szomszédossági mátrix
	 */
	private int adjacencyMatrix[][];

	/**
	 * csúcsokat tartalmazó tömb
	 */
	private Node<T> nodes[];
	/**
	 * csúcsok száma
	 */
	private int numberOfNodes;

	/**
	 * A felhasználo adhatja meg a kívánt irányítatlan gráf szomszédossági
	 * mátrixszát.
	 * 
	 * @param input Scanner
	 * @throws UndirectedGraphException nem iránított gráf
	 */
	public Graph(Scanner input) throws UndirectedGraphException {
		adjacencyMatrixInput(input);
	}

	/**
	 * .txt fájlból hozhatunk létre Graph -ot. A fájl tartalmazza a létrehozni
	 * kívánt gráf szomszédossági mátrixszát.
	 * 
	 * @param filename a beolvasni kívánt .txt kiterjesztésű fájlnak a neve.
	 * @throws IllegalArgumentException hibás karakter a szomszédossági mátrixban pl
	 *                                  betűket
	 * @throws InputMismatchException   hibás karakter a szomszédossági mátrixban
	 * @throws IOException              fájl nem található a megadott néven
	 * @throws UndirectedGraphException a megadott szomszédossági mátrix nem egy
	 *                                  irányítatlan gráfot reprezentál
	 */
	public Graph(String filename)
			throws IllegalArgumentException, InputMismatchException, IOException, UndirectedGraphException {
		adjmxFileImport(filename);
	}

	/**
	 * Csak teszteléshez használható
	 * 
	 * @param adjMx 2D int tömb
	 * @throws AssertionError a megadott szomszédossági mátrix nem egy irányítatlan
	 *                        gráfot reprezentál
	 */
	// for TEST ONLY
	protected Graph(int[][] adjMx) throws AssertionError {
		adjacencyMatrix = adjMx;
		numberOfNodes = adjMx.length;
		if (undirectedCheck(adjMx)) {
			setNodes();
			addEdges();
		} else {
			throw new AssertionError();
		}
	}

	/**
	 * A szomszédossági mátrix print-el kiíható alakját adja vissza.
	 * 
	 * @return String.
	 */
	public String adjacencyMatrixToString() {
		String stringMatrix = "";
		for (int row = 0; row < numberOfNodes; row++) {
			if (row > 0) {
				// new line after each row
				stringMatrix += System.getProperty("line.separator");
			}
			for (int column = 0; column < numberOfNodes; column++) {
				// add all elements to stringMatrix
				stringMatrix += (adjacencyMatrix[row][column] + " ");
			}
		}
		return stringMatrix;
	}

	/**
	 * Visszaadja a csúcsok számát
	 * 
	 * @return int numberOfNodes
	 */
	public int getNumberOfNodes() {
		return numberOfNodes;
	}

	/**
	 * Visszatér a szomszrédossági mátrixszal.
	 * 
	 * @return int[][] adjacencyMatrix
	 */
	protected int[][] getAdjacencyMatrix() {
		return adjacencyMatrix;
	}

	/**
	 * A mátrix ellenőrzései lefutottak. A nodes tömb méretének beállítása. Node
	 * objektumok létrehozása a mátrix sorait adva ID-nak
	 */



	@SuppressWarnings("unchecked")
	private void setNodes() {
		nodes = (Node<T>[]) new Node[numberOfNodes];
		// row is the ID for each Node from the adj matrix
		for (int row = 0; row < numberOfNodes; row++) {
			nodes[row] = new Node<T>(row);
		}
	}

	/**
	 * Az éllista printelhető és rendezett, olvasható változata.
	 * 
	 * @return String.
	 */
	public String getEdgeList() {
		String edgeList = "";
		// get nodeList from all Nodes
		for (Node<T> node : nodes) {
			edgeList += node.getEdges();
			edgeList += System.getProperty("line.separator");
		}
		return edgeList;
	}

	/**
	 * A Node addData() konstruktorával működik.
	 * 
	 * @param nodeID a kivilálasztott csúcs id-ja
	 * @param data   a csúcsban menteni kívánt adat.
	 */
	public void addData(int nodeID, T data) {
		try {
			// tries to add data to the specified Node
			nodes[nodeID].addData(data);
		} catch (ArrayIndexOutOfBoundsException e) {
			// no such NodeID
			System.out.println(nodeID + " :invalid nodeID");
		}
	}

	/**
	 * A Node removeData() konstruktorával működik.
	 * 
	 * @param nodeID a kivilálasztott csúcs id-ja
	 * @param rmData a csúcsból törölni kívánt adat.
	 */
	public void removeDate(int nodeID, T rmData) {
		nodes[nodeID].removeData(rmData);
	}

	/**
	 * Kilistázza az összes csúcs összes adatát. A Node listData() konstruktorával
	 * működik.
	 */
	public void listDataBank() {
		for (Node<T> node : nodes) {
			node.listData();
		}
	}

	/**
	 * Az élek számának meghatározására használható. Működése: a szomszédossági
	 * mátrix nem 0 értékeinek számosságának felével tér vissza.
	 * 
	 * @return int élek száma
	 */
	public int getNumberOfEdges() {
		int noe = 0; // number of edges
		for (int row = 0; row < numberOfNodes; row++) {
			for (int column = 0; column < numberOfNodes; column++) {
				if (adjacencyMatrix[row][column] != 0) {
					noe += 1;
				}
			}
		}
		return noe / 2;
	}

	/**
	 * Meghatározhatjuk hogy minden csúcs elérhető-e bármely másik csúcsból. Ehhez
	 * egy feszítőfát képez szélességi bejárással. Amennyiben a szélességi bejárás
	 * visszatérési értéke ugyanannyi élből áll, mint ahány csúcs van, úgy az
	 * állítás igaz; különben hamis.
	 * 
	 * @return boolean
	 */
	public boolean isConnected() {
		// BFS's path
		ArrayList<Integer> path;
		// nodes[0] is always valid
		path = BreadthFirstSearch(0);
		// if all nodes had been discovered
		if (path.size() == numberOfNodes) {
			return true;
		}
		return false;
	}

	/**
	 * Meghatározható, hogy teljes gráfunk van e. Azaz hogy minden másik csúcsba
	 * közvetlen él fut. Az élek számát getNumberOfEdges() metódussal határozza meg.
	 * Teljes gráfot feltételezve alkalmazza: (n*(n-1))/2 képletet ahol n a csúcsok
	 * száma. Amennyiben az élek és a feltételezett élek száma egyezik, tru-val tér
	 * vissza; ellenkező esetben false
	 * 
	 * @return boolean
	 */
	public boolean isComplete() {
		// if (n*(n-1))/2 equals the actual number of edges
		if ((numberOfNodes * (numberOfNodes - 1) / 2) == getNumberOfEdges()) {
			return true;
		}
		return false;
	}

	/**
	 * Szélességi bejárás, feszítőfát képez. Ebben a metódusban csak a
	 * BFSrecursive() tárolóinak inicializálása történik.
	 * 
	 * @param startNodeId a BFS bejárás kezdőcsúcs id-ja
	 * @return path - a bejárt csúcsok sorrendje.
	 */
	public ArrayList<Integer> BreadthFirstSearch(int startNodeId) {
		ArrayList<Integer> path = new ArrayList<>();
		// nextStart contains nodeIDs in the order which defines the BFSrecursive next
		// starting point
		Queue<Integer> nextStart = new LinkedList<>();
		// first start is the one given by the user
		nextStart.add(startNodeId);
		BFSrecursive(nextStart, path);
		// after BFS recursive discovered all nodes,
		// reset their visited status to false
		resetVisited();
		return path;
	}

	/**
	 * Csak a BreadthFirstSearch hívja meg.
	 * 
	 * @param nextStart queue
	 * @param path      a bejárt csúcsok id-ját tartalmazó lista. Kezdetben üres.
	 */
	private void BFSrecursive(Queue<Integer> nextStart, ArrayList<Integer> path) {
		// starting Node
		int nodeId = nextStart.poll();
		// if Node is already visited, return.
		if (nodes[nodeId].getVisited() == true) {
			return;
		} else {
			// else, set visited status to 'true'
			nodes[nodeId].setVisited(true);
			// add nodeID to the list which contains the path of BFS.
			path.add(nodeId);
			// get all Edges from the current Node
			for (Edge<T> edge : nodes[nodeId].getEdges()) {
				// get the destination of each Edges; (another Node)
				// if the destination is still not visited
				if (edge.getTo().getVisited() == false) {
					// add it to the nextSart queue
					nextStart.add(edge.getTo().getID());
				}
			}
			// if the nextStart queue isn't empty
			if (!nextStart.isEmpty()) {
				// call BFSrecursive() again with the same references
				BFSrecursive(nextStart, path);
			}
		}
	}

	/**
	 * Minden csúcs visited státuszát false-ra állítja. A visited státusz a csúcs
	 * létrejöttétekor false értékkel inicializálódik. Minden metódus után, ami ezt
	 * az értéket felhasználja futása során, ajánlott megívni ezt a metódust.
	 */
	private void resetVisited() {
		// Nodes' visited status are false by default;
		// reset them after each method which changes this value
		for (Node<T> node : nodes) {
			node.setVisited(false);
		}
	}

	/**
	 * A csúcsok éllistáinak rendezésére alkalmas Kétféle módon lehet rendezni:
	 * Élsúly vagy a kimenő él végpontának id-ja alapján.
	 * 
	 * @param by {weight, id}
	 */
	public void sortEdgeList(String by) {
		if (by.equals("weight")) {
			for (Node<T> node : nodes) {
				node.sortEdgeListByWeight();
			}
		} else if (by.equals("id")) {
			for (Node<T> node : nodes) {
				node.sortEdgeListByToId();
			}
		}
	}

	/**
	 * Amikor a konstruktort csak Scannerrel hívjuk meg, ez a metódus rendezi a
	 * bemenetet. Első körben meghív 2 függvényt ami bekéri a felhasználótól a
	 * csúcsok számát és a szomszédossági mátrixszot. Ezek után ellenőrzi, hogy
	 * valóban egy irányatatlan gráfot adott e meg a felhasználó. Ha minden stimmel,
	 * beállítja a szomszédossági mátrixszot, a csúcsok számát, létrrehozza a csúcs
	 * és él objektumokat.
	 * 
	 * @param input Scanner
	 * @throws InputMismatchException   érvénytelen bemenet.
	 * @throws UndirectedGraphException érvénytelen irányítatlan gráf.
	 */
	private void adjacencyMatrixInput(Scanner input) throws InputMismatchException, UndirectedGraphException {
		try {
			// user sets the number of nodes
			int n = numberOfNodesInput(input);
			try {
				// user sets the adjacency matrix
				int[][] matrix = matrixInput(n, input);
				// undirected check on the matrix given by the user
				if (undirectedCheck(matrix)) {
					// set adjacency matrix, number of nodes, Node and Edge objects
					adjacencyMatrix = matrix;
					numberOfNodes = n;
					setNodes();
					addEdges();
					System.out.println("Successful input.");
				} else {
					throw new UndirectedGraphException();
				}
			} catch (InputMismatchException e) {
				throw new InputMismatchException();
			}
		} catch (InputMismatchException e) {
			System.out.println("Only integer is accepted.");
			throw new InputMismatchException();
		}
	}

	/**
	 * Ebben a metódusban állítja be a felhsználó a csúcsok számát.
	 * 
	 * @param input Scanner
	 * @return n- number of nodes
	 * @throws InputMismatchException ha a felhasználó álltal megadott karakter nem
	 *                                szám
	 */
	private int numberOfNodesInput(Scanner input) throws InputMismatchException {
		System.out.println("Set the Number of Nodes");
		int n;
		// until user gives an integer > 0.
		while (true) {
			n = input.nextInt();
			if (n > 0) {
				break;
			}
			System.out.println("Invalid input. Only integer > 0 is accepted");
		}
		return n;
	}

	/**
	 * Ebben a metódusban állítja be a felhasználó a kívánt szomomszédossági
	 * mátrixszot.
	 * 
	 * @param n     csúcsok száma
	 * @param input Scanner
	 * @return int[][] matrix
	 */
	private int[][] matrixInput(int n, Scanner input) {
		int[][] matrix = new int[n][n];
		for (int row = 0; row < n; row++) {
			System.out.println("Type the numbers of the " + row + ". row");
			for (int c = 0; c < row; c++) {
				// graph must be undirected -> already known values
				System.out.println(matrix[c][row]);
			}
			for (int column = row; column < n; column++) {
				int weight = input.nextInt();
				// program works with integer 0 <= weights < 10
				if (weight < 0) {
					weight = 0;
				} else if (weight > 9) {
					weight = 9;
				}
				// undirected
				matrix[row][column] = weight;
				matrix[column][row] = weight;
			}
		}
		return matrix;
	}

	/**
	 * Ellenőrzi, hogy a paraméterül kapott mátrix valóban irányítatlan e. Konzolos
	 * beolvasás esetén elvileg csak az egyik átlókban maradhatott hiiba, mert az
	 * előre ismert értékeket automatiokusan kitölti a beolvasás metódusa.
	 * 
	 * @param matrix
	 * @return boolean
	 */
	private boolean undirectedCheck(int[][] matrix) {
		for (int row = 0; row < matrix.length; row++) {
			for (int column = 0; column < matrix.length; column++) {
				// should not run edge into itself
				if (row == column && matrix[row][column] != 0) {
					return false;
				}
				if (matrix[row][column] != matrix[column][row]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * A szomszédosági mátrix alapján elkészíti az éllistát minden egyes csúcshoz.
	 */
	private void addEdges() {
		for (int row = 0; row < numberOfNodes; row++) {
			for (int column = 0; column < numberOfNodes; column++) {
				// if an edge is needed between two Nodes
				if (adjacencyMatrix[row][column] != 0) {
					nodes[row].addEdge(new Edge<T>(nodes[row], nodes[column], adjacencyMatrix[row][column]));
				}
			}
		}
	}

	/**
	 * A szomszédossági mátrix betöltéséért felelős metódus
	 * 
	 * 
	 * @param filename String, mátrixot tartalmazó .txt fájl neve.
	 * @throws IOException              ha a fájl nem találálható
	 * @throws IllegalArgumentException ha nem megfelelő karaktereket tartalmaz a
	 *                                  mátrix
	 * @throws UndirectedGraphException ha a mátrix nem irányítatlan
	 */
	public void adjmxFileImport(String filename)
			throws IOException, IllegalArgumentException, UndirectedGraphException {
		try {
			String rawImport = readFileToString(filename);
			try {
				int[][] matrixImport = stringToMatrix(rawImport);
				if (undirectedCheck(matrixImport)) {
					// after passes all the required checks
					adjacencyMatrix = matrixImport;
					numberOfNodes = matrixImport.length;
					setNodes();
					addEdges();
					System.out.println("Graph imported successfully.");
				} else {
					System.out.println("Invalid undirected graph.");
					throw new UndirectedGraphException();
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException();
			}
		} catch (IOException e) {
			System.out.println("Sorry, '" + filename + ".txt' does not found.");
			throw new IOException();
		}
	}

	/**
	 * Az adjmxFileImport() hívja meg. A paraméterül kapott fájlt beolvassa egy
	 * Stringbe.
	 * 
	 * @param filename String, mátrixot tartalmazó .txt fájl neve.
	 * @return
	 * @throws IOException
	 */
	private String readFileToString(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename + ".txt"));
		String line;
		StringBuilder sb = new StringBuilder();

		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				// add new line
				sb.append(System.getProperty("line.separator"));
			}
			return sb.toString();
		} finally {
			reader.close();
		}
	}

	/**
	 * @param input String, a kapott mátrix
	 * @return int[][] mátrix
	 * @throws IllegalArgumentException ha a sorok nem egyenlő hosszúak
	 */
	private int[][] stringToMatrix(String input) throws IllegalArgumentException {
		String lines[] = input.split("\n");
		if (mxConsistencyCheck(lines)) {
			int[][] matrix = new int[lines.length][lines.length];
			for (int row = 0; row < lines.length; row++) {
				String[] line = lines[row].split(" ");
				int[] tempRow = new int[line.length];
				for (int element = 0; element < line.length; element++) {
					int weight = Integer.valueOf(line[element]);
					if (weight < 0) {
						weight = 0;
					} else if (weight > 9) {
						weight = 9;
					}
					tempRow[element] = Integer.valueOf(line[element]);
				}
				matrix[row] = tempRow;
			}
			return matrix;

		} else {
			System.out.println("The input is inconsistent.");
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Ellenőrzi, hogy a mátrix valóban n*n- es e.
	 * 
	 * @param lines mátrix sorai stringben
	 * @return true, ha n*n - es.
	 */
	public boolean mxConsistencyCheck(String[] lines) {
		int firstLineLength = lines[0].split(" ").length;
		// if one row contains different number of elements than the first row does
		for (int row = 1; row < lines.length; row++) {
			if (lines[row].split(" ").length != firstLineLength) {
				return false;
			}
		}
		// if the matrix has equivalent number of rows to number of elements in its
		// first row
		if (lines.length == firstLineLength) {
			return true;
		}
		return false;
	}

	/**
	 * Szomszédossági mátrix .txt fájlba írására alkalmas metódus. Végig iterál a
	 * mátrix elemein, és soronként fájlbaírja azokat.
	 * 
	 * @param filename String, a fájl neve
	 */
	public void adjmxSave(String filename) {
		try {
			FileWriter writer = new FileWriter(filename + ".txt");
			for (int row = 0; row < numberOfNodes; row++) {
				if (row > 0) {
					// new line before each row, except the first.
					writer.write(System.getProperty("line.separator"));
				}
				for (int column = 0; column < numberOfNodes; column++) {
					writer.write(adjacencyMatrix[row][column] + " ");
				}
			}
			writer.write(System.getProperty("line.separator"));
			writer.close();
			System.out.println(filename + ".txt saved successfully");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
