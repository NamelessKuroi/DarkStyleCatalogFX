/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

//App
import Model.Configuration;

import Model.Extras.*;

import Model.Material.*;

//Java
import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;

//JavaFX
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Nameless
 */
public class DarkStyleCatalogSQL {
    ////////////////////////////////////////////////////////////////////
    //                        Constants                              //
    ///////////////////////////////////////////////////////////////////

    private static final String DRIVER_NAME = "jdbc:sqlite:";
    private static final String DIR_DATABASE = "Storage/";
    private static final String DIR_PHOTOS = "Storage/Photos/";
    private static final String DIR_SPLASH = "Storage/Splash/";
    private static final String NAME_DATABASE = "DarkStyleCatalogFX.db";
    private static final String PATH_DATABASE = DIR_DATABASE + NAME_DATABASE;

    ////////////////////////////////////////////////////////////////////
    //                          Utils                                 //
    ///////////////////////////////////////////////////////////////////
    public static void checkWorkSpace() {

        String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();

        File dirDB = new File(currentDir + File.separatorChar + DIR_DATABASE);
        File dirPhoto = new File(currentDir + File.separatorChar + DIR_PHOTOS);
        File dirSplah = new File(currentDir + File.separatorChar + DIR_SPLASH);

        File fileDB = new File(currentDir + File.separatorChar + PATH_DATABASE);

        if (!dirPhoto.exists()) {
            dirPhoto.mkdir();
        }
        if (!dirSplah.exists()) {
            dirSplah.mkdir();
        }

        if (!fileDB.exists()) {
            dirDB.mkdir();
            makeDataBase();
        }

    }

    public static void makeDataBase() {

        Connection connection = null;
        Statement statement = null;
        System.out.println("");
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            statement = connection.createStatement();

            statement.addBatch(" CREATE TABLE Materials (  "
                    + " IdMaterial INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + " MaterialType TEXT NULL , "
                    + " Title TEXT NULL , "
                    + " Chapter INTEGER NULL , "
                    + " Synopsis INTEGER NULL , "
                    + " Year INTEGER NULL , "
                    + " PhotoAddress TEXT NULL , "
                    + " Rating REAL NULL , "
                    + " Country TEXT NULL );"
            );

            statement.addBatch("CREATE TABLE Configurations ( "
                    + " idConfiguration INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " Name TEXT NULL , "
                    + " Value TEXT NULL );");

            statement.addBatch(
                    " CREATE TABLE Kinds ( "
                    + " IdKind INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " Name TEXT NULL , "
                    + " Description TEXT NULL ); "
            );

            statement.addBatch("CREATE TABLE States ( "
                    + " IdState INTEGER PRIMARY KEY AUTOINCREMENT , "
                    + "  Name TEXT NULL , "
                    + " Description TEXT NULL );");

            statement.addBatch("CREATE TABLE Authors ( "
                    + " IdAuthor INTEGER PRIMARY KEY AUTOINCREMENT , "
                    + " Name TEXT NULL , "
                    + " Description TEXT NULL);");

            statement.addBatch("CREATE TABLE Studios ( "
                    + " IdStudio INTEGER PRIMARY KEY AUTOINCREMENT , "
                    + " Name TEXT NULL , "
                    + " Description TEXT NULL);");

            statement.addBatch(" CREATE TABLE MaterialsByAuthor ( "
                    + " IdMaterial INTEGER  , "
                    + " IdAuthor INTEGER  ,"
                    + " PRIMARY KEY( IdMaterial , IdAuthor )  );"
            );

            statement.addBatch("CREATE TABLE MaterialsByKind ( "
                    + " IdMaterial INTEGER  , "
                    + " IdKind INTEGER  , "
                    + " PRIMARY KEY( IdMaterial , IdKind ) ); ");

            statement.addBatch("CREATE TABLE MaterialsByState ( "
                    + " IdMaterial INTEGER  , "
                    + " IdState INTEGER , "
                    + " Position INTEGER NULL , "
                    + " PRIMARY KEY( IdMaterial , IdState ) );");

            statement.addBatch("CREATE TABLE MaterialsByStudio ( "
                    + " IdMaterial INTEGER , "
                    + " IdStudio INTEGER , "
                    + " PRIMARY KEY( IdMaterial , IdStudio ) );");

            statement.executeBatch();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

    }

