/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkstylecatalogfx;

//App
import Control.Counter;
import Control.LifeLabel;
import Control.PulseButton;
import Control.StarRating;
import Dialog.ContentMaterial;
import Manager.CatalogManager;

import Manager.DarkStyleCatalogSQL;
import Manager.SplashScreenManager;
import Manager.FileManager;

import Model.Material.*;

import Model.Extras.*;

import SlideShow.*;
import Transition.GrowDown;
import Transition.GrowUp;

import Utils.FilterExtensionImage;

import static Utils.Constants.*;
import static Utils.Checker.*;
import Utils.Display;
import static Utils.Display.*;

//AnimationFX
import animatefx.animation.*;

//Java
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Arrays;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

//FontAwesome
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

//ControlsFX
import org.controlsfx.control.HiddenSidesPane;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

//JFoenixz
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXToggleButton;

//JavaFX
import javafx.application.Application;
import javafx.application.Platform;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

import javafx.scene.Scene;
import javafx.scene.Node;

import javafx.scene.control.ToolBar;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javafx.scene.input.KeyCode;

import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

import javafx.geometry.Insets;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.ComboBox;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.Rectangle;

import javafx.util.Duration;
import javafx.util.StringConverter;

/**
 *
 * @author Nameless
 */
public final class DarkStyleCatalogFX extends Application {

    ////////////////////////////////////////////////////////////////////
    //                        Models                                 //
    ///////////////////////////////////////////////////////////////////
    private ArrayList<JFXButton> buttonOfFilters = null;
    private ArrayList<JFXButton> radialButtons = null;
    private ArrayList<String> actions = null;
    private ArrayList<String> filtters = null;

    private FilteredList<Material> materialsSearched = null;
    private FilteredList<Author> filteredByAuthors = null;
    private FilteredList<Kind> filteredByKinds = null;
    private FilteredList<State> filteredByStates = null;
    private FilteredList<Studio> filteredByStudios = null;

    private ObservableList<Author> filterByAuthors = null;
    private ObservableList<Kind> filterByKinds = null;
    private ObservableList<State> filterByStates = null;
    private ObservableList<Studio> filterByStudios = null;
    private ObservableList<Star> filterByRatings = null;
    private ObservableList<EmptyField> filterByEmptyFields = null;

    ////////////////////////////////////////////////////////////////////
    //                         State Variables                       //
    ///////////////////////////////////////////////////////////////////
    private BooleanProperty visibleIconTrash = null;
    private BooleanProperty visibleIconSlide = null;
    private BooleanProperty lockApplication = null;

    private boolean resetPhoto = false;

    private boolean unlockedRadialPane = true;

    private boolean activedSideBarLeft = false;
    private boolean activedSideBarRight = false;

    private boolean dialogSearchedHide = true;
    private boolean showingInfoAboutMaterial = false;

    ////////////////////////////////////////////////////////////////////
    //                        Numeric Variables                       //
    ///////////////////////////////////////////////////////////////////
    private int delayAnimation = 0;
    private int semaphoreAnimation = 0;
    private int quantityOfActions = 0;
    private int positionStateGobal = 0;

    ////////////////////////////////////////////////////////////////////
    //                        String Variables                       //
    ///////////////////////////////////////////////////////////////////
    private final String DIR_PHOTOS = "Storage/Photos/";
    private final String STYLESHEET = "Style/DarkStyle.css";

    private String RecentFolder = "";

    private String pathOfPhotoFile = "";

    private String pathFile = "";
    private String nameFile = "";

    ////////////////////////////////////////////////////////////////////
    //                       Object Variables                         //
    ///////////////////////////////////////////////////////////////////
    private FileChooser fileChooser = null;

    private JFXAutoCompletePopup<Material> suggestionsOfAnimes;
    private JFXAutoCompletePopup<Material> suggestionsOfDonghuas;
    private JFXAutoCompletePopup<Material> suggestionsOfLightNovels;

    private JFXAutoCompletePopup<Author> suggestionsOfAuthors;
    private JFXAutoCompletePopup<Kind> suggestionsOfKinds;
    private JFXAutoCompletePopup<State> suggestionsOfStates;
    private JFXAutoCompletePopup<Studio> suggestionsOfStudios;

    private JFXTextField materialToSearch = null;
    private JFXTextField kindToFilter = null;
    private JFXTextField stateToFilter = null;
    private JFXTextField authorToFilter = null;
    private JFXTextField studioToFilter = null;

    private StackPane containerOfKindFiltered = null;
    private StackPane containerOfStateFiltered = null;
    private StackPane containerOfAuthorFiltered = null;
    private StackPane containerOfStudioFiltered = null;

    private VBox boxForKind = null;
    private VBox boxForState = null;
    private VBox boxForAuthor = null;
    private VBox boxForStudio = null;

    private FontAwesomeIconView iconOfKindFiltered;
    private FontAwesomeIconView iconOfStateFiltered;
    private FontAwesomeIconView iconOfAuthorFiltered;
    private FontAwesomeIconView iconOfStudioFiltered;

    private FontAwesomeIconView iconOfMateriaSearched = null;
    private FontAwesomeIconView iconOfLeftMenu = null;
    private FontAwesomeIconView iconOfRightMenu = null;

    private Button buttonOfLeftMenu = null;
    private Button buttonOfRightMenu = null;

    private Stage splashStage = null;
    private Stage currentStage = null;

    private JFXDialog dialogRefresh = null;
    private JFXDialog dialogLock = null;

    private JFXDialog dialogConfiguration = null;
    private JFXDialog dialogMaterial = null;
    private JFXDialog dialogSearched = null;
    private JFXDialog dialogFilter = null;

    private JFXDialog dialogFormAnime = null;
    private JFXDialog dialogFormDonghua = null;
    private JFXDialog dialogFormLightNovel = null;
    private JFXDialog dialogFormState = null;
    private JFXDialog dialogFormKind = null;
    private JFXDialog dialogFormAuthor = null;
    private JFXDialog dialogFormStudio = null;

    private JFXDialog dialogGalleryState = null;
    private JFXDialog dialogGalleryKind = null;
    private JFXDialog dialogGalleryAuthor = null;
    private JFXDialog dialogGalleryStudio = null;

    private JFXAlert<Void> dialogAlert = null;

    private JFXDialogLayout contentConfiguration = null;
    private ContentMaterial contentMaterial = null;
    private JFXDialogLayout contentSearched = null;
    private JFXDialogLayout contentFilter = null;

    private JFXDialogLayout contentFormAnime = null;
    private JFXDialogLayout contentFormDonghua = null;
    private JFXDialogLayout contentFormLightNovel = null;
    private JFXDialogLayout contentFormState = null;
    private JFXDialogLayout contentFormKind = null;
    private JFXDialogLayout contentFormAuthor = null;
    private JFXDialogLayout contentFormStudio = null;

    private JFXDialogLayout contentGalleryState = null;
    private JFXDialogLayout contentGalleryKind = null;
    private JFXDialogLayout contentGalleryAuthor = null;
    private JFXDialogLayout contentGalleryStudio = null;

    private JFXDialogLayout contentAlert = null;
    private JFXDialogLayout contentRefresh = null;
    private JFXDialogLayout contentLock = null;

    private HiddenSidesPane MainMenu = null;
    private BorderPane borderPane = null;
    private JFXTabPane paneByTypes = null;

    private StackPane mainPane = null;
    private Scene sceneGallery = null;

    private GridView<Material> galleryOfMaterialSearched;

    private GridView<Kind> galleryOfFilterByKind = null;
    private GridView<State> galleryOfFilterByState = null;
    private GridView<Author> galleryOfFilterByAuthor = null;
    private GridView<Studio> galleryOfFilterByStudio = null;
    private GridView<Star> galleryOfFilterByRating = null;
    private GridView<EmptyField> galleryOfFilterByEmptyField = null;

    private GridView<Material> galleryOfAnimeForTabOfAnime = null;
    private GridView<Material> galleryOfDonghuaForTabOfDonghua = null;
    private GridView<Material> galleryOfLightNovelForTabOfLightNovel = null;

    private GridView<Kind> galleryOfKind = null;
    private GridView<State> galleryOfState = null;
    private GridView<Author> galleryOfAuthor = null;
    private GridView<Studio> galleryOfStudio = null;

    private GridView<Kind> galleryOfKindForForm = null;
    private GridView<State> galleryOfStateForForm = null;
    private GridView<Author> galleryOfAuthorForForm = null;
    private GridView<Studio> galleryOfStudioForForm = null;

    private VBox extrasForFormOfMaterial = null;
    private VBox dymanicConent = null;

    private TextArea synopsisForFormOfMaterial = null;
    private ImageView photoForFormOfMaterial = null;
    private Label highlight_photo = new Label();
    private StarRating starRatingForFormOfMaterial = null;
    private Counter counterForFormOfMaterial = null;

    private ComboBox<Integer> yearsForFormOfMaterial;
    private ComboBox<String> countriesForFormOfMaterial;

    private JFXToggleButton toggleOfFilterByKind = null;
    private JFXToggleButton toggleOfFilterByState = null;
    private JFXToggleButton toggleOfFilterByAuthor = null;
    private JFXToggleButton toggleOfFilterByStudio = null;
    private JFXToggleButton toggleOfFilterByEmptyField = null;

    private FontAwesomeIconView iconLock = null;
    private FontAwesomeIconView iconConfiguration = null;

    private FontAwesomeIconView iconSplash = null;
    private FontAwesomeIconView iconHelp = null;
    private FontAwesomeIconView iconLoosePhoto = null;

    private JFXButton buttonSplash = null;
    private JFXButton buttonHelp = null;
    private JFXButton buttonLoosePhoto = null;

    private FlowPane configurationLayout = null;

    private SlideShow slideShowMaterials = null;
    private SlideShow slideShowSplash = null;
    private SlideShow slideShowLoosePhotos = null;

    private ScrollPane scrollPaneSideBarLeft = null;
    private ScrollPane scrollPaneSideBarRight = null;

    private Timer delayDisable = null;
    private Timer animationSidebar = null;
    private Timer unlockSidebar = null;

    ////////////////////////////////////////////////////////////////////
    //                         initialization                         //
    ///////////////////////////////////////////////////////////////////
    public void initializeTimer() {
        delayDisable = new Timer();
        animationSidebar = new Timer();
        unlockSidebar = new Timer();

    }

    public void initializeStates() {
        visibleIconTrash = new SimpleBooleanProperty(false);
        visibleIconSlide = new SimpleBooleanProperty(false);
        lockApplication = new SimpleBooleanProperty(false);

        lockApplication.addListener((observable, oldValue, newValue) -> {
            buttonOfLeftMenu.setDisable(newValue);
            if (newValue) {
                MainMenu.setLeft(null);
                if (unlockedRadialPane) {
                    if (radialButtons.size() > 0 && radialButtons.get(0).isVisible()) {
                        showOrHideRadialPanel();
                    }
                }
            } else {
                MainMenu.setLeft(scrollPaneSideBarLeft);
            }
        });
    }

    public void initializeToggle() {
        String nameMode = ANY;
        toggleOfFilterByKind = new JFXToggleButton();
        toggleOfFilterByState = new JFXToggleButton();
        toggleOfFilterByAuthor = new JFXToggleButton();
        toggleOfFilterByStudio = new JFXToggleButton();
        toggleOfFilterByEmptyField = new JFXToggleButton();

        Arrays.asList(toggleOfFilterByKind,
                toggleOfFilterByState,
                toggleOfFilterByAuthor,
                toggleOfFilterByStudio,
                toggleOfFilterByEmptyField)
                .forEach((action)
                        -> {
                    action.setOnMouseClicked((event) -> {
                        action.setText(action.isSelected() ? ALL : ANY);
                        event.consume();
                    });
                }
                );

        toggleOfFilterByKind.setText(nameMode);
        toggleOfFilterByState.setText(nameMode);
        toggleOfFilterByAuthor.setText(nameMode);
        toggleOfFilterByStudio.setText(nameMode);
        toggleOfFilterByEmptyField.setText(nameMode);

    }

    public void initializeRatings() {

        filterByRatings = FXCollections.observableArrayList();
        for (int index = 1; index <= 7; index++) {
            filterByRatings.add(new Star(index));
        }

    }

    public void initializeEmptyFields() {
        filterByEmptyFields = FXCollections.observableArrayList();
        filterByEmptyFields.add(new EmptyField("Synopsis"));
        filterByEmptyFields.add(new EmptyField("Photo"));
        filterByEmptyFields.add(new EmptyField("Chapter"));
        filterByEmptyFields.add(new EmptyField("Year"));
        filterByEmptyFields.add(new EmptyField("Rating"));
        filterByEmptyFields.add(new EmptyField("Kind"));
        filterByEmptyFields.add(new EmptyField("State"));
        filterByEmptyFields.add(new EmptyField("Author"));
        filterByEmptyFields.add(new EmptyField("Studio"));

    }

    public void initalizeActions() {
        actions = new ArrayList<>();

        actions.add("Add Anime");
        actions.add("Add Donghua");
        actions.add("Add Light Novel");
        actions.add("Add State");
        actions.add("Add Kind");
        actions.add("Add Author");
        actions.add("Add Studio");
    }

    public void initiallizeFilters() {

        filtters = new ArrayList<>();
        filtters.add("By State");
        filtters.add("By Kind");
        filtters.add("By Studio");
        filtters.add("By Author");
        filtters.add("By Rating");
        filtters.add("By Empty Field");

    }

    private void initializeModelAndViewForFilter() {
        initializeEmptyFields();
        initializeRatings();

        filterByAuthors = FXCollections.observableArrayList();
        filterByKinds = FXCollections.observableArrayList();
        filterByStates = FXCollections.observableArrayList();
        filterByStudios = FXCollections.observableArrayList();

        galleryOfFilterByKind = createListCheckKinds();
        galleryOfFilterByState = createListCheckStates();
        galleryOfFilterByAuthor = createListCheckAuthors();
        galleryOfFilterByStudio = createListCheckStudios();
        galleryOfFilterByRating = createListCheckRatings();
        galleryOfFilterByEmptyField = createListCheckEmptyFields();

        galleryOfFilterByKind.getStyleClass().add(MODE_CHECK_LARGE);
        galleryOfFilterByState.getStyleClass().add(MODE_CHECK_LARGE);
        galleryOfFilterByAuthor.getStyleClass().add(MODE_CHECK_LARGE);
        galleryOfFilterByStudio.getStyleClass().add(MODE_CHECK_LARGE);
        galleryOfFilterByRating.getStyleClass().add(MODE_CHECK_LARGE);
        galleryOfFilterByEmptyField.getStyleClass().add(MODE_CHECK_LARGE);

        galleryOfFilterByKind.setItems(DarkStyleCatalogSQL.getAllKind());
        galleryOfFilterByState.setItems(DarkStyleCatalogSQL.getAllState());
        galleryOfFilterByAuthor.setItems(DarkStyleCatalogSQL.getAllAuthor());
        galleryOfFilterByStudio.setItems(DarkStyleCatalogSQL.getAllStudio());
        galleryOfFilterByRating.setItems(filterByRatings);
        galleryOfFilterByEmptyField.setItems(filterByEmptyFields);

    }

    public void initializeLayout() {
        splashStage = new Stage(StageStyle.TRANSPARENT);
        materialToSearch = new JFXTextField();
        paneByTypes = new JFXTabPane();

        MainMenu = new HiddenSidesPane();
        MainMenu.setAnimationDuration(Duration.millis(50));
        MainMenu.setLeft(createSideBarLeft());
        MainMenu.setRight(createSideBarRight());
        MainMenu.setContent(paneByTypes);

        borderPane = new BorderPane();
        borderPane.setId("background");
        borderPane.setTop(new JFXRippler(createToolBar()));
        borderPane.setCenter(MainMenu);

        mainPane = new StackPane(borderPane);

        createRadialPane(RADIUS_FROM_CENTER);
    }

    public void initializeDialogs() {

        contentFilter = new JFXDialogLayout();
        dialogFilter = new JFXDialog(mainPane, contentFilter, TRANSITION, DIALOG_CLOSED_BY_CLICK);
        dialogFilter.getStyleClass().add(DIALOG_DARK_STYLE);

        contentFormAnime = new JFXDialogLayout();
        dialogFormAnime = new JFXDialog(mainPane, contentFormAnime, TRANSITION, DIALOG_CLOSED_BY_ACTION);
        dialogFormAnime.getStyleClass().add(DIALOG_DARK_STYLE);

        contentFormDonghua = new JFXDialogLayout();
        dialogFormDonghua = new JFXDialog(mainPane, contentFormDonghua, TRANSITION, DIALOG_CLOSED_BY_ACTION);
        dialogFormDonghua.getStyleClass().add(DIALOG_DARK_STYLE);

        contentFormLightNovel = new JFXDialogLayout();
        dialogFormLightNovel = new JFXDialog(mainPane, contentFormLightNovel, TRANSITION, DIALOG_CLOSED_BY_ACTION);
        dialogFormLightNovel.getStyleClass().add(DIALOG_DARK_STYLE);

        contentFormState = new JFXDialogLayout();
        dialogFormState = new JFXDialog(mainPane, contentFormState, TRANSITION, DIALOG_CLOSED_BY_ACTION);
        dialogFormState.getStyleClass().add(DIALOG_DARK_STYLE);

        contentFormKind = new JFXDialogLayout();
        dialogFormKind = new JFXDialog(mainPane, contentFormKind, TRANSITION, DIALOG_CLOSED_BY_ACTION);
        dialogFormKind.getStyleClass().add(DIALOG_DARK_STYLE);

        contentFormAuthor = new JFXDialogLayout();
        dialogFormAuthor = new JFXDialog(mainPane, contentFormAuthor, TRANSITION, DIALOG_CLOSED_BY_ACTION);
        dialogFormAuthor.getStyleClass().add(DIALOG_DARK_STYLE);

        contentFormStudio = new JFXDialogLayout();
        dialogFormStudio = new JFXDialog(mainPane, contentFormStudio, TRANSITION, DIALOG_CLOSED_BY_ACTION);
        dialogFormStudio.getStyleClass().add(DIALOG_DARK_STYLE);

        contentGalleryState = new JFXDialogLayout();
        dialogGalleryState = new JFXDialog(mainPane, contentGalleryState, TRANSITION, DIALOG_CLOSED_BY_ACTION);
        dialogGalleryState.getStyleClass().add(DIALOG_DARK_STYLE);

        contentGalleryKind = new JFXDialogLayout();
        dialogGalleryKind = new JFXDialog(mainPane, contentGalleryKind, TRANSITION, DIALOG_CLOSED_BY_ACTION);
        dialogGalleryKind.getStyleClass().add(DIALOG_DARK_STYLE);

        contentGalleryAuthor = new JFXDialogLayout();
        dialogGalleryAuthor = new JFXDialog(mainPane, contentGalleryAuthor, TRANSITION, DIALOG_CLOSED_BY_ACTION);
        dialogGalleryAuthor.getStyleClass().add(DIALOG_DARK_STYLE);

        contentGalleryStudio = new JFXDialogLayout();
        dialogGalleryStudio = new JFXDialog(mainPane, contentGalleryStudio, TRANSITION, DIALOG_CLOSED_BY_ACTION);
        dialogGalleryStudio.getStyleClass().add(DIALOG_DARK_STYLE);

    }

