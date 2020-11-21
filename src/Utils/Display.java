/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

//Java
import java.io.File;
import java.net.MalformedURLException;

//JavaFX
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

//AnimateFX
import animatefx.animation.RotateOut;

//JFoenix
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;

//FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

/**
 *
 * @author Nameless
 */
public class Display {

    ////////////////////////////////////////////////////////////////////
    //                        STYLESHEET                              //
    ///////////////////////////////////////////////////////////////////
    public static final String MODE_CARD = "mode-card";
    public static final String MODE_TEXT = "mode-text";
    public static final String MODE_CHECK = "mode-check";
    public static final String MODE_CHECK_LARGE = "mode-check-large";

    public static final String LIGHT_OVERLAY = "light-overlay";

    public static final String DIALOG_DARK_STYLE = "dialog-dark-style";
    public static final String DIALOG_LIGHT_STYLE = "dialog-light-style";
    public static final String DIALOG_NOT_BACKGROUND = "dialog-not-background";

    public static final String ETIQUETTE_DIAGONAL = "etiquette-diagonal";
    public static final String ETIQUETTE_LATERAL = "etiquette-lateral";

    public static final String ETIQUETTE_YEAR = "etiquette-year";
    public static final String ETIQUETTE_STATE = "etiquette-state";
    public static final String ETIQUETTE_SUBSTATE = "etiquette-sub-state";
    public static final String ETIQUETTE_MATERIAL_TYPE = "etiquette-material-type";

    public static final String INDICATOR = "indicator";
    public static final String NOT_INDICATOR = "not-indicator";

    public static final String HEADLINE = "headline";

    public static final String HEADLINE_STANDARD = "headline-standard";
    public static final String HEADLINE_EMPHASIZE = "headline-emphasize";

    public static final String SLIDE_HEADLINE = "slide-headline";

    public static final String DIALOG_SMALL = "dialog-small";
    public static final String DIALOG_NORMAL = "dialog-normal";
    public static final String DIALOG_BIG = "dialog-big";

    public static final String HEADING_NORMAL = "heading-normal";
    public static final String HEADING_BIG = "heading-big";

    public static final String SYNOPSIS = "synopsis";
    public static final String FIELD = "field";

    public static final String BUTTON_SMALL = "button-small";
    public static final String BUTTON_CONFIGURATION = "button-configuration";
    public static final String BUTTON_OPTION = "button-option";
    public static final String BUTTON_ALERT = "button-alert";
    public static final String BUTTON_SIDEBAR = "button-sidebar";
    public static final String BUTTON_RADIAL = "button-radial";

    public static final String SHADOWED = "shadowed";

    public static final String ICON_HOVERED = "icon-hovered";

    public static final String ICON_GLYPH = "glyph-icon";

    public static final String ICON_CELL_MATERIAL = "icon-cell-material";
    public static final String ICON_SLIDESHOW = "icon-slideshow";

    public static final String ICON_SMALL = "icon-small";
    public static final String ICON_NORMAL = "icon-normal";
    public static final String ICON_MIDDLE = "icon-middle";
    public static final String ICON_BIG = "icon-big";
    public static final String ICON_BIGGER = "icon-bigger";
    public static final String ICON_BIGGEST = "icon-biggest";

    public static final String ICON_EXTRAS = "icon-extras";
    public static final String ICON_WITHOUT_FILTER = "icon-without-filter";

    public static final String ICON_STAR_UNMARKED = "icon-star-unmarked";
    public static final String ICON_STAR_MARKED = "icon-star-marked";
    public static final String ICON_STAR_GOLDEN = "icon-star-golden";
    public static final String ICON_STAR_CRIMSON = "icon-star-crimson";

    ////////////////////////////////////////////////////////////////////
    //                          Constants                             //
    ///////////////////////////////////////////////////////////////////
    public final static String INFO_NOT_AVAILABLE = "N / A";

    public final static byte TAB_FOR_ANIME = 0;
    public final static byte TAB_FOR_DONGHUA = 1;
    public final static byte TAB_FOR_LIGHTNOVEL = 2;

    
    public final static String ANY = "Any";
    public final static String ALL = "All";

    public final static double WIDTH_SPLASH = Constants.WIDTH_SCREEN * 0.58;
    public final static double HEIGHT_SPLASH = Constants.HEIGHT_SCREEN * 0.52;

    public final static int MINIMUN_CHAPTER = 0;
    public final static int MAXIMUN_CHAPTER = 5000;

    public final static int INITIAL_VALUE = 0;

    public final static int MAXIMUN_STAR = 5;

    public final static double SPARE_WIDTH_SCREEN = 100;
    public final static double SPARE_HEIGHT_SCREEN = 50;

    public final static double WIDTH_SCENE_SPLASH = WIDTH_SPLASH + SPARE_WIDTH_SCREEN;
    public final static double HEIGHT_SCENE_SPLASH = HEIGHT_SPLASH + SPARE_HEIGHT_SCREEN;

