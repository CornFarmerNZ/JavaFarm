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
public interface AnimalInterface {

    public int setHunger(int num);

    public int getHunger();

    public int setAge(int age);

    public int getAge();

    public int setThirst(int num);

    public int getThirst();

    public void setMood(Mood mood);

    public String getMood();

    public void kill();

    public String getType();

    public int getX();

    public int getY();

    public enum Mood {
        JOYFUL {
            public String asLowerCase() {
                return JOYFUL.toString().toUpperCase();
            }
        }, NEUTRAL {
            public String asLowerCase() {
                return NEUTRAL.toString().toUpperCase();
            }
        }, LAZY {
            public String asLowerCase() {
                return LAZY.toString().toUpperCase();
            }
        }, RELAXING {
            public String asLowerCase() {
                return RELAXING.toString().toUpperCase();
            }
        }, IRRITATED {
            public String asLowerCase() {
                return IRRITATED.toString().toUpperCase();
            }
        }
    }

}