    public void initializeOptionAndExtras() {
        createTextAreaForMaterial();
        createImageViewForMaterial();

        filteredByKinds = new FilteredList<>(DarkStyleCatalogSQL.getAllKind());
        filteredByStates = new FilteredList<>(DarkStyleCatalogSQL.getAllState());
        filteredByAuthors = new FilteredList<>(DarkStyleCatalogSQL.getAllAuthor());
        filteredByStudios = new FilteredList<>(DarkStyleCatalogSQL.getAllStudio());

        galleryOfAuthorForForm = createListCheckAuthors();
        galleryOfStateForForm = createListCheckStates();
        galleryOfKindForForm = createListCheckKinds();
        galleryOfStudioForForm = createListCheckStudios();

        createExtras();

        createFilterForKind();
        createFilterForState();
        createFilterForAuthor();
        createFilterForStudio();

    }

    public void setupEvents() {
        mainPane.setOnKeyPressed((event) -> {
            if (!lockApplication.getValue()) {
                if (unlockedRadialPane) {
                    if (event.isControlDown() && event.getCode() == KeyCode.R) {
                        showOrHideRadialPanel();
                    }
                }
            }

            if (event.isControlDown() && event.getCode() == KeyCode.L) {
                lockApplication.setValue(!lockApplication.getValue());
                showDialogLock();
            }

            if (event.isControlDown() && event.getCode() == KeyCode.A) {
                showOrHideActions();
            }

            if (event.isControlDown() && event.getCode() == KeyCode.F) {
                showOrHideFilters();
            }

            if (!lockApplication.getValue()) {
                if (event.isControlDown() && event.getCode() == KeyCode.T) {
                    visibleIconTrash.set(!isVisibleIconTrash());
                }
            }

            if (event.isControlDown() && event.getCode() == KeyCode.S) {
                visibleIconSlide.set(!isVisibleIconSlide());
            }

        });

        MainMenu.setOnMouseClicked((event) -> {
            mainPane.requestFocus();
        });

    }

    public DarkStyleCatalogFX() {

        DarkStyleCatalogSQL.checkWorkSpace();

        radialButtons = new ArrayList<>();
        buttonOfFilters = new ArrayList<>();
        fileChooser = new FileChooser();

        initalizeActions();
        initiallizeFilters();
        initializeTimer();
        initializeStates();
        initializeToggle();
        initializeModelAndViewForFilter();
        initializeLayout();
        initializeDialogs();
        initializeOptionAndExtras();
        setupEvents();

    }

