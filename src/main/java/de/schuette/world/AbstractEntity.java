package de.schuette.world;

import de.schuette.math.Point;
import de.schuette.math.Shape;
import de.schuette.world.skills.Entity;

/**
 * This class defines the most abstract implementation of an {@link Entity}.
 * 
 * @author schuettec
 *
 */
public abstract class AbstractEntity implements Entity, Shape {
	/**
	 * Holds the position of this entity in world coordinates.
	 */
	protected final Point worldCoordinates;

	/**
	 * Holds the current rotation of the entity in the world.
	 */
	protected double degrees;

	/**
	 * Holds the scaling factor of this entity.
	 */
	protected double scaling;

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
	public double getScale() {
		return this.scaling;
	}

	@Override
	public double getDegrees() {
		return degrees;
	}

	@Override
	public AbstractEntity rotate(double degrees) {
		this.degrees += degrees;
		return this;
	}

	@Override
	public AbstractEntity translate(Point translation) {
		this.worldCoordinates.translate(translation);
		return this;
	}

	@Override
	public AbstractEntity scale(double scaleFactor) {
		this.scaling += scaleFactor;
		return this;
	}

}
