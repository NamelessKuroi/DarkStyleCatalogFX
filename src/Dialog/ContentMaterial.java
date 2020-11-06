/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

//App
import Model.Material.LightNovel;
import Model.Material.Material;
import Utils.Constants;
import Utils.Display;
import static Utils.Display.DIALOG_NORMAL;

import static Utils.Display.FitToolTip;
import static Utils.Display.ICON_HOVERED;
import static Utils.Display.ICON_SMALL;
import static Utils.Display.INDICATOR;
import static Utils.Display.NOT_INDICATOR;
import static Utils.Display.SHADOWED;
import static Utils.Display.FIELD;
import static Utils.Display.HEADING_NORMAL;
import static Utils.Display.SYNOPSIS;


//AnimateFX
import animatefx.animation.RotateOut;

//JFoenix
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXScrollPane;

//FontAwesome
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

//Java
import java.util.ArrayList;

//JavaFX
import javafx.geometry.Pos;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 *
 * @author Nameless
 */
public class ContentMaterial extends JFXDialogLayout {

    private Material material;

    private Label title = null;
    private Label synopsis = null;
    private Label chapter = null;
    private Label year = null;
    private Label country = null;

    private VBox infoMaterial = null;

    private ArrayList<Label> heading = null;
    private ArrayList<Label> headingExtras = null;

    private FlowPane paneKinds = null;
    private FlowPane paneStates = null;
    private FlowPane paneAuthors = null;
    private FlowPane paneStudios = null;

    private FlowPane paneExtras = null;

    private VBox boxYear = null;
    private VBox boxChapter = null;
    private VBox boxCountry = null;

    private FontAwesomeIconView iconClose = null;
    private ScrollPane totalscrollpane = null;

    ////////////////////////////////////////////////////////////////////
    //                       ENUM                                     //
    ///////////////////////////////////////////////////////////////////
    private final int HEADING_SYSNOPSIS = 0;
    private final int HEADING_STATES = 1;
    private final int HEADING_KINDS = 2;
    private final int HEADING_AUTHORS = 3;
    private final int HEADING_STUDIOS = 4;

    private final int HEADING_YEAR = 0;
    private final int HEADING_CHAPTER = 1;
    private final int HEADING_COUNTRY = 2;

    public ContentMaterial() {
        initialize();
        createLayout();
    }

    private void initialize() {
        material = null;
        initializeHeadings();
        initializeTitle();
        initializeSynopsis();
        initializeExtras();
        initializeInfoMaterial();
        clearInfo();

    }

    private void initializeHeadings() {
        heading = new ArrayList<>();

        heading.add(new Label("Synopsis"));
        heading.add(new Label("States"));
        heading.add(new Label("Kinds"));
        heading.add(new Label("Authors"));
        heading.add(new Label("Studios"));

        heading.forEach((head) -> {
            head.getStyleClass().add(FIELD);
        });

        headingExtras = new ArrayList<>();

        headingExtras.add(new Label("Year"));
        headingExtras.add(new Label("Chapter"));
        headingExtras.add(new Label("Country"));

        headingExtras.forEach((head) -> {
            head.getStyleClass().add(FIELD);
            head.setAlignment(Pos.CENTER);
        });
    }

    private void initializeTitle() {
        title = new Label();
        title.getStyleClass().addAll(HEADING_NORMAL, DIALOG_NORMAL);
    }

    private void initializeSynopsis() {
        synopsis = new Label();
        synopsis.getStyleClass().add(SYNOPSIS);
    }

    private void initializeExtras() {
        chapter = new Label();
        year = new Label();
        country = new Label();
    }

    private void initializeInfoMaterial() {

        infoMaterial = new VBox();
        infoMaterial.setAlignment(Pos.CENTER);
        infoMaterial.setSpacing(5);
        
        paneKinds = new FlowPane();
        paneKinds.setAlignment(Pos.CENTER);

        paneStates = new FlowPane();
        paneStates.setAlignment(Pos.CENTER);

        paneAuthors = new FlowPane();
        paneAuthors.setAlignment(Pos.CENTER);

        paneStudios = new FlowPane();
        paneStudios.setAlignment(Pos.CENTER);

        paneExtras = new FlowPane();
        paneExtras.setHgap(200);
        paneExtras.setAlignment(Pos.CENTER);

        boxYear = new VBox();
        boxYear.setAlignment(Pos.CENTER);

        boxChapter = new VBox();
        boxChapter.setAlignment(Pos.CENTER);

        boxCountry = new VBox();
        boxCountry.setAlignment(Pos.CENTER);

    }

    public void connectClose(JFXDialog dialog) {
        iconClose.setOnMouseClicked(event -> {
            dialog.close();

            RotateOut animationOut = new RotateOut(dialog);
            animationOut.setSpeed(2);
            animationOut.play();
        });
    }

