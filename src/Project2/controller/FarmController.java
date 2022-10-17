/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project2.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Project2.entity.Animal;
import Project2.entity.AnimalFarm;
import Project2.entity.FarmInterface;
import Project2.service.DBManager;
import java.sql.Connection;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tim
 */
public class FarmController {

    private final DBManager dbManager;
    private final Connection conn;
    private Statement statement;
    private FarmInterface farm;

    public FarmController() {
        dbManager = new DBManager();
        conn = dbManager.getConnection();
    }

    public FarmInterface getFarm() {
        return farm;
    }

    public boolean newFarm(String farmName) {
        //exception is thrown and caught by the client.
        if (farmName.isBlank()) {
            throw new IllegalArgumentException();
        }
        farm = new AnimalFarm();
        farm.setName(farmName.strip());
        return true;
    }

    public boolean loadFarm(String user) {
        try {
            farm = dbManager.retrieveFarm(user);
        } catch (Exception ex) {
            System.out.println("Error loading farm.");
            return false;
        }
        return true;
    }

    public Map<Integer, Animal> getAnimals() {
        return ((AnimalFarm) farm).getAnimals();
    }

    public void save(String user) {
        for (Entry e : ((AnimalFarm) farm).getAnimals().entrySet()) {
            Animal animal = (Animal) e.getValue();
            dbManager.saveAnimal(user, animal);

        }

    }
}
