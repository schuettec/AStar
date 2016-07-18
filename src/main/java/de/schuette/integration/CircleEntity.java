package de.schuette.integration;

import java.util.List;

import de.schuette.math.Point;
import de.schuette.world.AbstractCircleObstacle;
import de.schuette.world.skills.CircleObstacle;
import de.schuette.world.skills.Obstacle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleEntity extends Circle implements JFxEntity, CircleObstacle {

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
		setRadius(entity.getRadius());
		setTranslateX(entity.getPosition().x);
		setTranslateY(entity.getPosition().y);
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
	public void scale(double scale) {
		entity.scale(scale);
	}

	@Override
	public double getScale() {
		return entity.getScale();
	}

	@Override
	public void rotate(double degrees) {
		entity.rotate(degrees);
	}

	@Override
	public double getDegrees() {
		return entity.getDegrees();
	}

	@Override
	public List<Point> detectCollision(Obstacle obstacle, boolean all) {
		return entity.detectCollision(obstacle, all);
	}


}
