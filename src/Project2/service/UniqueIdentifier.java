/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project2.service;

/**
 *
 * @author tim
 */
/**
 * Singleton pattern that generates a unique ID for each Farm Animal.
 *
 * @author tim
 */
public class UniqueIdentifier {

    int counter;
    private static UniqueIdentifier identifierInstance = null;

    private UniqueIdentifier() {
        this.counter = 0;
    }

    public synchronized int getID() {
        return getUniqueIdentifier().counter++;
    }

    public static UniqueIdentifier getUniqueIdentifier() {

        if (identifierInstance == null) {
            identifierInstance = new UniqueIdentifier();
        }
        return identifierInstance;
    }

}
