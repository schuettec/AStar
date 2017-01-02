package algorithm.astar.array;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Algorithm {

	public static List<int[]> findPath(Map map) {
		int[] start = search(map, Type.START);
		int[] end = search(map, Type.END);

		PriorityQueue<Node> openList = new PriorityQueue<Node>();
		Set<Node> closedList = new HashSet<>();

		Node startNode = new Node(start, 0);

		openList.add(startNode);

		Node current = null;
		do {

			// DEBUG LOG
			// {
			// Map copy = map.clone();
			// Node[] array = openList.toArray(new Node[openList.size()]);
			// Arrays.sort(array);
			// for (Node n : array) {
			// copy.set(n.x, n.y, new Type(String.valueOf(n.priority)));
			// }
			// System.out.println(copy);
			// }

			current = openList.poll();

			if (current.isAt(end)) {
				break;
			}

			closedList.add(current);

			expandNode(current, start, end, map, openList, closedList);

		} while (!openList.isEmpty());

		// Reconstruct path
		List<int[]> path = new LinkedList<>();

		int[] a = null, b = null, c = null;

		while ((current = current.getNext()) != null) {

			if (a == null) {
				a = current.getCoordinates();
				path.add(a);
				continue;
			} else if (b == null) {
				b = current.getCoordinates();
				continue;
			} else if (c == null) {
				c = current.getCoordinates();
			}

			// Post Processing
			b = normalize(a, b, c, map);
			path.add(b);

			a = b;
			b = c;
			c = null;

		}

		path.add(b);

		return path;

	}

	private static int[] normalize(int[] a, int[] b, int[] c, Map map) {
		if (a == null || b == null || c == null) {
			throw new IllegalArgumentException("Waypoints may not be null.");
		}
		// Optimal heuristic line
		List<int[]> line = line(a, c);
		int[] suggestion = line.get(1);
		if (map.isFree(suggestion)) {
			return suggestion;
		} else {
			return b;
		}
	}

	private static int heuristic(Node current, Node successor, int[] start, int[] end, Map map) {

		// {
		// Map copy = map.clone();
		// System.out.println(copy);
		// System.out.println();
		// }
		// {
		// Map copy = map.clone();
		// copy.set(line(current.getCoordinates(), end, copy), new Type("H"));
		// copy.set(line(current.getCoordinates(), successor.getCoordinates(),
		// copy), new Type("h"));
		// System.out.println(copy);
		// System.out.println();
		// }
		// {
		// Map copy = map.clone();
		// copy.set(current.getCoordinates(), new Type(String.valueOf("c")));
		// copy.set(successor.x, successor.y, new
		// Type(String.valueOf(successor.priority)));
		// System.out.println(copy);
		// System.out.println();
		// }

		return line(successor.getCoordinates(), end).size();
	}

	private static void expandNode(Node current, int[] start, int[] end, Map map, PriorityQueue<Node> openList,
			Set<Node> closedList) {

		List<int[]> successors = map.getFreeNeighbours(current.getCoordinates());

		// {
		// Map copy = map.clone();
		// copy.set(current.getCoordinates(), new Type("!"));
		// copy.set(successors, new Type("n"));
		// System.out.println(copy);
		// System.out.println();
		// }

		for (int[] successorCoords : successors) {

			Node successor = getSuccessor(successorCoords, openList);

			if (closedList.contains(successor)) {
				continue;
			}

			int tentative_g = current.priority + 1;

			if (openList.contains(successor) && tentative_g >= successor.getG()) {
				continue;
			}

			successor.setNext(current);
			successor.setG(tentative_g);

			int f = tentative_g + heuristic(current, successor, start, end, map);

			if (openList.contains(successor)) {
				openList.remove(successor);
			}
			successor.setPriority(f);
			openList.add(successor);

		}

	}

	/**
	 * Returns the successor from openlist if it exists or creates a new node.
	 * 
	 * @param successorCoords
	 * 
	 * @param openList
	 * @return
	 */
	private static Node getSuccessor(int[] coords, PriorityQueue<Node> openList) {
		Node node = new Node(coords, 0);
		if (openList.contains(node)) {
			for (Node next : openList) {
				if (next.equals(node))
					return next;
			}
		}
		return node;
	}

	/**
	 * Searches a specified type in the map and returns the first found
	 * coordinates
	 * 
	 * @param map
	 *            The map
	 * @param type
	 *            The type
	 * @return Returns the coordinates if found in an interger array of form
	 *         int[x][y]. If nothing was found <code>null</code> is returned.
	 */
	public static int[] search(Map map, Type type) {
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				if (map.get(x, y) == type) {
					return new int[] { x, y };
				}
			}
		}
		return null;
	}

	/**
	 * Calculates a line from start to end point. This method assumes that one
	 * start point and one end point is set on the map.
	 * 
	 * @param map
	 * @return Returns the list of coordinates of the line.
	 */
	public static List<int[]> line(Map map) {
		// TODO: If we have more than one algorithm to get the line, implement
		// strategy pattern.
		int[] start = search(map, Type.START);
		int[] end = search(map, Type.END);

		return bresenham(start, end);
	}

	/**
	 * Calculates a line from start to end point. This method assumes that one
	 * start point and one end point is set on the map.
	 * 
	 * @param start
	 * @param end
	 * 
	 * @param map
	 * @return Returns the list of coordinates of the line.
	 */
	public static List<int[]> line(int[] start, int[] end) {
		return bresenham(start, end);
	}

	/**
	 * Draws a line with algorithm of Bresenham
	 */
	private static List<int[]> bresenham(int[] start, int[] end) {
		int x1 = start[0];
		int y1 = start[1];
		int x2 = end[0];
		int y2 = end[1];

		List<int[]> points = new LinkedList<int[]>();
		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);

		int sx = (x1 < x2) ? 1 : -1;
		int sy = (y1 < y2) ? 1 : -1;

		int err = dx - dy;

		while (true) {
			points.add(new int[] { x1, y1 });

			if (x1 == x2 && y1 == y2) {
				break;
			}

			int e2 = 2 * err;

			if (e2 > -dy) {
				err = err - dy;
				x1 = x1 + sx;
			}

			if (e2 < dx) {
				err = err + dx;
				y1 = y1 + sy;
			}
		}
		return points;
	}
}
