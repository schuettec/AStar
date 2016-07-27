package de.schuette.world;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.schuette.math.Circle;
import de.schuette.math.Line;
import de.schuette.math.Math2D;
import de.schuette.math.Point;
import de.schuette.math.Polygon;
import de.schuette.math.Shape;
import de.schuette.world.skills.Entity;
import de.schuette.world.skills.Obstacle;

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
					detectCollision.addAll(detectCollision(o1, o2, all));
				}

			}
		}
		return detectCollision;
	}

	public static List<Point> detectCollision(Obstacle e1, Obstacle e2, boolean all) {
		Shape o1 = e1.getCollisionShape();
		Shape o2 = e2.getCollisionShape();
		if (o1 instanceof Polygon && o2 instanceof Polygon) {
			return _detectCollision((Polygon) o1, (Polygon) o2, all);
		} else if (o1 instanceof Circle && o2 instanceof Circle) {
			return _detectCollision((Circle) o1, (Circle) o2, all);
		} else if (o1 instanceof Circle && o2 instanceof Polygon) {
			return _detectCollision((Circle) o1, (Polygon) o2, all);
		} else {
			return _detectCollision((Circle) o2, (Polygon) o1, all);
		}
	}

	private static List<Point> _detectCollision(Circle p1, Circle p2, boolean all) {
		throw new UnsupportedOperationException();
	}

	private static List<Point> _detectCollision(Circle p1, Polygon p2, boolean all) {
		List<Point> collisions = new LinkedList<>();
		List<Line> h2 = p2.getLines();
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
						Point point = new Point(x, y);
						if (l2.isDefined(point)) {
							collisions.add(point);
						}
					}
				}
			}
		}

		return collisions;
	}

	private static List<Point> _detectCollision(Polygon p1, Polygon p2, boolean all) {
		List<Point> collisions = new LinkedList<>();
		List<Line> h1 = p1.getLines();
		List<Line> h2 = p2.getLines();
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

}
