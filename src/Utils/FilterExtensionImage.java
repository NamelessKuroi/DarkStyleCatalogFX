/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 *
 * @author Nameless
 */
public class FilterExtensionImage implements FilenameFilter {

    @Override
    public boolean accept(File dir, String name) {
        ArrayList<String> extensions = new ArrayList<>();
        extensions.add(".bmp");
        extensions.add(".gif");
        extensions.add(".jpg");
        extensions.add(".jpeg");
        extensions.add(".png");

        return extensions.stream().anyMatch((extension) -> (name.toLowerCase().endsWith(extension)));
       
    }

}
