package de.schuette.world;

import java.awt.Point;
import java.util.List;

import de.schuette.world.skills.CircleObstacle;
import de.schuette.world.skills.Entity;
import de.schuette.world.skills.Obstacle;

/**
 * Represents an abstract obstacle in the world. This is the most possible
 * abstract class for obstacle entities implementing common functions.
 * 
 * <p>
 * This class assumes that the collision hull for an entity is always
 * represented by a polygon. Therefore a list of entity points and collision
 * lines is managed by this object. Shapes that do not need a polygon for
 * collision detection (like circles - they can do it more efficiently) can
 * either override affected methods or can implement the skill interfaces
 * {@link Obstacle} and {@link Entity} themselves.
 * <p>
 * 
 * 
 * 
 * @author schuettec
 */
public class AbstractCircleObstacle extends AbstractEntity implements CircleObstacle {

	/**
	 * Holds the radius of the collision circle.
	 */
	protected double radius;

	public AbstractCircleObstacle() {
		super();
	}

	public AbstractCircleObstacle(Point worldCoordinates) {
		super(worldCoordinates);
	}

	public AbstractCircleObstacle(Point worldCoordinates, double radius) {
		super(worldCoordinates);
		setRadius(radius);
	}

	@Override
	public List<Point> detectCollision(Obstacle obstacle) {
		return Collision.detectCollision(this, obstacle);
	}

	@Override
	public List<Point> detectFirstCollision(Obstacle obstacle) {
		return Collision.detectFirstCollision(this, obstacle);
	}

	@Override
	public double getRadius() {
		return radius * scaling;
	}

	@Override
	public void setRadius(double radius) {
		this.radius = radius;
	}

}