    private void createLayout() {

        HBox headingTitle = new HBox();
        iconClose = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
        iconClose.getStyleClass().addAll(ICON_SMALL, ICON_HOVERED, SHADOWED);

        headingTitle.getChildren().add(title);
        headingTitle.getChildren().add(iconClose);
        setHeading(headingTitle);

        boxYear.getChildren().add(headingExtras.get(HEADING_YEAR));
        boxYear.getChildren().add(year);

        boxChapter.getChildren().add(headingExtras.get(HEADING_CHAPTER));
        boxChapter.getChildren().add(chapter);

        boxCountry.getChildren().add(headingExtras.get(HEADING_COUNTRY));
        boxCountry.getChildren().add(country);

        paneExtras.getChildren().add(boxYear);
        paneExtras.getChildren().add(boxChapter);
        paneExtras.getChildren().add(boxCountry);
        
        totalscrollpane = new ScrollPane(infoMaterial);
        totalscrollpane.setMaxHeight(Constants.HEIGHT_SCREEN * 0.70);
        JFXScrollPane.smoothScrolling(totalscrollpane);

        setBody(totalscrollpane);
    }

    private void clearInfo() {
        infoMaterial.getChildren().clear();
        paneKinds.getChildren().clear();
        paneStates.getChildren().clear();
        paneAuthors.getChildren().clear();
        paneStudios.getChildren().clear();
        chapter.setText(Display.INFO_NOT_AVAILABLE);
        year.setText(Display.INFO_NOT_AVAILABLE);
        country.setText(Display.INFO_NOT_AVAILABLE);
    }

    private void loadExtras() {

        if (material.getChapter() != 0) {
            chapter.setText(Integer.toString(material.getChapter()));
        }

        if (material.getYear() != 0) {
            year.setText(Integer.toString(material.getYear()));
        }

        if (material instanceof LightNovel) {
            String nameCountry = ((LightNovel) material).getCountry();
            if (!nameCountry.isEmpty()) {
                country.setText(nameCountry);
            }
        }
        infoMaterial.getChildren().add(paneExtras);
    }

    private void loadTitle() {
        title.setText(material.getTitle());

        if (!material.getSynopsis().isEmpty()) {
            synopsis.setText(material.getSynopsis());
            infoMaterial.getChildren().add(heading.get(HEADING_SYSNOPSIS));
            infoMaterial.getChildren().add(synopsis);
        }

    }

    private void loadKinds() {
        material.getKinds().forEach((kind) -> {
            Label name = new Label(kind.getName());
            if (!kind.getDescription().isEmpty()) {
                name.setTooltip(new Tooltip(FitToolTip(kind.getDescription())));
                name.getStyleClass().setAll(INDICATOR);
            } else {
                name.getStyleClass().add(NOT_INDICATOR);
            }
            paneKinds.getChildren().add(name);

        });

        if (!material.getKinds().isEmpty()) {
            infoMaterial.getChildren().add(heading.get(HEADING_KINDS));
            infoMaterial.getChildren().add(paneKinds);
        }
    }

    private void loadStates() {
        material.getStates().forEach((state) -> {
            Label name = new Label(state.getName());
            if (!state.getDescription().isEmpty()) {
                name.setTooltip(new Tooltip(FitToolTip(state.getDescription())));
                name.getStyleClass().setAll(INDICATOR);
            } else {
                name.getStyleClass().add(NOT_INDICATOR);
            }
            paneStates.getChildren().add(name);
        });

        if (!material.getStates().isEmpty()) {
            infoMaterial.getChildren().add(heading.get(HEADING_STATES));
            infoMaterial.getChildren().add(paneStates);
        }
    }

    private void loadAuthors() {
        material.getAuthors().forEach((author) -> {
            Label name = new Label(author.getName());
            name.getStyleClass().add(NOT_INDICATOR);
            paneAuthors.getChildren().add(name);
        });

        if (!material.getAuthors().isEmpty()) {
            infoMaterial.getChildren().add(heading.get(HEADING_AUTHORS));
            infoMaterial.getChildren().add(paneAuthors);
        }
    }

    private void loadStudios() {
        material.getStudios().forEach((studio) -> {
            Label name = new Label(studio.getName());
            if (!studio.getDescription().isEmpty()) {
                name.setTooltip(new Tooltip(FitToolTip(studio.getDescription())));
                name.getStyleClass().setAll(INDICATOR);
            } else {
                name.getStyleClass().add(NOT_INDICATOR);
            }
            paneStudios.getChildren().add(name);
        });

        if (!material.getStudios().isEmpty()) {
            infoMaterial.getChildren().add(heading.get(HEADING_STUDIOS));
            infoMaterial.getChildren().add(paneStudios);
        }

    }

    public void loadInfoMaterial(Material newMaterial) {
        material = newMaterial;
        clearInfo();
        loadTitle();
        loadStates();
        loadKinds();
        loadAuthors();
        loadStudios();
        loadExtras();
    }

}
