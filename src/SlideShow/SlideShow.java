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
    public final static int DELETE_RANDOM = 4;
    
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
    
    private FontAwesomeIconView randomIcon = null;
    private FontAwesomeIconView nextIcon = null;
    private FontAwesomeIconView backIcon = null;
    private FontAwesomeIconView playOrPauseIcon = null;
    private FontAwesomeIconView closeIcon = null;
    private FontAwesomeIconView trashIcon = null;
    private FontAwesomeIconView addIcon = null;
    
    private JFXDialog dialogSlide = null;
    private JFXDialogLayout contentSlide = null;
    
    private ObservableList<Slide> slides = null;
    
    private Stage currentStage = null;
    
    private void initialize() {
        this.currentSlide = 0;
        this.dialogSlide.getStyleClass().add(DIALOG_NOT_BACKGROUND);
        this.slides = FXCollections.observableArrayList();
        
        this.randomIcon = new FontAwesomeIconView(FontAwesomeIcon.RANDOM);
        this.closeIcon = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE_ALT);
        this.playOrPauseIcon = new FontAwesomeIconView(FontAwesomeIcon.PLAY);
        this.addIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
        this.trashIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
        
        Arrays.asList(randomIcon, trashIcon, addIcon, playOrPauseIcon, closeIcon)
                .forEach(icon -> icon.getStyleClass().addAll(ICON_SLIDESHOW, ICON_BIG, SHADOWED));
        
        this.nextIcon = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_DOUBLE_RIGHT);
        this.backIcon = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_DOUBLE_LEFT);
        
        this.nextIcon.getStyleClass().addAll(ICON_SLIDESHOW, ICON_BIGGEST, SHADOWED);
        this.backIcon.getStyleClass().addAll(ICON_SLIDESHOW, ICON_BIGGEST, SHADOWED);
        
        this.nextIcon.setOpacity(HIDDEN);
        this.backIcon.setOpacity(HIDDEN);
        
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
        this.heading = new HBox(randomIcon, trashIcon, addIcon, playOrPauseIcon, closeIcon);
        this.heading.setSpacing(30);
        this.heading.setMaxHeight(60);
        this.heading.setAlignment(Pos.CENTER);
        
        this.bodyContent = new HBox(backIcon, slideLayout, nextIcon);
        this.bodyContent.setAlignment(Pos.CENTER);
        this.bodyContent.setSpacing(30);
        
       
        contentSlide.setHeading(heading);
        contentSlide.setBody(bodyContent);
        
    }
    
    public void disableEdditable(int Option) {
        if ((Option & DELETE_TRASH) == DELETE_TRASH) {
            heading.getChildren().remove(trashIcon);
        }
        if ((Option & DELETE_ADD) == DELETE_ADD) {
            heading.getChildren().remove(addIcon);
        }
        if ((Option & DELETE_RANDOM) == DELETE_RANDOM) {
            heading.getChildren().remove(randomIcon);
        }
    }
    
    public void setCurrentSlide(int currentSlide) {
        this.currentSlide = currentSlide;
    }
    
    public ObservableList<Slide> getSliders() {
        return slides;
    }
    
    public void showSlides() {
        
        Arrays.asList(randomIcon, trashIcon, addIcon, playOrPauseIcon, closeIcon, nextIcon, backIcon)
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
        
        nextIcon.setOnMouseClicked((event) -> {
            if (slides.isEmpty()) {
                loadEmptySlide();
            } else {
                nextSlide();
            }
        });
        
        backIcon.setOnMouseClicked((event) -> {
            if (slides.isEmpty()) {
                loadEmptySlide();
            } else {
                backSlide();
            }
        });
        
        playOrPauseIcon.setOnMouseClicked((event) -> {
            doPlay = !doPlay;
            if (doPlay) {
                
                unlockSkipButtons = false;
                
                hideControlsToEdit();
                playOrPauseIcon.setIcon(FontAwesomeIcon.PAUSE);
                playSlider = new Timer();
                playSlider.schedule(
                        new TimerTask() {
                    @Override
                    public void run() {
                        nextSlide();
                    }
                }, DELAY_TIMER, DURATION_SLIDE);
                
            } else {
                playOrPauseIcon.setIcon(FontAwesomeIcon.PLAY);
                playSlider.cancel();
                showControlsToEdit();
                unlockSkipButtons = true;
                
            }
        });
        
        closeIcon.setOnMouseClicked((event) -> {
            
            AnimationFX animation = new BounceOutUp(dialogSlide);
            animation.play();
            if (playSlider != null) {
                playSlider.cancel();
            }
            animation.getTimeline().setOnFinished((e) -> {
                dialogSlide.close();
            });
            
        });
        
        addIcon.setOnMouseClicked((event) -> {
            if (currentStage == null) {
                return;
            }
            Slide temp = SplashScreenManager.addSplash(currentStage);
            if (temp != null) {
                slides.add(currentSlide, temp);
                loadSlide(slides.get(currentSlide));
                
                Arrays.asList(randomIcon, nextIcon, backIcon, trashIcon, playOrPauseIcon)
                        .forEach(icon -> {
                            icon.setVisible(true);
                        });
                unlockSkipButtons = true;
            }
        });
        
        trashIcon.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY) {
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
            event.consume();
        });
        
        switch (SplashScreenManager.ModeSplash()) {
            case SplashScreenManager.RANDOM: {
                randomIcon.getStyleClass().setAll(ICON_GLYPH, ICON_BIG, SHADOWED);
            }
            break;
            case SplashScreenManager.SEQUENTIAL: {
                randomIcon.getStyleClass().setAll(ICON_GLYPH, ICON_BIG, ICON_SLIDESHOW, SHADOWED);
            }
            break;
        }
        
        randomIcon.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                switch (SplashScreenManager.ModeSplash()) {
                    case SplashScreenManager.RANDOM: {
                        randomIcon.getStyleClass().setAll(ICON_GLYPH, ICON_BIG, ICON_SLIDESHOW, SHADOWED);
                        SplashScreenManager.changeModeSplash(SplashScreenManager.SEQUENTIAL);
                        
                    }
                    break;
                    case SplashScreenManager.SEQUENTIAL: {
                        randomIcon.getStyleClass().setAll(ICON_GLYPH, ICON_BIG, SHADOWED);
                        SplashScreenManager.changeModeSplash(SplashScreenManager.RANDOM);
                        
                    }
                    break;
                    
                }
            }
        });
        
        dialogSlide.setOnDialogOpened((event) -> {
            dialogSlide.requestFocus();
            AnimationFX inNext = new ZoomIn(nextIcon);
            AnimationFX inBack = new ZoomIn(backIcon);
            inNext.play();
            inBack.play();
        });
        
        dialogSlide.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.LEFT) {
                if (unlockSkipButtons) {
                    backSlide();
                }
            }
            
            if (event.getCode() == KeyCode.RIGHT) {
                if (unlockSkipButtons) {
                    nextSlide();
                }
            }
            
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
        
        Arrays.asList(randomIcon, trashIcon, playOrPauseIcon, nextIcon, backIcon)
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
        
        Arrays.asList(randomIcon, trashIcon, addIcon, playOrPauseIcon, closeIcon)
                .forEach(icon -> {
                    icon.setMouseTransparent(true);
                    AnimationFX animateIn = new ZoomIn(icon);
                    animateIn.getTimeline().setOnFinished((e) -> {
                        icon.setMouseTransparent(false);
                    });
                    animateIn.setSpeed(2);
                    animateIn.play();
                });
    }
    
    void hideHeading() {
        Arrays.asList(randomIcon, trashIcon, addIcon, playOrPauseIcon, closeIcon)
                .forEach(icon -> {
                    icon.setMouseTransparent(true);
                    AnimationFX animateOut = new ZoomOut(icon);
                    animateOut.getTimeline().setOnFinished((e) -> {
                        icon.setMouseTransparent(false);
                    });
                    animateOut.setSpeed(2);
                    animateOut.play();
                });
        
    }
    
    void showControlsToEdit() {
        Arrays.asList(randomIcon, trashIcon, addIcon, closeIcon, backIcon, nextIcon)
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
        
        Arrays.asList(randomIcon, trashIcon, addIcon, closeIcon, backIcon, nextIcon)
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
}
