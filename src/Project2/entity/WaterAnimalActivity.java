/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project2.entity;

/**
 *
 * @author tim
 */
public class WaterAnimalActivity extends AbstractActivity {

    AnimalFarm farm;

    public WaterAnimalActivity(AnimalFarm farm) {
        this.farm = farm;
    }

    @Override
    public boolean start(FarmInterface farm) {
        try {
            for (Animal a : ((AnimalFarm) farm).getAnimals()) {
                a.thirst -= 15;
            }
            return true;
        } catch (Exception ex) {
            System.out.println("Some of your animals refuses to drink water!");
            return false;
        }
    }

}
