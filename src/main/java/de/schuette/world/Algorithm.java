package de.schuette.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import de.schuette.integration.CircleEntity;
import de.schuette.integration.Testapp;
import de.schuette.math.Circle;
import de.schuette.math.Line;
import de.schuette.math.Math2D;
import de.schuette.math.Point;
import de.schuette.world.skills.Entity;
import de.schuette.world.skills.Obstacle;
import javafx.application.Platform;
import javafx.scene.paint.Color;

public class Algorithm {

	public static List<Point> findPath(CircleEntity entity, Point target, Map map, double stepRadius) {

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
						javafx.scene.shape.Circle c = new javafx.scene.shape.Circle(5);
						c.setFill(Color.YELLOW);
						c.setTranslateX(p.x);
						c.setTranslateY(p.y);
						Testapp.DEBUG.getChildren().add(c);
					}
				}
			});

			try {
				Thread.sleep(200);
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

	private static double heuristic(Point current, Point successor, CircleEntity start, Point end, Map map) {

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

	private static void expandNode(Point current, CircleEntity start, Point end, Map map, PriorityQueue<Point> openList,
			Set<Point> closedList, double radius) {

		List<Point> successors = new ArrayList<>();
		for (int i = 0; i <= 315; i += 45) {
			Point circle = Math2D.getCircle(current, radius, i);
			// Create collision shape: A line from current point to the next
			// step.
			Line line = new Line(current, circle);
			Set<Entity> ignore = new HashSet<>();
			ignore.add(start);
			boolean hasCollision = map.hasCollision(line.toPolygon(), ignore, false);
			if (!hasCollision) {
				successors.add(circle);
			}
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
