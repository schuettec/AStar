package de.schuette.world;

import de.schuette.math.Point;
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

	/**
	 * Holds the current rotation of the entity in the world.
	 */
	protected double degrees;

	/**
	 * Holds the current scaling factor.
	 */
	protected double scaling = 1;

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

	@Override
	public void rotate(double degrees) {
		this.degrees += degrees;
	}

	@Override
	public double getDegrees() {
		return degrees;
	}

}
