/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import Model.Configuration;

/**
 *
 * @author Nameless
 */
public class ConfigurationManager {

    public static void saveConfiguration(String name, String value) {
        Configuration currentConfiguration = getConfiguration(name);
        if (currentConfiguration == null) {
            DarkStyleCatalogSQL.addConfiguration(new Configuration(name, value));
        } else {
            currentConfiguration.setValue(value);
            DarkStyleCatalogSQL.editConfiguration(currentConfiguration);
        }
    }

    public static Configuration getConfiguration(String name) {
        return DarkStyleCatalogSQL.getAllConfiguration().get(name);
    }
}
