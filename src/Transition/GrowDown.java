/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transition;

//JavaFX
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author Nameless
 */
public class GrowDown {

    private final int DURATION_ANIMATION = 250;
    private final double NORMAL_SIZE = 1.0;
    private final double GROWN_SIZE = 1.05;

    private ScaleTransition growDown;

    Node target;

    public GrowDown(Node target) {
        this.target = target;
        setupAnimation();
    }

    private void setupAnimation() {
        growDown = new ScaleTransition(Duration.millis(DURATION_ANIMATION), target);
        growDown.setInterpolator(Interpolator.EASE_BOTH);
        growDown.setFromX(GROWN_SIZE);
        growDown.setFromY(GROWN_SIZE);
        growDown.setToX(NORMAL_SIZE);
        growDown.setToY(NORMAL_SIZE);

    }
    
    public void Down() {
        growDown.play();
    }
}
