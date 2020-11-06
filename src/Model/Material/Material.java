/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Material;

//Model
import Model.Extras.*;

//JavaFX
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author cedin
 */
public class Material {

    private int idMaterial;
    private String materialType;
    private String title;
    private String synopsis;
    private String photoAddress;
    private int chapter;
    private int year;
    private double rating;

    private ObservableList<Kind> kinds;
    private ObservableList<Author> authors;
    private ObservableList<State> states;
    private ObservableList<Studio> studios;

    public Material() {
        this.idMaterial = 0;
        this.title = "";
        this.synopsis = "";
        this.photoAddress = "";
        this.chapter = 0;
        this.year = 0;
        this.rating = .0;
        this.kinds = FXCollections.observableArrayList();
        this.authors = FXCollections.observableArrayList();
        this.states = FXCollections.observableArrayList();
        this.studios = FXCollections.observableArrayList();

    }

    public Material(int idMaterial, String title, String synopsis, String photoAddress, int chapter, int year, double rating) {
        this.idMaterial = idMaterial;
        this.title = title;
        this.synopsis = synopsis;
        this.photoAddress = photoAddress;
        this.chapter = chapter;
        this.year = year;
        this.rating = rating;
        this.kinds = FXCollections.observableArrayList();
        this.authors = FXCollections.observableArrayList();
        this.states = FXCollections.observableArrayList();
        this.studios = FXCollections.observableArrayList();
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPhotoAddress() {
        return photoAddress;
    }

    public void setPhotoAddress(String photoAddress) {
        this.photoAddress = photoAddress;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ObservableList<Kind> getKinds() {
        return kinds;
    }

    public void setKinds(ObservableList<Kind> kinds) {
        this.kinds = kinds;
    }

    public ObservableList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ObservableList<Author> authors) {
        this.authors = authors;
    }

    public ObservableList<State> getStates() {
        return states;
    }

    public void setStates(ObservableList<State> states) {
        this.states = states;
    }

    public ObservableList<Studio> getStudios() {
        return studios;
    }

    public void setStudios(ObservableList<Studio> studios) {
        this.studios = studios;
    }

}
