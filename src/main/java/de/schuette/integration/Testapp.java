package de.schuette.integration;

import java.awt.Point;

import de.schuette.world.EntityPoint;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

		PolygonEntity o = new PolygonEntity(new Point(100, 100), new EntityPoint(-1, -1), new EntityPoint(1, -1),
				new EntityPoint(-1, 1), new EntityPoint(1, 1));
		o.setScale(30);

		AnimationTimer timer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				o.synchronize();

				// Animate
				o.rotate(1);
			}
		};
		timer.start();

		// sceneContent.addEventFilter(MouseEvent.MOUSE_MOVED, new
		// EventHandler<MouseEvent>() {
		// @Override
		// public void handle(MouseEvent mouseEvent) {
		// o.setPosition(mouseEvent.getX(), mouseEvent.getY());
		// mouseEvent.consume();
		// }
		// });

		sceneContent.getChildren().addAll(o);

		Scene scene = new Scene(sceneContent);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