    public static boolean ImagenIsUsedByMaterial(String photo) {

        String query = "SELECT * FROM Materials WHERE PhotoAddress = \"" + photo + "\";";
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            return resultSet.next();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();

                }
                if (statement != null) {
                    statement.close();

                }
                if (connection != null) {
                    connection.close();

                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        return false;
    }

////////////////////////////////////////////////////////////////////
//                          Utils                                 //
///////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////
//                         Configurtion                           //
///////////////////////////////////////////////////////////////////
    public static TreeMap< String, Configuration> getAllConfiguration() {

        TreeMap< String, Configuration> Configurations = new TreeMap<>();
        String query = "SELECT * FROM Configurations ;";

        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            statement = connection.createStatement();
            resultSet = statement
                    .executeQuery(query);
            while (resultSet.next()) {

                int idConfiguration = resultSet.getInt("IdConfiguration");
                String name = resultSet.getString("Name");
                String value = resultSet.getString("Value");

                Configuration configuration = new Configuration(idConfiguration, name, value);
                Configurations.put(name, configuration);
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();

                }
                if (statement != null) {
                    statement.close();

                }
                if (connection != null) {
                    connection.close();

                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return Configurations;
    }

    public static void addConfiguration(Configuration newConfiguration) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "INSERT INTO Configurations ( Name , Value )"
                + " VALUES ( ? , ?) ";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, newConfiguration.getName());
            preparedStatement.setString(2, newConfiguration.getValue());

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void editConfiguration(Configuration configurationToEdit) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = " UPDATE Configurations "
                + " SET "
                + " Name = ? "
                + " , Value = ? "
                + " WHERE IdConfiguration =  ? ; ";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, configurationToEdit.getName());
            preparedStatement.setString(2, configurationToEdit.getValue());
            preparedStatement.setInt(3, configurationToEdit.getIdConfiguration());

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Edit Configuration:" + e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

