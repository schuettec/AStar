package de.schuette.integration;

import java.awt.Point;
import java.util.List;

import de.schuette.math.Line;
import de.schuette.world.AbstractPolygonObstacle;
import de.schuette.world.Collision;
import de.schuette.world.EntityPoint;
import de.schuette.world.skills.Obstacle;
import de.schuette.world.skills.PolygonObstacle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class PolygonEntity extends Polygon implements JFxEntity, PolygonObstacle {

	protected AbstractPolygonObstacle entity;

	public PolygonEntity(Point worldCoordinates, EntityPoint... entityPoints) {
		this(new AbstractPolygonObstacle(worldCoordinates, entityPoints));
	}

	public PolygonEntity(AbstractPolygonObstacle entity) {
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
		setTranslateX(getPosition().x);
		setTranslateY(getPosition().y);
		// Do not use the world view here: Otherwise the polygon is always
		// interpreted to have a size measured at 0|0.
		List<EntityPoint> entityPoints = Collision.clone(entity.getHullPoints(false));
		Collision.scale(entityPoints, getScale());
		Collision.rotate(entityPoints, entity.getDegrees());
		for (EntityPoint e : entityPoints) {
			getPoints().addAll(e.getCoordinates().getX(), e.getCoordinates().getY());
		}
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
	public List<Point> detectCollision(Obstacle obstacle) {
		return entity.detectCollision(obstacle);
	}

	@Override
	public List<Point> detectFirstCollision(Obstacle obstacle) {
		return entity.detectFirstCollision(obstacle);
	}

	@Override
	public List<Line> getHullPolygon(boolean worldCoordinates) {
		return entity.getHullPolygon(worldCoordinates);
	}

	@Override
	public List<EntityPoint> getHullPoints(boolean worldCoordinates) {
		return entity.getHullPoints(worldCoordinates);
	}

	@Override
	public void setHullPoints(EntityPoint[] entityPoints) {
		entity.setHullPoints(entityPoints);
	}

}
