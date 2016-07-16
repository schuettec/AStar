package de.schuette.integration;

import java.awt.Point;
import java.util.List;

import de.schuette.math.Line;
import de.schuette.world.AbstractObstacle;
import de.schuette.world.EntityPoint;
import de.schuette.world.skills.Obstacle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class PolygonEntity extends Polygon implements JFxEntity {

	protected AbstractObstacle entity;

	public PolygonEntity(Point worldCoordinates, EntityPoint... entityPoints) {
		this(new AbstractObstacle(worldCoordinates, entityPoints));
	}

	public PolygonEntity(AbstractObstacle entity) {
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
		// Synchronize points
		getPoints().clear();
		List<EntityPoint> entityPoints = entity.getHullPoints();
		for (EntityPoint e : entityPoints) {
			getPoints().addAll(e.getCoordinates().getX(), e.getCoordinates().getY());
		}
	}

	public Point getPosition() {
		return entity.getPosition();
	}

	public List<Line> getHullPolygon() {
		return entity.getHullPolygon();
	}

	public List<EntityPoint> detectCollision(Obstacle obstacle) {
		return entity.detectCollision(obstacle);
	}

	public void setPosition(Point worldCoordinates) {
		entity.setPosition(worldCoordinates);
	}

	public List<EntityPoint> getHullPoints() {
		return entity.getHullPoints();
	}

	public List<EntityPoint> detectFirstCollision(Obstacle obstacle) {
		return entity.detectFirstCollision(obstacle);
	}

	public void setPosition(int x, int y) {
		entity.setPosition(x, y);
	}

	public void setPosition(double x, double y) {
		entity.setPosition(x, y);
	}

	public void rotate(double degrees) {
		entity.rotate(degrees);
	}

	public void setDegrees(double degrees) {
		entity.setDegrees(degrees);
	}

	public void setScale(double scale) {
		entity.setScale(scale);
	}

	public void scale(double scale) {
		entity.scale(scale);
	}

	public double getScale() {
		return entity.getScale();
	}

}
