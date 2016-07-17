package de.schuette.world.skills;

import java.util.List;

import de.schuette.math.Point;

/**
 * Defines the minimum amount of methods an obstacle must support to detect a
 * collision. There are two kinds of obstacles:
 * <ul>
 * <li>Polygon obstacles: Consist of a polygon that represents the collision
 * bounds. Always represented by a set of lines.</li>
 * <li>Other obstacles: A circle for example is not represented by a set of
 * lines. This kind of obstacles must use another strategy to implement
 * {@link #detectCollision(Obstacle)}.</li>
 * </ul>
 * 
 * @author schuettec
 *
 */
public interface Obstacle extends Entity {

	/**
	 * Checks this entity and the specified entity for collisions.
	 * 
	 * @param obstacle
	 *            The other entity.
	 * @return Returns the list of points in world coordinates if there is a
	 *         collision. Otherwise <code>null</code> is returned.
	 */
	public List<Point> detectCollision(Obstacle obstacle);

	/**
	 * Checks this entity and the specified entity for collisions.
	 * 
	 * @param obstacle
	 *            The other entity.
	 * @return Returns the first detected point of collision in world
	 *         coordinates if there is a collision. Otherwise <code>null</code>
	 *         is returned.
	 */
	public List<Point> detectFirstCollision(Obstacle obstacle);

}
