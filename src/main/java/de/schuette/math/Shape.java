package de.schuette.math;

/**
 * This interface describes methods a geometry must provide.
 * 
 * @author Chris
 *
 */
public interface Shape {

	/**
	 * Rotates this {@link Shape} and returns itself to support a fluent API
	 * style of method calls. The default rotation point of this {@link Shape}
	 * is used
	 * 
	 * @param degrees
	 *            The amount of degrees to rotate.
	 * @return Returns this {@link Shape} to support a fluent API style of
	 *         method calls
	 */
	public Shape rotate(double degrees);

	// /**
	// * Rotates this {@link Shape} using the specified rotation point. The
	// * rotation point is specified relatively to the current translation of
	// this
	// * {@link Shape}
	// *
	// * @param degrees
	// * The amount of degrees to rotate.
	// * @param rotationCenter
	// * The rotation center relative to the current translation.
	// * @return Returns this {@link Shape} to support a fluent API style of
	// * method calls
	// */
	// public Shape rotate(double degrees, Point rotationCenter);

	/**
	 * Translates the shape by adding the specified translation.
	 * 
	 * @param translation
	 *            The point to add as translation.
	 * @return Returns this {@link Shape} to support a fluent API style of
	 *         method calls
	 */
	public Shape translate(Point translation);

	/**
	 * Scales this {@link Shape} by the specfied scale factor.
	 * 
	 * @param scaleFactor
	 *            The scale factor.
	 * @return Returns this {@link Shape} to support a fluent API style of
	 *         method calls
	 */
	public Shape scale(double scaleFactor);
}
