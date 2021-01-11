/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

//App
import Model.Configuration;
import Utils.Constants;
import Utils.Display;
import Utils.FilterExtensionImage;
import SlideShow.Slide;

//Java
import java.io.File;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Arrays;

//JavaFX
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Nameless
 */
public class SplashScreenManager {

    private static String pathFile = "";
    private static String nameFile = "";

    private static String RecentFolder = "";
    private static final String CURRENT_DIR = Paths.get(".").toAbsolutePath().normalize().toString();
    private static final String DIR_SPLASH = "/Storage/Splash/";
    private static final String PATH_SPLASH = CURRENT_DIR + DIR_SPLASH;

    public final static int RANDOM = 0;
    public final static int SEQUENTIAL = 1;
    public final static int FIXED = 2;

    private static int QuantityOfPhotos = 0;

    public static int ModeSplash() {
        Configuration currentConfiguration = ConfigurationManager.getConfiguration("ModeSplash");

        String modeSplash = "Random";
        if (currentConfiguration == null) {
            ConfigurationManager.saveConfiguration("ModeSplash", modeSplash);
        } else {
            modeSplash = currentConfiguration.getValue();
        }

        switch (modeSplash) {
            case "Sequential":
                return SEQUENTIAL;
            case "Fixed":
                return FIXED;
            default:
                return RANDOM;
        }
    }

    public static void changeModeSplash(int modeSplash) {
        switch (modeSplash) {
            case RANDOM:
                ConfigurationManager.saveConfiguration("ModeSplash", "Random");
                break;
            case SEQUENTIAL:
                ConfigurationManager.saveConfiguration("ModeSplash", "Sequential");
                break;
            case FIXED:
                ConfigurationManager.saveConfiguration("ModeSplash", "Fixed");
                break;
        }
    }

    private static String[] getFileSplash() {
        File file = new File(PATH_SPLASH);
        return file.list(new FilterExtensionImage());
    }

    private static Image randomSplash() {
        Random positionSplash = new Random();

        String[] splashImages = getFileSplash();

        if (splashImages != null) {
            QuantityOfPhotos = splashImages.length;
        }

        if (QuantityOfPhotos == 0) {
            return Display.NOT_PHOTO;
        }
        return new Image(Display.getAddressPhoto(PATH_SPLASH + splashImages[positionSplash.nextInt(QuantityOfPhotos)]),
                Display.WIDTH_SPLASH, Display.HEIGHT_SPLASH, Constants.PRESERVE_RATION, Constants.SMOOTH, false);

    }

    private static Image currentSplash() {

        int currentSplash;

        Configuration currentConfiguration = ConfigurationManager.getConfiguration("CurrentSplash");
        if (currentConfiguration == null) {
            currentSplash = 0;
        } else {
            currentSplash = Integer.parseInt(currentConfiguration.getValue());
        }

        String[] splashImages = getFileSplash();

        if (splashImages != null) {
            QuantityOfPhotos = splashImages.length;
        }

        if (QuantityOfPhotos == 0) {
            return Display.NOT_PHOTO;
        }
        return new Image(Display.getAddressPhoto(PATH_SPLASH + splashImages[currentSplash % QuantityOfPhotos]),
                Display.WIDTH_SPLASH, Display.HEIGHT_SPLASH, Constants.PRESERVE_RATION, Constants.SMOOTH, false);
    }

    public static Image generateSplash() {
        String modeSplash = ConfigurationManager.getConfiguration("ModeSplash").getValue();
        switch (modeSplash) {
            case "Random":
                return randomSplash();
            case "Sequential":
            case "Fixed":
                return currentSplash();
            default:
                return Display.NOT_PHOTO;
        }
    }

    private static void loadPhoto(Stage currentStage) {
        nameFile = "";
        pathFile = "";
        FileChooser fileChooser = new FileChooser();
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
        }

    }

    public static Slide addSplash(Stage currentStage) {
        loadPhoto(currentStage);
        if (pathFile.isEmpty()) {
            return null;
        }
        FileManager.copy(pathFile, CURRENT_DIR + DIR_SPLASH + nameFile);

        return new Slide(nameFile, CURRENT_DIR + DIR_SPLASH + nameFile);
    }

    public static ObservableList<Slide> createModelSlideSplash() {
        ObservableList<Slide> splashs = FXCollections.observableArrayList();
        File file = new File(PATH_SPLASH);

        for (String splash : file.list(new FilterExtensionImage())) {
            splashs.add(new Slide(splash, PATH_SPLASH + splash));
        }
        return splashs;
    }

    private static int quantity() {
        String[] splashImages = getFileSplash();
        if (splashImages == null) {
            return 0;
        }
        return splashImages.length;
    }

    private static void resetCurrentSplash() {
        ConfigurationManager.saveConfiguration("CurrentSplash", String.valueOf(0));
    }

    public static void forward() {
        int modelSplash = ModeSplash();
        if (quantity() == 0
                || modelSplash == RANDOM
                || modelSplash == FIXED) {
            return;
        }

        Configuration currentConfiguration = ConfigurationManager.getConfiguration("CurrentSplash");
        if (currentConfiguration == null) {
            ConfigurationManager.saveConfiguration("CurrentSplash", String.valueOf(1));
        } else {
            int currentSplash = Integer.parseInt(currentConfiguration.getValue());
            ConfigurationManager.saveConfiguration("CurrentSplash", String.valueOf(currentSplash % quantity() + 1));
        }

    }

    public static int getCurrentSplash() {
        Configuration currentConfiguration = ConfigurationManager.getConfiguration("CurrentSplash");

        if (currentConfiguration == null) {
            return 0;
        } else {
            int currentSplash = Integer.parseInt(currentConfiguration.getValue());
            return currentSplash;
        }
    }

    public static void setCurrentSplash(int currentSplash) {
        ConfigurationManager.saveConfiguration("CurrentSplash", String.valueOf(currentSplash));
    }
}
