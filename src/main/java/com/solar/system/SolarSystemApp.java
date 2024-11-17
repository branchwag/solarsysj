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

        Sphere sun = new Sphere(80);
        PhongMaterial sunMaterial = new PhongMaterial();
        sunMaterial.setDiffuseColor(Color.YELLOW);
        sunMaterial.setSelfIlluminationMap(sunMaterial.getDiffuseMap());
        sun.setMaterial(sunMaterial);

        Sphere mercury = new Sphere(10);
        PhongMaterial mercuryMaterial = new PhongMaterial();
        mercuryMaterial.setDiffuseColor(Color.GRAY);
        mercuryMaterial.setSelfIlluminationMap(mercuryMaterial.getDiffuseMap());
        mercury.setMaterial(mercuryMaterial);
        mercury.setTranslateX(100);

        Sphere venus = new Sphere(30);
        PhongMaterial venusMaterial = new PhongMaterial();
        venusMaterial.setDiffuseColor(Color.BURLYWOOD);
        venusMaterial.setSelfIlluminationMap(venusMaterial.getDiffuseMap());
        venus.setMaterial(venusMaterial);
        venus.setTranslateX(200);

        Sphere earth = new Sphere(20);
        PhongMaterial earthMaterial = new PhongMaterial();
        earthMaterial.setDiffuseColor(Color.BLUE);
        earth.setMaterial(earthMaterial);
        earth.setTranslateX(300);

        Sphere mars = new Sphere(15);
        PhongMaterial marsMaterial = new PhongMaterial();
        marsMaterial.setDiffuseColor(Color.RED);
        mars.setMaterial(marsMaterial);
        mars.setTranslateX(400);

        Sphere jupiter = new Sphere(50);
        PhongMaterial jupiterMaterial = new PhongMaterial();
        jupiterMaterial.setDiffuseColor(Color.BROWN);
        jupiter.setMaterial(jupiterMaterial);
        jupiter.setTranslateX(600);

        Sphere saturn = new Sphere(40);
        PhongMaterial saturnMaterial = new PhongMaterial();
        saturnMaterial.setDiffuseColor(Color.GOLDENROD);
        saturn.setMaterial(saturnMaterial);
        saturn.setTranslateX(800);

        Sphere uranus = new Sphere(35);
        PhongMaterial uranusMaterial = new PhongMaterial();
        uranusMaterial.setDiffuseColor(Color.CORNFLOWERBLUE);
        uranus.setMaterial(uranusMaterial);
        uranus.setTranslateX(1100);

        Sphere neptune = new Sphere(35);
        PhongMaterial neptuneMaterial = new PhongMaterial();
        neptuneMaterial.setDiffuseColor(Color.BLUE);
        neptune.setMaterial(neptuneMaterial);
        neptune.setTranslateX(1400);

        root.getChildren().addAll(sun, mercury, venus, earth, mars, jupiter, saturn, uranus, neptune);

        PointLight pointLight = new PointLight(Color.WHITE);
        pointLight.setTranslateX(0);
        pointLight.setTranslateY(0);
        pointLight.setTranslateZ(-100);

        AmbientLight ambientLight = new AmbientLight(Color.rgb(255, 255, 255, 0.1));

        root.getChildren().addAll(pointLight, ambientLight);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-2000);
        camera.setTranslateY(-200);
        camera.setTranslateX(700);
        camera.setNearClip(0.1);
        camera.setFarClip(4000.0);
        camera.setFieldOfView(65);

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, event -> {
                mercury.setRotate(mercury.getRotate() + 4);
                venus.setRotate(venus.getRotate() + 5);
                earth.setRotate(earth.getRotate() + 0.3);
                mars.setRotate(mars.getRotate() + 0.3);
                jupiter.setRotate(jupiter.getRotate() + 0.1);
                saturn.setRotate(saturn.getRotate() + 0.1);
                uranus.setRotate(uranus.getRotate() + 0.2);
                neptune.setRotate(neptune.getRotate() + 0.2);
                }),
                new KeyFrame(Duration.millis(1000.0 / 60)) //FPS
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Scene scene = new Scene(root, 1200, 600, true);
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
