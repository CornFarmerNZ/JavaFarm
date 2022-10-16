/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project2.service;

import Project2.entity.AbstractActivity;
import Project2.entity.AnimalFarm;
import Project2.entity.FeedAnimalActivity;
import Project2.entity.WaterAnimalActivity;

/**
 *
 * @author tim
 */
public class AnimalActivityFactory {

    AnimalFarm farm;

    public AnimalActivityFactory(AnimalFarm farm) {
        this.farm = farm;
    }

    /**
     * gets an Activity from the Activity Factory.
     *
     * @param activity Activity to get
     * @param farm
     * @return an activity.
     */
    public AbstractActivity get(String activity) {
        String input = activity.toUpperCase();

        if (input.equals("WATER ANIMALS")) {
            return new WaterAnimalActivity(farm);
        } else if (input.equals("FEED ANIMALS")) {
            return new FeedAnimalActivity(farm);
        }

        return null;
    }
}
