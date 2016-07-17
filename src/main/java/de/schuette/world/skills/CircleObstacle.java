package de.schuette.world.skills;

/**
 * This interface defines the {@link CircleObstacle}. This is an {@link Entity}
 * that does not manage a hull polygon for collision detection but a circle. A
 * circle allows to do a more efficient collision detection.
 * 
 * @author schuettec
 *
 */
public interface CircleObstacle extends Obstacle {

	/**
	 * @return Returns the collision radius for this entity.
	 */
	public double getRadius();

	/**
	 * @param radius
	 *            Sets the collision radius for this entity.
	 */
	public void setRadius(double radius);

}
