package org.tek.module3_group_assignment;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.List;

/**
 * Elan: DroidAnimator class that builds an animation that moves droid through a list of waypoints
 */
public class DroidAnimator {

    private static final double SPEED = 120; // pixels per second

    public static Timeline buildAnimation(Node droid, List<Double> points) {
        Timeline timeline = new Timeline();

        double prevX = points.get(0);
        double prevY = points.get(1);
        double time = 0;

        // Start frame: jump to the first waypoint
        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO,
                new KeyValue(droid.layoutXProperty(), prevX),
                new KeyValue(droid.layoutYProperty(), prevY)));

        // One frame per waypoint; time is based on distance so the speed stays even
        for (int i = 2; i + 1 < points.size(); i += 2) {
            double x = points.get(i);
            double y = points.get(i + 1);
            time += Math.hypot(x - prevX, y - prevY) / SPEED;

            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(time),
                    new KeyValue(droid.layoutXProperty(), x),
                    new KeyValue(droid.layoutYProperty(), y)));

            prevX = x;
            prevY = y;
        }

        return timeline;
    }
}
