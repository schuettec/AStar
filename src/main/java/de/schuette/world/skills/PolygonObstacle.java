package de.schuette.world.skills;

import java.util.List;

import de.schuette.math.Line;
import de.schuette.world.EntityPoint;

/**
 * This interface describes an obstacle that is represented by a polygon used
 * for collision detection.
 * 
 * @author schuettec
 *
 */
public interface PolygonObstacle extends Obstacle {

	/**
	 * Calculates the hull polygon of this entity. Entities may use this hull
	 * polygon to determine intersections between their own hull and the one
	 * returned by this method. No specific order is assumed. The list is
	 * assumed to be non-modifyable.
	 * 
	 * @return Returns the hull polygon of this {@link Obstacle}.
	 */
	public List<Line> getHullPolygon();

	/**
	 * Returns the current state of the collision entity points. Properties like
	 * rotation will influence the locations of the {@link EntityPoint}s this
	 * method returnes.
	 * <p>
	 * <b>This method returns the world coordinate, current state view of this
	 * entity.</b>
	 * <p>
	 * 
	 * @return Returns the list of entity points of this entity making up the
	 *         hull polygon. No specific order is assumed. The list is assumed
	 *         to be non-modifyable.
	 */
	public List<EntityPoint> getHullPoints();

}
