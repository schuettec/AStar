package de.schuette.world;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
public class Collisions {

	public static void detectCollision(CollisionMap collisionMap, Set<Entity> map, boolean all) {
		collisionMap.clearCollisions();

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
					Collision collision = detectCollision(o1, o2, all);
					// Collision may be null if there is none
					if (collision != null) {
						Collision reverse = detectCollision(o2, o1, all);
						collisionMap.addCollisionsBidirectional(collision, reverse);
					}
				}

			}
		}
	}

	public static Collision detectCollision(Obstacle e1, Obstacle e2, boolean all) {
		Shape o1 = e1.getCollisionShape();
		Shape o2 = e2.getCollisionShape();
		List<Point> collisions = null;

		if (o1 instanceof Polygon && o2 instanceof Polygon) {
			collisions = _detectCollision((Polygon) o1, (Polygon) o2, all);
		} else if (o1 instanceof Circle && o2 instanceof Circle) {
			collisions = _detectCollision((Circle) o1, (Circle) o2, all);
		} else if (o1 instanceof Circle && o2 instanceof Polygon) {
			collisions = _detectCollision((Circle) o1, (Polygon) o2, all);
		} else {
			collisions = _detectCollision((Circle) o2, (Polygon) o1, all);
		}

		if (collisions.isEmpty()) {
			return null;
		} else {
			return new Collision(e1, e2, collisions);
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

			double[] results = Math2D.pqFormula(p, q);

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

}
