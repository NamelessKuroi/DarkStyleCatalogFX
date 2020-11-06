/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

//AnimateFX
import animatefx.animation.BounceIn;

//JavaFX
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 *
 * @author Nameless
 */
public class LifeLabel extends Label {
    
    private BounceIn animationBounce;
    
    public LifeLabel() {
        setupAnimation();
    }
    
    public LifeLabel(String text) {
        super(text);
        setupAnimation();
    }
    
    public LifeLabel(String text, Node graphic) {
        super(text, graphic);
        setupAnimation();
    }
    
    public void becomeLife(double initialDelay, double LazyDelay, double spareDelay) {
        setOpacity(0);
        animationBounce.setDelay(Duration.millis(initialDelay + LazyDelay + spareDelay));
        animationBounce.play();
        
    }
    
    private void setupAnimation() {
        animationBounce = new BounceIn(this);
        animationBounce.setSpeed(0.5);
    }
    
}
