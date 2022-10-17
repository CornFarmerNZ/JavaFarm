/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project2.service;

import Project2.entity.Animal;
import Project2.entity.AnimalFarm;
import Project2.model.Client;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tim
 */
public class DBManager {

    private static final String USER_NAME = "pdc"; //your DB username
    private static final String PASSWORD = "pdc"; //your DB password
    private static final String URL = "jdbc:derby://localhost:1527/ProjectDB";  //url of the DB host

    Connection conn;

    public DBManager() {
        try {
            establishConnection();
            setUp();
        } catch (Exception exc) {
            System.out.println("Error connecting with database...");
        }
    }

    public void setUp() {
        // Generates table
        updateDB("CREATE TABLE USERS (ID INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1), USERNAME  VARCHAR(50), PASSWORD VARCHAR(50), FARMNAME VARCHAR(50), DAY INTEGER, ENERGY INTEGER, GOLD INTEGER)");
        updateDB("CREATE TABLE ANIMALS (ID INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1), OWNERID INTEGER, AGE INTEGER, TYPE VARCHAR(50), HUNGER INTEGER, THIRST INTEGER)");
    }

    public Connection getConnection() {
        return this.conn;
    }

    //Establish connection
    public void establishConnection() {
        try {
            //Establish a connection to Database
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException ex) {
            System.out.println("Error establishing connection...");
        }

    }

    public int login(String username, String password) {
        try {
            //finds user,pass, based on input username.
            ResultSet results = queryDB("SELECT USERNAME, PASSWORD FROM USERS WHERE USERNAME = '" + username + "'");

            System.out.println(results.getRow());
            String resultUsername = "";
            String resultPassword = "";
            if (results.next()) {
                resultUsername = results.getString(1);
                resultPassword = results.getString(2);
            } else {
                //if there is no Query entry resulting from username:
                updateDB("INSERT INTO USERS ( USERNAME, PASSWORD ) VALUES ( '" + username + "', '" + password + "' )");
                return 3;
            }
            //if there is query, returns number to be managed elsewhere.
            //correct password
            if (password.equals(resultPassword) && username.equals(resultUsername)) {
                return 0;
                //empty password
            } else if (username.trim().equals("") || password.trim().equals("")) {
                return 1;
            } else {
                //incorrect password
                return 2;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 404;
    }

    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public ResultSet queryDB(String sql) {
        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultSet;
    }

    public AnimalFarm retrieveFarm(String user) throws Exception {
        ResultSet results = queryDB("SELECT ID, FARMNAME, DAY, ENERGY, GOLD FROM USERS WHERE USERNAME = '" + user + "'");
        int userID = 0;
        String farmName = "";
        int day = 0;
        int energy = 0;
        int gold = 0;
        try {
            if (results.next()) {
                try {
                    //retrieves farm info from USERS table
                    userID = results.getInt(1);
                    farmName = results.getString(2);
                    day = results.getInt(3);
                    energy = results.getInt(4);
                    gold = results.getInt(5);
                    AnimalFarm farm = new AnimalFarm();
                    farm.setName(farmName);
                    farm.setDay(day);
                    farm.setEnergy(energy);
                    farm.setGold(gold);
                    ResultSet resultsAnimals = queryDB("SELECT AGE, TYPE, HUNGER, THIRST FROM ANIMALS WHERE OWNERID = " + userID + "");
                    //if any animals exist under the user's ID, query ANIMAL table.
                    //adds each animal to farm.
                    while (resultsAnimals.next()) {
                        int age = resultsAnimals.getInt(1);
                        String type = resultsAnimals.getString(2);
                        int hunger = resultsAnimals.getInt(3);
                        int thirst = resultsAnimals.getInt(4);
                        farm.addAnimal(age, type, hunger, thirst);
                    }
                    return farm;
                } catch (SQLException ex) {
                    Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    throw new Exception("Failed to save data");
                } catch (Exception ex) {
                    Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new Exception("Farm could not be loaded...");
    }

    /**
     * used before saving, to remove duplicate animals.
     *
     * @param user user to remove all animals from DB
     */
    public void removeAnimals(String user) {
        ResultSet results = queryDB("SELECT ID FROM USERS WHERE USERNAME = '" + user + "'");
        int userID = 0;
        try {
            if (results.next()) {
                try {
                    userID = results.getInt(1);
                } catch (SQLException ex) {
                    Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    throw new Exception("Failed to save data");
                } catch (Exception ex) {
                    Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateDB("DELETE FROM ANIMALS WHERE OWNERID = " + userID);
    }

    public void saveAnimal(String user, Animal animal) {
        //matches user ID to each animal

        ResultSet results = queryDB("SELECT ID FROM USERS WHERE USERNAME = '" + user + "'");
        int userID = 1;
        try {
            if (results.next()) {
                try {
                    userID = results.getInt(1);
                } catch (SQLException ex) {
                    Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    throw new Exception("Failed to save data");
                } catch (Exception ex) {
                    Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        //inserts animal with matching ownerid.
        //System.out.println("INSERT INTO ANIMALS ( ID, AGE, TYPE, HUNGER, THIRST ) VALUES ( " + userID + ", " + animal.getAge() + ", " + "'" + animal.getType() + "', " + animal.getHunger() + ", " + animal.getThirst() + ")");
        updateDB("INSERT INTO ANIMALS ( OWNERID, AGE, TYPE, HUNGER, THIRST ) VALUES ( " + userID + ", " + animal.getAge() + ", " + "'" + animal.getType() + "', " + animal.getHunger() + ", " + animal.getThirst() + ")");
    }

    public void updateDB(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
