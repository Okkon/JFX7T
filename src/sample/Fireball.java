package sample;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


public class Fireball extends ShotAction {
    public Fireball(int minDam, int randDam, int maxDistance) {
        super(maxDistance, minDam, randDam);
    }

    @Override
    protected Shell createShell() {
        return new Shell() {
            @Override
            public void hit(GObject obj) {
                List<GObject> list = new ArrayList<GObject>();
                list.add(obj);
                list.addAll(GameModel.MODEL.getNearUnits(obj.getPlace()));
                for (GObject object : list) {
                    final Hit hit = Hit.createHit(attacker, object, minDamage, maxDamage, damageType);
                    object.takeHit(hit);
                }
                obj.push(direction);

            }
        };
    }

    @Override
    protected void configureShell(Shell shell) {
        shell.setName("Fireball");
        shell.setDamageType(DamageType.MAGIC);
        shell.setVisualizer(new ShellVisualizer() {
            @Override
            public void step(GameCell cell, GameCell nextCell) {
                final BoardCell v = (BoardCell) cell.getVisualizer();
                final Circle circle = new Circle(
                        v.getWidth() / 2,
                        v.getHeight() / 2,
                        Math.max(v.getWidth(), v.getHeight()) / 3);
                v.getChildren().add(circle);
                circle.setFill(Color.PERU);
                circle.setEffect(new Glow(15));


                Path path = new Path();
                path.getElements().add(new MoveTo(50f, 50f));
                path.getElements().add(new CubicCurveTo(10, 10, 400, 10, 400, 400));
                PathTransition pathTransition = new PathTransition();
                pathTransition.setDuration(Duration.millis(10000));
                pathTransition.setNode(circle);
                pathTransition.setPath(path);
                pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                pathTransition.setCycleCount(1);
                pathTransition.setAutoReverse(true);
                pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                    }
                });

                pathTransition.play();
            }
        });
    }
}