////////////////////////////////////////////////////////////////////
//                          Configurtion                          //
///////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////
//                          Material                             //
///////////////////////////////////////////////////////////////////
    public static int getLastIdMaterial() {

        String query = " SELECT max(IdMaterial) FROM Materials ";

        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            statement = connection.createStatement();
            resultSet = statement
                    .executeQuery(query);
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return -1;
    }

    public static ObservableList<Material> getAllAnime() {

        ObservableList<Material> animes = FXCollections.observableArrayList();

        String query = " SELECT * FROM Materials WHERE MaterialType = 'Anime' ORDER BY title ; ";

        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;

        int idMaterial;
        String title;
        String synopsis;
        String photoAddress;
        int chapter;
        int year;
        double rating;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            statement = connection.createStatement();
            resultSet = statement
                    .executeQuery(query);

            while (resultSet.next()) {
                idMaterial = resultSet.getInt("IdMaterial");
                title = resultSet.getString("Title");
                synopsis = resultSet.getString("Synopsis");
                photoAddress = resultSet.getString("PhotoAddress");
                chapter = resultSet.getInt("Chapter");
                year = resultSet.getInt("Year");
                rating = resultSet.getFloat("Rating");

                Anime currentAnime = new Anime(idMaterial, title, synopsis, photoAddress, chapter, year, rating);
                currentAnime.setKinds(getAssignedKindsToMaterial(idMaterial));
                currentAnime.setStates(getAssignedStatesToMaterial(idMaterial));
                currentAnime.setAuthors(getAssignedAuthorsToMaterial(idMaterial));
                currentAnime.setStudios(getAssignedStudiosToMaterial(idMaterial));
                animes.add(currentAnime);
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        return animes;
    }

    public static ObservableList<Material> getAllDonghua() {
        ObservableList<Material> donghuas = FXCollections.observableArrayList();

        String query = "SELECT * FROM Materials WHERE MaterialType = 'Donghua' ORDER BY title;";

        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        int idMaterial;
        String title;
        String synopsis;
        String photoAddress;
        int chapter;
        int year;
        double rating;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            statement = connection.createStatement();
            resultSet = statement
                    .executeQuery(query);
            while (resultSet.next()) {

                idMaterial = resultSet.getInt("IdMaterial");
                title = resultSet.getString("Title");
                synopsis = resultSet.getString("Synopsis");
                photoAddress = resultSet.getString("PhotoAddress");
                chapter = resultSet.getInt("Chapter");
                year = resultSet.getInt("Year");
                rating = resultSet.getDouble("Rating");

                Material currentDonghua = new Donghua(idMaterial, title, synopsis, photoAddress, chapter, year, rating);
                donghuas.add(currentDonghua);
                currentDonghua.setKinds(getAssignedKindsToMaterial(idMaterial));
                currentDonghua.setStates(getAssignedStatesToMaterial(idMaterial));
                currentDonghua.setAuthors(getAssignedAuthorsToMaterial(idMaterial));
                currentDonghua.setStudios(getAssignedStudiosToMaterial(idMaterial));

            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return donghuas;
    }

    public static ObservableList<Material> getAllLightNovel() {
        ObservableList<Material> lightnovels = FXCollections.observableArrayList();

        String query = "SELECT * FROM Materials WHERE MaterialType = 'Light Novel' ORDER BY title;";

        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        int idMaterial;
        String title;
        String synopsis;
        String photoAddress;
        int chapter;
        int year;
        double rating;
        String country;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            statement = connection.createStatement();
            resultSet = statement
                    .executeQuery(query);
            while (resultSet.next()) {

                idMaterial = resultSet.getInt("IdMaterial");
                title = resultSet.getString("Title");
                synopsis = resultSet.getString("Synopsis");
                photoAddress = resultSet.getString("PhotoAddress");
                chapter = resultSet.getInt("Chapter");
                year = resultSet.getInt("Year");
                rating = resultSet.getDouble("Rating");
                country = resultSet.getString("Country");

                Material currentLightNovel = new LightNovel(idMaterial, title, synopsis, photoAddress, chapter, year, rating, country);
                lightnovels.add(currentLightNovel);
                currentLightNovel.setKinds(getAssignedKindsToMaterial(idMaterial));
                currentLightNovel.setStates(getAssignedStatesToMaterial(idMaterial));
                currentLightNovel.setAuthors(getAssignedAuthorsToMaterial(idMaterial));
                currentLightNovel.setStudios(getAssignedStudiosToMaterial(idMaterial));

            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return lightnovels;
    }

    public static ObservableList<Material> getMaterialsSearched(String textToSearch) {
        ObservableList<Material> materialsSearched = FXCollections.observableArrayList();

        String query = " SELECT * FROM Materials "
                + " WHERE Title LIKE '%" + textToSearch + "%' "
                /*+ " OR Synopsis LIKE '%" + textToSearch + "%' "*/
                + " ORDER BY title; ";

        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            statement = connection.createStatement();
            resultSet = statement
                    .executeQuery(query);
            while (resultSet.next()) {

                int idMaterial = resultSet.getInt("IdMaterial");
                String title = resultSet.getString("Title");
                String materialType = resultSet.getString("MaterialType");
                String synopsis = resultSet.getString("Synopsis");
                String photoAddress = resultSet.getString("PhotoAddress");
                int chapter = resultSet.getInt("Chapter");
                int year = resultSet.getInt("Year");
                double rating = resultSet.getDouble("Rating");
                String country = resultSet.getString("Country");

                Material material = new Material();

                switch (materialType) {
                    case "Anime":
                        material = new Anime(idMaterial, title, synopsis, photoAddress, chapter, year, rating);
                        break;
                    case "Donghua":
                        material = new Donghua(idMaterial, title, synopsis, photoAddress, chapter, year, rating);
                        break;
                    case "Light Novel":
                        material = new LightNovel(idMaterial, title, synopsis, photoAddress, chapter, year, rating, country);
                        break;
                }
                materialsSearched.add(material);

                material.setKinds(getAssignedKindsToMaterial(idMaterial));
                material.setStates(getAssignedStatesToMaterial(idMaterial));
                material.setAuthors(getAssignedAuthorsToMaterial(idMaterial));
                material.setStudios(getAssignedStudiosToMaterial(idMaterial));

            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return materialsSearched;
    }

    public static void addAnime(Anime newAnime) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = " INSERT INTO Materials ( MaterialType, Title, Chapter, Synopsis, Year, PhotoAddress, Rating )"
                + " VALUES ( ? , ? , ? , ? , ? , ? , ? ) ";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, newAnime.getMaterialType());
            preparedStatement.setString(2, newAnime.getTitle());
            preparedStatement.setInt(3, newAnime.getChapter());
            preparedStatement.setString(4, newAnime.getSynopsis());
            preparedStatement.setInt(5, newAnime.getYear());
            preparedStatement.setString(6, newAnime.getPhotoAddress());
            preparedStatement.setDouble(7, newAnime.getRating());

            preparedStatement.executeUpdate();
       
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Anime" + e);

            }
        }
    }

    public static void addDonghua(Donghua newDonghua) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "INSERT INTO Materials ( MaterialType, Title, Chapter, Synopsis, Year, PhotoAddress, Rating )"
                + " VALUES ( ? , ? , ? , ? , ? , ? , ? ); ";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, newDonghua.getMaterialType());
            preparedStatement.setString(2, newDonghua.getTitle());
            preparedStatement.setInt(3, newDonghua.getChapter());
            preparedStatement.setString(4, newDonghua.getSynopsis());
            preparedStatement.setInt(5, newDonghua.getYear());
            preparedStatement.setString(6, newDonghua.getPhotoAddress());
            preparedStatement.setDouble(7, newDonghua.getRating());

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void addLightNovel(LightNovel newLightNovel) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "INSERT INTO Materials ( MaterialType, Title, Chapter, Synopsis, Year, PhotoAddress , Rating , Country )"
                + " VALUES ( ? , ? , ? , ? , ? , ? , ? , ? ) ;";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, newLightNovel.getMaterialType());
            preparedStatement.setString(2, newLightNovel.getTitle());
            preparedStatement.setInt(3, newLightNovel.getChapter());
            preparedStatement.setString(4, newLightNovel.getSynopsis());
            preparedStatement.setInt(5, newLightNovel.getYear());
            preparedStatement.setString(6, newLightNovel.getPhotoAddress());
            preparedStatement.setDouble(7, newLightNovel.getRating());
            preparedStatement.setString(8, newLightNovel.getCountry());

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void editAnime(Anime animeToEdit) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "UPDATE Materials"
                + " SET "
                + " Title = ?"
                + " , Chapter  = ?"
                + " , Synopsis  = ?"
                + " , Year  = ?"
                + " , PhotoAddress  = ?"
                + " , Rating  = ? "
                + "  WHERE IdMaterial  = ? ;";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, animeToEdit.getTitle());
            preparedStatement.setInt(2, animeToEdit.getChapter());
            preparedStatement.setString(3, animeToEdit.getSynopsis());
            preparedStatement.setInt(4, animeToEdit.getYear());
            preparedStatement.setString(5, animeToEdit.getPhotoAddress());
            preparedStatement.setDouble(6, animeToEdit.getRating());
            preparedStatement.setInt(7, animeToEdit.getIdMaterial());

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void editDonghua(Donghua donghuaToEdit) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "UPDATE Materials"
                + " SET "
                + " Title = ?"
                + " , Chapter  = ?"
                + " , Synopsis  = ?"
                + " , Year  = ?"
                + " , PhotoAddress  = ?"
                + " , Rating  = ? "
                + "  WHERE IdMaterial  = ? ;";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, donghuaToEdit.getTitle());
            preparedStatement.setInt(2, donghuaToEdit.getChapter());
            preparedStatement.setString(3, donghuaToEdit.getSynopsis());
            preparedStatement.setInt(4, donghuaToEdit.getYear());
            preparedStatement.setString(5, donghuaToEdit.getPhotoAddress());
            preparedStatement.setDouble(6, donghuaToEdit.getRating());
            preparedStatement.setInt(7, donghuaToEdit.getIdMaterial());

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void editLightNovel(LightNovel lightNovelToEdit) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "UPDATE Materials"
                + " SET "
                + " Title = ?"
                + " , Chapter  = ?"
                + " , Synopsis  = ?"
                + " , Year  = ?"
                + " , PhotoAddress  = ?"
                + " , Rating  = ? "
                + " , Country  = ? "
                + "  WHERE IdMaterial  = ? ;";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, lightNovelToEdit.getTitle());
            preparedStatement.setInt(2, lightNovelToEdit.getChapter());
            preparedStatement.setString(3, lightNovelToEdit.getSynopsis());
            preparedStatement.setInt(4, lightNovelToEdit.getYear());
            preparedStatement.setString(5, lightNovelToEdit.getPhotoAddress());
            preparedStatement.setDouble(6, lightNovelToEdit.getRating());
            preparedStatement.setString(7, lightNovelToEdit.getCountry());
            preparedStatement.setInt(8, lightNovelToEdit.getIdMaterial());

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void deleteMaterial(Material materialToDelete) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "DELETE FROM Materials WHERE IdMaterial  = ? ;";
        String queryKind = "DELETE FROM MaterialsByKind WHERE IdMaterial  = ? ;";
        String queryState = "DELETE FROM MaterialsByState WHERE IdMaterial  = ? ;";
        String queryAuthor = "DELETE FROM MaterialsByAuthor WHERE IdMaterial  = ? ;";
        String queryStudio = "DELETE FROM MaterialsByStudio WHERE IdMaterial  = ? ;";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, materialToDelete.getIdMaterial());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(queryKind);
            preparedStatement.setInt(1, materialToDelete.getIdMaterial());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(queryState);
            preparedStatement.setInt(1, materialToDelete.getIdMaterial());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(queryAuthor);
            preparedStatement.setInt(1, materialToDelete.getIdMaterial());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(queryStudio);
            preparedStatement.setInt(1, materialToDelete.getIdMaterial());
            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

