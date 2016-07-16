package de.schuette.integration;

import java.awt.Point;
import java.util.List;

import de.schuette.world.AbstractObstacle;
import de.schuette.world.EntityPoint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class PolygonEntity extends Polygon implements JFxEntity<AbstractObstacle> {

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

	@Override
	public AbstractObstacle getEntity() {
		return entity;
	}

}
