/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

//AnimateFX
import animatefx.animation.Pulse;

//JFoenix
import com.jfoenix.controls.JFXButton;

//JavaFX
import javafx.animation.Animation;
import javafx.scene.Node;

/**
 *
 * @author Nameless
 */
public class PulseButton extends JFXButton {

    private Pulse animation;

    public PulseButton() {
        createPulse();
        DoAnimation();
    }

    public PulseButton(String text) {
        super(text);
        createPulse();
        DoAnimation();
    }

    public PulseButton(String text, Node graphic) {
        super(text, graphic);
        createPulse();
        DoAnimation();

    }

    private void createPulse() {
        animation = new Pulse(this);
    }

    private void DoAnimation() {
        setOnMouseEntered((event) -> {
            animation.setCycleCount(Animation.INDEFINITE);
            animation.setSpeed(2);
            animation.play();
        });

        setOnMouseExited((event) -> {
            animation.stop();
            setScaleX(1);
            setScaleY(1);

        });

        setOnMouseClicked((event) -> {
            animation.stop();
            setScaleX(1);
            setScaleY(1);
            event.consume();
        });

    }
}
