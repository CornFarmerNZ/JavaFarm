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
/**
 *
 * @author tim
 */
public interface FarmInterface {

    /**
     *
     * @return True if playing, false if not playing.
     */
    public boolean isPlaying();

    /**
     *
     * @param playing Boolean to set farm's playing variable to.
     */
    public void setPlaying(boolean playing);

    /**
     *
     * @return The gold that the player has.
     */
    public int getGold();

    /**
     *
     * @param day Day to set.
     */
    public void setDay(int day);

    /**
     *
     * @return The day that the farm is on.
     */
    public int getDay();

    /**
     *
     * @param gold Sets the player's gold.
     */
    public void setGold(int gold);

    /**
     *
     * @return the Type of the farm as a String. e.g., Animal, Crop, Mixed.
     */
    public String getType();

    /**
     * unimplemented, for now.
     */
    public void incrementDay();

    /**
     *
     * Sets the Name of the farm.
     *
     * @param name Name of the farm.
     */
    public void setName(String name);

    /**
     *
     * @return Name of the farm.
     */
    public String getName();

    /**
     * Advances the farm time.
     *
     * @param numberOfDays The number of days to fast-forward by.
     */
    public void advanceTime(int numberOfDays);

    /**
     *
     * @return Energy amount.
     */
    public int getEnergy();

    /**
     * Sets energy to new value.
     *
     * @param energy Energy to set.
     */
    public void setEnergy(int energy);

    public void endDay();

}
