package de.schuette.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import de.schuette.integration.Testapp;
import de.schuette.math.Math2D;
import de.schuette.math.Point;
import de.schuette.world.skills.CircleObstacle;
import de.schuette.world.skills.Entity;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Algorithm {

	public static List<Point> findPath(CircleObstacle entity, Point target, List<Entity> map, double stepRadius) {

		PriorityQueue<Point> openList = new PriorityQueue<Point>(new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				return Double.compare(Math2D.getEntfernung(o1, target), Math2D.getEntfernung(o2, target));
			}
		});

		Set<Point> closedList = new HashSet<>();

		Point startNode = entity.getPosition();

		openList.add(startNode);

		Point current = null;

		double targetRadius = entity.getRadius();

		do {
			System.out.println("Läuft");
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Testapp.DEBUG.getChildren().clear();
					for (Point p : openList) {
						Circle c = new Circle(5);
						c.setFill(Color.YELLOW);
						c.setTranslateX(p.x);
						c.setTranslateY(p.y);
						Testapp.DEBUG.getChildren().add(c);
					}
				}
			});

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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

			if (Math2D.isInCircle(current, target, targetRadius)) {
				break;
			}

			closedList.add(current);

			expandNode(current, entity, target, map, openList, closedList, stepRadius);

		} while (!openList.isEmpty());

		// Reconstruct path
		List<Point> path = new LinkedList<>();

		while ((current = getData(current).next) != null) {
			path.add(current);
		}

		return path;

	}

	private static double heuristic(Point current, Point successor, Entity start, Point end, List<Entity> map) {

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

		return Math2D.getEntfernung(successor, end);
	}

	private static void expandNode(Point current, Entity start, Point end, List<Entity> map,
			PriorityQueue<Point> openList, Set<Point> closedList, double radius) {

		List<Point> successors = new ArrayList<>();
		for (int i = 0; i <= 315; i += 45) {
			Point circle = Math2D.getCircle(current, radius, i);
			// Check for collision:

			successors.add(circle);
		}

		// {
		// Map copy = map.clone();
		// copy.set(current.getCoordinates(), new Type("!"));
		// copy.set(successors, new Type("n"));
		// System.out.println(copy);
		// System.out.println();
		// }

		for (Point successor : successors) {

			if (closedList.contains(successor)) {
				continue;
			}

			double tentative_g = getData(current).priority + 1;

			if (openList.contains(successor) && tentative_g >= getData(successor).g) {
				continue;
			}

			getData(successor).next = current;
			getData(successor).g = tentative_g;

			double f = tentative_g + heuristic(current, successor, start, end, map);

			if (openList.contains(successor)) {
				openList.remove(successor);
			}
			getData(successor).priority = f;
			openList.add(successor);

		}

	}

	public static AStarData getData(Point p) {
		if (p.userObject == null) {
			p.userObject = new AStarData();
		}
		return (AStarData) p.userObject;
	}

}
