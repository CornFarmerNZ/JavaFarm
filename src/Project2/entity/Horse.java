/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project2.entity;

import Project2.service.UniqueIdentifier;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tim
 */
public class Horse extends Animal {

    public Horse() {
        this.hunger = 50;
        this.thirst = 50;
        random = new Random();
        alive = true;
        this.id = UniqueIdentifier.getUniqueIdentifier().getID();
        this.type = "Horse";
        this.mood = moodCheck();
        this.value = 225;
        this.x = random.nextInt(600) + 200;
        this.y = random.nextInt(350) + 50;
        this.direction = 1;
    }

    @Override
    public String getType() {
        return "Horse";
    }

    @Override
    public int setHunger(int num) {
        synchronized (this) {
            this.hunger = num;
        }
        return this.hunger;
    }

    @Override
    public int getHunger() {
        return this.hunger;
    }

    @Override
    public int setThirst(int num) {
        synchronized (this) {
            this.thirst = num;
        }
        return this.thirst;
    }

    @Override
    public int getThirst() {
        return this.thirst;
    }

    @Override
    public void setMood(Mood mood) {
        synchronized (this) {
            this.mood = mood;
        }
    }

    @Override
    public String getMood() {
        return this.mood.toString();
    }

    @Override
    public int setAge(int age) {
        synchronized (this) {
            this.age = age;
        }
        return age;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public void run() {
        while (alive) {
            if (age > 365) {
                if (age < 2146000000) {
                    System.out.println(this.toString() + " has died of old age");
                } else if (age > 2146000000) {
                    System.out.println(this.type + " - " + this.id + " has been sold!");
                }
                alive = false;
            }
            this.mood = moodCheck();
            this.x += (random.nextInt(10) * this.direction);
            this.y += (random.nextInt(5) * this.direction);
            if (this.x > 950 || this.y > 350) {
                this.direction = -1;
            } else if (this.x < 50 || this.y < 50) {
                this.direction = 1;
            }

        }
    }

    @Override
    public String toString() {

        return "" + this.type + " - " + this.id + " Age: " + this.age + " Hunger: " + this.hunger + " Thirst: " + this.thirst + " Mood: " + this.mood;
    }

    public Mood moodCheck() {
        if (this.hunger < 20 && this.thirst < 20) {
            return Mood.JOYFUL;
        } else if (this.hunger < 40 && this.thirst < 40) {
            return Mood.RELAXING;
        } else if (this.hunger < 60 && this.thirst < 60) {
            return Mood.NEUTRAL;
        } else if (this.hunger < 80 && this.thirst < 80) {
            return Mood.LAZY;
        } else {
            return Mood.IRRITATED;
        }
    }

    @Override
    public void kill() {
        this.alive = false;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

}
