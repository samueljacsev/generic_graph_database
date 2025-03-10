package undirectedGenericGraph;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A Graph osztályt vezérlő kezelőfelületet biztosítja
 * 
 * @author sam
 */
public class Main {

	/**
	 * A parancsok szétválasztását és a megdfeleelő metódusok hívását végzi. A Graph
	 * osztály összes publikus metódusa innen érhető el.
	 * 
	 * @throws Exception több féle kivételt dob, de ezek mimd kezelve vannak
	 *                   lejjebbi rétegeken. Ezen a ponton már nem lényeges
	 *                   specifikálni.
	 */
	private static void mainMenu() throws InputMismatchException, UndirectedGraphException, IllegalArgumentException,
			IOException, ClassNotFoundException {
		Scanner input = new Scanner(System.in);
		try {
			Graph<String> graph = userCreatesGraph(input);
			while (true) {
				String command = input.nextLine();
				String[] commands = command.split(" ");
				try {
					if (commands[0].equals("exit")) {
						break;
					} else if (commands[0].equals("print") && commands[1].equals("matrix")) {
						System.out.println(graph.adjacencyMatrixToString() + "\n");
					} else if (commands[0].equals("print") && commands[1].equals("edgelist")) {
						System.out.println(graph.getEdgeList());
					} else if (commands[0].equals("get") && commands[1].equals("non")) {
						System.out.println(graph.getNumberOfNodes());
					} else if (commands[0].equals("get") && commands[1].equals("noe")) {
						System.out.println(graph.getNumberOfEdges());
					} else if (commands[0].equals("add") && commands[1].equals("data")) {
						userAddData(graph, input);
					} else if (commands[0].equals("remove") && commands[1].equals("data")) {
						userRemoveData(graph, input);
					} else if (commands[0].equals("list") && commands[1].equals("data")) {
						graph.listDataBank();
					} else if (commands[0].equals("isconnected")) {
						System.out.println(graph.isConnected());
					} else if (commands[0].equals("iscomplete")) {
						System.out.println(graph.isComplete());
					} else if (commands[0].equals("bfs")) {
						userBfsStart(graph, commands);
					} else if (commands[0].equals("save") && commands[1].equals("matrix")) {
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss");
						LocalDateTime now = LocalDateTime.now();
						graph.adjmxSave("matrix" + dtf.format(now));
					} else if (commands[0].equals("save") && commands[1].equals("graph")) {
						saveGraph(graph, commands[2]);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("No match");
				} catch (InputMismatchException e) {
					System.out.println("Incorrect nodeID");
				}
			}
		} catch (InputMismatchException e) {
			throw new InputMismatchException();
		}
		input.close();
	}

	/**
	 * A MainMenü minden egynél több soros metódushívása külön metódusba helyezkedik
	 * el, hogy minél rövidebb legyen az else if. Ez dönti el, hogy a Graph melyik
	 * konstruktora legyen meghívva. Esetleg szerializált gráf betöltése.
	 * 
	 * @param <T>   generikus
	 * @param input Scanner
	 * @return graph, Gráf
	 * @throws InputMismatchException   a Graph konstruktora dobja. Hiba a
	 *                                  bevitelben.
	 * @throws UndirectedGraphException a Graph konstruktora dobja; irányítatlan
	 *                                  gráf.
	 * @throws IllegalArgumentException a Graph konstruktora dobja; nem egész szám
	 *                                  az inputban
	 * @throws IOException              a Graph konstruktora dobja; nem található a
	 *                                  megadott nevű fájl
	 * @throws ClassNotFoundException   a Graph konstruktora dobja. Hiba a
	 *                                  bevitelben.
	 */


	@SuppressWarnings("unchecked") // load from serialization
	private static Graph<String> userCreatesGraph(Scanner input) throws InputMismatchException,
			UndirectedGraphException, IllegalArgumentException, IOException, ClassNotFoundException {
		System.out.println("Graph or Matrix input");
		System.out.println("weight < 0 is 0; weight > 9 is 9.");
		System.out.println("create || matrix filename || load filename");
		String inputFrom = input.nextLine();
		String[] from = inputFrom.split(" ");
		// user creates adjacency matrix
		if (from[0].equals("create")) {
			Graph<String> graph = new Graph<>(input);
			return graph;
			// load from serialization
		} else if (from[0].equals("load")) {
			try {
				Graph<String> graph;
				FileInputStream f = new FileInputStream(from[1]);
				ObjectInputStream in = new ObjectInputStream(f);
				graph = (Graph<String>) in.readObject();
				in.close();
				System.out.println(from[1] + " is opened successfully");
				return graph;
			} catch (IOException e) {
				System.out.println(from[1] + " is not found");
				throw new IOException();
			}

			// import adjacency matrix from .txt file
		} else if (from[0].equals("matrix")) {
			Graph<String> graph = new Graph<>(from[1]);
			return graph;
		} else {
			// non of these
			throw new IOException();
		}
	}

	/**
	 * A felhasználó adatot tud elhelyezni a megadott cssúcsban.
	 * 
	 * @param <T>
	 * @param graph gráf
	 * @param input Scanner
	 */
	private static void userAddData(Graph<String> graph, Scanner input) {
		System.out.println("Enter the nodeID");
		// user chooses nodeID
		int nodeId = input.nextInt();
		if (nodeId < graph.getNumberOfNodes()) {
			System.out.println("Enter your important data");
			String data = input.next();
			graph.addData(nodeId, data);
		} else {
			// if the specified Node does not exist
			System.out.println("incorrect nodeID");
		}
	}

	/**
	 * A felhasználó adatot tud törölni a gráf valamely csúcsából.
	 * 
	 * @param <T>   generikus
	 * @param graph gráf
	 * @param input Scanner
	 */
	private static void userRemoveData(Graph<String> graph, Scanner input) {
		System.out.println("Enter the nodeID");
		// user chooses nodeID
		int nodeId = input.nextInt();
		if (nodeId < graph.getNumberOfNodes()) {
			System.out.println("Enter your data to remove");
			// input data to removed
			String data = input.next();
			graph.removeDate(nodeId, data);
		} else {
			// if the specified Node does not exist
			System.out.println("incorrect nodeID.");
		}
	}

	/**
	 * A felhasználó kétféleképpen indíthat BFS-t Élsúlyok alapján bejárva A
	 * csúcsból kiinduló élek végpontjainak id-ja alapján
	 * 
	 * @param <T>      generikus
	 * @param graph    gráf
	 * @param commands {weight, id}
	 */
	private static void userBfsStart(Graph<String> graph, String[] commands) {

		if (commands[2].equals("id")) {
			// sort edgelist
			graph.sortEdgeList("id");
		} else if (commands[2].equals("weight")) {
			// sort edgelist
			graph.sortEdgeList("weight");
		}
		ArrayList<Integer> discovered = graph.BreadthFirstSearch(Integer.valueOf(commands[1]));
		System.out.println(discovered);
	}

	// serialization
	/**
	 * A gráf szerializálással elmenthető, és a mentéskor aktuális állapot
	 * bisszatölthető Ez a mentésért felelős metódus.
	 * 
	 * @param <T>      generikus
	 * @param graph    gráf
	 * @param filename fájl neve
	 */
	private static void saveGraph(Graph<String> graph, String filename) {
		try {
			FileOutputStream f = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(f);
			out.writeObject(graph);
			out.close();
			System.out.println(filename + " saved successfully");
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	/**
	 * A main metódus. Egyetlen dolga meghívni a főmenüt. Ha hibát talál kilép.
	 * Hibát csakis beolvasás közben találhat, ami hibás input eredménye. A többi
	 * kivételt a főmenü kezeli.
	 * 
	 * @param args args
	 */
	public static void main(String[] args) {
		try {
			mainMenu();

		} catch (InputMismatchException e) {
			System.out.println("Exit.");
		} catch (UndirectedGraphException e) {
			System.out.println("Invalid undirected graph.");
			System.out.println("Exit.");
		} catch (IllegalArgumentException e) {
			System.out.println("The input may contains inappropriate characters.");
			System.out.println("Exit.");
		} catch (IOException e) {
			System.out.println("No such file or command.");
			System.out.println("Exit.");
		} catch (ClassNotFoundException e) {
			System.out.println("File not supported.");
			System.out.println("Exit.");
		}
	}
}
