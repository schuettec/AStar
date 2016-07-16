package de.schuette.world;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.schuette.math.Line;
import de.schuette.math.Math2D;
import de.schuette.world.skills.Obstacle;
import de.schuette.world.skills.PolygonObstacle;

/**
 * A public util class with methods to perform a collision detection.
 * 
 * @author schuettec
 *
 */
public class Collision {

	public static List<Point> detectCollision(Obstacle o1, Obstacle o2) {
		return _detectCollision(o1, o2, true);
	}

	public static List<Point> detectFirstCollision(Obstacle o1, Obstacle o2) {
		return _detectCollision(o1, o2, false);
	}

	private static List<Point> _detectCollision(Obstacle o1, Obstacle o2, boolean all) {
		List<Point> collisions = new LinkedList<>();
		if (o1 instanceof PolygonObstacle && o2 instanceof PolygonObstacle) {
			PolygonObstacle p1 = (PolygonObstacle) o1;
			PolygonObstacle p2 = (PolygonObstacle) o2;
			List<Line> h1 = p1.getHullPolygon();
			List<Line> h2 = p2.getHullPolygon();
			for (Line l1 : h1) {
				for (Line l2 : h2) {
					Point intersection = l1.intersects(l2);
					if (intersection != null) {
						collisions.add(intersection);
						if (!all) {
							return collisions;
						}
					}
				}
			}
		}
		return collisions;
	}

	/**
	 * This method calculates the polygon from the specified list of entity
	 * points.
	 * 
	 * @param points
	 * @return Returns the list of lines that make up the polygon.
	 */
	public static List<Line> getHullPolygon(List<EntityPoint> sortedPoints) {
		final List<Line> lineList = new ArrayList<Line>();

		if (sortedPoints.size() > 0) {
			for (int i = 1; i < sortedPoints.size(); i++) {
				EntityPoint start = sortedPoints.get(i);
				EntityPoint end = sortedPoints.get(i - 1);
				lineList.add(new Line(start.getCoordinates(), end.getCoordinates()));
			}

			EntityPoint start = sortedPoints.get(0);
			EntityPoint end = sortedPoints.get(sortedPoints.size() - 1);
			lineList.add(new Line(start.getCoordinates(), end.getCoordinates()));
		}

		return lineList;
	}

	/**
	 * Sorts the specified list of points so that the points connected with
	 * lines in the order the list specified result in a polygon with non
	 * crossing lines.
	 * 
	 * @param points
	 */
	public static void sortEntityPoints(List<EntityPoint> points) {
		if (points.size() == 0)
			return;

		for (int i = 1; i < points.size(); i++) {
			EntityPoint current = points.get(i - 1);
			List<EntityPoint> subListView = points.subList(i, points.size());
			EntityPoint nextPoint = Math2D.getPointNextTo(current, subListView);
			points.remove(nextPoint);
			points.add(i, nextPoint);
		}
	}

	public static void translate(List<EntityPoint> entityPoints, Point worldCoordinates) {
		for (EntityPoint point : entityPoints) {
			point.translate(worldCoordinates);
		}
	}

	public static void rotate(List<EntityPoint> entityPoints, double degrees) {
		for (EntityPoint point : entityPoints) {
			point.rotate(degrees);
		}
	}

	public static List<EntityPoint> clone(List<EntityPoint> points) {
		List<EntityPoint> clone = new LinkedList<>();
		for (EntityPoint e : points) {
			EntityPoint n = new EntityPoint(e.getDegrees(), e.getRadius());
			clone.add(n);
		}
		return clone;
	}

	public static void scale(List<EntityPoint> points, double scaling) {
		for (EntityPoint point : points) {
			point.scale(scaling);
		}
	}

}
