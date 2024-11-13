package com.solar.system;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SolarSystemApp extends Application { 

    @Override
    public void start(Stage primaryStage) {
        Sphere sun = new Sphere(50);
        PhongMaterial sunMaterial = new PhongMaterial();
        sunMaterial.setDiffuseColor(Color.YELLOW);
        sun.setMaterial(sunMaterial);

        Sphere earth = new Sphere(20);
        PhongMaterial earthMaterial = new PhongMaterial();
        earthMaterial.setDiffuseColor(Color.BLUE);
        earth.setMaterial(earthMaterial);
        earth.setTranslateX(200);

        Sphere mars = new Sphere(15);
        PhongMaterial marsMaterial = new PhongMaterial();
        marsMaterial.setDiffuseColor(Color.RED);
        mars.setMaterial(marsMaterial);
        mars.setTranslateX(300);

        Group root = new Group();
        root.getChildren().addAll(sun, earth, mars);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-500);

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0), event -> {
                earth.setRotate(earth.getRotate() + 1);
                mars.setRotate(mars.getRotate() + 0.5);
                }),
                new KeyFrame(Duration.seconds(1.0 / 60)) //FPS
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Scene scene = new Scene(root, 800, 600, true);
        scene.setFill(Color.BLACK);
        scene.setCamera(camera);

        primaryStage.setTitle("The Solar System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