    public final static boolean SHOW_MATERIAL_TYPE = true;
    public final static boolean HIDE_MATERIAL_TYPE = false;

    public final static boolean UNTOUCHABLE = true;
    public final static boolean TOUCHABLE = false;

    public final static int HIDDEN = 0;

    private final static boolean PRESERVE_RATION = false;
    private final static boolean SMOOTH = true;

    private final static int HORIZONTAL_CELL_SPACING = 25 * 2;
    private final static int WIDTH_GRIDCELL = 210;

    private static final int WIDTH_CELL = WIDTH_GRIDCELL + HORIZONTAL_CELL_SPACING;
    private static final int DELAY_BETWEEN_GRIDCELL = 300;

    private final static String NON_IMAGE = "Resource/Non-Image.png";

    public final static Image NOT_PHOTO = new Image(NON_IMAGE, WIDTH_SPLASH, HEIGHT_SPLASH, PRESERVE_RATION, SMOOTH, false);

    public static double GridRowReady(double widthGridView) {
        int QuantityOfGridCellByRow = (int) (widthGridView / WIDTH_CELL);
        return QuantityOfGridCellByRow * DELAY_BETWEEN_GRIDCELL;
    }

    public static double AnimationDelayForCell(int index, double widthGridView) {
        int QuantityOfGridCellByRow = (int) (widthGridView / WIDTH_CELL);
        int ColumnOfGridCell = index % QuantityOfGridCellByRow;
        return ColumnOfGridCell * DELAY_BETWEEN_GRIDCELL;
    }

    public static double calculateCoordenatePolarForX(double radius, double angle) {
        return radius * Math.cos(Math.toRadians(angle));
    }

    public static double calculateCoordenatePolarForY(double radius, double angle) {
        return radius * Math.sin(Math.toRadians(angle));
    }

    public static HBox createHeadingForDialogRotateOut(Label title, JFXDialog dialog) {
        HBox heading = new HBox();
        FontAwesomeIconView iconClose = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
        iconClose.getStyleClass().addAll(ICON_SMALL, ICON_HOVERED, SHADOWED);
        iconClose.setOnMouseClicked(event -> {
            dialog.close();

            RotateOut animationOut = new RotateOut(dialog);
            animationOut.setSpeed(2);
            animationOut.play();
        });
        heading.getChildren().add(title);
        heading.getChildren().add(iconClose);
        return heading;
    }

    public static HBox createHeadingForDialog(Label title, JFXDialog dialog) {
        HBox heading = new HBox();
        FontAwesomeIconView iconClose = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
        iconClose.setOnMouseClicked(event -> dialog.close());
        iconClose.getStyleClass().addAll(ICON_SMALL, ICON_HOVERED, SHADOWED);

        heading.getChildren().add(title);
        heading.getChildren().add(iconClose);
        return heading;
    }

    public static HBox createHeadingForDialog(JFXTextField title, JFXDialog dialog) {
        HBox heading = new HBox();
        FontAwesomeIconView iconClose = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
        iconClose.getStyleClass().addAll(ICON_SMALL, ICON_HOVERED, SHADOWED);

        iconClose.setOnMouseClicked(event -> dialog.close());
        heading.getChildren().add(title);
        heading.getChildren().add(iconClose);
        return heading;
    }

    public static void toProcessImagen(Image photo, ImageView view) {

        if (photo.isBackgroundLoading()) {
            photo.errorProperty().addListener((ObservableValue<? extends Boolean> prop, Boolean oldValue, Boolean newValue) -> {
                if (newValue) {
                    view.setImage(NOT_PHOTO);
                }
            });
        } else if (photo.isError()) {
            view.setImage(NOT_PHOTO);
        }
    }

    public static String getAddressPhoto(String FilePhoto) {
        File file = new File(FilePhoto);
        try {
            return file.toURI().toURL().toString();
        } catch (MalformedURLException ex) {
            System.out.println("MalformedURL");
        }
        return "";
    }

    public static String FitToolTip(String text) {
        String fitText = "";

        String[] blocks = text.split("\n");

        for (String block : blocks) {
            fitText += buildBlock(block);
            fitText += "\n";
        }
        return fitText;

    }

    public static String buildBlock(String block) {
        String[] tokens = block.split(" ");
        String fitTokens = "";

        int QuantityOfLetterbyWord = 6;
        int QuantityOfWordbySentence = 15;
        int lengthSentence = QuantityOfLetterbyWord * QuantityOfWordbySentence;
        int currentQuantityOfLetter = 0;

        for (String token : tokens) {
            if (currentQuantityOfLetter + token.length() < lengthSentence) {

                fitTokens += token;
                fitTokens += " ";
                currentQuantityOfLetter += token.length();

            } else {
                currentQuantityOfLetter = token.length();
                fitTokens += "\n";
                fitTokens += token;
                fitTokens += " ";
            }
        }
        return fitTokens;
    }

}
