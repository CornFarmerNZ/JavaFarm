/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project2.service;

import Project2.entity.Animal;
import Project2.entity.Horse;
import Project2.entity.Kiwi;
import Project2.entity.Pig;
import Project2.entity.Sheep;

/**
 *
 * @author tim
 */
public class AnimalFactory {

    public Animal get(String s) {
        String selection = s.toUpperCase();
        if (selection.equals("PIG")) {
            Animal pig = new Pig();
            Thread threadPig = new Thread(pig);
            threadPig.start();
            return pig;
        } else if (selection.equals("HORSE")) {
            Animal horse = new Horse();
            Thread threadHorse = new Thread(horse);
            threadHorse.start();
            return horse;
        } else if (selection.equals("SHEEP")) {
            Animal sheep = new Sheep();
            Thread threadSheep = new Thread(sheep);
            threadSheep.start();
            return sheep;
        } else if (selection.equals("KIWI")) {
            Animal kiwi = new Kiwi();
            Thread threadKiwi = new Thread(kiwi);
            threadKiwi.start();
            return kiwi;
        }
        return null;
    }

}
