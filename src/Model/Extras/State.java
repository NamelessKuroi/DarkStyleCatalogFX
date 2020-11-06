/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Extras;

import Utils.Constants;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author cedin
 */
public class State {

    private int idState;
    private String name;
    private String Description;
    private int Position;
    private  BooleanProperty checked;
    
     public State( String name, String Description) {
        this.name = name;
        this.Description = Description;
        this.Position = Constants.INFINITY;
        this.checked = new SimpleBooleanProperty(false);
    }
    public State(int idState, String name, String Description) {
        this.idState = idState;
        this.name = name;
        this.Description = Description;
        this.Position = Constants.INFINITY;
        this.checked = new SimpleBooleanProperty(false);
    }

    public int getIdState() {
        return idState;
    }

    public void setIdState(int idState) {
        this.idState = idState;
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

    public int getPosition() {
        return Position;
    }

    public void setPosition(int Order) {
        this.Position = Order;
    }
}
