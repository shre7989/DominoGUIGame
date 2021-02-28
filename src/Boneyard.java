/**
 * Boneyard class for the domino game, has functionalities for a boneyard object in the game
 * @author: Mausam Shrestha
 * @date: 02/18/2020
 * @project: 2 - Domino game
 * @version: 1
 * @UNMId: 101865530
 * @course: CS351
 */
package sample;

import java.util.ArrayList;
import java.util.Random;

public class Boneyard {
    /* list of dominos in the boneyard */
    private ArrayList<Domino> bones;

    /**
     * init - Initializes the boneyard with 28 pieces of domino
     */
    public void init(){
        int i,j;
        bones = new ArrayList<>();
        for(i = 0; i <= 6; i++){
            for(j = i; j <= 6; j++){
                bones.add(new Domino(i,j));
            }
        }
    }

    /**
     * getBones - returns the dominos in the boneyard
     * @return - arraylist of the dominos in the boneyard
     */
    public ArrayList<Domino> getBones() {
        return bones;
    }

    /**
     * distributeHands - randomly distributes 7 dominos
     * @return - arraylist of 7 random dominos
     */
    public ArrayList<Domino> distributeHands(){
        ArrayList<Domino> hands = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 7; i++){
            Domino temp = bones.get(random.nextInt(bones.size()));
            bones.remove(temp);
            hands.add(temp);
        }
        return hands;
    }

}
