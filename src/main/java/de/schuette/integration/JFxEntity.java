package de.schuette.integration;

import de.schuette.world.skills.Entity;
import javafx.scene.Node;

/**
 * This interface describes methods to enable a JavaFX entity to synchronize its
 * internal state with the {@link Entity} of Cobra2D.
 * 
 * @author schuettec
 *
 */
public interface JFxEntity extends Entity {

	/**
	 * This method is called by the framework to synchronize the JavaFX
	 * {@link Node} with the properties of the {@link Entity} defined by the
	 * Cobra2D engine.
	 */
	public void synchronize();

}
