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
public class Author {

    private int idAuthor;
    private String name;
    private BooleanProperty checked;

    public Author(String name) {
        this.name = name;
        this.checked = new SimpleBooleanProperty(false);
    }

    public Author(int idAuthor, String name) {
        this.idAuthor = idAuthor;
        this.name = name;
        this.checked = new SimpleBooleanProperty(false);
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
