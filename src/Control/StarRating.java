/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

//App
import static Utils.Display.*;

//AnimateFX
import animatefx.animation.AnimationFX;
import animatefx.animation.FadeInRight;

//FontAwesome
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

//Java
import java.util.ArrayList;

//JavaFX
import javafx.geometry.Pos;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;

import javafx.util.Duration;

/**
 *
 * @author Nameless
 */
public final class StarRating extends HBox {

  

    private int rating;
    private int quantityOfStars;

    private boolean goldenState;
    private boolean crimsonState;

    private ArrayList< FontAwesomeIconView> ribbonOfStars;

    private void initialize() {
        this.goldenState = false;
        this.crimsonState = false;
        this.ribbonOfStars = new ArrayList<>();
        this.setAlignment(Pos.CENTER);
        this.setSpacing(2);
    }

    public StarRating() {
        initialize();
        setQuantityOfStars(5);
        setRating(0);
    }

    public StarRating(int quantityOfStars) {
        initialize();
        setQuantityOfStars(quantityOfStars);
        setRating(0);

    }

    public StarRating(int quantityOfStars, int rating) {
        initialize();
        setQuantityOfStars(quantityOfStars);
        setRating(rating);
    }

    private void hideRibbonStars() {
        ribbonOfStars.forEach((star) -> {
            star.setOpacity(0);
        });
    }

    public void showRibbonStars(double initialDealy, double LazyDelay) {
        hideRibbonStars();
        double delayAnimation = initialDealy + LazyDelay;
        for (FontAwesomeIconView star : ribbonOfStars) {
            AnimationFX in = new FadeInRight(star);
            in.setDelay(new Duration(delayAnimation));
            in.play();
            delayAnimation += 200;
        }
    }

    public boolean isGoldenState() {
        return goldenState;
    }

    public boolean isCrimsonState() {
        return goldenState;
    }

    public void setGoldenState(boolean goldenState) {
        if (goldenState) {
            setRating(quantityOfStars + 1);
        } else {
            setRating(quantityOfStars);
        }
    }

    public void setCrimsonState(boolean crimsonState) {
        if (crimsonState) {
            setRating(quantityOfStars + 2);
        } else {
            setRating(quantityOfStars);
        }
    }

    private void determinateState() {
        if (rating == quantityOfStars + 1) {
            goldenState = true;
            crimsonState = false;
            drawSpecialState();
        } else if (rating == quantityOfStars + 2) {
            goldenState = false;
            crimsonState = true;
            drawSpecialState();
        } else {
            goldenState = false;
            crimsonState = false;
            updateRating(rating);
        }
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 0) {
            rating = 0;
        }

        if (rating > quantityOfStars + 2) {
            rating = quantityOfStars + 2;
        }

        this.rating = rating;

        determinateState();
    }

    public int getQuantityOfStars() {
        return quantityOfStars;
    }

    public void setQuantityOfStars(int quantityOfStars) {
        if (quantityOfStars < 1) {
            quantityOfStars = 1;
        }

        if (quantityOfStars > 10) {
            quantityOfStars = 10;
        }

        this.quantityOfStars = quantityOfStars;
        adjustRating();
    }

    private FontAwesomeIconView makeStar(int index) {
        FontAwesomeIconView star = new FontAwesomeIconView(FontAwesomeIcon.STAR_ALT);
        star.setUserData(index + 1);
        star.getStyleClass().setAll(ICON_GLYPH ,ICON_MIDDLE, ICON_STAR_UNMARKED , ICON_HOVERED, SHADOWED);
        star.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                rating = (Integer) star.getUserData();
                updateRating(rating);
            }
        });
        return star;
    }

    private void adjustRating() {
        this.ribbonOfStars.clear();
        this.getChildren().clear();
        for (int index = 0; index < quantityOfStars; index++) {
            ribbonOfStars.add(makeStar(index));
        }
        this.getChildren().addAll(ribbonOfStars);
    }

    public final void updateRating(int newRating) {

        for (int index = 0; index < newRating; index++) {
            ribbonOfStars.get(index).getStyleClass().setAll(ICON_GLYPH , ICON_BIG, ICON_STAR_MARKED , ICON_HOVERED, SHADOWED);
        }
        for (int index = newRating; index < quantityOfStars; index++) {
            ribbonOfStars.get(index).getStyleClass().setAll(ICON_GLYPH ,ICON_MIDDLE, ICON_STAR_UNMARKED , ICON_HOVERED, SHADOWED);
        }
        goldenState = false;
        crimsonState = false;
    }

    public final void drawSpecialState() {
        String specialState = "";

        if (goldenState) {
            specialState = ICON_STAR_GOLDEN;
        }

        if (crimsonState) {
            specialState = ICON_STAR_CRIMSON;
        }

        for (int index = 0; index < quantityOfStars; index++) {
            ribbonOfStars.get(index).getStyleClass().setAll(ICON_GLYPH, ICON_BIG , specialState , ICON_HOVERED, SHADOWED);
        }
    }

}
