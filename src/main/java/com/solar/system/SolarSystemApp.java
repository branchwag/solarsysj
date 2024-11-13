package com.solar.system;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.PointLight;

public class SolarSystemApp extends Application { 

    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();

        Sphere sun = new Sphere(50);
        PhongMaterial sunMaterial = new PhongMaterial();
        sunMaterial.setDiffuseColor(Color.YELLOW);
        sunMaterial.setSelfIlluminationMap(sunMaterial.getDiffuseMap());
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

        root.getChildren().addAll(sun, earth, mars);

        PointLight pointLight = new PointLight(Color.WHITE);
        pointLight.setTranslateX(0);
        pointLight.setTranslateY(0);
        pointLight.setTranslateZ(-100);

        AmbientLight ambientLight = new AmbientLight(Color.rgb(255, 255, 255, 0.2));

        root.getChildren().addAll(pointLight, ambientLight);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-800);
        camera.setTranslateY(-50);
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setFieldOfView(35);

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, event -> {
                earth.setRotate(earth.getRotate() + 1);
                mars.setRotate(mars.getRotate() + 0.5);
                }),
                new KeyFrame(Duration.millis(1000.0 / 60)) //FPS
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Scene scene = new Scene(root, 800, 600, true);
        scene.setFill(Color.BLACK);
        scene.setCamera(camera);

        Platform.runLater(() -> {
            camera.setTranslateZ(-800);
            camera.setTranslateY(-50);
            });

        primaryStage.setTitle("The Solar System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
