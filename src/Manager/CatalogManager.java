/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

//App
import Model.Extras.*;

import Model.Material.*;

//JavaFX
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 *
 * @author Nameless
 */
public class CatalogManager {

    ////////////////////////////////////////////////////////////////////
    //                      UTILS                                     //
    ///////////////////////////////////////////////////////////////////
    public static final byte UPDATE_AUTHOR = 0;
    public static final byte UPDATE_KIND = 1;
    public static final byte UPDATE_STATE = 3;
    public static final byte UPDATE_STUDIO = 4;

    public static void updateAnime(byte which) {
        ANIMES.forEach((material) -> {
            updateExtras(material, which);
        });
    }

    public static void updateDonghua(byte which) {
        DONGHUAS.forEach((material) -> {
            updateExtras(material, which);
        });
    }

    public static void updateLightNovel(byte which) {
        LIGHTNOVELS.forEach((material) -> {
            updateExtras(material, which);
        });
    }

    public static void updateExtras(Material material) {
        material.setAuthors(DarkStyleCatalogSQL.getAssignedAuthorsToMaterial(material.getIdMaterial()));
        material.setKinds(DarkStyleCatalogSQL.getAssignedKindsToMaterial(material.getIdMaterial()));
        material.setStates(DarkStyleCatalogSQL.getAssignedStatesToMaterial(material.getIdMaterial()));
        material.setStudios(DarkStyleCatalogSQL.getAssignedStudiosToMaterial(material.getIdMaterial()));
    }

    public static void updateExtras(Material material, byte which) {
        switch (which) {
            case UPDATE_AUTHOR:
                material.setAuthors(DarkStyleCatalogSQL.getAssignedAuthorsToMaterial(material.getIdMaterial()));
                break;
            case UPDATE_KIND:
                material.setKinds(DarkStyleCatalogSQL.getAssignedKindsToMaterial(material.getIdMaterial()));
                break;
            case UPDATE_STATE:
                material.setStates(DarkStyleCatalogSQL.getAssignedStatesToMaterial(material.getIdMaterial()));
                break;
            case UPDATE_STUDIO:
                material.setStudios(DarkStyleCatalogSQL.getAssignedStudiosToMaterial(material.getIdMaterial()));
                break;
        }

    }

    public static void updateMaterial(byte which) {
        updateAnime(which);
        updateDonghua(which);
        updateLightNovel(which);

    }

    ////////////////////////////////////////////////////////////////////
    //                       MATERIAL                                //
    ///////////////////////////////////////////////////////////////////
    public static final ObservableList<Material> ANIMES = DarkStyleCatalogSQL.getAllAnime();
    public static final ObservableList<Material> DONGHUAS = DarkStyleCatalogSQL.getAllDonghua();
    public static final ObservableList<Material> LIGHTNOVELS = DarkStyleCatalogSQL.getAllLightNovel();

    public static final FilteredList<Material> searchedMaterial() {
        ObservableList<Material> searched = FXCollections.observableArrayList();
      
        searched.addAll(ANIMES);
        searched.addAll(DONGHUAS);
        searched.addAll(LIGHTNOVELS);
        return new FilteredList<>(searched);
    }

    public static void animeOrdenByTitle() {
        FXCollections.sort(ANIMES, (firstItem, secondItem) -> {
            return firstItem.getTitle().compareToIgnoreCase(secondItem.getTitle());
        });
    }

    public static void donghuaOrdenByTitle() {
        FXCollections.sort(DONGHUAS, (firstItem, secondItem) -> {
            return firstItem.getTitle().compareToIgnoreCase(secondItem.getTitle());
        });
    }

    public static void lightNovelOrdenByTitle() {
        FXCollections.sort(LIGHTNOVELS, (firstItem, secondItem) -> {
            return firstItem.getTitle().compareToIgnoreCase(secondItem.getTitle());
        });
    }

    public static void addAnime(Anime newAnime) {
        DarkStyleCatalogSQL.addAnime(newAnime);
        ANIMES.add(newAnime);
        updateExtras(newAnime);
        animeOrdenByTitle();

    }

    public static void editAnime(Anime editedAnime) {
        DarkStyleCatalogSQL.editAnime(editedAnime);
        updateExtras(editedAnime);
        animeOrdenByTitle();

    }

    public static void removeAnime(Anime removedAnime) {
        ANIMES.remove(removedAnime);
        animeOrdenByTitle();
    }

    public static void addDonghua(Donghua newDonghua) {
        DONGHUAS.add(newDonghua);
        updateExtras(newDonghua);
        donghuaOrdenByTitle();

    }

