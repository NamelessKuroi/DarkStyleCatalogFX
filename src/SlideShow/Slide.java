/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SlideShow;

/**
 *
 * @author Nameless
 */
public class Slide {
    private String title;
    private String photoAddress;

    public Slide(String title, String photoAddress) {
        this.title = title;
        this.photoAddress = photoAddress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoAddress() {
        return photoAddress;
    }

    public void setPhotoAddress(String photoAddress) {
        this.photoAddress = photoAddress;
    }

    
    
    
}
