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
public class GrowUp {
    private final int DURATION_ANIMATION = 250;
    private final double NORMAL_SIZE = 1.0;
    private final double GROWN_SIZE = 1.05;
    
    private ScaleTransition growUp;
    
    Node target;
    
    public GrowUp(Node target) {
        this.target = target;
        setupAnimation();
    }
    
    private void setupAnimation() {
        growUp = new ScaleTransition(Duration.millis(DURATION_ANIMATION), target);
        growUp.setInterpolator(Interpolator.EASE_BOTH);
        growUp.setFromX(NORMAL_SIZE);
        growUp.setFromY(NORMAL_SIZE);
        growUp.setToX(GROWN_SIZE);
        growUp.setToY(GROWN_SIZE);
    }
    
    public void Up() {
        growUp.play();
    }
  
}
