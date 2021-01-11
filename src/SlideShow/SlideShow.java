/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SlideShow;

//App
import Manager.SplashScreenManager;
import Utils.Constants;
import Utils.Display;
import static Utils.Display.*;

//AnimateFX
import animatefx.animation.*;

//JFoenix
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

//FontAwesome
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

//Java
import java.io.File;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

//JavaFX
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import javafx.stage.Stage;

/**
 *
 * @author Nameless
 */
public class SlideShow {

    public final static int DELETE_TRASH = 1;
    public final static int DELETE_ADD = 2;
    public final static int DELETE_MODELSPLASH = 4;
   

    private final int DELAY_TIMER = 1000;

    private final int WIDTH_SLIDE = 800;
    private final int HEIGHT_SLIDE = 550;

    private final int ONE_SECOND = 1000;
    private final int DURATION_SLIDE = 3 * ONE_SECOND;

    private boolean unlockSkipButtons = true;

    private boolean doPlay = false;

    private int currentSlide = 0;

    private VBox slideLayout = null;

    private HBox heading = null;
    private HBox bodyContent = null;

    private ImageView photo = null;
    private Label title = null;
    private Label footNote = null;
    private Timer playSlider = null;

    private FontAwesomeIconView iconOfModeSplash = null;
    private FontAwesomeIconView iconOfNext = null;
    private FontAwesomeIconView iconOfBack = null;
    private FontAwesomeIconView iconOfPlayOrPause = null;
    private FontAwesomeIconView iconOfClose = null;
    private FontAwesomeIconView iconOfTrash = null;
    private FontAwesomeIconView iconOfAdd = null;

    private JFXDialog dialogSlide = null;
    private JFXDialogLayout contentSlide = null;

    private ObservableList<Slide> slides = null;

    private Stage currentStage = null;
    
  
    private void initialize() {
        this.currentSlide = 0;
        this.dialogSlide.getStyleClass().add(DIALOG_NOT_BACKGROUND);
        this.slides = FXCollections.observableArrayList();

        this.iconOfModeSplash = new FontAwesomeIconView(FontAwesomeIcon.RANDOM);
        this.iconOfClose = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE_ALT);
        this.iconOfPlayOrPause = new FontAwesomeIconView(FontAwesomeIcon.PLAY);
        this.iconOfAdd = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
        this.iconOfTrash = new FontAwesomeIconView(FontAwesomeIcon.TRASH);

        Arrays.asList(iconOfModeSplash, iconOfTrash, iconOfAdd, iconOfPlayOrPause, iconOfClose)
                .forEach(icon -> icon.getStyleClass().addAll(ICON_SLIDESHOW, ICON_BIG, SHADOWED));

