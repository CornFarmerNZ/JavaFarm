/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project2.entity;

import java.util.Random;

/**
 *
 * @author tim
 */
public abstract class Animal implements AnimalInterface, Runnable {

    int hunger; //0 -> hungry, 100 -> satiated.
    int thirst;//0 -> thirsty, 100 -> satiated.
    Mood mood; //"Joyful", "Neutral", "Lazy", "Relaxing", "Irritated"
    int age; //days
    boolean alive;
    int id;
    String type;
    int value;
    Random random;
    //x & y for gui
    int x;
    int y;

}
