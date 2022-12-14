/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project2.entity;

import Project2.service.AnimalFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tim
 */
public class AnimalFarm implements FarmInterface, Runnable {

    String name;
    int day;
    Map<Integer, Animal> animals;
    AnimalFactory factory;
    int energy;
    boolean hasKiwi = false;
    int gold;
    boolean playing;

    public AnimalFarm() {
        animals = new HashMap<>();
        factory = new AnimalFactory();
        playing = true;
        this.day = 1;
        this.energy = 3;
        this.gold = 0;

    }

    public void addAnimal(String animal) {
        Animal temp = factory.get(animal);
        animals.put(temp.id, temp);
    }

    public void addAnimal(int age, String animal, int hunger, int thirst) {
        Animal temp = factory.get(animal);
        temp.age = age;
        temp.hunger = hunger;
        temp.thirst = thirst;
        animals.put(temp.id, temp);
    }

    public void addAnimal(Animal animal) {
        animals.put(animal.id, animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal.id);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void advanceTime(int numberOfDays) {
        this.day++;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getType() {
        return "Animal";
    }

    @Override
    public int getDay() {
        return this.day;
    }

    @Override
    public String toString() {
        String temp = "";
        for (Entry e : animals.entrySet()) {
            if (e != null) {
                temp += e.getValue().toString() + "\n";
            }
        }
        return temp;
    }

    public Map<Integer, Animal> getAnimals() {
        return this.animals;
    }

    @Override
    public int getEnergy() {
        return this.energy;
    }

    @Override
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public void incrementDay() {
        this.day++;
    }

    @Override
    public void endDay() {
        incrementDay();
        setEnergy(3);
        ArrayList<Animal> dead = new ArrayList<>();
        for (Entry e : animals.entrySet()) {
            Animal a = (Animal) e.getValue();
            a.age++;
            if (!a.getClass().equals(Kiwi.class)) {
                a.setHunger(a.getHunger() + 2);
                a.setThirst(a.getThirst() + 4);
            }
            if (a.getHunger() > 100) { //values cap out at 100.
                a.setHunger(100);
            }
            if (a.getThirst() > 100) {
                a.setThirst(100);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(AnimalFarm.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!a.alive) {
                dead.add(a);
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AnimalFarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Animal a : dead) {
            animals.remove(a.id);
        }
    }

    @Override
    public int getGold() {
        return this.gold;
    }

    @Override
    public void setGold(int gold) {
        this.gold = gold;
    }

    @Override
    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public boolean isPlaying() {
        return this.playing;
    }

    @Override
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    @Override
    public void run() {
        while (playing) {
            try {
                Thread.sleep(5000);
                System.out.println("Playing...");
            } catch (InterruptedException ex) {
                Logger.getLogger(AnimalFarm.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
