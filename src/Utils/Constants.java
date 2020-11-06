/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

//JFoenix
import com.jfoenix.controls.JFXDialog;

//JavaFX
import javafx.stage.Screen;

/**
 *
 * @author Nameless
 */
public class Constants {

    ////////////////////////////////////////////////////////////////////
    //                          Constants                             //
    ///////////////////////////////////////////////////////////////////
  

    public static final JFXDialog.DialogTransition TRANSITION = JFXDialog.DialogTransition.CENTER;

    public static final int INFINITY = 1000000;
    

    public static final int RADIUS_FROM_CENTER = 200;
    public static final int MARGIN_BOTTOM_TOOLBAR = 45;
    public static final int SPACE_RADIAL = 360;
    public static final int INITIAL_SPACE_RADIAL = 218;

    public static final boolean DIALOG_CLOSED_BY_CLICK = true;
    public static final boolean DIALOG_CLOSED_BY_ACTION = false;

    public static final double WIDTH_SCREEN = Screen.getPrimary().getBounds().getWidth();
    public static final double HEIGHT_SCREEN = Screen.getPrimary().getBounds().getHeight();

    public static final int WIDTH_PHOTO = 200;
    public static final int HEIGHT_PHOTO = 230;

    public static final int HEIGHT_DIALOG_CENTRAL = 650;

    public static final boolean PRESERVE_RATION = false;
    public static final boolean SMOOTH = true;
    public static boolean BACKGROUND_LOADING = true;

    public static final int SECOND = 1000;
    public static final int DELAY_SPLASH_SCREEN = 4 * SECOND;
    public static final int DELAY_TIMER = SECOND;

}
