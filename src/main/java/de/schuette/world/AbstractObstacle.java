package de.schuette.world;

import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import de.schuette.math.Line;
import de.schuette.world.skills.Entity;
import de.schuette.world.skills.Obstacle;
import de.schuette.world.skills.PolygonObstacle;
import de.schuette.world.skills.Rotateable;
import de.schuette.world.skills.Scalable;

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
public class AbstractObstacle extends AbstractEntity implements PolygonObstacle, Rotateable, Scalable {

	/**
	 * Holds the list of {@link Line}s making up the collision hull polygon.
	 * Note: After every modification of the list call
	 * {@link Collision#sortEntityPoints(List)} to make sure the points are
	 * ordered as polygon with non-crossing lines.
	 */
	protected List<EntityPoint> entityPoints;

	/**
	 * Holds the current scaling factor.
	 */
	protected double scaling = 0;

	/**
	 * Holds the current rotation of the entity in the world.
	 */
	protected double degrees;

	public AbstractObstacle() {
		super();
		this.entityPoints = new LinkedList<>();
	}

	public AbstractObstacle(Point worldCoordinates) {
		super(worldCoordinates);
		this.entityPoints = new LinkedList<EntityPoint>();
	}

	public AbstractObstacle(Point worldCoordinates, EntityPoint... entityPoints) {
		super(worldCoordinates);
		this.entityPoints = new LinkedList<EntityPoint>();
		setEntityPoints(entityPoints);
	}

	// TODO: Should this become a general method defined by interface?
	public void setEntityPoints(EntityPoint[] entityPoints) {
		this.entityPoints.clear();
		this.entityPoints.addAll(Arrays.asList(entityPoints));
		Collision.sortEntityPoints(this.entityPoints);
	}

	@Override
	public void rotate(double degrees) {
		this.degrees += degrees;
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
	public List<Line> getHullPolygon() {
		return Collision.getHullPolygon(getHullPoints());
	}

	@Override
	public List<EntityPoint> getHullPoints() {
		return getWorldView(entityPoints);
	}

	private List<EntityPoint> getWorldView(List<EntityPoint> points) {
		// Rotate to the current state of the entity.
		List<EntityPoint> clone = Collision.clone(points);
		// Rotate to the current state of the entity.
		Collision.rotate(clone, this.degrees);
		// Translate to world coordinates
		Collision.scale(clone, scaling);
		// Translate to world coordinates
		Collision.translate(clone, worldCoordinates);

		return clone;
	}

	@Override
	public void setDegrees(double degrees) {
		this.degrees = degrees;
	}

	@Override
	public void setScale(double scale) {
		this.scaling = scale;
	}

	@Override
	public void scale(double scale) {
		this.scaling += scaling;
	}

	@Override
	public double getScale() {
		return this.scaling;
	}

}