    @Override
    public void start(Stage primaryStage) {
        currentStage = primaryStage;
        slideShowMaterials = new SlideShow(mainPane);
        slideShowSplash = new SlideShow(mainPane, primaryStage);
        slideShowLoosePhotos = new SlideShow(mainPane, primaryStage);
        slideShowMaterials.disableEdditable(SlideShow.DELETE_RANDOM | SlideShow.DELETE_ADD | SlideShow.DELETE_TRASH);
        slideShowLoosePhotos.disableEdditable(SlideShow.DELETE_RANDOM | SlideShow.DELETE_ADD);

        sceneGallery = new Scene(mainPane, WIDTH_SCREEN, HEIGHT_SCREEN);

        sceneGallery.getStylesheets().add(STYLESHEET);

        primaryStage.setResizable(false);
        primaryStage.setScene(sceneGallery);
        primaryStage.setFullScreenExitHint("");

        initializeApplication(primaryStage);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);
    }

    ////////////////////////////////////////////////////////////////////
    //                          Services                             //
    ///////////////////////////////////////////////////////////////////
    private final Service<Void> splashService = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    loadGalleryMaterials();
                    Thread.sleep(DELAY_SPLASH_SCREEN);
                    return null;
                }
            };
        }
    };
    private final Service<Void> refreshService = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                public Void call() throws Exception {

                    Platform.runLater(()
                            -> {
                        if (!dialogSearchedHide) {
                            System.out.println(materialToSearch.getText());
                            loadSearchMateriales(materialToSearch.getText());
                        }
                    });
                    applyFilter();
                    return null;
                }
            };
        }
    };
    private final Service<Void> loadPhotoService = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    Platform.runLater(()
                            -> {
                        loadPhotoForMaterial();
                    });
                    return null;
                }
            };
        }
    };

    public void initializeApplication(Stage primaryStage) {
        splashService.restart();

        splashService.setOnRunning((event) -> {
            showOrHideSplashScreen(true);
        });

        splashService.setOnSucceeded((event) -> {
            splashService.cancel();
            showOrHideSplashScreen(false);
            primaryStage.setFullScreen(true);
            primaryStage.show();
            SplashScreenManager.forward();

        });

    }

    public void makeSplashScreen(StackPane scene) {
        ImageView splashImage = new ImageView(SplashScreenManager.generateSplash());
        JFXSpinner loading = new JFXSpinner();
        loading.setRadius(WIDTH_PHOTO);

        scene.getChildren().add(splashImage);
        scene.getChildren().add(loading);

        AnimationFX animation = new BounceIn(scene);
        animation.getTimeline().setAutoReverse(true);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.setSpeed(0.75);
        animation.play();
    }

    public void showOrHideSplashScreen(boolean showSplashScreen) {

        if (showSplashScreen) {
            StackPane pane = new StackPane();
            pane.getStyleClass().add("splash-screen");
            makeSplashScreen(pane);

            Scene scene = new Scene(pane, WIDTH_SCENE_SPLASH, HEIGHT_SCENE_SPLASH);

            scene.getStylesheets().add(STYLESHEET);
            scene.setFill(Color.TRANSPARENT);
            splashStage.setScene(scene);
            splashStage.show();

        } else {
            splashStage.hide();

        }

    }

    ////////////////////////////////////////////////////////////////////
    //                          ToolBar                              //
    ///////////////////////////////////////////////////////////////////
    private Button makeIconAndButtonMenuLeft() {
        iconOfLeftMenu = new FontAwesomeIconView(FontAwesomeIcon.NAVICON);
        iconOfLeftMenu.getStyleClass().addAll(ICON_NORMAL, ICON_HOVERED, SHADOWED);

        buttonOfLeftMenu = new Button();
        buttonOfLeftMenu.getStyleClass().add("first");
        buttonOfLeftMenu.setGraphic(iconOfLeftMenu);

        buttonOfLeftMenu.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                showOrHideActions();
            }
        });

        return buttonOfLeftMenu;
    }

    private Button makeIconAndButtonStates() {
        FontAwesomeIconView iconStates = new FontAwesomeIconView(FontAwesomeIcon.WECHAT);
        iconStates.getStyleClass().addAll(ICON_NORMAL, ICON_HOVERED, SHADOWED);

        Button statesButton = new Button();
        statesButton.setGraphic(iconStates);
        statesButton.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                ShowDialogGalleryState();
            }
        });

        statesButton.disableProperty().bindBidirectional(lockApplication);
        return statesButton;
    }

    private Button makeIconAndButtonKinds() {
        FontAwesomeIconView iconKinds = new FontAwesomeIconView(FontAwesomeIcon.TAGS);
        iconKinds.getStyleClass().addAll(ICON_NORMAL, ICON_HOVERED, SHADOWED);

        Button kindsButton = new Button();
        kindsButton.setGraphic(iconKinds);
        kindsButton.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                ShowDialogGalleryKind();
            }
        });

        kindsButton.getStyleClass().addAll("last");

        kindsButton.disableProperty().bindBidirectional(lockApplication);
        return kindsButton;
    }

    private Button makeIconAndButtonConfiguration() {
        FontAwesomeIconView iconConfig = new FontAwesomeIconView(FontAwesomeIcon.GEAR);
        iconConfig.getStyleClass().addAll(ICON_NORMAL, ICON_HOVERED, SHADOWED);

        Button configurationButton = new Button();

        configurationButton.setGraphic(iconConfig);
        configurationButton.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                showDialogConfiguration();
            }
        });
        configurationButton.disableProperty().bindBidirectional(lockApplication);

        return configurationButton;
    }

    public HBox createBarHome() {
        HBox buttonBar = new HBox();
        buttonBar.getStyleClass().setAll("segmented-button-bar");

        buttonBar.getChildren().add(new JFXRippler(makeIconAndButtonMenuLeft()));
        buttonBar.getChildren().add(new JFXRippler(makeIconAndButtonConfiguration()));
        buttonBar.getChildren().add(new JFXRippler(makeIconAndButtonStates()));
        buttonBar.getChildren().add(new JFXRippler(makeIconAndButtonKinds()));

        return buttonBar;
    }

    public HBox createBarSearch() {
        HBox SearchBar = new HBox();
        SearchBar.setAlignment(Pos.CENTER);

        StackPane container = new StackPane();

        iconOfMateriaSearched = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
        iconOfMateriaSearched.getStyleClass().addAll("search-icon", ICON_SMALL, SHADOWED);
        iconOfMateriaSearched.setMouseTransparent(UNTOUCHABLE);

        materialToSearch.setPromptText("Introduzca Material Buscado");
        materialToSearch.getStyleClass().add("search");

        materialToSearch.textProperty().addListener(observable -> {
            doSearch();
        });
        materialToSearch.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                doSearch();
            }
        });

        materialToSearch.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                iconOfMateriaSearched.setVisible(false);
            } else {
                if (materialToSearch.getText().isEmpty()) {
                    iconOfMateriaSearched.setVisible(true);
                }
            }
        });

        SearchBar.getStyleClass().setAll("segmented-button-bar");
        container.getChildren().add(materialToSearch);
        container.getChildren().add(iconOfMateriaSearched);
        SearchBar.getChildren().add(container);

        return SearchBar;
    }

    private Button makeIconAndButtonAuthor() {
        FontAwesomeIconView iconAuthors = new FontAwesomeIconView(FontAwesomeIcon.BOOK);

        Button authorsButton = new Button();
        iconAuthors.getStyleClass().addAll(ICON_NORMAL, ICON_HOVERED, SHADOWED);
        authorsButton.getStyleClass().add("first");
        authorsButton.setGraphic(iconAuthors);

        authorsButton.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                ShowDialogGalleryAuthor();
            }
        });

        authorsButton.disableProperty().bindBidirectional(lockApplication);
        return authorsButton;
    }

    private Button makeIconAndButtonStudio() {
        FontAwesomeIconView iconStudios = new FontAwesomeIconView(FontAwesomeIcon.BUILDING);
        iconStudios.getStyleClass().addAll(ICON_NORMAL, ICON_HOVERED, SHADOWED);

        Button studiosButton = new Button();
        studiosButton.setGraphic(iconStudios);
        studiosButton.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                ShowDialogGalleryStudio();
            }
        });
        studiosButton.disableProperty().bindBidirectional(lockApplication);
        return studiosButton;
    }

    private Button makeIconAndButtonClose() {
        FontAwesomeIconView iconClose = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE_ALT);
        iconClose.getStyleClass().addAll(ICON_NORMAL, ICON_HOVERED, SHADOWED, SHADOWED);

        Button closeButton = new Button();
        closeButton.setGraphic(iconClose);
        closeButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                showAlertClose();
            }
        }
        );
        return closeButton;
    }

    private Button makeIconAndButtonMenuRight() {
        iconOfRightMenu = new FontAwesomeIconView(FontAwesomeIcon.NAVICON);
        iconOfRightMenu.getStyleClass().addAll(ICON_NORMAL, ICON_HOVERED, SHADOWED);

        buttonOfRightMenu = new Button();
        buttonOfRightMenu.getStyleClass().add("last");
        buttonOfRightMenu.setGraphic(iconOfRightMenu);
        buttonOfRightMenu.setPrefWidth(80);
        buttonOfRightMenu.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                showOrHideFilters();
            }
        });
        return buttonOfRightMenu;
    }

    public HBox createBarExtras() {
        HBox extraBar = new HBox();

        extraBar.getStyleClass().setAll("segmented-button-bar");
        extraBar.getChildren().add(new JFXRippler(makeIconAndButtonAuthor()));
        extraBar.getChildren().add(new JFXRippler(makeIconAndButtonStudio()));
        extraBar.getChildren().add(new JFXRippler(makeIconAndButtonClose()));
        extraBar.getChildren().add(new JFXRippler(makeIconAndButtonMenuRight()));

        return extraBar;
    }

    public ToolBar createToolBar() {

        BorderPane pane = new BorderPane();

        pane.setPadding(new Insets(10));
        pane.setPrefWidth(WIDTH_SCREEN - 20);

        pane.setLeft(createBarHome());
        pane.setCenter(createBarSearch());
        pane.setRight(createBarExtras());

        ToolBar toolBar = new ToolBar();
        toolBar.getItems().add(pane);

        toolBar.setOnMouseClicked((event) -> {
            mainPane.requestFocus();
        });

        return toolBar;
    }

    ////////////////////////////////////////////////////////////////////
    //                         initialze Dialog                       //
    ///////////////////////////////////////////////////////////////////
    public void generateDialogAlertClose() {

        Label msg = new Label("Realmente Desear Salir");

        msg.getStyleClass().addAll(SLIDE_HEADLINE, HEADLINE_STANDARD, SHADOWED);

        FontAwesomeIconView iconAccept = new FontAwesomeIconView(FontAwesomeIcon.CHECK);
        FontAwesomeIconView iconCancel = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);

        iconAccept.getStyleClass().add(ICON_NORMAL);
        iconCancel.getStyleClass().add(ICON_NORMAL);

        contentAlert = new JFXDialogLayout();
        contentAlert.setAlignment(Pos.CENTER);

        contentAlert.setHeading(msg);

        JFXButton accept = new JFXButton("Si", iconAccept);

        accept.getStyleClass().addAll(BUTTON_SMALL, BUTTON_ALERT);

        JFXButton cancel = new JFXButton("No", iconCancel);
        cancel.getStyleClass().addAll(BUTTON_SMALL, BUTTON_ALERT);

        contentAlert.setActions(accept, cancel);

        dialogAlert = new JFXAlert<>(currentStage);
        dialogAlert.setAnimation(JFXAlertAnimation.TOP_ANIMATION);
        dialogAlert.setContent(contentAlert);
        dialogAlert.setOverlayClose(true);
        dialogAlert.initModality(Modality.WINDOW_MODAL);

        cancel.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                dialogAlert.close();
            }
            event.consume();
        });

        accept.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                Platform.exit();
            }
        });

    }

    public void generateDialogLock() {
        contentLock = new JFXDialogLayout();
        iconLock = new FontAwesomeIconView(FontAwesomeIcon.LOCK);
        iconLock.getStyleClass().addAll(ICON_BIGGEST, SHADOWED);
        VBox container = new VBox(iconLock);
        container.setAlignment(Pos.CENTER);
        contentLock.setBody(container);

        dialogLock = new JFXDialog(mainPane, contentLock, TRANSITION, DIALOG_CLOSED_BY_CLICK);
        dialogLock.getStyleClass().add(DIALOG_NOT_BACKGROUND);
    }

    public void generateDialogRefresh() {
        contentRefresh = new JFXDialogLayout();
        ProgressIndicator indicator = new ProgressIndicator();
        contentRefresh.setBody(indicator);

        dialogRefresh = new JFXDialog(mainPane, contentRefresh, TRANSITION, DIALOG_CLOSED_BY_ACTION);
        dialogRefresh.getStyleClass().add(DIALOG_NOT_BACKGROUND);
        dialogRefresh.setOnDialogOpened((event) -> {
            refreshService.restart();
        });
    }

    public void generateDialogConfiguration() {
        contentConfiguration = new JFXDialogLayout();
        dialogConfiguration = new JFXDialog(mainPane, contentConfiguration, TRANSITION, DIALOG_CLOSED_BY_ACTION);
        dialogConfiguration.getStyleClass().add(DIALOG_DARK_STYLE);

        iconConfiguration = new FontAwesomeIconView(FontAwesomeIcon.GEAR);
        iconConfiguration.getStyleClass().addAll(ICON_NORMAL, SHADOWED);
        iconSplash = new FontAwesomeIconView(FontAwesomeIcon.IMAGE);
        iconHelp = new FontAwesomeIconView(FontAwesomeIcon.QUESTION_CIRCLE);
        iconLoosePhoto = new FontAwesomeIconView(FontAwesomeIcon.PHOTO);

        buttonSplash = new JFXButton("Splash Screens", iconSplash);
        buttonHelp = new JFXButton("Help", iconHelp);
        buttonLoosePhoto = new JFXButton("Loose Photos", iconLoosePhoto);

        Arrays.asList(iconSplash, iconHelp, iconLoosePhoto)
                .forEach(icon -> icon.getStyleClass().addAll(ICON_BIG, SHADOWED, ICON_HOVERED));

        Arrays.asList(buttonSplash, buttonHelp, buttonLoosePhoto)
                .forEach(t -> t.getStyleClass().add(BUTTON_CONFIGURATION));

        iconConfiguration.getStyleClass().add(ICON_NORMAL);
        configurationLayout = new FlowPane(buttonSplash, buttonLoosePhoto);
        configurationLayout.setAlignment(Pos.CENTER);
        configurationLayout.setHgap(25);
        configurationLayout.setVgap(5);

        buttonSplash.setOnMouseClicked((event)
                -> {
            slideShowSplash.setCurrentSlide(0);
            slideShowSplash.setSlides(SplashScreenManager.createModelSlideSplash());
            slideShowSplash.showSlides();
        });

        buttonLoosePhoto.setOnMouseClicked((event)
                -> {
            slideShowLoosePhotos.setSlides(createLoosePhotoSlides());
            slideShowLoosePhotos.showSlides();
        });

        Label title = new Label("Settings", iconConfiguration);
        title.getStyleClass().addAll(HEADING_BIG, DIALOG_SMALL);

        contentConfiguration.setHeading(createHeadingForDialogRotateOut(title, dialogConfiguration));
        contentConfiguration.setBody(configurationLayout);

    }

    public void generateDialogSearchedMaterials() {
        contentSearched = new JFXDialogLayout();
        dialogSearched = new JFXDialog(mainPane, contentSearched, TRANSITION, DIALOG_CLOSED_BY_CLICK);
        dialogSearched.getStyleClass().add(DIALOG_DARK_STYLE);

        FontAwesomeIconView iconSearch = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
        iconSearch.getStyleClass().addAll(ICON_NORMAL, SHADOWED);
        Label headingSearched = new Label("Materiales Buscados", iconSearch);
        headingSearched.getStyleClass().addAll(HEADING_BIG, DIALOG_BIG);
        headingSearched.setGraphicTextGap(10);

        contentSearched.setHeading(createHeadingForDialog(headingSearched, dialogSearched));
        galleryOfMaterialSearched = createGalleryMaterials(SHOW_MATERIAL_TYPE);
        galleryOfMaterialSearched.setItems(materialsSearched);
        contentSearched.setPrefHeight(HEIGHT_DIALOG_CENTRAL);

        dialogSearched.setOnDialogClosed((event) -> {
            dialogSearchedHide = true;
        });
    }

    public void generateDialogInfoOfMaterial() {
        contentMaterial = new ContentMaterial();
        dialogMaterial = new JFXDialog(mainPane, contentMaterial, TRANSITION, DIALOG_CLOSED_BY_CLICK);
        dialogMaterial.getStyleClass().addAll(DIALOG_LIGHT_STYLE, LIGHT_OVERLAY);

    }

    ////////////////////////////////////////////////////////////////////
    //                         Show Dialog                            //
    ///////////////////////////////////////////////////////////////////
    public void showAlertClose() {
        if (dialogAlert == null) {
            generateDialogAlertClose();
        }
        if (dialogAlert != null) {
            dialogAlert.show();
            contentAlert.setOpacity(HIDDEN);
            AnimationFX animation = new FadeInDown(contentAlert);
            animation.setDelay(Duration.millis(250));
            animation.play();
        }

    }

    public void showDialogLock() {
        if (dialogLock == null) {
            generateDialogLock();
        }

        if (dialogLock != null) {

            if (lockApplication.getValue()) {
                iconLock.setIcon(FontAwesomeIcon.LOCK);
            } else {
                iconLock.setIcon(FontAwesomeIcon.UNLOCK_ALT);
            }

            if (!dialogLock.isVisible()) {
                dialogLock.show();
            } else {
                iconLock.setOpacity(HIDDEN);
                AnimationFX in = new ZoomIn(iconLock);
                in.play();
            }
        }
    }

    public void showDialogRefresh() {
        if (dialogRefresh == null) {
            generateDialogRefresh();
        }
        if (dialogRefresh != null) {
            dialogRefresh.show();
        }
    }

    public void showDialogConfiguration() {
        if (dialogConfiguration == null) {
            generateDialogConfiguration();
        }

        if (dialogConfiguration != null) {
            dialogConfiguration.show();
            AnimationFX animationIn = new RotateIn(dialogConfiguration);
            animationIn.setSpeed(2);
            animationIn.play();
        }

    }

    public void ShowDialogInfoOfMaterial(Material item, GridCell<Material> gridcell) {

        if (dialogMaterial == null) {
            generateDialogInfoOfMaterial();
            contentMaterial.connectClose(dialogMaterial);
        }
        if (dialogMaterial != null) {
            dialogMaterial.setOnDialogClosed((event) -> {
                BounceInDown animation = new BounceInDown(gridcell);
                animation.setSpeed(1.5);
                animation.play();
                showingInfoAboutMaterial = false;
            });
            contentMaterial.loadInfoMaterial(item);
            dialogMaterial.show();
            RotateIn animationIn = new RotateIn(dialogMaterial);
            animationIn.setSpeed(2);
            animationIn.play();

        }

    }

    public void ShowDialogSearchedMaterials() {

        if (dialogSearched == null) {
            generateDialogSearchedMaterials();
        }

        if (dialogSearched != null) {
            if (materialsSearched.isEmpty()) {
                Label NonContent = new Label("No Hay Materiales Con Ese Nombre o Descripcion.");
                NonContent.getStyleClass().addAll(HEADING_BIG, DIALOG_BIG);
                contentSearched.setBody(NonContent);
            } else {
                galleryOfMaterialSearched.setItems(materialsSearched);
                contentSearched.setBody(galleryOfMaterialSearched);
            }
        }

        if (dialogSearchedHide && dialogSearched != null) {
            dialogSearched.show();
            AnimationFX animationIn = new ZoomInDown(dialogSearched);
            animationIn.setSpeed(0.5);
            animationIn.play();
            dialogSearchedHide = false;
        }

    }

    public void ShowDialogByFilter(String TypeFilter) {
        StackPane container = new StackPane();
        FontAwesomeIconView iconOfFilter = new FontAwesomeIconView(FontAwesomeIcon.FILTER);
        FontAwesomeIconView extraOfIcon = createIconForCategoryAndOption(TypeFilter);

        iconOfFilter.getStyleClass().addAll(ICON_MIDDLE, SHADOWED);
        extraOfIcon.getStyleClass().setAll(ICON_GLYPH, ICON_SMALL, ICON_EXTRAS, SHADOWED);

        container.getChildren().add(extraOfIcon);
        container.getChildren().add(iconOfFilter);

        Label name = new Label("Filter " + TypeFilter, container);
        name.getStyleClass().addAll(HEADING_BIG, DIALOG_BIG);
        contentFilter.setHeading(createHeadingForDialog(name, dialogFilter));

        switch (TypeFilter) {
            case "By Kind": {

                filterByKinds = galleryOfFilterByKind.getItems();

                galleryOfFilterByKind.getStyleClass().add(MODE_CHECK_LARGE);

                galleryOfFilterByKind.setItems(DarkStyleCatalogSQL.getAllKind());
                restoreFilterKind(galleryOfFilterByKind.getItems());

                VBox content = new VBox();
                content.setAlignment(Pos.CENTER);
                content.getChildren().add(toggleOfFilterByKind);
                content.getChildren().add(galleryOfFilterByKind);
                contentFilter.setBody(content);
            }
            break;
            case "By State": {

                filterByStates = galleryOfFilterByState.getItems();

                galleryOfFilterByState.getStyleClass().add(MODE_CHECK_LARGE);

                galleryOfFilterByState.setItems(DarkStyleCatalogSQL.getAllState());
                restoreFilterState(galleryOfFilterByState.getItems());
                VBox content = new VBox();
                content.setAlignment(Pos.CENTER);
                content.getChildren().add(toggleOfFilterByState);
                content.getChildren().add(galleryOfFilterByState);
                contentFilter.setBody(content);
            }
            break;

            case "By Author": {

                filterByAuthors = galleryOfFilterByAuthor.getItems();
                galleryOfFilterByAuthor.getStyleClass().add(MODE_CHECK_LARGE);

                galleryOfFilterByAuthor.setItems(DarkStyleCatalogSQL.getAllAuthor());
                restoreFilterAuthor(galleryOfFilterByAuthor.getItems());
                VBox content = new VBox();
                content.setAlignment(Pos.CENTER);
                content.getChildren().add(toggleOfFilterByAuthor);
                content.getChildren().add(galleryOfFilterByAuthor);
                contentFilter.setBody(content);
            }
            break;

            case "By Studio": {

                filterByStudios = galleryOfFilterByStudio.getItems();
                galleryOfFilterByStudio.getStyleClass().add(MODE_CHECK_LARGE);

                galleryOfFilterByStudio.setItems(DarkStyleCatalogSQL.getAllStudio());
                restoreFilterStudio(galleryOfFilterByStudio.getItems());
                VBox content = new VBox();
                content.setAlignment(Pos.CENTER);
                content.getChildren().add(toggleOfFilterByStudio);
                content.getChildren().add(galleryOfFilterByStudio);
                contentFilter.setBody(content);
            }
            break;

            case "By Rating": {

                galleryOfFilterByRating.getStyleClass().add(MODE_CHECK_LARGE);;

                VBox content = new VBox();
                content.setAlignment(Pos.CENTER);
                content.getChildren().add(galleryOfFilterByRating);
                contentFilter.setBody(content);
            }
            break;

            case "By Empty Field": {

                galleryOfFilterByEmptyField.getStyleClass().add(MODE_CHECK_LARGE);

                VBox content = new VBox();
                content.setAlignment(Pos.CENTER);
                content.getChildren().add(toggleOfFilterByEmptyField);
                content.getChildren().add(galleryOfFilterByEmptyField);
                contentFilter.setBody(content);
            }
            break;

            default:
                contentFilter.setBody(new VBox());
                break;

        }

        dialogFilter.show();
        ZoomInDown animationIn = new ZoomInDown(dialogFilter);
        animationIn.setSpeed(0.75);
        animationIn.play();

        dialogFilter.setOnDialogClosed((event) -> {
            showDialogRefresh();
        });
    }

    public void ShowDialogFormAnime(Anime animeToEdit) {
        resetOptionAndExtras();

        suggestionsOfAnimes.setSuggestionsCellFactory(
                list -> {
                    ListCell<Material> listCell = new ListCell<Material>() {
                @Override
                protected void updateItem(Material item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        setGraphic(null);
                        setText(item.getTitle());
                    }
                }

            };
                    return listCell;
                }
        );

        JFXTextField title = new JFXTextField();

        title.getStyleClass().add("title");
        title.setPromptText("Introduzca El Titulo Del Anime");
        contentFormAnime.setHeading(createHeadingForDialog(title, dialogFormAnime));

        VBox content = new VBox();
        dymanicConent = new VBox();
        content.setSpacing(10);

        content.getChildren().add(createOptions());
        content.getChildren().add(dymanicConent);
        contentFormAnime.setBody(content);

        JFXButton apply = new JFXButton("Add");
        apply.setDisable(true);
        apply.setOnMouseClicked((event) -> {
            if (animeToEdit == null) {
                if (validateTitleAnime(title.getText())) {
                    Anime newAnime = new Anime();

                    newAnime.setTitle(title.getText());
                    newAnime.setSynopsis(synopsisForFormOfMaterial.getText());
                    newAnime.setChapter(counterForFormOfMaterial.getValue());
                    newAnime.setYear(yearsForFormOfMaterial.getValue() == null ? 0 : yearsForFormOfMaterial.getValue());
                    newAnime.setRating(starRatingForFormOfMaterial.getRating());
                    newAnime.setPhotoAddress(nameFile);
                    cleanPhoto(newAnime);
                    copyPhoto();

                    CatalogManager.addAnime(newAnime);
                    doAssignsToMaterial(newAnime.getIdMaterial());
                    CatalogManager.updateExtras(newAnime);

                    paneByTypes.getSelectionModel().select(Display.TAB_FOR_ANIME);
                    title.setPromptText("El Anime Ha Sido Agregado");
                    showDialogRefresh();
                } else {
                    title.setPromptText("El Anime Esta Disponible");
                }
            } else {

                animeToEdit.setTitle(title.getText());
                animeToEdit.setSynopsis(synopsisForFormOfMaterial.getText());
                animeToEdit.setChapter(counterForFormOfMaterial.getValue());
                animeToEdit.setYear(yearsForFormOfMaterial.getValue() == null ? 0 : yearsForFormOfMaterial.getValue());
                animeToEdit.setRating(starRatingForFormOfMaterial.getRating());
                animeToEdit.setPhotoAddress(nameFile);

                cleanPhoto(animeToEdit);
                copyPhoto();

                doAssignsToMaterial(animeToEdit.getIdMaterial());

                CatalogManager.editAnime(animeToEdit);

                title.setPromptText("El Anime Ha Sido Editado");

                showDialogRefresh();
            }
        });
        apply.getStyleClass().add(BUTTON_SMALL);
        ArrayList<JFXButton> buttonActions = new ArrayList<>();
        buttonActions.add(apply);
        contentFormAnime.setActions(buttonActions);

        if (animeToEdit != null) {
            System.out.println("Edit Anime:" + animeToEdit.getIdMaterial());
            title.setText(animeToEdit.getTitle());
            synopsisForFormOfMaterial.setText(animeToEdit.getSynopsis());
            counterForFormOfMaterial.setValue(animeToEdit.getChapter());
            if (animeToEdit.getYear() != 0) {
                yearsForFormOfMaterial.setValue(animeToEdit.getYear());
            }
            starRatingForFormOfMaterial.setRating((int) animeToEdit.getRating());
            nameFile = animeToEdit.getPhotoAddress();
            Image pImage = new Image(getAddressPhoto(DIR_PHOTOS + nameFile));
            photoForFormOfMaterial.setImage(pImage);
            toProcessImagen(pImage, photoForFormOfMaterial);

            loadAssignedKindsToMaterial(animeToEdit.getIdMaterial());
            loadAssignedStatesToMaterial(animeToEdit.getIdMaterial());
            loadAssignedAuthorsToMaterial(animeToEdit.getIdMaterial());
            loadAssignedStudiosToMaterial(animeToEdit.getIdMaterial());

            apply.setText("Edit");
            apply.setDisable(false);
        } else {
            galleryOfStateForForm.getItems().forEach((t) -> {
                if (t.getName().equalsIgnoreCase("Wanted")) {
                    t.setChecked(true);
                }
            });

        }

        suggestionsOfAnimes.setSelectionHandler(event -> title.setText(event.getObject().getTitle()));
        title.textProperty().addListener(observable -> {
            suggestionsOfAnimes.filter(s -> s.getTitle().toLowerCase().contains(title.getText().toLowerCase()));
            if (!suggestionsOfAnimes.getFilteredSuggestions().isEmpty()) {
                suggestionsOfAnimes.show(title);
            } else {
                suggestionsOfAnimes.hide();
            }
        });

        title.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                title.setPromptText("Introduzca El Titulo Del Anime");
            }
        });

        title.textProperty().addListener((observable) -> {
            apply.setDisable(title.getText().isEmpty());
        });

        dialogFormAnime.show();

    }

    public void ShowDialogFormDonghua(Donghua donghuaToEdit) {
        resetOptionAndExtras();

        suggestionsOfDonghuas.setSuggestionsCellFactory(
                list -> {
                    ListCell<Material> listCell = new ListCell<Material>() {
                @Override
                protected void updateItem(Material item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        setGraphic(null);
                        setText(item.getTitle());
                    }
                }

            };
                    return listCell;
                }
        );

        JFXTextField title = new JFXTextField();
        title.getStyleClass().add("title");
        title.setPromptText("Introduzca El Titulo Del Donghua");
        contentFormDonghua.setHeading(createHeadingForDialog(title, dialogFormDonghua));

        VBox content = new VBox();
        dymanicConent = new VBox();
        content.setSpacing(10);
        content.getChildren().add(createOptions());
        content.getChildren().add(dymanicConent);
        contentFormDonghua.setBody(content);

        JFXButton apply = new JFXButton("Add");
        apply.setDisable(true);
        apply.setOnMouseClicked((event) -> {
            if (donghuaToEdit == null) {
                if (validateTitleDonghua(title.getText())) {
                    Donghua newDonghua = new Donghua();

                    newDonghua.setTitle(title.getText());
                    newDonghua.setSynopsis(synopsisForFormOfMaterial.getText());
                    newDonghua.setChapter(counterForFormOfMaterial.getValue());
                    newDonghua.setYear(yearsForFormOfMaterial.getValue() == null ? 0 : yearsForFormOfMaterial.getValue());
                    newDonghua.setRating(starRatingForFormOfMaterial.getRating());
                    newDonghua.setPhotoAddress(nameFile);
                    cleanPhoto(newDonghua);
                    copyPhoto();

                    CatalogManager.addDonghua(newDonghua);
                    doAssignsToMaterial(newDonghua.getIdMaterial());
                    CatalogManager.updateExtras(newDonghua);

                    suggestionsOfDonghuas.getSuggestions().setAll(DarkStyleCatalogSQL.getAllDonghua());
                    galleryOfDonghuaForTabOfDonghua.setItems(DarkStyleCatalogSQL.getAllDonghua());
                    paneByTypes.getSelectionModel().select(Display.TAB_FOR_DONGHUA);
                    title.setPromptText("El Donghua Ha Sido Agregado");
                    showDialogRefresh();
                } else {
                    title.setPromptText("El Donghua Esta Disponible");
                }
            } else {

                donghuaToEdit.setTitle(title.getText());
                donghuaToEdit.setSynopsis(synopsisForFormOfMaterial.getText());
                donghuaToEdit.setChapter(counterForFormOfMaterial.getValue());
                donghuaToEdit.setYear(yearsForFormOfMaterial.getValue() == null ? 0 : yearsForFormOfMaterial.getValue());
                donghuaToEdit.setRating(starRatingForFormOfMaterial.getRating());
                donghuaToEdit.setPhotoAddress(nameFile);
                cleanPhoto(donghuaToEdit);
                copyPhoto();

                doAssignsToMaterial(donghuaToEdit.getIdMaterial());
                CatalogManager.editDonghua(donghuaToEdit);

                title.setPromptText("El Donghua Ha Sido Editado");
                suggestionsOfDonghuas.getSuggestions().setAll(DarkStyleCatalogSQL.getAllDonghua());
                galleryOfDonghuaForTabOfDonghua.setItems(DarkStyleCatalogSQL.getAllDonghua());
                showDialogRefresh();
            }
        });
        apply.getStyleClass().add(BUTTON_SMALL);
        ArrayList<JFXButton> buttonActions = new ArrayList<>();
        buttonActions.add(apply);
        contentFormDonghua.setActions(buttonActions);

        if (donghuaToEdit != null) {
            title.setText(donghuaToEdit.getTitle());
            synopsisForFormOfMaterial.setText(donghuaToEdit.getSynopsis());
            counterForFormOfMaterial.setValue(donghuaToEdit.getChapter());
            if (donghuaToEdit.getYear() != 0) {
                yearsForFormOfMaterial.setValue(donghuaToEdit.getYear());
            }
            starRatingForFormOfMaterial.setRating((int) donghuaToEdit.getRating());
            nameFile = donghuaToEdit.getPhotoAddress();
            Image pImage = new Image(getAddressPhoto(DIR_PHOTOS + nameFile));
            photoForFormOfMaterial.setImage(pImage);
            toProcessImagen(pImage, photoForFormOfMaterial);

            loadAssignedKindsToMaterial(donghuaToEdit.getIdMaterial());
            loadAssignedStatesToMaterial(donghuaToEdit.getIdMaterial());
            loadAssignedAuthorsToMaterial(donghuaToEdit.getIdMaterial());
            loadAssignedStudiosToMaterial(donghuaToEdit.getIdMaterial());

            apply.setText("Edit");
            apply.setDisable(false);
        } else {
            galleryOfStateForForm.getItems().forEach((t) -> {
                if (t.getName().equalsIgnoreCase("Wanted")) {
                    t.setChecked(true);
                }
            });

        }

        suggestionsOfDonghuas.setSelectionHandler(event -> title.setText(event.getObject().getTitle()));
        title.textProperty().addListener(observable -> {
            suggestionsOfDonghuas.filter(s -> s.getTitle().toLowerCase().contains(title.getText().toLowerCase()));
            if (!suggestionsOfDonghuas.getFilteredSuggestions().isEmpty()) {
                suggestionsOfDonghuas.show(title);
            } else {
                suggestionsOfDonghuas.hide();
            }
        });

        title.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                title.setPromptText("Introduzca El Titulo Del Donghua");
            }
        });

        title.textProperty().addListener((observable) -> {
            apply.setDisable(title.getText().isEmpty());
        });
        dialogFormDonghua.show();
    }

    public void ShowDialogFormLightNovel(LightNovel lightNovelToEdit) {
        resetOptionAndExtras();

        suggestionsOfLightNovels.setSuggestionsCellFactory(
                list -> {
                    ListCell<Material> listCell = new ListCell<Material>() {
                @Override
                protected void updateItem(Material item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        setGraphic(null);
                        setText(item.getTitle());
                    }
                }

            };
                    return listCell;
                }
        );

        JFXTextField title = new JFXTextField();
        title.getStyleClass().add("title");
        title.setPromptText("Introduzca El Titulo De la Novela Ligera");
        contentFormLightNovel.setHeading(createHeadingForDialog(title, dialogFormLightNovel));

        VBox content = new VBox();
        dymanicConent = new VBox();
        content.setSpacing(10);
        content.getChildren().add(createOptions());
        content.getChildren().add(dymanicConent);
        contentFormLightNovel.setBody(content);

        JFXButton apply = new JFXButton("Add");
        apply.setDisable(true);
        apply.setOnMouseClicked((event) -> {
            if (lightNovelToEdit == null) {
                if (validateTitleLightNovel(title.getText())) {
                    LightNovel newLightNovel = new LightNovel();

                    newLightNovel.setTitle(title.getText());
                    newLightNovel.setSynopsis(synopsisForFormOfMaterial.getText());
                    newLightNovel.setChapter(counterForFormOfMaterial.getValue());
                    newLightNovel.setYear(yearsForFormOfMaterial.getValue() == null ? 0 : yearsForFormOfMaterial.getValue());
                    newLightNovel.setRating(starRatingForFormOfMaterial.getRating());
                    newLightNovel.setPhotoAddress(nameFile);
                    newLightNovel.setCountry(countriesForFormOfMaterial.getValue() == null ? "" : countriesForFormOfMaterial.getValue());
                    cleanPhoto(newLightNovel);
                    copyPhoto();

                    CatalogManager.addLightNovel(newLightNovel);
                    doAssignsToMaterial(newLightNovel.getIdMaterial());
                    CatalogManager.updateExtras(newLightNovel);

                    suggestionsOfLightNovels.getSuggestions().setAll(DarkStyleCatalogSQL.getAllLightNovel());
                    galleryOfLightNovelForTabOfLightNovel.setItems(DarkStyleCatalogSQL.getAllLightNovel());
                    paneByTypes.getSelectionModel().select(Display.TAB_FOR_LIGHTNOVEL);
                    title.setPromptText("La Novela Ligera Ha Sido Agregado");
                    showDialogRefresh();
                } else {
                    title.setPromptText("La Novela Ligera Esta Disponible");
                }
            } else {

                lightNovelToEdit.setTitle(title.getText());
                lightNovelToEdit.setSynopsis(synopsisForFormOfMaterial.getText());
                lightNovelToEdit.setChapter(counterForFormOfMaterial.getValue());
                lightNovelToEdit.setYear(yearsForFormOfMaterial.getValue() == null ? 0 : yearsForFormOfMaterial.getValue());
                lightNovelToEdit.setRating(starRatingForFormOfMaterial.getRating());
                lightNovelToEdit.setPhotoAddress(nameFile);
                lightNovelToEdit.setCountry(countriesForFormOfMaterial.getValue() == null ? "" : countriesForFormOfMaterial.getValue());
                cleanPhoto(lightNovelToEdit);
                copyPhoto();

                doAssignsToMaterial(lightNovelToEdit.getIdMaterial());
                CatalogManager.editLightNovel(lightNovelToEdit);

                title.setPromptText("La Novela Ligera Ha Sido Editado");
                suggestionsOfLightNovels.getSuggestions().setAll(DarkStyleCatalogSQL.getAllLightNovel());
                galleryOfLightNovelForTabOfLightNovel.setItems(DarkStyleCatalogSQL.getAllLightNovel());
                showDialogRefresh();
            }
        });
        apply.getStyleClass().add(BUTTON_SMALL);
        ArrayList<JFXButton> buttonActions = new ArrayList<>();
        buttonActions.add(apply);
        contentFormLightNovel.setActions(buttonActions);

        if (lightNovelToEdit != null) {
            title.setText(lightNovelToEdit.getTitle());
            synopsisForFormOfMaterial.setText(lightNovelToEdit.getSynopsis());
            counterForFormOfMaterial.setValue(lightNovelToEdit.getChapter());
            if (lightNovelToEdit.getYear() != 0) {
                yearsForFormOfMaterial.setValue(lightNovelToEdit.getYear());
            }
            if (!lightNovelToEdit.getCountry().isEmpty()) {
                countriesForFormOfMaterial.setValue(lightNovelToEdit.getCountry());
            }
            starRatingForFormOfMaterial.setRating((int) lightNovelToEdit.getRating());
            nameFile = lightNovelToEdit.getPhotoAddress();
            Image pImage = new Image(getAddressPhoto(DIR_PHOTOS + nameFile));
            photoForFormOfMaterial.setImage(pImage);
            toProcessImagen(pImage, photoForFormOfMaterial);

            loadAssignedKindsToMaterial(lightNovelToEdit.getIdMaterial());
            loadAssignedStatesToMaterial(lightNovelToEdit.getIdMaterial());
            loadAssignedAuthorsToMaterial(lightNovelToEdit.getIdMaterial());
            loadAssignedStudiosToMaterial(lightNovelToEdit.getIdMaterial());

            apply.setText("Edit");
            apply.setDisable(false);
        } else {
            galleryOfStateForForm.getItems().forEach((t) -> {
                if (t.getName().equalsIgnoreCase("Wanted")) {
                    t.setChecked(true);
                }
            });

        }

        suggestionsOfLightNovels.setSelectionHandler(event -> title.setText(event.getObject().getTitle()));
        title.textProperty().addListener(observable -> {
            suggestionsOfLightNovels.filter(s -> s.getTitle().toLowerCase().contains(title.getText().toLowerCase()));
            if (!suggestionsOfLightNovels.getFilteredSuggestions().isEmpty()) {
                suggestionsOfLightNovels.show(title);
            } else {
                suggestionsOfLightNovels.hide();
            }
        });

        title.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                title.setPromptText("Introduzca El Titulo De La Novela Ligera");
            }
        });

        title.textProperty().addListener((observable) -> {
            apply.setDisable(title.getText().isEmpty());
        });

        dialogFormLightNovel.show();
    }

    public void ShowDialogFormState(State stateToEdit) {

        suggestionsOfStates.setSuggestionsCellFactory(
                list -> {
                    ListCell<State> listCell = new ListCell<State>() {
                @Override
                protected void updateItem(State item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        setGraphic(null);
                        setText(item.getName());
                    }
                }

            };
                    return listCell;
                }
        );
        JFXTextField name = new JFXTextField();
        name.getStyleClass().add("title");
        name.setPromptText("Introduzca El Nombre Del Estado");

        contentFormState.setHeading(createHeadingForDialog(name, dialogFormState));

        TextArea description = new TextArea();
        contentFormState.setBody(description);

        JFXButton apply = new JFXButton("Add");
        apply.setDisable(true);
        apply.setOnMouseClicked((event) -> {
            if (stateToEdit == null) {
                if (validateNameState(name.getText())) {
                    CatalogManager.addState(new State(name.getText(), description.getText()));
                    name.setPromptText("El Estado Ha Sido Agregado");
                    showDialogRefresh();
                } else {
                    name.setPromptText("El Estado Esta Disponible");
                }
            } else {
                stateToEdit.setName(name.getText());
                stateToEdit.setDescription(description.getText());
                CatalogManager.editState(stateToEdit);
                name.setPromptText("El Estado Ha Sido Editado");
                showDialogRefresh();
            }
        });
        apply.getStyleClass().add(BUTTON_SMALL);
        ArrayList<JFXButton> buttonActions = new ArrayList<>();
        buttonActions.add(apply);
        contentFormState.setActions(buttonActions);

        if (stateToEdit != null) {
            name.setText(stateToEdit.getName());
            description.setText(stateToEdit.getDescription());
            apply.setText("Edit");
            apply.setDisable(false);
        }

        suggestionsOfStates.setSelectionHandler(event -> name.setText(event.getObject().getName()));

        name.textProperty().addListener(observable -> {
            suggestionsOfStates.filter(s -> s.getName().toLowerCase().contains(name.getText().toLowerCase()));
            if (!suggestionsOfStates.getFilteredSuggestions().isEmpty()) {
                suggestionsOfStates.show(name);
            } else {
                suggestionsOfStates.hide();
            }
        });

        name.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                name.setPromptText("Introduzca El Nombre Del Estado");
            }
        });
        name.textProperty().addListener((observable) -> {
            apply.setDisable(name.getText().isEmpty());
        });
        dialogFormState.show();

    }

    public void ShowDialogFormKind(Kind kindToEdit) {

        suggestionsOfKinds.setSuggestionsCellFactory(
                list -> {
                    ListCell<Kind> listCell = new ListCell<Kind>() {
                @Override
                protected void updateItem(Kind item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        setGraphic(null);
                        setText(item.getName());
                    }
                }

            };
                    return listCell;
                }
        );
        JFXTextField name = new JFXTextField();
        name.getStyleClass().add("title");
        name.setPromptText("Introduzca El Nombre Del Genero");

        contentFormKind.setHeading(createHeadingForDialog(name, dialogFormKind));

        TextArea description = new TextArea();
        contentFormKind.setBody(description);

        JFXButton apply = new JFXButton("Add");
        apply.setDisable(true);
        apply.setOnMouseClicked((event) -> {
            if (kindToEdit == null) {
                if (validateNameKind(name.getText())) {
                    CatalogManager.addKind(new Kind(name.getText(), description.getText()));
                    name.setPromptText("El Genero Ha Sido Agregado");
                    showDialogRefresh();
                } else {
                    name.setPromptText("El Genero Esta Disponible");
                }
            } else {
                kindToEdit.setName(name.getText());
                kindToEdit.setDescription(description.getText());
                CatalogManager.editKind(kindToEdit);
                name.setPromptText("El Genero Ha Sido Editado");
                showDialogRefresh();
            }
        });
        apply.getStyleClass().add(BUTTON_SMALL);
        ArrayList<JFXButton> buttonActions = new ArrayList<>();
        buttonActions.add(apply);
        contentFormKind.setActions(buttonActions);

        if (kindToEdit != null) {
            name.setText(kindToEdit.getName());
            description.setText(kindToEdit.getDescription());
            apply.setText("Edit");
            apply.setDisable(false);
        }

        suggestionsOfKinds.setSelectionHandler(event -> name.setText(event.getObject().getName()));

        name.textProperty().addListener(observable -> {
            suggestionsOfKinds.filter(s -> s.getName().toLowerCase().contains(name.getText().toLowerCase()));
            if (!suggestionsOfKinds.getFilteredSuggestions().isEmpty()) {
                suggestionsOfKinds.show(name);
            } else {
                suggestionsOfKinds.hide();
            }
        });

        name.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                name.setPromptText("Introduzca El Nombre Del Genero");
            }
        });
        name.textProperty().addListener((observable) -> {
            apply.setDisable(name.getText().isEmpty());
        });
        dialogFormKind.show();
    }

    public void ShowDialogFormAuthor(Author authorToEdit) {
        suggestionsOfAuthors.setSuggestionsCellFactory(
                list -> {
                    ListCell<Author> listCell = new ListCell<Author>() {
                @Override
                protected void updateItem(Author item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        setGraphic(null);
                        setText(item.getName());
                    }
                }

            };
                    return listCell;
                }
        );
        JFXTextField name = new JFXTextField();
        name.getStyleClass().add("title");
        name.setPromptText("Introduzca El Nombre Del Autor");

        contentFormAuthor.setHeading(createHeadingForDialog(name, dialogFormAuthor));

        // TextArea description = new TextArea();
        // contentFormAuthor.setBody(description);
        JFXButton apply = new JFXButton("Add");
        apply.setDisable(true);
        apply.setOnMouseClicked((event) -> {
            if (authorToEdit == null) {
                if (validateNameAuthor(name.getText())) {
                    CatalogManager.addAuthor(new Author(name.getText()/*, description.getText()*/));
                    name.setPromptText("El Autor Ha Sido Agregado");
                    showDialogRefresh();
                } else {
                    name.setPromptText("El Autor Esta Disponible");
                }
            } else {
                authorToEdit.setName(name.getText());
                // authorToEdit.setDescription(description.getText());
                CatalogManager.editAuthor(authorToEdit);
                name.setPromptText("El Genero Ha Sido Editado");
                galleryOfAuthor.setItems(DarkStyleCatalogSQL.getAllAuthor());
                showDialogRefresh();
            }
        });
        apply.getStyleClass().add(BUTTON_SMALL);
        ArrayList<JFXButton> buttonActions = new ArrayList<>();
        buttonActions.add(apply);
        contentFormAuthor.setActions(buttonActions);

        if (authorToEdit != null) {
            name.setText(authorToEdit.getName());
            // description.setText(authorToEdit.getDescription());
            apply.setText("Edit");
            apply.setDisable(false);
        }

        suggestionsOfAuthors.setSelectionHandler(event -> name.setText(event.getObject().getName()));

        name.textProperty().addListener(observable -> {
            suggestionsOfAuthors.filter(s -> s.getName().toLowerCase().contains(name.getText().toLowerCase()));
            if (!suggestionsOfAuthors.getFilteredSuggestions().isEmpty()) {
                suggestionsOfAuthors.show(name);
            } else {
                suggestionsOfAuthors.hide();
            }
        });

        name.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                name.setPromptText("Introduzca El Nombre Del Autor");
            }
        });
        name.textProperty().addListener((observable) -> {
            apply.setDisable(name.getText().isEmpty());
        });
        dialogFormAuthor.show();
    }

    public void ShowDialogFormStudio(Studio studioToEdit) {

        suggestionsOfStudios.setSuggestionsCellFactory(
                list -> {
                    ListCell<Studio> listCell = new ListCell<Studio>() {
                @Override
                protected void updateItem(Studio item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        setGraphic(null);
                        setText(item.getName());
                    }
                }

            };
                    return listCell;
                }
        );
        JFXTextField name = new JFXTextField();
        name.getStyleClass().add("title");
        name.setPromptText("Introduzca El Nombre Del Estudio");

        contentFormStudio.setHeading(createHeadingForDialog(name, dialogFormStudio));

        TextArea description = new TextArea();
        contentFormStudio.setBody(description);

        JFXButton apply = new JFXButton("Add");
        apply.setDisable(true);
        apply.setOnMouseClicked((event) -> {
            if (studioToEdit == null) {
                if (validateNameStudio(name.getText())) {
                    CatalogManager.addStudio(new Studio(name.getText(), description.getText()));
                    name.setPromptText("El Estudio Ha Sido Agregado");
                    showDialogRefresh();
                } else {
                    name.setPromptText("El Estudio Esta Disponible");
                }
            } else {
                studioToEdit.setName(name.getText());
                studioToEdit.setDescription(description.getText());
                CatalogManager.editStudio(studioToEdit);
                name.setPromptText("El Estudio Ha Sido Editado");
                galleryOfStudio.setItems(DarkStyleCatalogSQL.getAllStudio());
                showDialogRefresh();
            }
        });
        apply.getStyleClass().add(BUTTON_SMALL);
        ArrayList<JFXButton> buttonActions = new ArrayList<>();
        buttonActions.add(apply);
        contentFormStudio.setActions(buttonActions);

        if (studioToEdit != null) {
            name.setText(studioToEdit.getName());
            description.setText(studioToEdit.getDescription());
            apply.setText("Edit");
            apply.setDisable(false);
        }

        suggestionsOfStudios.setSelectionHandler(event -> name.setText(event.getObject().getName()));

        name.textProperty().addListener(observable -> {
            suggestionsOfStudios.filter(s -> s.getName().toLowerCase().contains(name.getText().toLowerCase()));
            if (!suggestionsOfStudios.getFilteredSuggestions().isEmpty()) {
                suggestionsOfStudios.show(name);
            } else {
                suggestionsOfStudios.hide();
            }
        });

        name.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                name.setPromptText("Introduzca El Nombre Del Estudio");
            }
        });
        name.textProperty().addListener((observable) -> {
            apply.setDisable(name.getText().isEmpty());
        });
        dialogFormStudio.show();
    }

    public void ShowDialogGalleryState() {
        Label name = new Label("States", createIconForCategoryAndOption("States"));
        name.getStyleClass().addAll(HEADING_BIG, DIALOG_BIG);
        contentGalleryState.setHeading(createHeadingForDialog(name, dialogGalleryState));
        galleryOfState = createGalleryStates();
        galleryOfState.setItems(CatalogManager.STATES);
        contentGalleryState.setBody(galleryOfState);

        dialogGalleryState.show();

        ZoomInDown animationIn = new ZoomInDown(dialogGalleryState);
        animationIn.setSpeed(0.75);
        animationIn.play();
    }

    public void ShowDialogGalleryKind() {

        Label name = new Label("Kinds", createIconForCategoryAndOption("Kinds"));
        name.getStyleClass().addAll(HEADING_BIG, DIALOG_BIG);
        contentGalleryKind.setHeading(createHeadingForDialog(name, dialogGalleryKind));
        galleryOfKind = createGalleryKinds();
        galleryOfKind.setItems(CatalogManager.KINDS);
        contentGalleryKind.setBody(galleryOfKind);

        dialogGalleryKind.show();

        ZoomInDown animationIn = new ZoomInDown(dialogGalleryKind);
        animationIn.setSpeed(0.75);
        animationIn.play();

    }

    public void ShowDialogGalleryAuthor() {

        Label name = new Label("Authors", createIconForCategoryAndOption("Authors"));
        name.getStyleClass().addAll(HEADING_BIG, DIALOG_BIG);
        contentGalleryAuthor.setHeading(createHeadingForDialog(name, dialogGalleryAuthor));
        galleryOfAuthor = createGalleryAuthors();
        galleryOfAuthor.setItems(CatalogManager.AUTHORS);
        contentGalleryAuthor.setBody(galleryOfAuthor);

        dialogGalleryAuthor.show();
        ZoomInDown animationIn = new ZoomInDown(dialogGalleryAuthor);
        animationIn.setSpeed(0.75);
        animationIn.play();
    }

    public void ShowDialogGalleryStudio() {

        Label name = new Label("Studios", createIconForCategoryAndOption("Studios"));
        name.getStyleClass().addAll(HEADING_BIG, DIALOG_BIG);
        contentGalleryStudio.setHeading(createHeadingForDialog(name, dialogGalleryStudio));
        galleryOfStudio = createGalleryStudios();
        galleryOfStudio.setItems(CatalogManager.STUDIOS);
        contentGalleryStudio.setBody(galleryOfStudio);

        dialogGalleryStudio.show();

        ZoomInDown animationIn = new ZoomInDown(dialogGalleryStudio);
        animationIn.setSpeed(0.75);
        animationIn.play();
    }

    ////////////////////////////////////////////////////////////////////
    //                          Create Content                        //
    ///////////////////////////////////////////////////////////////////
    public void createFilterForKind() {

        iconOfKindFiltered = new FontAwesomeIconView(FontAwesomeIcon.FILTER);
        iconOfKindFiltered.getStyleClass().addAll("search-icon", ICON_SMALL, SHADOWED);
        iconOfKindFiltered.setMouseTransparent(UNTOUCHABLE);

        kindToFilter = new JFXTextField();
        kindToFilter.getStyleClass().add("search");
        kindToFilter.setPromptText("Introduzca Criterio de Filtrado");

        kindToFilter.textProperty().addListener(observable -> {
            filteredByKinds.setPredicate((extra) -> {
                return extra.getName().toLowerCase().contains(kindToFilter.getText().toLowerCase());
            });
        });
        kindToFilter.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                filteredByKinds.setPredicate((extra) -> {
                    return extra.getName().toLowerCase().contains(kindToFilter.getText().toLowerCase());
                });
            }
        });

        kindToFilter.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                iconOfKindFiltered.setVisible(false);
            } else {
                if (kindToFilter.getText().isEmpty()) {
                    iconOfKindFiltered.setVisible(true);
                }
            }
        });

        containerOfKindFiltered = new StackPane(kindToFilter, iconOfKindFiltered);
        boxForKind = new VBox(containerOfKindFiltered, galleryOfKindForForm);
        boxForKind.setSpacing(10);

    }

    public void createFilterForState() {

        iconOfStateFiltered = new FontAwesomeIconView(FontAwesomeIcon.FILTER);
        iconOfStateFiltered.getStyleClass().addAll("search-icon", ICON_SMALL, SHADOWED);
        iconOfStateFiltered.setMouseTransparent(UNTOUCHABLE);

        stateToFilter = new JFXTextField();
        stateToFilter.getStyleClass().add("search");
        stateToFilter.setPromptText("Introduzca Criterio de Filtrado");

        stateToFilter.textProperty().addListener(observable -> {
            filteredByStates.setPredicate((extra) -> {
                return extra.getName().toLowerCase().contains(stateToFilter.getText().toLowerCase());
            });
        });
        stateToFilter.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                filteredByStates.setPredicate((extra) -> {
                    return extra.getName().toLowerCase().contains(stateToFilter.getText().toLowerCase());
                });
            }
        });

        stateToFilter.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                iconOfStateFiltered.setVisible(false);
            } else {
                if (stateToFilter.getText().isEmpty()) {
                    iconOfStateFiltered.setVisible(true);
                }
            }
        });

        containerOfStateFiltered = new StackPane(stateToFilter, iconOfStateFiltered);
        boxForState = new VBox(containerOfStateFiltered, galleryOfStateForForm);
        boxForState.setSpacing(10);

    }

    public void createFilterForAuthor() {

        iconOfAuthorFiltered = new FontAwesomeIconView(FontAwesomeIcon.FILTER);
        iconOfAuthorFiltered.getStyleClass().addAll("search-icon", ICON_SMALL, SHADOWED);
        iconOfAuthorFiltered.setMouseTransparent(UNTOUCHABLE);

        authorToFilter = new JFXTextField();
        authorToFilter.getStyleClass().add("search");
        authorToFilter.setPromptText("Introduzca Criterio de Filtrado");

        authorToFilter.textProperty().addListener(observable -> {
            filteredByAuthors.setPredicate((extra) -> {
                return extra.getName().toLowerCase().contains(authorToFilter.getText().toLowerCase());
            });
        });
        authorToFilter.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                filteredByAuthors.setPredicate((extra) -> {
                    return extra.getName().toLowerCase().contains(authorToFilter.getText().toLowerCase());
                });
            }
        });

        authorToFilter.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                iconOfAuthorFiltered.setVisible(false);
            } else {
                if (authorToFilter.getText().isEmpty()) {
                    iconOfAuthorFiltered.setVisible(true);
                }
            }
        });

        containerOfAuthorFiltered = new StackPane(authorToFilter, iconOfAuthorFiltered);
        boxForAuthor = new VBox(containerOfAuthorFiltered, galleryOfAuthorForForm);
        boxForAuthor.setSpacing(10);

    }

    public void createFilterForStudio() {

        iconOfStudioFiltered = new FontAwesomeIconView(FontAwesomeIcon.FILTER);
        iconOfStudioFiltered.getStyleClass().addAll("search-icon", ICON_SMALL, SHADOWED);
        iconOfStudioFiltered.setMouseTransparent(UNTOUCHABLE);

        studioToFilter = new JFXTextField();
        studioToFilter.getStyleClass().add("search");
        studioToFilter.setPromptText("Introduzca Criterio de Filtrado");

        studioToFilter.textProperty().addListener(observable -> {
            filteredByStudios.setPredicate((extra) -> {
                return extra.getName().toLowerCase().contains(studioToFilter.getText().toLowerCase());
            });
        });
        studioToFilter.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                filteredByStudios.setPredicate((extra) -> {
                    return extra.getName().toLowerCase().contains(studioToFilter.getText().toLowerCase());
                });
            }
        });

        studioToFilter.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                iconOfStudioFiltered.setVisible(false);
            } else {
                if (studioToFilter.getText().isEmpty()) {
                    iconOfStudioFiltered.setVisible(true);
                }
            }
        });

        containerOfStudioFiltered = new StackPane(studioToFilter, iconOfStudioFiltered);
        boxForStudio = new VBox(containerOfStudioFiltered, galleryOfStudioForForm);
        boxForStudio.setSpacing(10);
    }

    public ScrollPane createSideBarLeft() {

        scrollPaneSideBarLeft = new ScrollPane();
        VBox buttonActions = new VBox();
        buttonActions.setPadding(new Insets(20, 10, 0, 10));
        buttonActions.setSpacing(10);
        scrollPaneSideBarLeft.setContent(buttonActions);
        actions.forEach((action) -> {

            JFXButton buttonAction = new PulseButton(action);
            buttonAction.setOpacity(HIDDEN);
            buttonAction.setGraphic(createIconForCategoryAndOption(action));
            buttonAction.setOnMouseClicked((event) -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    DoAction(action);
                }
            });
            buttonAction.getStyleClass().add(BUTTON_SIDEBAR);
            buttonActions.getChildren().add(buttonAction);
            quantityOfActions++;
        });
        JFXScrollPane.smoothScrolling(scrollPaneSideBarLeft);

        scrollPaneSideBarLeft.setOnMouseEntered((event) -> {
            delayDisable.purge();
            buttonOfLeftMenu.setDisable(true);
            showOptions(buttonActions);
        });

        scrollPaneSideBarLeft.setOnMouseExited((event) -> {
            scrollPaneSideBarLeft.setMouseTransparent(UNTOUCHABLE);
            delayDisable.schedule(new TimerTask() {
                @Override
                public void run() {
                    buttonOfLeftMenu.setDisable(false);
                    scrollPaneSideBarLeft.setMouseTransparent(TOUCHABLE);
                    if (!activedSideBarLeft) {
                        hideOptions((VBox) scrollPaneSideBarLeft.getContent());
                    }
                }
            }, 200);
        });

        return scrollPaneSideBarLeft;
    }

    public ScrollPane createSideBarRight() {
        scrollPaneSideBarRight = new ScrollPane();
        scrollPaneSideBarRight.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        VBox buttonFilters = new VBox();
        buttonFilters.setPadding(new Insets(20, 10, 0, 10));
        buttonFilters.setSpacing(10);
        scrollPaneSideBarRight.setContent(buttonFilters);

        filtters.forEach(filtter -> {
            JFXButton buttonFilter = new PulseButton(filtter);
            buttonFilter.setOpacity(HIDDEN);
            StackPane pane = new StackPane();
            FontAwesomeIconView iconFilter = new FontAwesomeIconView(FontAwesomeIcon.FILTER);
            iconFilter.getStyleClass().addAll(ICON_MIDDLE, SHADOWED, ICON_WITHOUT_FILTER);

            FontAwesomeIconView extraIcon = createIconForCategoryAndOption(filtter);
            extraIcon.getStyleClass().setAll(ICON_GLYPH, ICON_EXTRAS, SHADOWED, ICON_WITHOUT_FILTER);

            pane.getChildren().add(extraIcon);
            pane.getChildren().add(iconFilter);
            buttonFilter.setGraphic(pane);
            buttonFilter.setOnMouseClicked((event) -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    ShowDialogByFilter(filtter);
                }
                event.consume();
            });
            buttonFilter.getStyleClass().add(BUTTON_SIDEBAR);
            buttonFilters.getChildren().add(buttonFilter);
            buttonOfFilters.add(buttonFilter);

        });

        JFXScrollPane.smoothScrolling(scrollPaneSideBarRight);

        scrollPaneSideBarRight.setOnMouseEntered((event) -> {
            delayDisable.purge();
            buttonOfRightMenu.setDisable(true);
            showOptions(buttonFilters);
        });

        scrollPaneSideBarRight.setOnMouseExited((event) -> {
            scrollPaneSideBarRight.setMouseTransparent(UNTOUCHABLE);
            delayDisable.schedule(new TimerTask() {
                @Override
                public void run() {
                    buttonOfRightMenu.setDisable(false);
                    scrollPaneSideBarRight.setMouseTransparent(TOUCHABLE);
                    if (!activedSideBarRight) {
                        hideOptions((VBox) scrollPaneSideBarRight.getContent());
                    }
                }
            }, 200);
        });

        return scrollPaneSideBarRight;
    }

    public GridView<Kind> createListCheckKinds() {
        GridView<Kind> galleryKinds = new GridView<>();
        galleryKinds.getStyleClass().add(MODE_CHECK);
        galleryKinds.setCellFactory(gridView -> new GridCell<Kind>() {

            @Override
            public void updateItem(Kind item, boolean empty) {
                this.getStyleClass().add(MODE_CHECK);
                this.setOpacity(HIDDEN);
                if (item == null || empty) {
                    setGraphic(null);
                    setText(null);
                } else {

                    setText(null);

                    JFXCheckBox name = new JFXCheckBox(item.getName());
                    if (!item.getDescription().isEmpty()) {
                        name.getStyleClass().add("check-indicator");
                    }
                    name.selectedProperty().bindBidirectional(item.checkedProperty());

                    setTooltip(item.getDescription().isEmpty() ? null : new Tooltip(FitToolTip(item.getDescription())));
                    setGraphic(name);
                    AnimationFX fadeIn = new FadeIn(this);
                    fadeIn.setSpeed(0.5);
                    fadeIn.play();
                }

            }
        }
        );

        return galleryKinds;
    }

    public GridView<State> createListCheckStates() {
        GridView<State> galleryStates = new GridView<>();
        galleryStates.getStyleClass().add(MODE_CHECK);

        galleryStates.setCellFactory(gridView -> new GridCell<State>() {

            @Override
            public void updateItem(State item, boolean empty) {
                this.getStyleClass().add(MODE_CHECK);
                this.setOpacity(HIDDEN);
                if (item == null || empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setText(null);

                    JFXCheckBox name = new JFXCheckBox((getIndex() + 1) + "-" + item.getName());
                    if (!item.getDescription().isEmpty()) {
                        name.getStyleClass().add("check-indicator");
                    }
                    name.selectedProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue) {
                            item.setPosition(++positionStateGobal);
                        } else {
                            item.setPosition(INFINITY);
                        }
                    });
                    name.selectedProperty().bindBidirectional(item.checkedProperty());
                    setTooltip(item.getDescription().isEmpty() ? null : new Tooltip(FitToolTip(item.getDescription())));
                    setGraphic(name);
                    AnimationFX fadeIn = new FadeIn(this);
                    fadeIn.setSpeed(0.5);
                    fadeIn.play();
                }

            }
        }
        );

        return galleryStates;
    }

    public GridView<Author> createListCheckAuthors() {
        GridView<Author> galleryAuthors = new GridView<>();
        galleryAuthors.getStyleClass().add(MODE_CHECK);

        galleryAuthors.setCellFactory(gridView -> new GridCell<Author>() {

            @Override
            public void updateItem(Author item, boolean empty) {
                this.getStyleClass().add(MODE_CHECK);
                this.setOpacity(HIDDEN);
                if (item == null || empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setText(null);
                    JFXCheckBox name = new JFXCheckBox(item.getName());

                    name.selectedProperty().bindBidirectional(item.checkedProperty());
                    setGraphic(name);
                    AnimationFX fadeIn = new FadeIn(this);
                    fadeIn.setSpeed(0.5);
                    fadeIn.play();
                }

            }
        }
        );

        return galleryAuthors;
    }

    public GridView<Studio> createListCheckStudios() {
        GridView<Studio> galleryStudios = new GridView<>();
        galleryStudios.getStyleClass().add(MODE_CHECK);

        galleryStudios.setCellFactory(gridView -> new GridCell<Studio>() {

            @Override
            public void updateItem(Studio item, boolean empty) {
                this.getStyleClass().add(MODE_CHECK);
                this.setOpacity(HIDDEN);
                if (item == null || empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setText(null);
                    JFXCheckBox name = new JFXCheckBox(item.getName());
                    if (!item.getDescription().isEmpty()) {
                        name.getStyleClass().add("check-indicator");
                    }
                    name.selectedProperty().bindBidirectional(item.checkedProperty());
                    setTooltip(item.getDescription().isEmpty() ? null : new Tooltip(FitToolTip(item.getDescription())));
                    setGraphic(name);
                    AnimationFX fadeIn = new FadeIn(this);
                    fadeIn.setSpeed(0.5);
                    fadeIn.play();
                }

            }
        }
        );

        return galleryStudios;
    }

    public GridView<Star> createListCheckRatings() {
        GridView<Star> galleryRatings = new GridView<>();
        galleryRatings.getStyleClass().add(MODE_CHECK);

        galleryRatings.setCellFactory(gridView -> new GridCell<Star>() {

            @Override
            public void updateItem(Star item, boolean empty) {
                this.getStyleClass().add(MODE_CHECK);
                if (item == null || empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setText(null);
                    JFXCheckBox name = new JFXCheckBox();
                    StarRating rating = new StarRating(MAXIMUN_STAR, item.getNumber());
                    rating.setAlignment(Pos.CENTER_LEFT);
                    rating.setMouseTransparent(UNTOUCHABLE);
                    name.setGraphic(rating);
                    name.selectedProperty().bindBidirectional(item.checkedProperty());
                    setGraphic(name);
                    AnimationFX fadeIn = new FadeIn(this);
                    fadeIn.setSpeed(0.5);
                    fadeIn.play();
                }

            }
        }
        );

        return galleryRatings;
    }

    public GridView<EmptyField> createListCheckEmptyFields() {
        GridView<EmptyField> galleryEmptyFields = new GridView<>();
        galleryEmptyFields.getStyleClass().add(MODE_CHECK);

        galleryEmptyFields.setCellFactory(gridView -> new GridCell<EmptyField>() {

            @Override
            public void updateItem(EmptyField item, boolean empty) {
                this.getStyleClass().add(MODE_CHECK);
                if (item == null || empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setText(null);
                    JFXCheckBox name = new JFXCheckBox(item.getName());
                    name.selectedProperty().bindBidirectional(item.checkedProperty());
                    setGraphic(name);
                    AnimationFX fadeIn = new FadeIn(this);
                    fadeIn.setSpeed(0.5);
                    fadeIn.play();
                }

            }
        }
        );

        return galleryEmptyFields;
    }

    public GridView<Kind> createGalleryKinds() {
        GridView<Kind> galleryKinds = new GridView<>();

        galleryKinds.getStyleClass().add(MODE_TEXT);

        galleryKinds.setCellFactory(gridView -> new GridCell<Kind>() {

            @Override
            public void updateItem(Kind item, boolean empty) {
                this.getStyleClass().add(MODE_TEXT);
                this.setOpacity(HIDDEN);
                if (item == null || empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setText(null);
                    FontAwesomeIconView iconTrash = new FontAwesomeIconView(FontAwesomeIcon.TRASH_ALT);
                    iconTrash.visibleProperty().bindBidirectional(visibleIconTrash);

                    iconTrash.getStyleClass().addAll(ICON_NORMAL, SHADOWED);
                    ZoomIn in = new ZoomIn(iconTrash);

                    iconTrash.visibleProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue) {
                            visibleIconSlide.set(false);
                            in.play();
                        }
                    });

                    Pulse pulse = new Pulse(iconTrash);

                    iconTrash.setOnMouseEntered((event) -> {
                        pulse.setCycleCount(Animation.INDEFINITE);
                        pulse.setSpeed(2);
                        pulse.play();
                    });

                    iconTrash.setOnMouseExited((event) -> {
                        pulse.stop();

                    });

                    iconTrash.setOnMouseClicked((event) -> {

                        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                            DarkStyleCatalogSQL.deleteKind(item);
                            CatalogManager.removeKind(item);
                            showDialogRefresh();
                        }

                    });
                    Label name = new Label(item.getName(), iconTrash);
                    if (item.getDescription().isEmpty()) {
                        name.getStyleClass().addAll(HEADLINE, HEADLINE_STANDARD);
                    } else {
                        name.getStyleClass().addAll(HEADLINE, HEADLINE_EMPHASIZE);
                    }

                    name.setOnMouseClicked((event) -> {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            ShowDialogFormKind(item);
                        }
                    });
                    setTooltip(item.getDescription().isEmpty() ? null : new Tooltip(FitToolTip(item.getDescription())));
                    setGraphic(name);
                    AnimationFX fadeIn = new FadeIn(this);
                    fadeIn.setSpeed(0.5);
                    fadeIn.play();
                }

            }
        }
        );

        return galleryKinds;
    }

    public GridView<State> createGalleryStates() {
        GridView<State> galleryStates = new GridView<>();
        galleryStates.getStyleClass().add(MODE_TEXT);

        galleryStates.setCellFactory(gridView -> new GridCell<State>() {

            @Override
            public void updateItem(State item, boolean empty) {
                this.getStyleClass().add(MODE_TEXT);
                this.setOpacity(HIDDEN);
                if (item == null || empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setText(null);
                    FontAwesomeIconView iconTrash = new FontAwesomeIconView(FontAwesomeIcon.TRASH_ALT);
                    iconTrash.visibleProperty().bindBidirectional(visibleIconTrash);

                    iconTrash.getStyleClass().addAll(ICON_NORMAL, SHADOWED);

                    ZoomIn in = new ZoomIn(iconTrash);

                    iconTrash.visibleProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue) {
                            visibleIconSlide.set(false);
                            in.play();
                        }
                    });

                    Pulse pulse = new Pulse(iconTrash);

                    iconTrash.setOnMouseEntered((event) -> {
                        pulse.setCycleCount(Animation.INDEFINITE);
                        pulse.setSpeed(2);
                        pulse.play();
                    });

                    iconTrash.setOnMouseExited((event) -> {
                        pulse.stop();

                    });
                    iconTrash.setOnMouseClicked((event) -> {

                        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                            DarkStyleCatalogSQL.deleteState(item);
                            CatalogManager.removeState(item);
                            showDialogRefresh();
                        }

                    });
                    Label name = new Label(item.getName(), iconTrash);

                    if (item.getDescription().isEmpty()) {
                        name.getStyleClass().addAll(HEADLINE, HEADLINE_STANDARD);
                    } else {
                        name.getStyleClass().addAll(HEADLINE, HEADLINE_EMPHASIZE);
                    }

                    name.setOnMouseClicked((event) -> {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            ShowDialogFormState(item);
                        }
                    });
                    setTooltip(item.getDescription().isEmpty() ? null : new Tooltip(FitToolTip(item.getDescription())));
                    setGraphic(name);
                    AnimationFX fadeIn = new FadeIn(this);
                    fadeIn.setSpeed(0.5);
                    fadeIn.play();
                }

            }
        }
        );

        return galleryStates;
    }

    public GridView<Author> createGalleryAuthors() {
        GridView<Author> galleryAuthors = new GridView<>();
        galleryAuthors.getStyleClass().add(MODE_TEXT);

        galleryAuthors.setCellFactory(gridView -> new GridCell<Author>() {

            @Override
            public void updateItem(Author item, boolean empty) {
                this.getStyleClass().add(MODE_TEXT);
                this.setOpacity(HIDDEN);
                if (item == null || empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setText(null);
                    FontAwesomeIconView iconTrash = new FontAwesomeIconView(FontAwesomeIcon.TRASH_ALT);
                    iconTrash.visibleProperty().bindBidirectional(visibleIconTrash);

                    iconTrash.getStyleClass().addAll(ICON_NORMAL, SHADOWED);

                    ZoomIn in = new ZoomIn(iconTrash);

                    iconTrash.visibleProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue) {
                            visibleIconSlide.set(false);
                            in.play();
                        }
                    });

                    Pulse pulse = new Pulse(iconTrash);

                    iconTrash.setOnMouseEntered((event) -> {
                        pulse.setCycleCount(Animation.INDEFINITE);
                        pulse.setSpeed(2);
                        pulse.play();
                    });

                    iconTrash.setOnMouseExited((event) -> {
                        pulse.stop();

                    });

                    iconTrash.setOnMouseClicked((event) -> {

                        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                            DarkStyleCatalogSQL.deleteAuthor(item);
                            CatalogManager.removeAuthor(item);
                            showDialogRefresh();
                        }

                    });
                    Label name = new Label(item.getName(), iconTrash);
                    name.getStyleClass().addAll(HEADLINE, HEADLINE_STANDARD);
                    name.setOnMouseClicked((event) -> {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            ShowDialogFormAuthor(item);
                        }
                    });
                    setGraphic(name);
                    AnimationFX fadeIn = new FadeIn(this);
                    fadeIn.setSpeed(0.5);
                    fadeIn.play();
                }

            }
        }
        );

        return galleryAuthors;
    }

    public GridView<Studio> createGalleryStudios() {
        GridView<Studio> galleryStudios = new GridView<>();
        galleryStudios.getStyleClass().add(MODE_TEXT);

        galleryStudios.setCellFactory(gridView -> new GridCell<Studio>() {

            @Override
            public void updateItem(Studio item, boolean empty) {
                this.getStyleClass().add(MODE_TEXT);
                this.setOpacity(HIDDEN);
                if (item == null || empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setText(null);
                    FontAwesomeIconView iconTrash = new FontAwesomeIconView(FontAwesomeIcon.TRASH_ALT);
                    iconTrash.visibleProperty().bindBidirectional(visibleIconTrash);

                    iconTrash.getStyleClass().addAll(ICON_NORMAL, SHADOWED);

                    ZoomIn in = new ZoomIn(iconTrash);

                    iconTrash.visibleProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue) {
                            visibleIconSlide.set(false);
                            in.play();
                        }
                    });

                    Pulse pulse = new Pulse(iconTrash);

                    iconTrash.setOnMouseEntered((event) -> {
                        pulse.setCycleCount(Animation.INDEFINITE);
                        pulse.setSpeed(2);
                        pulse.play();
                    });

                    iconTrash.setOnMouseExited((event) -> {
                        pulse.stop();

                    });

                    iconTrash.setOnMouseClicked((event) -> {

                        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                            DarkStyleCatalogSQL.deleteStudio(item);
                            CatalogManager.removeStudio(item);
                            showDialogRefresh();
                        }

                    });
                    Label name = new Label(item.getName(), iconTrash);
                    if (item.getDescription().isEmpty()) {
                        name.getStyleClass().addAll(HEADLINE, HEADLINE_STANDARD);
                    } else {
                        name.getStyleClass().addAll(HEADLINE, HEADLINE_EMPHASIZE);
                    }

                    name.setOnMouseClicked((event) -> {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            ShowDialogFormStudio(item);
                        }
                    });
                    setTooltip(item.getDescription().isEmpty() ? null : new Tooltip(FitToolTip(item.getDescription())));
                    setGraphic(name);
                    AnimationFX fadeIn = new FadeIn(this);
                    fadeIn.setSpeed(0.5);
                    fadeIn.play();
                }

            }
        }
        );

        return galleryStudios;
    }

    public GridView<Material> createGalleryMaterials(boolean visibleMaterialType) {
        GridView<Material> galleryMaterials = new GridView<>();
        galleryMaterials.getStyleClass().add(MODE_CARD);

        galleryMaterials.setCellFactory(gridView
                -> {
            GridCell<Material> gridCell = new GridCell<Material>() {
                @Override
                public void updateItem(Material item, boolean empty) {
                    setOpacity(HIDDEN);
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setOpacity(HIDDEN);
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(null);
                        setGraphic(createGridCellForMaterial(item, this, visibleMaterialType));
                        setOpacity(HIDDEN);
                    }

                }

            };

            gridCell.getStyleClass().add(MODE_CARD);

            gridCell.itemProperty().addListener((observable, oldValue, newValue) -> {
                gridCell.setMouseTransparent(UNTOUCHABLE);
                int index = gridCell.getIndex();
                if (index != -1 && newValue != null) {
                    double widthGrid = gridCell.getGridView().getWidth();
                    if (widthGrid != 0.0) {
                        double delayForCellOnShow = Display.AnimationDelayForCell(index, widthGrid);
                        double delayOnBounce = 20;

                        AnimationFX firstAnimation = new RollIn(gridCell);
                        AnimationFX secondAnimation = new BounceIn(gridCell);

                        firstAnimation.setDelay(Duration.millis(delayForCellOnShow));
                        secondAnimation.setDelay(Duration.millis(delayForCellOnShow + delayOnBounce));

                        ParallelTransition transition = new ParallelTransition(firstAnimation.getTimeline(),
                                secondAnimation.getTimeline());
                        transition.setOnFinished((e) -> {
                            gridCell.setMouseTransparent(TOUCHABLE);
                            e.consume();
                        });
                        transition.play();

                    }
                }
            });

            gridCell.setOnMouseClicked((event) -> {

                if (!showingInfoAboutMaterial) {
                    if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                        showingInfoAboutMaterial = true;
                        BounceOutUp animation = new BounceOutUp(gridCell);
                        animation.setSpeed(2);
                        animation.getTimeline().setOnFinished((e) -> {
                            ShowDialogInfoOfMaterial(gridCell.getItem(), gridCell);
                        });
                        animation.play();
                    }
                }

                if (lockApplication.getValue() == false && event.getButton() == MouseButton.SECONDARY) {
                    switch (gridCell.getItem().getMaterialType()) {
                        case "Anime":
                            ShowDialogFormAnime((Anime) (gridCell.getItem()));
                            break;
                        case "Donghua":
                            ShowDialogFormDonghua((Donghua) (gridCell.getItem()));
                            break;
                        case "Light Novel":
                            ShowDialogFormLightNovel((LightNovel) (gridCell.getItem()));
                            break;
                    }

                }

                event.consume();
            });

            gridCell.setOnMouseEntered((event) -> {
                GrowUp grown = new GrowUp(gridCell);
                grown.Up();
                event.consume();

            });

            gridCell.setOnMouseExited((event) -> {
                GrowDown grown = new GrowDown(gridCell);
                grown.Down();
                event.consume();
            });

            return gridCell;
        }
        );

        return galleryMaterials;
    }

    public GridView<Material> createGalleryMaterialsByType(ObservableList<Material> pMaterials) {
        GridView<Material> galleryMaterials = createGalleryMaterials(HIDE_MATERIAL_TYPE);
        galleryMaterials.setItems(pMaterials);
        return galleryMaterials;
    }

    private FontAwesomeIconView makeIconTrash(Material item, GridView<Material> view) {
        FontAwesomeIconView iconTrash = new FontAwesomeIconView(FontAwesomeIcon.TRASH_ALT);
        iconTrash.getStyleClass().addAll(ICON_CELL_MATERIAL, ICON_NORMAL, SHADOWED);

        iconTrash.visibleProperty().bindBidirectional(visibleIconTrash);

        ZoomIn in = new ZoomIn(iconTrash);

        iconTrash.visibleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                visibleIconSlide.set(false);
                in.play();
            }
        });

        Pulse pulse = new Pulse(iconTrash);

        iconTrash.setOnMouseEntered((event) -> {
            pulse.setCycleCount(Animation.INDEFINITE);
            pulse.setSpeed(2);
            pulse.play();
        });

        iconTrash.setOnMouseExited((event) -> {
            pulse.stop();

        });

        iconTrash.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                pulse.stop();
                DarkStyleCatalogSQL.deleteMaterial(item);
                switch (item.getMaterialType()) {
                    case "Anime": {
                        CatalogManager.removeAnime((Anime) item);
                        showDialogRefresh();
                    }
                    break;
                    case "Donghua": {
                        CatalogManager.removeDonghua((Donghua) item);
                        showDialogRefresh();
                    }
                    break;
                    case "Light Novel": {
                        CatalogManager.removeLightNovel((LightNovel) item);
                        showDialogRefresh();
                    }
                    break;
                }

            }

            event.consume();
        });

        return iconTrash;
    }

    private FontAwesomeIconView makeIconSlide(Material material, GridView<Material> view) {

        FontAwesomeIconView iconSlide = new FontAwesomeIconView(FontAwesomeIcon.CLONE);
        iconSlide.getStyleClass().addAll(ICON_CELL_MATERIAL, ICON_NORMAL, SHADOWED);

        Pulse pulse = new Pulse(iconSlide);

        iconSlide.setOnMouseEntered((event) -> {
            pulse.setCycleCount(Animation.INDEFINITE);
            pulse.setSpeed(2);
            pulse.play();
        });

        iconSlide.setOnMouseExited((event) -> {
            pulse.stop();
        });

        iconSlide.visibleProperty().bindBidirectional(visibleIconSlide);
        ZoomIn in = new ZoomIn(iconSlide);
        iconSlide.visibleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                visibleIconTrash.set(false);
                in.play();
            }
        });

        iconSlide.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                pulse.stop();
                switch (material.getMaterialType()) {
                    case "Anime":
                        slideShowMaterials.setSlides(createMaterialSlides(CatalogManager.ANIMES, material));
                        slideShowMaterials.showSlides();
                        break;
                    case "Donghua":
                        slideShowMaterials.setSlides(createMaterialSlides(CatalogManager.DONGHUAS, material));
                        slideShowMaterials.showSlides();

                        break;
                    case "Light Novel":
                        slideShowMaterials.setSlides(createMaterialSlides(CatalogManager.LIGHTNOVELS, material));
                        slideShowMaterials.showSlides();

                        break;

                }

            }

            event.consume();
        });

        return iconSlide;
    }

    public StackPane createGridCellForMaterial(Material item, GridCell<Material> cell, boolean visibleMaterialType) {

        int index = cell.getIndex();
        GridView<Material> view = cell.getGridView();

        Label title = new Label(item.getTitle());

        StarRating rating = new StarRating(5);
        rating.setMouseTransparent(UNTOUCHABLE);
        rating.setRating((int) item.getRating());

        Label Highlight = new Label();
        Highlight.setGraphic(createImageForMaterial(item, index, view.getWidth()));
        VBox contentOfCell = new VBox(Highlight, title, rating);

        contentOfCell.setAlignment(Pos.TOP_CENTER);

        LifeLabel materialType = new LifeLabel();
        materialType.getStyleClass().addAll(ETIQUETTE_LATERAL, ETIQUETTE_MATERIAL_TYPE);

        StackPane panel = new StackPane();
        JFXRippler rippler = new JFXRippler(panel);

        panel.getChildren().addAll(contentOfCell, makeIconTrash(item, view));

        if (!item.getPhotoAddress().isEmpty()) {
            panel.getChildren().add(makeIconSlide(item, view));
        }

        LifeLabel state = new LifeLabel();
        LifeLabel subState = new LifeLabel();
        LifeLabel year = new LifeLabel();

        int firstState = 0;
        int SecondState = 1;

        if (!item.getStates().isEmpty()) {
            state.getStyleClass().addAll(ETIQUETTE_DIAGONAL, ETIQUETTE_STATE);
            state.setText(item.getStates().get(firstState).getName());
            panel.getChildren().addAll(state);
            if (item.getStates().size() > 1) {
                subState.getStyleClass().addAll(ETIQUETTE_DIAGONAL, ETIQUETTE_SUBSTATE);
                subState.setText(item.getStates().get(SecondState).getName());
                panel.getChildren().add(subState);
            }
        }

        if (item.getYear() != 0) {
            year.setText(Integer.toString(item.getYear()));
            year.getStyleClass().addAll(ETIQUETTE_DIAGONAL, ETIQUETTE_YEAR);
            panel.getChildren().add(year);
        }

        if (visibleMaterialType) {
            materialType.setText(item.getMaterialType());
            materialType.setGraphic(createIconForCategoryAndOption(item.getMaterialType()));
            materialType.getGraphic().getStyleClass().remove(SHADOWED);
            panel.getChildren().add(materialType);
        }

        double widthGridView = view.getWidth();
        if (index != -1 && widthGridView != 0.0) {
            state.becomeLife(Display.GridRowReady(widthGridView), Display.AnimationDelayForCell(index, widthGridView), 0);
            subState.becomeLife(Display.GridRowReady(widthGridView), Display.AnimationDelayForCell(index, widthGridView), 250);
            year.becomeLife(Display.GridRowReady(widthGridView), Display.AnimationDelayForCell(index, widthGridView), 500);
            materialType.becomeLife(Display.GridRowReady(widthGridView), Display.AnimationDelayForCell(index, widthGridView), 750);
            rating.showRibbonStars(Display.GridRowReady(widthGridView), Display.AnimationDelayForCell(index, widthGridView));
        }
        return rippler;

    }

    public void createRadialPane(double radius) {
        int index = 0;
        double xPolar, yPolar;
        double slope = 0;

        if (quantityOfActions > 0) {
            slope = SPACE_RADIAL / quantityOfActions;
        }

        for (String action : actions) {

            JFXButton buttonAction = new JFXButton(action);

            buttonAction.setVisible(false);
            buttonAction.getStyleClass().add(BUTTON_RADIAL);
            buttonAction.setOpacity(HIDDEN);
            buttonAction.setGraphic(createIconForCategoryAndOption(action));
            buttonAction.setOnMouseClicked((event) -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    DoAction(action);
                }
            });

            xPolar = calculateCoordenatePolarForX(radius, index * slope + INITIAL_SPACE_RADIAL);
            yPolar = calculateCoordenatePolarForY(radius, index * slope + INITIAL_SPACE_RADIAL);
            buttonAction.setTranslateX(xPolar);
            buttonAction.setTranslateY(yPolar + MARGIN_BOTTOM_TOOLBAR);
            radialButtons.add(buttonAction);
            mainPane.getChildren().add(buttonAction);
            index++;

        }
    }

    public FontAwesomeIconView createIconForCategoryAndOption(String typeIcon) {
        FontAwesomeIconView iconButton;
        switch (typeIcon) {
            case "Synopsis":
                iconButton = new FontAwesomeIconView(FontAwesomeIcon.ALIGN_JUSTIFY);
                break;
            case "Photo":
                iconButton = new FontAwesomeIconView(FontAwesomeIcon.PICTURE_ALT);
                break;
            case "Kinds":
            case "Add Kind":
            case "By Kind":
                iconButton = new FontAwesomeIconView(FontAwesomeIcon.TAGS);
                break;
            case "States":
            case "Add State":
            case "By State":
                iconButton = new FontAwesomeIconView(FontAwesomeIcon.WECHAT);
                break;

            case "Authors":
            case "Add Author":
            case "By Author":
                iconButton = new FontAwesomeIconView(FontAwesomeIcon.BOOK);
                break;
            case "Studios":
            case "Add Studio":
            case "By Studio":
                iconButton = new FontAwesomeIconView(FontAwesomeIcon.BUILDING);
                break;

            case "Extras":
            case "By Rating":
                iconButton = new FontAwesomeIconView(FontAwesomeIcon.STAR);
                break;

            case "Animes":
            case "Anime":
            case "Add Anime":
            case "Donghuas":
            case "Donghua":
            case "Add Donghua":
                iconButton = new FontAwesomeIconView(FontAwesomeIcon.FILM);
                break;

            case "Light Novels":
            case "Light Novel":
            case "Add Light Novel":
                iconButton = new FontAwesomeIconView(FontAwesomeIcon.LEANPUB);
                break;
            case "By Empty Field":
                iconButton = new FontAwesomeIconView(FontAwesomeIcon.FILE_ALT);
                break;

            default:
                iconButton = new FontAwesomeIconView(FontAwesomeIcon.SQUARE_ALT);
                break;
        }
        iconButton.getStyleClass().addAll(ICON_NORMAL, SHADOWED);
        return iconButton;
    }

    public void createExtras() {
        extrasForFormOfMaterial = new VBox();
        starRatingForFormOfMaterial = new StarRating(MAXIMUN_STAR, INITIAL_VALUE);
        counterForFormOfMaterial = new Counter(MINIMUN_CHAPTER, INITIAL_VALUE, MAXIMUN_CHAPTER);

        extrasForFormOfMaterial.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                starRatingForFormOfMaterial.setRating(0);
                yearsForFormOfMaterial.setValue(null);
                countriesForFormOfMaterial.setValue(null);
                event.consume();
            }
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                extrasForFormOfMaterial.requestFocus();
            }

        });

        starRatingForFormOfMaterial.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                if (starRatingForFormOfMaterial.isGoldenState()) {
                    starRatingForFormOfMaterial.setCrimsonState(true);
                } else {
                    starRatingForFormOfMaterial.setGoldenState(true);
                }
            }
        });

        yearsForFormOfMaterial = new ComboBox<>(generateNumberOfYears());
        yearsForFormOfMaterial.setPromptText("YEARS");
        yearsForFormOfMaterial.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer year) {
                return year == null ? null : String.valueOf(year);
            }

            @Override
            public Integer fromString(String year) {
                return year == null ? null : Integer.parseInt(year);
            }
        });

        countriesForFormOfMaterial = new ComboBox<>(generatenameOfCountries());
        countriesForFormOfMaterial.setPromptText("COUNTRIES");

        yearsForFormOfMaterial.setPrefWidth(200);
        countriesForFormOfMaterial.setPrefWidth(200);
        counterForFormOfMaterial.setPrefWidth(200);

        HBox row = new HBox();
        row.setPadding(new Insets(50, 0, 50, 0));
        row.setSpacing(150);
        row.getChildren().add(yearsForFormOfMaterial);
        row.getChildren().add(counterForFormOfMaterial);
        row.getChildren().add(countriesForFormOfMaterial);

        extrasForFormOfMaterial.setAlignment(Pos.CENTER);
        extrasForFormOfMaterial.setPadding(new Insets(10, 0, 10, 0));
        extrasForFormOfMaterial.getChildren().add(row);
        extrasForFormOfMaterial.getChildren().add(starRatingForFormOfMaterial);
    }

    public HBox createOptions() {

        JFXButton editSynopsis = new JFXButton("Synopsis");
        JFXButton editPhoto = new JFXButton("Photo");
        JFXButton editKinds = new JFXButton("Kinds");
        JFXButton editAuthors = new JFXButton("Authors");
        JFXButton editStudios = new JFXButton("Studios");
        JFXButton editStates = new JFXButton("States");
        JFXButton editExtras = new JFXButton("Extras");

        editSynopsis.setGraphic(createIconForCategoryAndOption("Synopsis"));
        editPhoto.setGraphic(createIconForCategoryAndOption("Photo"));
        editKinds.setGraphic(createIconForCategoryAndOption("Kinds"));
        editAuthors.setGraphic(createIconForCategoryAndOption("Authors"));
        editStudios.setGraphic(createIconForCategoryAndOption("Studios"));
        editStates.setGraphic(createIconForCategoryAndOption("States"));
        editExtras.setGraphic(createIconForCategoryAndOption("Extras"));

        editSynopsis.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                clearDymanicConent();
                dymanicConent.getChildren().add(synopsisForFormOfMaterial);
            }
        });

        editPhoto.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                clearDymanicConent();
                dymanicConent.getChildren().add(highlight_photo);
                AnimationFX animatioIn = new FadeIn(photoForFormOfMaterial);
                animatioIn.play();
            }
            event.consume();
        });

        editKinds.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                clearDymanicConent();
                dymanicConent.getChildren().add(boxForKind);
            }
        });
        editAuthors.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                clearDymanicConent();
                dymanicConent.getChildren().add(boxForAuthor);
            }
        });
        editStudios.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                clearDymanicConent();
                dymanicConent.getChildren().add(boxForStudio);
            }
        });
        editStates.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                clearDymanicConent();
                dymanicConent.getChildren().add(boxForState);

            }
        });
        editExtras.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                clearDymanicConent();
                dymanicConent.getChildren().add(extrasForFormOfMaterial);
            }
        });

        HBox optionsForFormMaterial = new HBox();
        optionsForFormMaterial.setAlignment(Pos.CENTER);
        optionsForFormMaterial.setPadding(new Insets(5));
        optionsForFormMaterial.setSpacing(10);

        List<JFXButton> buttonOptions = Arrays.asList(editSynopsis, editPhoto, editKinds, editAuthors,
                editStudios, editStates, editExtras);

        buttonOptions.forEach((button) -> {
            button.getStyleClass().addAll(BUTTON_SMALL, BUTTON_OPTION);
            optionsForFormMaterial.getChildren().add(button);
        });

        return optionsForFormMaterial;
    }

    public void createTextAreaForMaterial() {
        synopsisForFormOfMaterial = new TextArea();
        synopsisForFormOfMaterial.setPromptText("La Sinopsis No Ha Sido Agregada A Este Titulo");
    }

    public ImageView createImageForMaterial(Material item, int index, double widthGridView) {
        Image localImage;
        ImageView photoMaterial = new ImageView();

        photoMaterial.setSmooth(SMOOTH);
        photoMaterial.setFitWidth(WIDTH_PHOTO);
        photoMaterial.setFitHeight(HEIGHT_PHOTO);

        pathOfPhotoFile = getAddressPhoto(DIR_PHOTOS + item.getPhotoAddress());
        localImage = new Image(pathOfPhotoFile, WIDTH_PHOTO, HEIGHT_PHOTO, PRESERVE_RATION, SMOOTH, BACKGROUND_LOADING);

        Rectangle clip = new Rectangle(
                photoMaterial.getFitWidth(), photoMaterial.getFitHeight()
        );
        clip.setArcWidth(50);
        clip.setArcHeight(50);
        photoMaterial.setClip(clip);

        photoMaterial.setImage(localImage);
        toProcessImagen(localImage, photoMaterial);

        if (index != -1 && widthGridView != 0.0) {
            photoMaterial.setOpacity(HIDDEN);
            AnimationFX animation = new FadeIn(photoMaterial);
            animation.setSpeed(2);
            animation.setDelay(Duration.millis(GridRowReady(widthGridView) + Display.AnimationDelayForCell(index, widthGridView)));
            animation.play();
        }

        return photoMaterial;
    }

    public void createImageViewForMaterial() {
        highlight_photo = new Label();
        photoForFormOfMaterial = new ImageView(NOT_PHOTO);
        photoForFormOfMaterial.setSmooth(true);
        photoForFormOfMaterial.setFitWidth(790);
        photoForFormOfMaterial.setFitHeight(400);

        highlight_photo.setGraphic(photoForFormOfMaterial);
        Rectangle clip = new Rectangle(photoForFormOfMaterial.getFitWidth(),
                photoForFormOfMaterial.getFitHeight()
        );
        clip.setArcWidth(25);
        clip.setArcHeight(25);

        photoForFormOfMaterial.setClip(clip);

        highlight_photo.setOnMouseClicked((e) -> {
            if (e.getButton() == MouseButton.SECONDARY && e.getClickCount() == 1) {
                loadPhotoService.restart();
            }
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                photoForFormOfMaterial.setImage(NOT_PHOTO);
                resetPhoto = true;
                pathFile = nameFile = "";
            }
        });

        highlight_photo.setOnDragOver((event) -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                event.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();
        });

        highlight_photo.setOnDragDropped((event) -> {
            boolean isCompleted = false;
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                isCompleted = transferImageFile(dragboard.getFiles());
                resetPhoto = false;
            }

        });
    }

    ////////////////////////////////////////////////////////////////////
    //                          Load Content                         //
    ///////////////////////////////////////////////////////////////////
    public void loadSearchMateriales(String textToSearch) {
        if (materialsSearched != null) {
            materialsSearched.getSource().clear();
        }
        materialsSearched = CatalogManager.searchedMaterial();
        materialsSearched.setPredicate((material) -> {
            return material.getTitle().toLowerCase().contains(textToSearch.toLowerCase()) /*|| material.getSynopsis().toLowerCase().contains(textToSearch.toLowerCase())*/;
        });
        ShowDialogSearchedMaterials();
    }

    public void loadGalleryMaterials() {
        loadTabs();
        loadSuggestions();

    }

    public void loadTabs() {

        Tab pTabAnime = new Tab("Animes");

        pTabAnime.setGraphic(createIconForCategoryAndOption("Animes"));

        pTabAnime.getGraphic().setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                slideShowMaterials.setSlides(createMaterialSlides(CatalogManager.ANIMES));
                slideShowMaterials.showSlides();
            }
        });

        galleryOfAnimeForTabOfAnime = createGalleryMaterialsByType(CatalogManager.ANIMES);
        Platform.runLater(() -> pTabAnime.setContent(galleryOfAnimeForTabOfAnime));
        Platform.runLater(() -> paneByTypes.getTabs().add(pTabAnime));

        Tab pTabDonghua = new Tab("Donghuas");
        pTabDonghua.setGraphic(createIconForCategoryAndOption("Donghuas"));
        pTabDonghua.getGraphic().setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                slideShowMaterials.setSlides(createMaterialSlides(CatalogManager.DONGHUAS));
                slideShowMaterials.showSlides();
            }
        });
        galleryOfDonghuaForTabOfDonghua = createGalleryMaterialsByType(CatalogManager.DONGHUAS);
        Platform.runLater(() -> pTabDonghua.setContent(galleryOfDonghuaForTabOfDonghua));
        Platform.runLater(() -> paneByTypes.getTabs().add(pTabDonghua));

        Tab pTabLightNovel = new Tab("Light Novels");
        pTabLightNovel.setGraphic(createIconForCategoryAndOption("Light Novels"));
        pTabLightNovel.getGraphic().setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                slideShowMaterials.setSlides(createMaterialSlides(CatalogManager.LIGHTNOVELS));
                slideShowMaterials.showSlides();
            }

        });
        galleryOfLightNovelForTabOfLightNovel = createGalleryMaterialsByType(CatalogManager.LIGHTNOVELS);
        Platform.runLater(() -> pTabLightNovel.setContent(galleryOfLightNovelForTabOfLightNovel));
        Platform.runLater(() -> paneByTypes.getTabs().add(pTabLightNovel));

    }

    public void loadSuggestions() {
        final int WIDTH_OF_SUGGESTION = 790;
        suggestionsOfAnimes = new JFXAutoCompletePopup<>();
        suggestionsOfAnimes.setPrefWidth(WIDTH_OF_SUGGESTION);

        Platform.runLater(() -> suggestionsOfAnimes.getSuggestions().setAll(CatalogManager.ANIMES));

        suggestionsOfDonghuas = new JFXAutoCompletePopup<>();
        suggestionsOfDonghuas.setPrefWidth(WIDTH_OF_SUGGESTION);

        Platform.runLater(() -> suggestionsOfDonghuas.getSuggestions().setAll(CatalogManager.DONGHUAS));

        suggestionsOfLightNovels = new JFXAutoCompletePopup<>();
        suggestionsOfLightNovels.setPrefWidth(WIDTH_OF_SUGGESTION);

        Platform.runLater(() -> suggestionsOfLightNovels.getSuggestions().setAll(CatalogManager.LIGHTNOVELS));

        suggestionsOfAuthors = new JFXAutoCompletePopup<>();
        suggestionsOfAuthors.setPrefWidth(WIDTH_OF_SUGGESTION);

        Platform.runLater(() -> suggestionsOfAuthors.getSuggestions().setAll(CatalogManager.AUTHORS));

        suggestionsOfKinds = new JFXAutoCompletePopup<>();
        suggestionsOfKinds.setPrefWidth(WIDTH_OF_SUGGESTION);

        Platform.runLater(() -> suggestionsOfKinds.getSuggestions().setAll(CatalogManager.KINDS));

        suggestionsOfStates = new JFXAutoCompletePopup<>();
        suggestionsOfStates.setPrefWidth(WIDTH_OF_SUGGESTION);

        Platform.runLater(() -> suggestionsOfStates.getSuggestions().setAll(CatalogManager.STATES));

        suggestionsOfStudios = new JFXAutoCompletePopup<>();
        suggestionsOfStudios.setPrefWidth(WIDTH_OF_SUGGESTION);

        Platform.runLater(() -> suggestionsOfStudios.getSuggestions().setAll(CatalogManager.STUDIOS));
    }

    public void loadPhotoForMaterial() {
        File dirRecent = new File(RecentFolder);
        if (dirRecent.exists()) {
            fileChooser.setInitialDirectory(dirRecent);
        }

        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Support Format",
                        Arrays.asList("*.gif ", "*.bmp", "*.jpg", "*.jpeg", "*.png"))
        );

        File file = fileChooser.showOpenDialog(currentStage);
        if (file != null) {
            RecentFolder = file.getParent();
            pathFile = file.getAbsolutePath();
            nameFile = file.getName();
            Image pImage = new Image(getAddressPhoto(pathFile));
            photoForFormOfMaterial.setImage(pImage);
            toProcessImagen(pImage, photoForFormOfMaterial);
            resetPhoto = false;
        }
    }

    ////////////////////////////////////////////////////////////////////
    //                          Utils                                //
    ///////////////////////////////////////////////////////////////////
    void resetOptionAndExtras() {
        nameFile = "";
        pathFile = "";
        resetPhoto = false;
        photoForFormOfMaterial.setImage(NOT_PHOTO);
        synopsisForFormOfMaterial.setText("");

        kindToFilter.setText("");
        stateToFilter.setText("");
        authorToFilter.setText("");
        studioToFilter.setText("");

        filteredByKinds = new FilteredList<>(DarkStyleCatalogSQL.getAllKind());
        filteredByStates = new FilteredList<>(DarkStyleCatalogSQL.getAllState());
        filteredByAuthors = new FilteredList<>(DarkStyleCatalogSQL.getAllAuthor());
        filteredByStudios = new FilteredList<>(DarkStyleCatalogSQL.getAllStudio());

        galleryOfKindForForm.setItems(filteredByKinds);

        galleryOfStateForForm.setItems(filteredByStates);

        galleryOfAuthorForForm.setItems(filteredByAuthors);

        galleryOfStudioForForm.setItems(filteredByStudios);

        starRatingForFormOfMaterial.setRating(
                0);
        counterForFormOfMaterial.setValue(
                0);
        yearsForFormOfMaterial.setValue(
                null);
        countriesForFormOfMaterial.setValue(
                null);

    }

    public void showOrHideRadialPanel() {
        unlockedRadialPane = false;
        semaphoreAnimation = 0;
        delayAnimation = 0;
        radialButtons.forEach((radialButton) -> { //hide
            if (radialButton.isVisible()) {
                radialButton.setMouseTransparent(UNTOUCHABLE);
                AnimationFX zoomOut = new BounceOut(radialButton);
                zoomOut.setSpeed(2);
                zoomOut.setDelay(Duration.millis(delayAnimation));
                delayAnimation += 100;

                zoomOut.play();
                zoomOut.getTimeline().setOnFinished((event) -> {
                    radialButton.setVisible(false);
                    semaphoreAnimation++;
                    if (semaphoreAnimation == quantityOfActions) {
                        unlockedRadialPane = true;
                    }
                });
            } else { //show

                radialButton.setMouseTransparent(UNTOUCHABLE);
                AnimationFX zoomIn = new BounceIn(radialButton);
                zoomIn.setSpeed(2);
                zoomIn.setDelay(Duration.millis(delayAnimation));
                delayAnimation += 100;
                zoomIn.play();
                radialButton.setVisible(true);

                zoomIn.getTimeline().setOnFinished((event) -> {
                    radialButton.setMouseTransparent(TOUCHABLE);
                    semaphoreAnimation++;
                    if (semaphoreAnimation == quantityOfActions) {
                        unlockedRadialPane = true;
                    }
                });
            }

        });
        Collections.reverse(radialButtons);

    }

    public void showOrHideActions() {
        if (activedSideBarLeft) {//hide

            activedSideBarLeft = false;
            MainMenu.setPinnedSide(null);
            iconOfLeftMenu.setIcon(FontAwesomeIcon.NAVICON);
            animationSidebar.purge();

            scrollPaneSideBarLeft.setMouseTransparent(UNTOUCHABLE);

            unlockSidebar.schedule(new TimerTask() {
                @Override
                public void run() {
                    scrollPaneSideBarLeft.setMouseTransparent(TOUCHABLE);
                    hideOptions((VBox) scrollPaneSideBarLeft.getContent());

                }
            }, 500);

            scrollPaneSideBarLeft.setOnMouseEntered((event) -> {
                showOptions((VBox) scrollPaneSideBarLeft.getContent());
                buttonOfLeftMenu.setDisable(true);

            });

        } else {//show
            hideOptions((VBox) scrollPaneSideBarLeft.getContent());
            unlockSidebar.purge();
            activedSideBarRight = false;
            activedSideBarLeft = true;
            MainMenu.setPinnedSide(Side.LEFT);

            iconOfRightMenu.setIcon(FontAwesomeIcon.NAVICON);
            iconOfLeftMenu.setIcon(FontAwesomeIcon.ARROW_LEFT);

            animationSidebar.schedule(new TimerTask() {
                @Override
                public void run() {
                    showOptions((VBox) scrollPaneSideBarLeft.getContent());
                }
            }, 10);
            scrollPaneSideBarLeft.setOnMouseEntered((event) -> {

            });

        }
    }

    public void showOrHideFilters() {
        if (activedSideBarRight) {//hide
            activedSideBarRight = false;
            hideOptions((VBox) scrollPaneSideBarRight.getContent());
            MainMenu.setPinnedSide(null);
            iconOfRightMenu.setIcon(FontAwesomeIcon.NAVICON);
            animationSidebar.purge();

            scrollPaneSideBarRight.setMouseTransparent(UNTOUCHABLE);
            unlockSidebar.schedule(new TimerTask() {
                @Override
                public void run() {
                    scrollPaneSideBarRight.setMouseTransparent(TOUCHABLE);
                    hideOptions((VBox) scrollPaneSideBarRight.getContent());
                }
            }, 500);

            scrollPaneSideBarRight.setOnMouseEntered((event) -> {
                buttonOfRightMenu.setDisable(true);
                showOptions((VBox) scrollPaneSideBarRight.getContent());
            });

        } else {//show
            hideOptions((VBox) scrollPaneSideBarRight.getContent());

            unlockSidebar.purge();
            activedSideBarLeft = false;
            activedSideBarRight = true;
            MainMenu.setPinnedSide(Side.RIGHT);

            iconOfLeftMenu.setIcon(FontAwesomeIcon.NAVICON);
            iconOfRightMenu.setIcon(FontAwesomeIcon.ARROW_RIGHT);

            animationSidebar.schedule(new TimerTask() {
                @Override
                public void run() {
                    showOptions((VBox) scrollPaneSideBarRight.getContent());
                }
            }, 10);

            scrollPaneSideBarRight.setOnMouseEntered((event) -> {

            });
        }

    }

    public void DoAction(String nameAction) {
        switch (nameAction) {
            case "Add Anime":
                ShowDialogFormAnime(null);
                break;
            case "Add Donghua":
                ShowDialogFormDonghua(null);
                break;
            case "Add Light Novel":
                ShowDialogFormLightNovel(null);
                break;
            case "Add State":
                ShowDialogFormState(null);
                break;
            case "Add Kind":
                ShowDialogFormKind(null);
                break;
            case "Add Author":
                ShowDialogFormAuthor(null);
                break;
            case "Add Studio":
                ShowDialogFormStudio(null);
                break;
        }
    }

    public void clearDymanicConent() {
        dymanicConent.getChildren().clear();
    }

    public void doSearch() {

        if (materialToSearch.getText().isEmpty()) {
            dialogSearched.close();
        } else {
            loadSearchMateriales(materialToSearch.getText());
        }
    }

    ////////////////////////////////////////////////////////////////////
    //                         Assignations                           //
    ///////////////////////////////////////////////////////////////////
    public void assignKindsToMaterial(int IdMaterial, ObservableList<Kind> kindToAssign) {
        DarkStyleCatalogSQL.deleteAssignedKindsToMaterial(IdMaterial);
        kindToAssign.forEach((extra) -> {
            if (extra.isChecked()) {
                DarkStyleCatalogSQL.assignKindToMaterial(IdMaterial, extra.getIdKind());
            }
        });
    }

    public void assignStatesToMaterial(int IdMaterial, ObservableList<State> stateToAssign) {
        DarkStyleCatalogSQL.deleteAssignedStatesToMaterial(IdMaterial);

        stateToAssign = fitIndex(stateToAssign);

        stateToAssign.forEach((extra) -> {
            if (extra.isChecked()) {
                DarkStyleCatalogSQL.assignStateToMaterial(IdMaterial, extra.getIdState(), extra.getPosition());
            }
        });
    }

    public void assignAuthorsToMaterial(int IdMaterial, ObservableList<Author> authorToAssign) {
        DarkStyleCatalogSQL.deleteAssignedAuthorsToMaterial(IdMaterial);
        authorToAssign.forEach((extra) -> {
            if (extra.isChecked()) {
                DarkStyleCatalogSQL.assignAuthorToMaterial(IdMaterial, extra.getIdAuthor());
            }
        });
    }

    public void assignStudiosToMaterial(int IdMaterial, ObservableList<Studio> studioToAssign) {
        DarkStyleCatalogSQL.deleteAssignedStudiosToMaterial(IdMaterial);
        studioToAssign.forEach((extra) -> {
            if (extra.isChecked()) {
                DarkStyleCatalogSQL.assignStudioToMaterial(IdMaterial, extra.getIdStudio());
            }
        });
    }

    public void loadAssignedKindsToMaterial(int IdMaterial) {
        ObservableList<Kind> kindToAssign = DarkStyleCatalogSQL.getAssignedKindsToMaterial(IdMaterial);
        ObservableList<Kind> ListCheck = galleryOfKindForForm.getItems();

        ListCheck.forEach((currentStudio) -> {
            kindToAssign.forEach(extra -> {
                if (currentStudio.getIdKind() == extra.getIdKind()) {
                    currentStudio.setChecked(true);
                }
            });
        });
    }

    public void loadAssignedStatesToMaterial(int IdMaterial) {
        ObservableList<State> statesToAssign = DarkStyleCatalogSQL.getAssignedStatesToMaterial(IdMaterial);
        ObservableList<State> ListCheck = (ObservableList<State>) filteredByStates.getSource();

        positionStateGobal = statesToAssign.size();
        ListCheck.forEach((currentState) -> {
            statesToAssign.forEach(extras -> {
                if (currentState.getIdState() == extras.getIdState()) {
                    currentState.setChecked(true);
                    currentState.setPosition(extras.getPosition());
                }
            });
        });
        sortStateByPosition(ListCheck);

    }

    public void loadAssignedAuthorsToMaterial(int IdMaterial) {
        ObservableList<Author> authorToAssign = DarkStyleCatalogSQL.getAssignedAuthorsToMaterial(IdMaterial);
        ObservableList<Author> ListCheck = galleryOfAuthorForForm.getItems();

        ListCheck.forEach((currentAuthor) -> {
            authorToAssign.forEach(extra -> {
                if (currentAuthor.getIdAuthor() == extra.getIdAuthor()) {
                    currentAuthor.setChecked(true);
                }
            });
        });
    }

    public void loadAssignedStudiosToMaterial(int IdMaterial) {
        ObservableList<Studio> studioToAssign = DarkStyleCatalogSQL.getAssignedStudiosToMaterial(IdMaterial);
        ObservableList<Studio> ListCheck = galleryOfStudioForForm.getItems();

        ListCheck.forEach((currentStudio) -> {
            studioToAssign.forEach(extra -> {
                if (currentStudio.getIdStudio() == extra.getIdStudio()) {
                    currentStudio.setChecked(true);
                }
            });
        });
    }

    ////////////////////////////////////////////////////////////////////
    //                        restoreFilters                          //
    ///////////////////////////////////////////////////////////////////
    public void restoreFilterKind(ObservableList<Kind> newKinds) {
        newKinds.forEach((currentKind) -> {
            filterByKinds.forEach(extra -> {
                if (currentKind.getIdKind() == extra.getIdKind()) {
                    currentKind.setChecked(extra.isChecked());
                }
            });
        });
    }

    public void restoreFilterState(ObservableList<State> newStates) {
        newStates.forEach((currentState) -> {
            filterByStates.forEach(extra -> {
                if (currentState.getIdState() == extra.getIdState()) {
                    currentState.setChecked(extra.isChecked());
                }
            });
        });
    }

    public void restoreFilterAuthor(ObservableList<Author> newAuthors) {
        newAuthors.forEach((currentAuthor) -> {
            filterByAuthors.forEach(extra -> {
                if (currentAuthor.getIdAuthor() == extra.getIdAuthor()) {
                    currentAuthor.setChecked(extra.isChecked());
                }
            });
        });
    }

    public void restoreFilterStudio(ObservableList<Studio> newStudios) {
        newStudios.forEach((currentStudio) -> {
            filterByStudios.forEach(extra -> {
                if (currentStudio.getIdStudio() == extra.getIdStudio()) {
                    currentStudio.setChecked(extra.isChecked());
                }
            });
        });
    }

    public FilteredList<Material> doFiltered(ObservableList<Material> data) {
        FilteredList<Material> filteredListAnime = new FilteredList<>(data);

        filteredListAnime.setPredicate((material)
                -> {
            boolean kind = toggleOfFilterByKind.isSelected(),
                    state = toggleOfFilterByState.isSelected(),
                    author = toggleOfFilterByAuthor.isSelected(),
                    studio = toggleOfFilterByStudio.isSelected(),
                    rating = false,
                    empty = toggleOfFilterByEmptyField.isSelected();

            for (Kind extra : galleryOfFilterByKind.getItems()) {
                if (extra.isChecked()) {

                    if (!toggleOfFilterByKind.isSelected()) {
                        kind |= materialHaveKind(material, extra);
                    } else {
                        kind &= materialHaveKind(material, extra);
                    }
                }
            }

            for (State extra : galleryOfFilterByState.getItems()) {
                if (extra.isChecked()) {
                    if (!toggleOfFilterByState.isSelected()) {
                        state |= materialHaveState(material, extra);
                    } else {
                        state &= materialHaveState(material, extra);
                    }
                }
            }

            for (Author extra : galleryOfFilterByAuthor.getItems()) {
                if (extra.isChecked()) {
                    if (!toggleOfFilterByAuthor.isSelected()) {
                        author |= materialHaveAuthor(material, extra);
                    } else {
                        author &= materialHaveAuthor(material, extra);
                    }
                }
            }

            for (Studio extra : galleryOfFilterByStudio.getItems()) {
                if (extra.isChecked()) {
                    if (!toggleOfFilterByStudio.isSelected()) {

                        studio |= materialHaveStudio(material, extra);
                    } else {
                        studio &= materialHaveStudio(material, extra);
                    }
                }
            }

            for (Star extra : filterByRatings) {
                if (extra.isChecked()) {
                    rating |= material.getRating() == extra.getNumber();
                }
            }

            for (EmptyField extra : filterByEmptyFields) {
                if (extra.isChecked()) {
                    switch (extra.getName()) {

                        case "Synposis": {
                            if (!toggleOfFilterByEmptyField.isSelected()) {
                                empty |= material.getSynopsis().isEmpty();
                            } else {
                                empty &= material.getSynopsis().isEmpty();
                            }
                        }
                        break;
                        case "Photo": {
                            if (!toggleOfFilterByEmptyField.isSelected()) {
                                empty |= material.getPhotoAddress().isEmpty();
                            } else {
                                empty &= material.getPhotoAddress().isEmpty();
                            }
                        }
                        break;
                        case "Chapter": {
                            if (!toggleOfFilterByEmptyField.isSelected()) {
                                empty |= material.getChapter() == 0;
                            } else {
                                empty &= material.getChapter() == 0;
                            }
                        }
                        break;
                        case "Year": {
                            if (!toggleOfFilterByEmptyField.isSelected()) {
                                empty |= material.getYear() == 0;
                            } else {
                                empty &= material.getYear() == 0;
                            }
                        }
                        break;
                        case "Rating": {
                            if (!toggleOfFilterByEmptyField.isSelected()) {
                                empty |= material.getRating() == 0;
                            } else {
                                empty &= material.getRating() == 0;
                            }
                        }
                        break;
                        case "Kind": {
                            if (!toggleOfFilterByEmptyField.isSelected()) {
                                empty |= material.getKinds().isEmpty();
                            } else {
                                empty &= material.getKinds().isEmpty();
                            }
                        }
                        break;
                        case "State": {
                            if (!toggleOfFilterByEmptyField.isSelected()) {
                                empty |= material.getStates().isEmpty();
                            } else {
                                empty &= material.getStates().isEmpty();
                            }
                        }
                        break;
                        case "Author": {
                            if (!toggleOfFilterByEmptyField.isSelected()) {
                                empty |= material.getAuthors().isEmpty();
                            } else {
                                empty &= material.getAuthors().isEmpty();
                            }
                        }
                        break;
                        case "Studio": {
                            if (!toggleOfFilterByEmptyField.isSelected()) {
                                empty |= material.getStudios().isEmpty();
                            } else {
                                empty &= material.getStudios().isEmpty();
                            }
                        }
                        break;

                    }
                }
            }

            empty |= !filterByEmptyFields.stream().anyMatch((item) -> {
                return item.isChecked();
            });
            kind |= !galleryOfFilterByKind.getItems().stream().anyMatch((item) -> {
                return item.isChecked();
            });
            state |= !galleryOfFilterByState.getItems().stream().anyMatch((item) -> {
                return item.isChecked();
            });
            author |= !galleryOfFilterByAuthor.getItems().stream().anyMatch((item) -> {
                return item.isChecked();
            });
            studio |= !galleryOfFilterByStudio.getItems().stream().anyMatch((item) -> {
                return item.isChecked();
            });
            rating |= !filterByRatings.stream().anyMatch((item) -> {
                return item.isChecked();
            });

            return kind && state && author && studio && rating && empty;

        }
        );

        return filteredListAnime;
    }

    public void applyFilter() {

        Platform.runLater(() -> CatalogManager.animeOrdenByTitle());
        Platform.runLater(() -> CatalogManager.donghuaOrdenByTitle());
        Platform.runLater(() -> CatalogManager.lightNovelOrdenByTitle());

        Platform.runLater(() -> galleryOfAnimeForTabOfAnime.setItems(doFiltered(CatalogManager.ANIMES)));
        Platform.runLater(() -> galleryOfDonghuaForTabOfDonghua.setItems(doFiltered(CatalogManager.DONGHUAS)));
        Platform.runLater(() -> galleryOfLightNovelForTabOfLightNovel.setItems(doFiltered(CatalogManager.LIGHTNOVELS)));

        Platform.runLater(() -> updateStateOfFilter());

        Platform.runLater(() -> dialogRefresh.close());

    }

    ObservableList<Slide> createMaterialSlides(ObservableList<Material> materials) {
        ObservableList<Slide> slideModel = FXCollections.observableArrayList();
        slideShowMaterials.setCurrentSlide(0);
        materials.forEach((t) -> {
            if (!t.getPhotoAddress().isEmpty()) {
                slideModel.add(new Slide(t.getTitle(), "Storage/Photos/" + t.getPhotoAddress()));
            }
        });
        return slideModel;
    }

    ObservableList<Slide> createMaterialSlides(ObservableList<Material> materials, Material currentMaterial) {
        int positionSlide = 0;
        ObservableList<Slide> slideModel = FXCollections.observableArrayList();
        for (Material material : materials) {
            if (material.getPhotoAddress().isEmpty()) {
                continue;
            }
            if (material.getIdMaterial() == currentMaterial.getIdMaterial()) {
                slideShowMaterials.setCurrentSlide(positionSlide);
            }
            slideModel.add(new Slide(material.getTitle(), DIR_PHOTOS + material.getPhotoAddress()));
            positionSlide++;

        }
        return slideModel;
    }

    ObservableList<Slide> createLoosePhotoSlides() {
        ObservableList<Slide> sliderModel = FXCollections.observableArrayList();

        File filePhotos = new File(DIR_PHOTOS);
        String[] Images = filePhotos.list(new FilterExtensionImage());
        for (String image : Images) {
            if (!DarkStyleCatalogSQL.ImagenIsUsedByMaterial(image)) {
                sliderModel.add(new Slide(image, DIR_PHOTOS + image));
            }
        }
        return sliderModel;
    }

    private boolean isVisibleIconTrash() {
        return visibleIconTrash.get();
    }

    private boolean isVisibleIconSlide() {
        return visibleIconSlide.get();
    }

    private ObservableList<State> fitIndex(ObservableList<State> states) {
        positionStateGobal = 1;
        ObservableList<State> statesByIndex = FXCollections.observableArrayList();

        sortStateByPosition(states);
        states.forEach((currentState) -> {
            if (currentState.isChecked()) {
                currentState.setPosition(++positionStateGobal);
                statesByIndex.add(currentState);
            }
        });

        return statesByIndex;
    }

    public void sortStateByPosition(ObservableList<State> states) {
        FXCollections.sort(states, (fisrtState, secondState) -> {
            if (fisrtState.getPosition() < secondState.getPosition()) {
                return -1;
            } else if (fisrtState.getPosition() > secondState.getPosition()) {
                return 1;
            } else {
                return 0;
            }
        });
    }

    void hideOptions(VBox buttons) {
        buttons.getChildren().forEach((button) -> {
            button.setOpacity(HIDDEN);
        });
    }

    void showOptions(VBox buttons) {
        hideOptions(buttons);
        delayAnimation = 500;
        for (Node button : buttons.getChildren()) {
            button.setMouseTransparent(UNTOUCHABLE);
            AnimationFX zoomIn = new BounceIn(button);
            zoomIn.setSpeed(2);
            zoomIn.setDelay(Duration.millis(delayAnimation));
            delayAnimation += 100;
            zoomIn.play();
            button.setVisible(true);
            zoomIn.getTimeline().setOnFinished((event) -> {
                button.setMouseTransparent(TOUCHABLE);
            });
        }
    }

    private boolean transferImageFile(List<File> files) {
        for (File file : files) {
            if (!file.exists()) {
                return false;
            }
            String mimeType;
            try {
                mimeType = Files.probeContentType(file.toPath());
                if (mimeType != null && mimeType.startsWith("image/")) {
                    pathFile = file.getAbsolutePath();
                    nameFile = file.getName();
                    transferImageUrl(file.toURI().toURL().toExternalForm());
                    return true;
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return false;
    }

    private boolean transferImageUrl(String imageUrl) {
        try {
            photoForFormOfMaterial.setImage(new Image(imageUrl));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void cleanPhoto(Material currentMaterial) {
        if (!(pathFile.isEmpty() || nameFile.isEmpty())) {
            return;
        }
        if (resetPhoto) {
            currentMaterial.setPhotoAddress("");
            resetPhoto = false;
        }
    }

    public void copyPhoto() {
        if (pathFile.isEmpty() || nameFile.isEmpty() || resetPhoto) {
            return;
        }
        FileManager.copy(pathFile, DIR_PHOTOS + nameFile);
    }

    public ObservableList<Integer> generateNumberOfYears() {
        ObservableList<Integer> numberOfYears = FXCollections.observableArrayList();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int year = currentYear; year >= 1950; year--) {
            numberOfYears.add(year);
        }
        return numberOfYears;
    }

    public ObservableList<String> generatenameOfCountries() {
        ObservableList<String> nameOfCountries = FXCollections.observableArrayList("Chinese", "Japan", "Korean");
        return nameOfCountries;
    }

    public void doAssignsToMaterial(int idAnime) {
        assignKindsToMaterial(idAnime, (ObservableList<Kind>) filteredByKinds.getSource());
        assignStatesToMaterial(idAnime, (ObservableList<State>) filteredByStates.getSource());
        assignAuthorsToMaterial(idAnime, (ObservableList<Author>) filteredByAuthors.getSource());
        assignStudiosToMaterial(idAnime, (ObservableList<Studio>) filteredByStudios.getSource());

    }

    public boolean isLockApplication() {
        return lockApplication.get();
    }

    public void setLockApplication(boolean value) {
        lockApplication.set(value);
    }

    public BooleanProperty lockApplicationProperty() {
        return lockApplication;
    }

    public void ActivateOrDeactivateFilter(String name, boolean actived) {
        for (JFXButton button : buttonOfFilters) {

            if (button.getText().equalsIgnoreCase(name))/* Activando...*/ {
                if (actived) {
                    StackPane iconos = (StackPane) button.getGraphic();
                    iconos.getChildren().forEach((icon) -> {
                        if (icon.getStyleClass().contains(ICON_WITHOUT_FILTER)) {
                            icon.getStyleClass().remove(ICON_WITHOUT_FILTER);
                        }
                    });
                } else /* Desactivado ...*/ {
                    StackPane iconos = (StackPane) button.getGraphic();
                    iconos.getChildren().forEach((icon) -> {
                        if (!icon.getStyleClass().contains(ICON_WITHOUT_FILTER)) {
                            icon.getStyleClass().add(ICON_WITHOUT_FILTER);
                        }
                    });
                }
            }
        }
    }

    void updateStateOfFilter() {

        boolean empty = filterByEmptyFields.stream().anyMatch((item) -> {
            return item.isChecked();
        });

        ActivateOrDeactivateFilter("By Empty Field", empty);

        boolean kind = galleryOfFilterByKind.getItems().stream().anyMatch((item) -> {
            return item.isChecked();
        });

        ActivateOrDeactivateFilter("By Kind", kind);

        boolean state = galleryOfFilterByState.getItems().stream().anyMatch((item) -> {
            return item.isChecked();
        });
        ActivateOrDeactivateFilter("By State", state);

        boolean author = galleryOfFilterByAuthor.getItems().stream().anyMatch((item) -> {
            return item.isChecked();
        });

        ActivateOrDeactivateFilter("By Author", author);

        boolean studio = galleryOfFilterByStudio.getItems().stream().anyMatch((item) -> {
            return item.isChecked();
        });

        ActivateOrDeactivateFilter("By Studio", studio);

        boolean rating = filterByRatings.stream().anyMatch((item) -> {
            return item.isChecked();
        });

        ActivateOrDeactivateFilter("By Rating", rating);
    }
}