////////////////////////////////////////////////////////////////////
//                          Material                              //
///////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////
//                          Extras                              //
///////////////////////////////////////////////////////////////////
    public static ObservableList<Author> getAllAuthor() {
        ObservableList<Author> authors = FXCollections.observableArrayList();

        String query = "SELECT * FROM Authors ORDER BY Name ; ";

        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            statement = connection.createStatement();
            resultSet = statement
                    .executeQuery(query);
            while (resultSet.next()) {

                int idAuthor = resultSet.getInt("IdAuthor");
                String name = resultSet.getString("Name");

                Author currentAuthor = new Author(idAuthor, name);
                authors.add(currentAuthor);

            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return authors;
    }

    public static ObservableList<Kind> getAllKind() {
        ObservableList<Kind> kinds = FXCollections.observableArrayList();

        String query = "SELECT * FROM Kinds ORDER BY Name ;";

        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            statement = connection.createStatement();
            resultSet = statement
                    .executeQuery(query);
            while (resultSet.next()) {

                int idKind = resultSet.getInt("IdKind");
                String name = resultSet.getString("Name");
                String description = resultSet.getString("Description");

                Kind currentKind = new Kind(idKind, name, description);
                kinds.add(currentKind);

            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return kinds;
    }

    public static ObservableList<State> getAllState() {
        ObservableList<State> states = FXCollections.observableArrayList();

        String query = "SELECT * FROM States ORDER BY Name ;";

        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            statement = connection.createStatement();
            resultSet = statement
                    .executeQuery(query);
            while (resultSet.next()) {

                int idState = resultSet.getInt("IdState");
                String name = resultSet.getString("Name");
                String description = resultSet.getString("Description");

                State currentState = new State(idState, name, description);
                states.add(currentState);

            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return states;
    }

    public static ObservableList<Studio> getAllStudio() {
        ObservableList<Studio> studios = FXCollections.observableArrayList();

        String query = "SELECT * FROM Studios ORDER BY Name ;";

        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            statement = connection.createStatement();
            resultSet = statement
                    .executeQuery(query);
            while (resultSet.next()) {

                int idStudio = resultSet.getInt("IdStudio");
                String name = resultSet.getString("Name");
                String description = resultSet.getString("Description");

                Studio currentStudio = new Studio(idStudio, name, description);
                studios.add(currentStudio);

            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return studios;
    }

    public static void addAuthor(Author newAuthor) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "INSERT INTO Authors ( Name)"
                + " VALUES ( ? ) ";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, newAuthor.getName());

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void addKind(Kind newKind) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "INSERT INTO Kinds ( Name , Description )"
                + " VALUES ( ? , ?) ";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, newKind.getName());
            preparedStatement.setString(2, newKind.getDescription());

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void addState(State newState) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "INSERT INTO States ( Name , Description)"
                + " VALUES ( ? , ? ) ";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, newState.getName());
            preparedStatement.setString(2, newState.getDescription());

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void addStudio(Studio newStudio) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "INSERT INTO Studios ( Name , Description)"
                + " VALUES ( ? , ?) ";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, newStudio.getName());
            preparedStatement.setString(2, newStudio.getDescription());

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void editAuthor(Author newAuthor) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = " UPDATE Authors "
                + " SET "
                + " Name = ? "
                + " WHERE IdAuthor = ? ;";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, newAuthor.getName());
            preparedStatement.setInt(2, newAuthor.getIdAuthor());

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void editKind(Kind newKind) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "UPDATE Kinds "
                + " SET "
                + " Name = ? "
                + " , Description = ? "
                + " WHERE IdKind =  ? ; ";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, newKind.getName());
            preparedStatement.setString(2, newKind.getDescription());
            preparedStatement.setInt(3, newKind.getIdKind());

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void editState(State newState) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "UPDATE States "
                + " SET "
                + " Name = ? "
                + " , Description = ? "
                + " WHERE IdState =  ? ; ";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, newState.getName());
            preparedStatement.setString(2, newState.getDescription());
            preparedStatement.setInt(3, newState.getIdState());

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void editStudio(Studio newStudio) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "UPDATE Studios "
                + " SET "
                + " Name = ? "
                + " , Description = ? "
                + " WHERE IdStudio =  ? ; ";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, newStudio.getName());
            preparedStatement.setString(2, newStudio.getDescription());
            preparedStatement.setInt(3, newStudio.getIdStudio());

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void deleteAuthor(Author newAuthor) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "DELETE FROM Authors WHERE IdAuthor  = ? ;";
        String queryAuthor = "DELETE FROM MaterialsByAuthor WHERE IdAuthor  = ? ;";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, newAuthor.getIdAuthor());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(queryAuthor);
            preparedStatement.setInt(1, newAuthor.getIdAuthor());
            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void deleteKind(Kind newKind) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "DELETE FROM Kinds WHERE IdKind  = ? ;";
        String queryKind = "DELETE FROM MaterialsByKind WHERE IdKind  = ? ;";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, newKind.getIdKind());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(queryKind);
            preparedStatement.setInt(1, newKind.getIdKind());
            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void deleteState(State newState) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "DELETE FROM States WHERE IdState  = ? ;";
        String queryState = "DELETE FROM MaterialsByState WHERE IdState  = ? ;";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, newState.getIdState());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(queryState);
            preparedStatement.setInt(1, newState.getIdState());
            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void deleteStudio(Studio newStudio) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "DELETE FROM Studios WHERE IdStudio  = ? ;";
        String queryStudio = "DELETE FROM MaterialsByStudio WHERE IdStudio  = ? ;";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, newStudio.getIdStudio());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(queryStudio);
            preparedStatement.setInt(1, newStudio.getIdStudio());
            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