    public static void editDonghua(Donghua editedDonghua) {
        DarkStyleCatalogSQL.editDonghua(editedDonghua);
        updateExtras(editedDonghua);
        donghuaOrdenByTitle();
    }

    public static void removeDonghua(Donghua removedDonghua) {
        DONGHUAS.add(removedDonghua);
        donghuaOrdenByTitle();
    }

    public static void addLightNovel(LightNovel newLightNovel) {
        LIGHTNOVELS.add(newLightNovel);
        updateExtras(newLightNovel);
        lightNovelOrdenByTitle();

    }

    public static void editLightNovel(LightNovel editedLightNovel) {
        DarkStyleCatalogSQL.editLightNovel(editedLightNovel);
        updateExtras(editedLightNovel);
        lightNovelOrdenByTitle();

    }

    public static void removeLightNovel(LightNovel removedLightNovel) {
        LIGHTNOVELS.add(removedLightNovel);
        lightNovelOrdenByTitle();
    }

    ////////////////////////////////////////////////////////////////////
    //                       EXTRAS                                   //
    ///////////////////////////////////////////////////////////////////
    public static final ObservableList<Author> AUTHORS = DarkStyleCatalogSQL.getAllAuthor();
    public static final ObservableList<Kind> KINDS = DarkStyleCatalogSQL.getAllKind();
    public static final ObservableList<State> STATES = DarkStyleCatalogSQL.getAllState();
    public static final ObservableList<Studio> STUDIOS = DarkStyleCatalogSQL.getAllStudio();

    public static void authorOrdenByName() {
        FXCollections.sort(AUTHORS, (firstItem, secondItem) -> {
            return firstItem.getName().compareToIgnoreCase(secondItem.getName());
        });
    }

    public static void kindOrdenByName() {
        FXCollections.sort(KINDS, (firstItem, secondItem) -> {
            return firstItem.getName().compareToIgnoreCase(secondItem.getName());
        });
    }

    public static void stateOrdenByName() {
        FXCollections.sort(STATES, (firstItem, secondItem) -> {
            return firstItem.getName().compareToIgnoreCase(secondItem.getName());
        });
    }

    public static void studioOrdenByName() {
        FXCollections.sort(STUDIOS, (firstItem, secondItem) -> {
            return firstItem.getName().compareToIgnoreCase(secondItem.getName());
        });
    }

    public static void addAuthor(Author newAuthor) {
        DarkStyleCatalogSQL.addAuthor(newAuthor);
        AUTHORS.add(newAuthor);
        authorOrdenByName();
    }

    public static void editAuthor(Author editedAuthor) {
        DarkStyleCatalogSQL.editAuthor(editedAuthor);
        updateMaterial(UPDATE_AUTHOR);
        authorOrdenByName();

    }

    public static void removeAuthor(Author removedAuthor) {
        AUTHORS.remove(removedAuthor);
        updateMaterial(UPDATE_AUTHOR);
        authorOrdenByName();

    }

    public static void addKind(Kind newKind) {
        DarkStyleCatalogSQL.addKind(newKind);
        KINDS.add(newKind);
        kindOrdenByName();
    }

    public static void editKind(Kind editedKind) {
        DarkStyleCatalogSQL.editKind(editedKind);
        updateMaterial(UPDATE_KIND);
        kindOrdenByName();

    }

    public static void removeKind(Kind removedKind) {
        KINDS.remove(removedKind);
        updateMaterial(UPDATE_KIND);
        kindOrdenByName();

    }

    public static void addState(State newState) {
        DarkStyleCatalogSQL.addState(newState);
        STATES.add(newState);
        stateOrdenByName();
    }

    public static void editState(State editedState) {
        DarkStyleCatalogSQL.editState(editedState);
        updateMaterial(UPDATE_STATE);
        stateOrdenByName();

    }

    public static void removeState(State removedState) {
        STATES.remove(removedState);
        updateMaterial(UPDATE_STATE);
        stateOrdenByName();

    }

    public static void addStudio(Studio newStudio) {
        DarkStyleCatalogSQL.addStudio(newStudio);
        STUDIOS.add(newStudio);
        studioOrdenByName();
    }

    public static void editStudio(Studio editedStudio) {
        DarkStyleCatalogSQL.editStudio(editedStudio);
        updateMaterial(UPDATE_STUDIO);
        studioOrdenByName();

    }

    public static void removeStudio(Studio removedStudio) {
        STUDIOS.remove(removedStudio);
        updateMaterial(UPDATE_STUDIO);
        studioOrdenByName();

    }

}
