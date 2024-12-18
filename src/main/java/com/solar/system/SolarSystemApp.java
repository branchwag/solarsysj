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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.PointLight;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

class Star extends Circle {
    private final Random random = new Random();
    private double baseOpacity;

    public Star(double x, double y, double z) {
        super(1.5, Color.WHITE);
        setTranslateX(x);
        setTranslateY(y);
        setTranslateZ(z);
        baseOpacity = 0.3 + random.nextDouble() * 0.7;
        setOpacity(baseOpacity);
    }

    public void twinkle() {
        double variation = (random.nextDouble() - 0.5) * 0.3;
        setOpacity(Math.min(1, Math.max(0.1, baseOpacity + variation)));
    }
}

public class SolarSystemApp extends Application { 

    private final double MERCURY_SPEED = 4.0;
    private final double VENUS_SPEED = 3.0;
    private final double EARTH_SPEED = 2.5;
    private final double MARS_SPEED = 2.0;
    private final double JUPITER_SPEED = 1.5;
    private final double SATURN_SPEED = 1.2;
    private final double URANUS_SPEED = 0.9;
    private final double NEPTUNE_SPEED = 0.7;

    private double angle = 0;
    private List<Star> stars = new ArrayList<>();
    private Random random = new Random();

    private void createStars(Group root) {
        final int NUM_STARS = 1000;
        final double STAR_DISTANCE = 10;

        double sceneWidth = 3200;
        double sceneHeight = 2800;

        double padding = 100;
        double width = sceneWidth + (padding * 2);
        double height = sceneHeight + (padding * 2);

        for (int i = 0; i < NUM_STARS; i++) {

            double x = (random.nextDouble() * width) - (width / 2);
            double y = (random.nextDouble() * height) - (height / 2);
            double z = STAR_DISTANCE + (random.nextDouble() - 0.5) * 200;

            Star star = new Star(x, y, z);

            double size = 0.5 + (random.nextDouble() * 2.0);
            star.setRadius(size);

            if (random.nextDouble() < 0.1) {
                Color starColor = switch(random.nextInt(3)) {
                    case 0 -> Color.rgb(255, 220, 220); //reddish
                    case 1 -> Color.rgb(220, 220, 255); //bluish
                    default -> Color.rgb(255, 255, 220); //yellowish
                };
                star.setFill(starColor);
            }

            stars.add(star);
            root.getChildren().add(star);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();

        createStars(root);

        Sphere sun = new Sphere(80);
        PhongMaterial sunMaterial = new PhongMaterial();
        sunMaterial.setDiffuseColor(Color.YELLOW);
        sunMaterial.setSelfIlluminationMap(sunMaterial.getDiffuseMap());
        sun.setMaterial(sunMaterial);

        Sphere mercury = createPlanet(10, Color.GRAY);
        Sphere venus = createPlanet(30, Color.BURLYWOOD);
        Sphere earth = createPlanet(20, Color.BLUE);
        Sphere mars = createPlanet(15, Color.RED);
        Sphere jupiter = createPlanet(50, Color.BROWN);
        Sphere saturn = createPlanet(40, Color.GOLDENROD);
        Sphere uranus = createPlanet(35, Color.CORNFLOWERBLUE);
        Sphere neptune = createPlanet(35, Color.BLUE);

        root.getChildren().addAll(sun, mercury, venus, earth, mars, jupiter, saturn, uranus, neptune);

        PointLight pointLight = new PointLight(Color.WHITE);
        pointLight.setTranslateZ(-100);
        AmbientLight ambientLight = new AmbientLight(Color.rgb(255, 255, 255, 0.1));
        root.getChildren().addAll(pointLight, ambientLight);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1800);
        camera.setTranslateY(-100);
        camera.setTranslateX(0);
        camera.setNearClip(0.1);
        camera.setFarClip(4000.0);
        camera.setFieldOfView(65);

        Timeline planetTimeline = new Timeline(
            new KeyFrame(Duration.ZERO, event -> {
                angle += 0.5;
                
                updatePlanetPosition(mercury, 100, MERCURY_SPEED * angle);
                updatePlanetPosition(venus, 200, VENUS_SPEED * angle);
                updatePlanetPosition(earth, 300, EARTH_SPEED * angle);
                updatePlanetPosition(mars, 400, MARS_SPEED * angle);
                updatePlanetPosition(jupiter, 600, JUPITER_SPEED * angle);
                updatePlanetPosition(saturn, 800, SATURN_SPEED * angle);
                updatePlanetPosition(uranus, 1100, URANUS_SPEED * angle);
                updatePlanetPosition(neptune, 1400, NEPTUNE_SPEED * angle);

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
        planetTimeline.setCycleCount(Timeline.INDEFINITE);
        planetTimeline.play();

        Timeline starTimeline = new Timeline(
            new KeyFrame(Duration.ZERO, event -> {
                for (int i = 0; i < 100; i++) {
                    stars.get(random.nextInt(stars.size())).twinkle();
                }
                }),
                new KeyFrame(Duration.millis(50))
        );

        starTimeline.setCycleCount(Timeline.INDEFINITE);
        starTimeline.play();

        Scene scene = new Scene(root, 1200, 800, true);
        scene.setFill(Color.BLACK);
        scene.setCamera(camera);

        primaryStage.setTitle("The Solar System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Sphere createPlanet(double radius, Color color) {
        Sphere planet = new Sphere(radius);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        planet.setMaterial(material);
        return planet;
    }

    private void updatePlanetPosition(Sphere planet, double radius, double angle) {
        double x = radius * Math.cos(Math.toRadians(angle));
        double z = radius * Math.sin(Math.toRadians(angle)) / 2;
        planet.setTranslateX(x);
        planet.setTranslateZ(z);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
