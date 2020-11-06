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
public class Donghua extends Material{
   
    public Donghua() {
         super.setMaterialType("Donghua");
    }

    
    public Donghua(int idMaterial, String title, String synopsis, String photoAddress, int chapter, int year, double rating) {
        super(idMaterial, title, synopsis, photoAddress, chapter, year, rating);
         super.setMaterialType("Donghua");
    }

   
}
