/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Material;

/**
 *
 * @author Nameless
 */
public class LightNovel extends Material {

    private String Country;

    public LightNovel() {
        super.setMaterialType("Light Novel");
    }

    public LightNovel(int idMaterial, String title, String synopsis, String photoAddress, int chapter, int year, double rating, String Country) {
        super(idMaterial, title, synopsis, photoAddress, chapter, year, rating);
        super.setMaterialType("Light Novel");
        this.Country = Country;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }
}