        this.iconOfNext = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_DOUBLE_RIGHT);
        this.iconOfBack = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_DOUBLE_LEFT);

        this.iconOfNext.getStyleClass().addAll(ICON_SLIDESHOW, ICON_BIGGEST, SHADOWED);
        this.iconOfBack.getStyleClass().addAll(ICON_SLIDESHOW, ICON_BIGGEST, SHADOWED);

        this.iconOfNext.setOpacity(HIDDEN);
        this.iconOfBack.setOpacity(HIDDEN);

        this.title = new Label();
        this.title.getStyleClass().addAll(SLIDE_HEADLINE, HEADLINE_STANDARD, SHADOWED);

        this.footNote = new Label();
        this.footNote.getStyleClass().addAll(SLIDE_HEADLINE, HEADLINE_STANDARD, SHADOWED);

        this.photo = new ImageView();

        this.slideLayout = new VBox(title, photo, footNote);
        this.slideLayout.setSpacing(5);
        this.slideLayout.setAlignment(Pos.CENTER);

        createLayout();
        setupEvents();
        loadModeSplash();
   
    
    }

    public SlideShow(StackPane mainPane) {
        this.contentSlide = new JFXDialogLayout();
        this.dialogSlide = new JFXDialog(mainPane, contentSlide, Constants.TRANSITION, Constants.DIALOG_CLOSED_BY_ACTION);
        initialize();

    }

    public SlideShow(StackPane mainPane, Stage currentStage) {
        this.currentStage = currentStage;
        this.contentSlide = new JFXDialogLayout();
        this.dialogSlide = new JFXDialog(mainPane, contentSlide, Constants.TRANSITION, Constants.DIALOG_CLOSED_BY_ACTION);
        initialize();
    }

    private void createLayout() {

        this.heading = new HBox(iconOfModeSplash, iconOfTrash, iconOfAdd, iconOfPlayOrPause, iconOfClose);
        this.heading.setSpacing(30);
        this.heading.setMaxHeight(60);
        this.heading.setAlignment(Pos.CENTER);

        this.bodyContent = new HBox(iconOfBack, slideLayout, iconOfNext);
        this.bodyContent.setAlignment(Pos.CENTER);
        this.bodyContent.setSpacing(30);

        contentSlide.setHeading(heading);
        contentSlide.setBody(bodyContent);

    }

    public void disableEdditable(int Option) {
        if ((Option & DELETE_TRASH) == DELETE_TRASH) {
            heading.getChildren().remove(iconOfTrash);
        }
        if ((Option & DELETE_ADD) == DELETE_ADD) {
            heading.getChildren().remove(iconOfAdd);
        }
        if ((Option & DELETE_MODELSPLASH) == DELETE_MODELSPLASH) {
            heading.getChildren().remove(iconOfModeSplash);
        }

    }

    public void setCurrentSlide(int currentSlide) {
        this.currentSlide = currentSlide;
    }

    public void showSlides() {

        Arrays.asList(iconOfModeSplash, iconOfTrash, iconOfAdd, iconOfPlayOrPause, iconOfClose, iconOfNext, iconOfBack)
                .forEach(icon -> {
                    icon.setOpacity(HIDDEN);
                });

        if (slides.isEmpty()) {
            loadEmptySlide();
        } else {
            loadSlide(slides.get(currentSlide));
        }

        dialogSlide.show();
        AnimationFX animation = new BounceInDown(dialogSlide);
        animation.play();
    }

    private void setupEvents() {

        heading.setOnMouseEntered((event) -> {
            showHeading();
            event.consume();

        });

        heading.setOnMouseExited((event) -> {
            hideHeading();
            event.consume();
        });

        iconOfNext.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                if (slides.isEmpty()) {
                    loadEmptySlide();
                } else {
                    nextSlide();
                }
            }
        });

        iconOfBack.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                if (slides.isEmpty()) {
                    loadEmptySlide();
                } else {
                    backSlide();
                }
            }
        });

        iconOfPlayOrPause.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                doPlay = !doPlay;
                if (doPlay) {

                    unlockSkipButtons = false;

                    hideControlsToEdit();
                    iconOfPlayOrPause.setIcon(FontAwesomeIcon.PAUSE);
                    playSlider = new Timer();
                    playSlider.schedule(
                            new TimerTask() {
                        @Override
                        public void run() {
                            nextSlide();
                        }
                    }, DELAY_TIMER, DURATION_SLIDE);

                } else {
                    iconOfPlayOrPause.setIcon(FontAwesomeIcon.PLAY);
                    playSlider.cancel();
                    showControlsToEdit();
                    unlockSkipButtons = true;

                }
            }
        });

        iconOfClose.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                AnimationFX animation = new BounceOutUp(dialogSlide);
                animation.play();
                if (playSlider != null) {
                    playSlider.cancel();
                }
                animation.getTimeline().setOnFinished((e) -> {
                    dialogSlide.close();
                });
            }
        });

        iconOfAdd.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                if (currentStage == null) {
                    return;
                }
                Slide temp = SplashScreenManager.addSplash(currentStage);
                if (temp != null) {
                    slides.add(currentSlide, temp);
                    loadSlide(slides.get(currentSlide));

                    Arrays.asList(iconOfModeSplash, iconOfNext, iconOfBack, iconOfTrash, iconOfPlayOrPause)
                            .forEach(icon -> {
                                icon.setVisible(true);
                            });
                    unlockSkipButtons = true;
                }
            }
        });

        iconOfTrash.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                File photoTemp = new File(slides.get(currentSlide).getPhotoAddress());
                if (slides.isEmpty()) {
                    loadEmptySlide();
                } else {
                    slides.remove(currentSlide);
                    photoTemp.delete();
                    currentSlide--;
                    nextSlide();
                }
            }
        });

        iconOfModeSplash.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 1) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    changeModeSplash();
                }

                if (event.getButton() == MouseButton.SECONDARY) {
                    if (SplashScreenManager.ModeSplash() == SplashScreenManager.FIXED) {
                        if (currentSlide != SplashScreenManager.getCurrentSplash()) {
                            iconOfModeSplash.setIcon(FontAwesomeIcon.LOCK);
                            SplashScreenManager.setCurrentSplash(currentSlide);
                        }
                    }
                }
            }
        });

        dialogSlide.setOnDialogOpened((event) -> {
            dialogSlide.requestFocus();
            AnimationFX inNext = new ZoomIn(iconOfNext);
            AnimationFX inBack = new ZoomIn(iconOfBack);
            inNext.play();
            inBack.play();
        });

        dialogSlide.setOnKeyPressed((event) -> {
          
            if (unlockSkipButtons) {
                if (event.getCode() == KeyCode.LEFT) {
                    backSlide();
                }
                if (event.getCode() == KeyCode.RIGHT) {
                    nextSlide();
                }
            }
            event.consume();
        });
    }

    private void nextSlide() {

        AnimationFX out = new BounceOutLeft(slideLayout);
        out.play();
        hideLayout();
        out.getTimeline().setOnFinished((e) -> {
            if (slides.isEmpty()) {
                loadEmptySlide();
            } else {
                currentSlide = nextPosition(currentSlide, slides.size());
                loadSlide(slides.get(currentSlide));
            }
            AnimationFX in = new BounceInRight(slideLayout);

            in.play();

            showLayout();
            dialogSlide.requestFocus();
        });

    }

    private void backSlide() {

        AnimationFX out = new BounceOutRight(slideLayout);
        out.play();
        hideLayout();
        out.getTimeline().setOnFinished((e) -> {

            if (slides.isEmpty()) {
                loadEmptySlide();
            } else {
                currentSlide = backPosition(currentSlide, slides.size());
                loadSlide(slides.get(currentSlide));
            }

            AnimationFX in = new BounceInLeft(slideLayout);
            in.play();
            showLayout();

            dialogSlide.requestFocus();
        });

    }

    private int backPosition(int currentPosition, int length) {
        return (currentPosition + length - 1) % length;
    }

    private int nextPosition(int currentPosition, int length) {
        return (currentPosition + 1) % length;
    }

    private void loadSlide(Slide slider) {
        Image tempImage = new Image(Display.getAddressPhoto(slider.getPhotoAddress()),
                WIDTH_SLIDE, HEIGHT_SLIDE, Constants.PRESERVE_RATION, Constants.SMOOTH, false);
        photo.setImage(tempImage);
        Display.toProcessImagen(tempImage, photo);
        photo.setSmooth(Constants.SMOOTH);
        photo.setPreserveRatio(Constants.PRESERVE_RATION);

        photo.setFitWidth(WIDTH_SLIDE);
        photo.setFitHeight(HEIGHT_SLIDE);
        title.setText(slider.getTitle());
        footNote.setText(String.format("%s / %s", currentSlide % slides.size() + 1, slides.size()));

        Rectangle clip = new Rectangle(
                photo.getFitWidth(), photo.getFitHeight()
        );
        clip.setArcWidth(50);
        clip.setArcHeight(50);
        photo.setClip(clip);
        if (SplashScreenManager.ModeSplash() == SplashScreenManager.FIXED) {
            if (currentSlide == SplashScreenManager.getCurrentSplash()) {
                iconOfModeSplash.setIcon(FontAwesomeIcon.LOCK);
            } else {
                iconOfModeSplash.setIcon(FontAwesomeIcon.PHOTO);
            }
        }
    }

    private void loadEmptySlide() {
        unlockSkipButtons = false;
        title.setText("");
        footNote.setText("");
        photo.setImage(Display.NOT_PHOTO);
        photo.setSmooth(Constants.SMOOTH);
        photo.setPreserveRatio(Constants.PRESERVE_RATION);
        photo.setFitWidth(WIDTH_SLIDE);
        photo.setFitHeight(HEIGHT_SLIDE);

        Arrays.asList(iconOfModeSplash, iconOfTrash, iconOfPlayOrPause, iconOfNext, iconOfBack)
                .forEach(icon -> {
                    icon.setVisible(false);
                });
        currentSlide = 0;
    }

    public void setSlides(ObservableList<Slide> slides) {
        slides.removeIf(t -> t.getPhotoAddress().isEmpty());
        this.slides = slides;
    }

    void showHeading() {

        Arrays.asList(iconOfModeSplash, iconOfTrash, iconOfAdd, iconOfPlayOrPause, iconOfClose)
                .forEach(icon -> {
                    icon.setMouseTransparent(UNTOUCHABLE);
                    AnimationFX animateIn = new ZoomIn(icon);
                    animateIn.getTimeline().setOnFinished((e) -> {
                        icon.setMouseTransparent(TOUCHABLE);
                    });
                    animateIn.setSpeed(2);
                    animateIn.play();
                });
    }

    void hideHeading() {
        Arrays.asList(iconOfModeSplash, iconOfTrash, iconOfAdd, iconOfPlayOrPause, iconOfClose)
                .forEach(icon -> {
                    icon.setMouseTransparent(UNTOUCHABLE);
                    AnimationFX animateOut = new ZoomOut(icon);
                    animateOut.getTimeline().setOnFinished((e) -> {
                        icon.setMouseTransparent(TOUCHABLE);
                    });
                    animateOut.setSpeed(2);
                    animateOut.play();
                });

    }

    void showControlsToEdit() {
        Arrays.asList(iconOfModeSplash, iconOfTrash, iconOfAdd, iconOfClose, iconOfBack, iconOfNext)
                .forEach(icon -> {
                    icon.setMouseTransparent(UNTOUCHABLE);
                    icon.setOpacity(HIDDEN);
                    icon.setVisible(true);
                    AnimationFX animateIn = new ZoomIn(icon);
                    animateIn.getTimeline().setOnFinished((e) -> {
                        icon.setMouseTransparent(TOUCHABLE);
                    });
                    animateIn.setSpeed(2);
                    animateIn.play();
                });

    }

    void hideControlsToEdit() {

        Arrays.asList(iconOfModeSplash, iconOfTrash, iconOfAdd, iconOfClose, iconOfBack, iconOfNext)
                .forEach(icon -> {
                    icon.setMouseTransparent(UNTOUCHABLE);
                    AnimationFX animateOut = new ZoomOut(icon);
                    animateOut.getTimeline().setOnFinished((e) -> {
                        icon.setMouseTransparent(TOUCHABLE);
                        icon.setVisible(false);
                    });
                    animateOut.setSpeed(2);
                    animateOut.play();
                });

    }

    public void showLayout() {
        AnimationFX zoomIn = new ZoomIn(slideLayout);
        zoomIn.play();
    }

    public void hideLayout() {
        AnimationFX zoomOut = new ZoomOut(slideLayout);
        zoomOut.setSpeed(0.5);
        zoomOut.play();
    }

    public ImageView getPhoto() {
        return photo;
    }

    public int getCurrentSlide() {
        return currentSlide;
    }

    private void changeModeSplash() {
        switch (SplashScreenManager.ModeSplash()) {
            case SplashScreenManager.RANDOM: {
                SplashScreenManager.changeModeSplash(SplashScreenManager.SEQUENTIAL);
                SplashScreenManager.setCurrentSplash(currentSlide);
            }
            break;
            case SplashScreenManager.SEQUENTIAL: {
                SplashScreenManager.changeModeSplash(SplashScreenManager.FIXED);
            }
            break;
            case SplashScreenManager.FIXED: {
                SplashScreenManager.changeModeSplash(SplashScreenManager.RANDOM);

            }
            break;
        }
        loadModeSplash();
    }

    private void loadModeSplash() {
        switch (SplashScreenManager.ModeSplash()) {
            case SplashScreenManager.RANDOM: {
                iconOfModeSplash.setIcon(FontAwesomeIcon.RANDOM);
            }
            break;
            case SplashScreenManager.SEQUENTIAL: {
                iconOfModeSplash.setIcon(FontAwesomeIcon.RETWEET);
                
            }
            break;

            case SplashScreenManager.FIXED: {
                iconOfModeSplash.setIcon(FontAwesomeIcon.LOCK);
            }
            break;

        }
    }
}
