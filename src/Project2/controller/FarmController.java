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
import Project2.entity.AnimalFarm;
import Project2.entity.FarmInterface;
import Project2.service.DBManager;
import java.sql.Connection;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.Statement;

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

    public boolean loadFarm() {
        farm = new AnimalFarm();

        return false;
    }
}
