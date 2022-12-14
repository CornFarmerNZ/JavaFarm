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
import Project2.service.UniqueIdentifier;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Kiwi is a rare Animal that is never thirsty and Mood is always JOYFUL.
 *
 * @author tim
 */
public class Kiwi extends Animal {

    public Kiwi() {
        this.hunger = 50;
        this.thirst = 0;
        random = new Random();
        alive = true;
        this.id = UniqueIdentifier.getUniqueIdentifier().getID();
        this.type = "Kiwi";
        this.mood = Mood.JOYFUL;
        this.value = 888;
        this.x = random.nextInt(600) + 200;
        this.y = random.nextInt(350) + 50;
        this.direction = 1;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getType() {
        return "Kiwi";
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
        while (alive) { //never dies
            if (age > 2146000000) {
                System.out.println(this.type + " - " + this.id + " has been sold!");
                alive = false;
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(Sheep.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.mood = moodCheck();
            this.x += (1 * this.direction);
            this.y += (1 * this.direction);
            this.x += (random.nextInt(10) * this.direction);
            this.y += (random.nextInt(5) * this.direction);
            if (this.x > 950 || this.y > 350) {
                this.direction = -1;
                this.x = 950;
                this.y = 350;
            } else if (this.x < 50 || this.y < 50) {
                this.direction = 1;
                this.x = 50;
                this.y = 50;
            }

        }
    }

    @Override
    public String toString() {

        return "" + this.type + " - " + this.id + " Age: " + this.age + " Hunger: " + this.hunger + " Thirst: " + this.thirst + " Mood: " + this.mood;
    }

    public Mood moodCheck() {
        return mood.JOYFUL;
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