////////////////////////////////////////////////////////////////////
//                          Extras                              //
///////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////
//                      Material - Extras                        //
///////////////////////////////////////////////////////////////////
    public static ObservableList<Kind> getAssignedKindsToMaterial(int IdMaterial) {
        ObservableList<Kind> kinds = FXCollections.observableArrayList();

        String query = "SELECT Kinds.* "
                + " FROM MaterialsByKind  , Kinds "
                + " WHERE MaterialsByKind.IdMaterial = " + IdMaterial
                + " AND MaterialsByKind.IdKind = Kinds.IdKind "
                + " ORDER BY Kinds.name ;";

        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            statement = connection.createStatement();
            resultSet = statement
                    .executeQuery(query);
            while (resultSet.next()) {

                int idKind = resultSet.getInt("IdKind");
                String name = resultSet.getString("Name");
                String description = resultSet.getString("Description");

                Kind currentKind = new Kind(idKind, name, description);
                kinds.add(currentKind);

            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return kinds;
    }

    public static ObservableList<State> getAssignedStatesToMaterial(int IdMaterial) {
        ObservableList<State> states = FXCollections.observableArrayList();

        String query = "SELECT States.* , MaterialsByState.Position "
                + " FROM MaterialsByState  , States "
                + " WHERE MaterialsByState.IdMaterial = " + IdMaterial
                + " AND MaterialsByState.IdState = States.IdState "
                + " ORDER BY MaterialsByState.Position , States.name ;";

        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            statement = connection.createStatement();
            resultSet = statement
                    .executeQuery(query);
            while (resultSet.next()) {

                int idState = resultSet.getInt("IdState");
                String name = resultSet.getString("Name");
                String description = resultSet.getString("Description");
                int position = resultSet.getInt("Position");

                State currentState = new State(idState, name, description);
                currentState.setPosition(position);
                states.add(currentState);

            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return states;
    }

    public static ObservableList<Author> getAssignedAuthorsToMaterial(int IdMaterial) {
        ObservableList<Author> authors = FXCollections.observableArrayList();

        String query = "SELECT Authors.* "
                + " FROM MaterialsByAuthor  , Authors "
                + " WHERE MaterialsByAuthor.IdMaterial = " + IdMaterial
                + " AND MaterialsByAuthor.IdAuthor = Authors.IdAuthor "
                + " ORDER BY Authors.name ;";

        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            statement = connection.createStatement();
            resultSet = statement
                    .executeQuery(query);
            while (resultSet.next()) {

                int idAuthor = resultSet.getInt("IdAuthor");
                String name = resultSet.getString("Name");

                Author currentAuthor = new Author(idAuthor, name);
                authors.add(currentAuthor);

            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return authors;
    }

    public static ObservableList<Studio> getAssignedStudiosToMaterial(int IdMaterial) {
        ObservableList<Studio> studios = FXCollections.observableArrayList();

        String query = "SELECT Studios.* "
                + " FROM MaterialsByStudio  , Studios "
                + " WHERE MaterialsByStudio.IdMaterial = " + IdMaterial
                + " AND MaterialsByStudio.IdStudio = Studios.IdStudio"
                + " ORDER BY Studios.name ;";

        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            statement = connection.createStatement();
            resultSet = statement
                    .executeQuery(query);
            while (resultSet.next()) {

                int idStudio = resultSet.getInt("IdStudio");
                String name = resultSet.getString("Name");
                String description = resultSet.getString("Description");

                Studio currentStudio = new Studio(idStudio, name, description);
                studios.add(currentStudio);

            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return studios;
    }

    public static void assignKindToMaterial(int IdMaterial, int IdKind) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "INSERT INTO MaterialsByKind ( IdMaterial, IdKind )"
                + " VALUES ( ? , ? ) ";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, IdMaterial);
            preparedStatement.setInt(2, IdKind);

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void assignStateToMaterial(int IdMaterial, int IdState, int Position) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "INSERT INTO MaterialsByState ( IdMaterial , IdState , Position )"
                + " VALUES ( ? , ? , ? ) ";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, IdMaterial);
            preparedStatement.setInt(2, IdState);
            preparedStatement.setInt(3, Position);

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void assignAuthorToMaterial(int IdMaterial, int IdAuthor) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "INSERT INTO MaterialsByAuthor ( IdMaterial, IdAuthor)"
                + " VALUES ( ? , ? ) ";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, IdMaterial);
            preparedStatement.setInt(2, IdAuthor);

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void assignStudioToMaterial(int IdMaterial, int IdStudio) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "INSERT INTO MaterialsByStudio ( IdMaterial, IdStudio)"
                + " VALUES ( ? , ? ) ";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, IdMaterial);
            preparedStatement.setInt(2, IdStudio);

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static ObservableList<Kind> deleteAssignedKindsToMaterial(int IdMaterial) {
        ObservableList<Kind> kinds = FXCollections.observableArrayList();

        String query = " DELETE FROM MaterialsByKind "
                + " WHERE MaterialsByKind.IdMaterial = ?";

        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, IdMaterial);

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return kinds;
    }

    public static ObservableList<State> deleteAssignedStatesToMaterial(int IdMaterial) {
        ObservableList<State> states = FXCollections.observableArrayList();

        String query = " DELETE FROM MaterialsByState "
                + " WHERE MaterialsByState.IdMaterial = ?";

        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, IdMaterial);

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return states;
    }

    public static ObservableList<Author> deleteAssignedAuthorsToMaterial(int IdMaterial) {
        ObservableList<Author> authors = FXCollections.observableArrayList();

        String query = " DELETE FROM MaterialsByAuthor "
                + " WHERE MaterialsByAuthor.IdMaterial = ?";

        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, IdMaterial);

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return authors;
    }

    public static ObservableList<Studio> deleteAssignedStudiosToMaterial(int IdMaterial) {
        ObservableList<Studio> studios = FXCollections.observableArrayList();

        String query = " DELETE FROM MaterialsByStudio "
                + " WHERE MaterialsByStudio.IdMaterial = ?";

        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DRIVER_NAME + PATH_DATABASE);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, IdMaterial);

            preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return studios;
    }

////////////////////////////////////////////////////////////////////
//                   Material - Extras                            //
///////////////////////////////////////////////////////////////////
}
