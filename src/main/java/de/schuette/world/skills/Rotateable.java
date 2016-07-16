package de.schuette.world.skills;

public interface Rotateable extends Entity {

	/**
	 * This method set the degrees of the entity rotation.
	 * 
	 * @param degrees
	 *            The degrees of the entity rotation.
	 */
	public void setDegrees(double degrees);

	/**
	 * This method rotates the {@link Entity} by adding the specific amount of
	 * degrees to the current entity rotation.
	 * 
	 * @param degrees
	 *            The degrees to add to the entities rotation.
	 */
	public void rotate(double degrees);

}
