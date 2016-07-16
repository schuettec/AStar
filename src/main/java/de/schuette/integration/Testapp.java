package de.schuette.integration;

import java.awt.Point;
import java.util.Iterator;
import java.util.List;

import de.schuette.world.EntityPoint;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Testapp extends Application {

	public static void main(String[] args) {
		Application.launch(Testapp.class, args);

	}

	protected int radius = 40;
	protected int viewWidth = 1024;
	protected int viewHeight = 786;

	@Override
	public void start(Stage primaryStage) throws Exception {

		Pane sceneContent = new Pane();

		PolygonEntity user = new PolygonEntity(new Point(1000, 1000), new EntityPoint(-1, -1), new EntityPoint(1, -1),
				new EntityPoint(-1, 1), new EntityPoint(1, 1));
		user.getEntity().setScale(30);

		PolygonEntity mapEntity = new PolygonEntity(new Point(100, 100), new EntityPoint(-1, -1),
				new EntityPoint(1, -1), new EntityPoint(-1, 1), new EntityPoint(1, 1));
		mapEntity.getEntity().setScale(30);

		sceneContent.getChildren().addAll(user, mapEntity);

		AnimationTimer timer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				// Call synchronize on JFxEntities
				Iterator<Node> it = sceneContent.getChildren().iterator();

				while (it.hasNext()) {
					Node node = it.next();
					if (node instanceof JFxEntity) {
						JFxEntity jFxEntity = (JFxEntity) node;
						jFxEntity.synchronize();
					}
				}

				List<Point> detectCollision = mapEntity.getEntity().detectCollision(user.getEntity());
				for (Point p : detectCollision) {

					System.out.println(p);

					Circle c = new Circle(5);
					c.setFill(Color.RED);
					c.setTranslateX(p.x);
					c.setTranslateY(p.y);
					sceneContent.getChildren().add(c);
					Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500),
							new KeyValue(c.opacityProperty(), 100), new KeyValue(c.opacityProperty(), 0)));
					timeline.setOnFinished(e -> {
						sceneContent.getChildren().remove(c);
					});
					timeline.play();

				}

				// Animate
				mapEntity.getEntity().rotate(1);
			}
		};
		timer.start();

		sceneContent.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				user.getEntity().setPosition(mouseEvent.getX(), mouseEvent.getY());
				mouseEvent.consume();
			}
		});

		Scene scene = new Scene(sceneContent);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
