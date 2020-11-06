/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

//App
import Manager.DarkStyleCatalogSQL;
import Model.Extras.*;
import Model.Material.Material;

/**
 *
 * @author Nameless
 */
public  class Checker {
   
    ////////////////////////////////////////////////////////////////////
    //                        validate                               //
    ///////////////////////////////////////////////////////////////////
    
    public static boolean validateTitleAnime(String title) {
        return !DarkStyleCatalogSQL.getAllAnime()
                .stream().anyMatch((item) -> (item.getTitle().equalsIgnoreCase(title)));
    }

    public static boolean validateTitleDonghua(String title) {
        return !DarkStyleCatalogSQL.getAllDonghua()
                .stream().anyMatch((item) -> (item.getTitle().equalsIgnoreCase(title)));
    }

    public static boolean validateTitleLightNovel(String title) {
        return !DarkStyleCatalogSQL.getAllLightNovel()
                .stream().anyMatch((item) -> (item.getTitle().equalsIgnoreCase(title)));
    }

    public static boolean validateNameKind(String name) {
        return !DarkStyleCatalogSQL.getAllKind()
                .stream().anyMatch((item) -> (item.getName().equalsIgnoreCase(name)));
    }

    public static boolean validateNameState(String name) {
        return !DarkStyleCatalogSQL.getAllState()
                .stream().anyMatch((item) -> (item.getName().equalsIgnoreCase(name)));
    }

    public static boolean validateNameAuthor(String name) {
        return !DarkStyleCatalogSQL.getAllAuthor()
                .stream().anyMatch((item) -> (item.getName().equalsIgnoreCase(name)));
    }

    public static boolean validateNameStudio(String name) {
        return !DarkStyleCatalogSQL.getAllStudio()
                .stream().anyMatch((item) -> (item.getName().equalsIgnoreCase(name)));
    }
    
    
    
    ////////////////////////////////////////////////////////////////////
    //                        Have                                    //
    ///////////////////////////////////////////////////////////////////
    public static boolean materialHaveKind(Material m, Kind k) {
        return m.getKinds().stream().anyMatch((extras) -> (extras.getIdKind() == k.getIdKind()));
    }

    public static boolean materialHaveState(Material m, State s) {
        return m.getStates().stream().anyMatch((extras) -> (extras.getIdState() == s.getIdState()));
    }

    public static boolean materialHaveAuthor(Material m, Author a) {
        return m.getAuthors().stream().anyMatch((extras) -> (extras.getIdAuthor() == a.getIdAuthor()));
    }

    public static boolean materialHaveStudio(Material m, Studio s) {
        return m.getStudios().stream().anyMatch((extras) -> (extras.getIdStudio() == s.getIdStudio()));
    }
 

}
