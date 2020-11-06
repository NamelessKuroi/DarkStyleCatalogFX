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
public class Studio {

    private int idStudio;
    private String name;
    private String Description;
    private final BooleanProperty checked;

    public Studio(String name, String Description) {
        this.name = name;
        this.Description = Description;
        this.checked = new SimpleBooleanProperty(false);
    }

    public Studio(int idStudio, String name, String Description) {
        this.idStudio = idStudio;
        this.name = name;
        this.Description = Description;
        this.checked = new SimpleBooleanProperty(false);
    }

    public int getIdStudio() {
        return idStudio;
    }

    public void setIdStudio(int idStudio) {
        this.idStudio = idStudio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
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
