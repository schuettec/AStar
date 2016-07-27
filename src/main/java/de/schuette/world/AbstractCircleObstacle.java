package de.schuette.world;

import de.schuette.math.Circle;
import de.schuette.math.Point;
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
public class AbstractCircleObstacle extends AbstractEntity implements Obstacle {

	/**
	 * Holds the collision shape of this entity.
	 */
	protected Circle circle;

	public AbstractCircleObstacle(Point worldCoordinates, double radius) {
		super(worldCoordinates);
		this.circle = new Circle(new Point(0, 0), radius);
	}

	@Override
	public Circle getCollisionShape() {
		return circle.clone().scale(scaling).rotate(degrees).translate(worldCoordinates);
	}

	public double getRadius() {
		return circle.getRadius();
	}

}
