package de.schuette.world;

import java.awt.Point;

import de.schuette.world.skills.Entity;

/**
 * This class defines the most abstract implementation of an {@link Entity}.
 * This class basically manages the position attribute.
 * 
 * @author schuettec
 *
 */
public class AbstractEntity implements Entity {
	/**
	 * Holds the position of this entity in world coordinates.
	 */
	protected final Point worldCoordinates;

	public AbstractEntity() {
		this(new Point(0, 0));
	}

	public AbstractEntity(Point worldCoordinates) {
		this.worldCoordinates = worldCoordinates;
	}

	@Override
	public Point getPosition() {
		return worldCoordinates;
	}

	public void setPosition(int x, int y) {
		this.worldCoordinates.setLocation(x, y);
	}

	public void setPosition(double x, double y) {
		this.worldCoordinates.setLocation(x, y);
	}

	@Override
	public void setPosition(Point worldCoordinates) {
		this.worldCoordinates.setLocation(worldCoordinates);
	}

}
