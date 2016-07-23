package de.schuette.integration;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.schuette.math.Point;
import de.schuette.world.AbstractCircleObstacle;
import de.schuette.world.Collision;
import de.schuette.world.EntityPoint;
import de.schuette.world.skills.Entity;
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

	public static Pane DEBUG;

	@Override
	public void start(Stage primaryStage) throws Exception {

		Pane sceneContent = new Pane();
//		Pane debugContent = new Pane();
//		StackPane stack = new StackPane(sceneContent, debugContent);
//		DEBUG = debugContent;


		CircleEntity user = new CircleEntity(new AbstractCircleObstacle(new Point(700, 700), 1));
		user.setScale(65);

		PolygonEntity e1 = new PolygonEntity(new Point(200, 200), new EntityPoint(45d, 1d), new EntityPoint(135d, 1d),
				new EntityPoint(225d, 1d), new EntityPoint(315d, 1d));
		e1.setScale(65);

		PolygonEntity e2 = new PolygonEntity(new Point(100, 100), new EntityPoint(45d, 1d), new EntityPoint(135d, 1d),
				new EntityPoint(225d, 1d), new EntityPoint(315d, 1d));
		e2.setScale(65);
		PolygonEntity e3 = new PolygonEntity(new Point(300, 300), new EntityPoint(45d, 1d), new EntityPoint(135d, 1d),
				new EntityPoint(225d, 1d), new EntityPoint(315d, 1d));
		e3.setScale(65);
		PolygonEntity e4 = new PolygonEntity(new Point(150, 150), new EntityPoint(45d, 1d), new EntityPoint(135d, 1d),
				new EntityPoint(200d, 1d), new EntityPoint(235d, 1d), new EntityPoint(265d, 1d), new EntityPoint(300d, 1d), new EntityPoint(330d, 1d), new EntityPoint(360d, 1d));
		e4.setScale(65);

		List<Entity> map = new LinkedList<>();
		map.add(user);
		map.add(e1);
		map.add(e2);
		map.add(e3);
		map.add(e4);

		sceneContent.getChildren().addAll(user, e1, e2, e3, e4);

		synchronizeEntities(sceneContent);

		AnimationTimer timer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				synchronizeEntities(sceneContent);

				List<Point> detectCollision = null;

				// Detect full collision set to show them all
				detectCollision = Collision.detectCollision(map,  true);
				
				for (Point p : detectCollision) {

					Circle c = new Circle(5);
					c.setFill(Color.RED);
					c.setTranslateX(p.x);
					c.setTranslateY(p.y);
					sceneContent.getChildren().add(c);
					Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10),
							new KeyValue(c.opacityProperty(), 100), new KeyValue(c.opacityProperty(), 0)));
					timeline.setOnFinished(e -> {
						sceneContent.getChildren().remove(c);
					});
					timeline.play();

				}

				// Animate
				e1.rotate(1);
				e2.rotate(1);
				e3.rotate(1);
				e4.rotate(1);
			}

		};
		timer.start();

		List<Circle> paths = new LinkedList<>();

		// Find path:
		//
		// Thread t = new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		//
		// List<Point> findPath = Algorithm.findPath(user, new Point(0, 0), map,
		// user.getRadius());
		// System.out.println(findPath.size());
		//
		// // sceneContent.getChildren().removeAll(paths);
		//
		// for (Point p : findPath) {
		//
		// Circle c = new Circle(5);
		// c.setFill(Color.GREEN);
		// c.setTranslateX(p.x);
		// c.setTranslateY(p.y);
		// paths.add(c);
		//
		// }
		// }
		// });
		// t.start();

		 sceneContent.addEventFilter(MouseEvent.MOUSE_MOVED, new
		 EventHandler<MouseEvent>() {
		 @Override
		 public void handle(MouseEvent mouseEvent) {
		 user.setPosition(mouseEvent.getX(), mouseEvent.getY());
		 mouseEvent.consume();
		
		
		 }
		 });

		Scene scene = new Scene(sceneContent, 1000, 1000);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void synchronizeEntities(Pane sceneContent) {
		// Call synchronize on JFxEntities
		Iterator<Node> it = sceneContent.getChildren().iterator();

		while (it.hasNext()) {
			Node node = it.next();
			if (node instanceof JFxEntity) {
				JFxEntity jFxEntity = (JFxEntity) node;
				jFxEntity.synchronize();
			}
		}
	}
}
