package de.schuette.world.skills;

public interface Scalable extends Entity {

	/**
	 * @param scale
	 *            Sets the scaling factor.
	 */
	public void setScale(double scale);

	/**
	 * @param scale
	 *            Returns the scaling factor.
	 */
	public double getScale();

	/**
	 * @param scale
	 *            Scales this entity by adding the amount of scaling factor to
	 *            the current scaling.
	 */
	public void scale(double scale);

}
