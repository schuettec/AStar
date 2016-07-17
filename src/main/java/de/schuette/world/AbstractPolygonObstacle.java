package de.schuette.world;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.schuette.math.Line;
import de.schuette.world.skills.Entity;
import de.schuette.world.skills.Obstacle;
import de.schuette.world.skills.PolygonObstacle;

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
public class AbstractPolygonObstacle extends AbstractEntity implements PolygonObstacle {

	/**
	 * Holds the list of {@link Line}s making up the collision hull polygon.
	 * Note: After every modification of the list call
	 * {@link Collision#sortEntityPoints(List)} to make sure the points are
	 * ordered as polygon with non-crossing lines.
	 */
	protected List<EntityPoint> entityPoints;

	public AbstractPolygonObstacle() {
		super();
		this.entityPoints = new LinkedList<>();
	}

	public AbstractPolygonObstacle(Point worldCoordinates) {
		super(worldCoordinates);
		this.entityPoints = new LinkedList<EntityPoint>();
	}

	public AbstractPolygonObstacle(Point worldCoordinates, EntityPoint... entityPoints) {
		super(worldCoordinates);
		this.entityPoints = new LinkedList<EntityPoint>();
		setHullPoints(entityPoints);
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
	public List<Line> getHullPolygon(boolean worldCoordinates) {
		return Collision.getHullPolygon(getHullPoints(worldCoordinates));
	}

	@Override
	public List<EntityPoint> getHullPoints(boolean worldCoordinates) {
		if (worldCoordinates) {
			return getWorldView(entityPoints);
		} else {
			return Collections.unmodifiableList(entityPoints);
		}
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
	public void setHullPoints(EntityPoint[] entityPoints) {
		this.entityPoints.clear();
		this.entityPoints.addAll(Arrays.asList(entityPoints));
		Collision.sortEntityPoints(this.entityPoints);
	}

}
