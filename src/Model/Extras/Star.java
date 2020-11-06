/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Extras;

//JavaFX
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author Nameless
 */
public class Star {

    private int number;
    private final BooleanProperty checked;

    public Star(int number) {
        this.number = number;
        this.checked = new SimpleBooleanProperty(false);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public boolean isChecked() {
        return checked.get();
    }

    public void setChecked(boolean value) {
        checked.set(value);
    }

    public BooleanProperty checkedProperty() {
        return checked;
    }

}
