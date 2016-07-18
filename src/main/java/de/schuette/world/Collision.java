package de.schuette.world;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.schuette.math.Line;
import de.schuette.math.Math2D;
import de.schuette.math.Point;
import de.schuette.world.skills.CircleObstacle;
import de.schuette.world.skills.Entity;
import de.schuette.world.skills.Obstacle;
import de.schuette.world.skills.PolygonObstacle;

/**
 * A public util class with methods to perform a collision detection.
 * 
 * @author schuettec
 *
 */
public class Collision {


	public static List<Point> detectCollision(List<Entity> map, boolean all) {
		List<Point> detectCollision = new LinkedList<>();
		
		for (Entity c1 : new ArrayList<>(map)) {
			if (!(c1 instanceof Obstacle)) {
				continue;
			}

			for (Entity c2 : new ArrayList<>(map)) {
				if (!(c2 instanceof Obstacle)) {
					continue;
				}
				if (c1 == c2)
					continue;

				Obstacle o1 = (Obstacle) c1;
				Obstacle o2 = (Obstacle) c2;
				{
					detectCollision = o1.detectCollision(o2, all);
				}

			}
		}
		return detectCollision;
	}


	public static List<Point> detectCollision(Obstacle o1, Obstacle o2, boolean all) {
		if (o1 instanceof PolygonObstacle && o2 instanceof PolygonObstacle) {
			return _detectCollision((PolygonObstacle) o1, (PolygonObstacle) o2, all);
		} else if (o1 instanceof CircleObstacle && o2 instanceof CircleObstacle) {
			return _detectCollision((CircleObstacle) o1, (CircleObstacle) o2, all);
		} else if (o1 instanceof CircleObstacle && o2 instanceof PolygonObstacle) {
			return _detectCollision((CircleObstacle) o1, (PolygonObstacle) o2, all);
		} else {
			return _detectCollision((CircleObstacle) o2, (PolygonObstacle) o1, all);
		}
	}

	private static List<Point> _detectCollision(CircleObstacle p1, CircleObstacle p2, boolean all) {
		throw new UnsupportedOperationException();
	}

	private static List<Point> _detectCollision(CircleObstacle p1, PolygonObstacle p2, boolean all) {
		List<Point> collisions = new LinkedList<>();
		List<Line> h2 = p2.getHullPolygon(true);
		for (Line l2 : h2) {
			// Kreisgleichung:
			// (x - xM)² + ([m * x + n] - yM)² = r²
			// =>
			// x² - ((xm + cm) / (m² + 1)) x + (xm² + c² - r²) / (m² + 1) = 0
			double xm = p1.getPosition().x;
			double ym = p1.getPosition().y;
			double r = p1.getRadius();
			double m = l2.getM();
			double n = l2.getB();

			double p = ((-2d * xm + 2d * m * n - 2d * m * ym) / (Math.pow(m, 2) + 1d));
			double q = ((Math.pow(xm, 2) + Math.pow(n - ym, 2) - Math.pow(r, 2)) / (Math.pow(m, 2) + 1d));

			double[] results = pqFormula(p, q);

			if (results.length > 0) {
				for (double result : results) {
					if (result >= 0) {
						double x = result;
						double y = m * x + n;

						// double rTest = Math.pow(x - xm, 2) + Math.pow(y - ym,
						// 2);
						// boolean isDefined = rTest <= Math.pow(r, 2);

						if (l2.isDefined(new Point(x, y))) {
							collisions.add(new Point(x, y));
						}
					}
				}
			}
		}

		return collisions;
	}

	private static List<Point> _detectCollision(PolygonObstacle p1, PolygonObstacle p2, boolean all) {
		List<Point> collisions = new LinkedList<>();
		List<Line> h1 = p1.getHullPolygon(true);
		List<Line> h2 = p2.getHullPolygon(true);
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
		return collisions;
	}

	public static double[] pqFormula(double pP, double pQ) {
		double diskriminante;
		diskriminante = (pP / 2.0) * (pP / 2.0) - pQ;
		if (diskriminante >= 0) {
			double x1, x2;
			x1 = -(pP / 2) + Math.sqrt(diskriminante);
			x2 = -(pP / 2) - Math.sqrt(diskriminante);
			if (x1 == x2) {
				return new double[] { x2 };
			} else {
				return new double[] { x1, x2 };// Was x1, x2
			}
		} else {
			return new double[] {};
		}
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
