package de.schuette.integration;

import de.schuette.math.Point;
import de.schuette.world.AbstractCircleObstacle;
import de.schuette.world.AbstractEntity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleEntity extends Circle implements JFxEntity {

	protected AbstractCircleObstacle entity;

	public CircleEntity(Point worldCoordinates, double radius) {
		this(new AbstractCircleObstacle(worldCoordinates, radius));
	}

	public CircleEntity(AbstractCircleObstacle entity) {
		super();
		setFXProperties();
		this.entity = entity;

	}

	private void setFXProperties() {
		setFill(null);
		setStroke(Color.BLACK);
	}

	@Override
	public void synchronize() {
		de.schuette.math.Circle collisionShape = entity.getCollisionShape();
		setRadius(collisionShape.getRadius());
		setTranslateX(collisionShape.getPosition().x);
		setTranslateY(collisionShape.getPosition().y);
	}

	@Override
	public Point getPosition() {
		return entity.getPosition();
	}

	public void setPosition(int x, int y) {
		entity.setPosition(x, y);
	}

	public void setPosition(double x, double y) {
		entity.setPosition(x, y);
	}

	@Override
	public void setPosition(Point worldCoordinates) {
		entity.setPosition(worldCoordinates);
	}

	@Override
	public void setDegrees(double degrees) {
		entity.setDegrees(degrees);
	}

	@Override
	public void setScale(double scale) {
		entity.setScale(scale);
	}

	@Override
	public double getScale() {
		return entity.getScale();
	}

	@Override
	public double getDegrees() {
		return entity.getDegrees();
	}

	public AbstractEntity rotate(double degrees) {
		return entity.rotate(degrees);
	}

	public AbstractEntity translate(Point translation) {
		return entity.translate(translation);
	}

	public AbstractEntity scale(double scaleFactor) {
		return entity.scale(scaleFactor);
	}

	public de.schuette.math.Circle getCollisionShape() {
		return entity.getCollisionShape();
	}

}
