package de.schuette.math;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.schuette.world.Collision;
import de.schuette.world.EntityPoint;

/**
 * This class describes a geometric polygon in a 2D coordinate system. It is
 * basically a list of {@link Line}.
 * 
 * @author Chris
 *
 */
public class Polygon {

	/**
	 * Holds the list of {@link Line}s making up the collision hull polygon.
	 * Note: After every modification of the list call
	 * {@link Collision#sortEntityPoints(List)} to make sure the points are
	 * ordered as polygon with non-crossing lines.
	 */
	protected List<EntityPoint> entityPoints;

	public Polygon() {
		super();
		this.entityPoints = new LinkedList<>();
	}

	public Polygon(EntityPoint... entityPoints) {
		this.entityPoints = new LinkedList<EntityPoint>();
		Collision.sortEntityPoints(this.entityPoints);
	}

	public List<EntityPoint> getEntityPoints() {
		return Collections.unmodifiableList(entityPoints);
	}

}
