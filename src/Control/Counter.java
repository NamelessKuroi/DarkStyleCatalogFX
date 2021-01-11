/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

//App
import static Utils.Display.*;
import animatefx.animation.*;

//JFoenix
import com.jfoenix.controls.JFXTextField;

//FontAwesome
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

//JavaFX
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;

/**
 *
 * @author Nameless
 */
public final class Counter extends HBox {

    private int minimun_value;
    private int value;
    private int maximun_value;

    private FontAwesomeIconView increment;
    private FontAwesomeIconView decrement;

    private AnimationFX fadeInIncrement;
    private AnimationFX fadeInDecrement;

    private AnimationFX fadeOutIncrement;
    private AnimationFX fadeOutDecrement;

    private JFXTextField editor;

    public Counter(int minimun_value, int value, int maximun_value) {
        this.value = value;
        this.minimun_value = minimun_value;
        this.maximun_value = maximun_value;
        createControls();
        setupAnimation();
    }

    public void setupAnimation() {
        fadeInIncrement = new FadeIn(increment);
        fadeInDecrement = new FadeIn(decrement);

        fadeOutIncrement = new FadeOut(increment);
        fadeOutDecrement = new FadeOut(decrement);

    }

    public void createControls() {
        setSpacing(10);
        increment = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
        decrement = new FontAwesomeIconView(FontAwesomeIcon.MINUS_CIRCLE);

        increment.getStyleClass().addAll(ICON_NORMAL, ICON_HOVERED, SHADOWED);
        decrement.getStyleClass().addAll(ICON_NORMAL, ICON_HOVERED, SHADOWED);

        editor = new JFXTextField(Integer.toString(value));
        editor.getStyleClass().add("counter");

        editor.setOnKeyTyped((event) -> {
            String letters = event.getCharacter();
            for (int index = 0; index < letters.length(); index++) {
                Character letter = letters.charAt(index);
                if (!Character.isDigit(letter)) {
                    event.consume();
                }
                if (Character.isDigit(letter)) {
                    String number = editor.getText();
                    number += letter;
                    if (!validateRange(Integer.parseInt(number))) {
                        event.consume();
                    }
                }
            }

        });

        editor.setOnKeyReleased((event) -> {
            if (!editor.getText().isEmpty()) {
                value = Integer.parseInt(editor.getText());
            }
        });

        editor.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                hiddenControls();
                editor.clear();
            } else {
                if (editor.getText().isEmpty()) {
                    editor.setText(Integer.toString(value));
                }
                showControls();
            }
        });

        increment.setOnMouseClicked((event) -> {
            if (editor.getText().isEmpty()) {
                return;
            }

            if (event.getButton() == MouseButton.PRIMARY) {
                value = Integer.parseInt(editor.getText());
                if (value < maximun_value) {
                    value++;
                }
                editor.setText(Integer.toString(value));
            }
            event.consume();
        });

        decrement.setOnMouseClicked((event) -> {
            if (editor.getText().isEmpty()) {
                return;
            }

            if (event.getButton() == MouseButton.PRIMARY) {
                value = Integer.parseInt(editor.getText());
                if (value > minimun_value) {
                    value--;
                }
                editor.setText(Integer.toString(value));

            }
            event.consume();
        });
        
        this.getChildren().add(increment);
        this.getChildren().add(editor);
        this.getChildren().add(decrement);

    }

    private void hiddenControls() {
        fadeOutIncrement.play();
        fadeOutDecrement.play();
    }

    private void showControls() {
        fadeInIncrement.play();
        fadeInDecrement.play();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        editor.setText(Integer.toString(value));
    }

    public int getMinimunValue() {
        return minimun_value;
    }

    public void setMinimunValue(int minimun_value) {
        this.minimun_value = minimun_value;
    }

    public int getMaximunValue() {
        return maximun_value;
    }

    public void setMaximunValue(int maximun_value) {
        this.maximun_value = maximun_value;
    }

    public void setRange(int min, int max) {
        this.minimun_value = min;
        this.maximun_value = max;
    }

    public boolean validateRange(int number) {
        return number >= minimun_value && number <= maximun_value;
    }
}
