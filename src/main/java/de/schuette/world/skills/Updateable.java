package de.schuette.world.skills;

/**
 * This interface describes an updateable object. This object provides an update
 * method that is called by the rendering framework for every new frame.
 * 
 * @author Chris
 *
 */
public interface Updateable {

	/**
	 * Called by the rendering framework to signal that the object should update
	 * for the new frame.
	 */
	public void update();
}
