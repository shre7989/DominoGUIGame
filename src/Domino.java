/**
 * Domino object for the game, has functionalities for what a domino tile could do!
 * @author: Mausam Shrestha
 * @date: 02/18/2020
 * @project: 2 - Domino game
 * @version: 1
 * @UNMId: 101865530
 * @course: CS351
 */

package sample;

public class Domino {
    /* sides of Domino */
    private int left;
    private int right;

    /**
     * Domino - Domino constructor which initialized the Domino with the given values
     * @param left - left value for the domino
     * @param right - right value for the domino
     */
    public Domino(int left, int right){
        this.left = left;
        this.right = right;
    }

    /**
     * getLeft - gets the value in the left hand side of the domino
     * @return - returns the value in the left hand side of domino
     */
    public int getLeft() { return left; }

    /**
     * getRight - gets the value in the right hand side of the domino
     * @return - returns the value in the right hand side of domino
     */
    public int getRight() { return right; }

    /**
     * isMatch - checks if a domino has matching values with the given domino
     * @param match - domino object to match with
     * @return - true if match and false if no match
     */
    public boolean isMatch(Domino match){
        return (this.left == match.left || this.left == match.right || this.right == match.right || this.right == match.left);
    }

    /**
     * isMatchLeft - checks if this domino matches the left value of the given domino
     * @param match - domino object to match with
     * @return - true if match and false otherwise
     */
    public boolean isMatchLeft(Domino match){
        return (this.right == match.left || this.left == match.left);
    }

    /**
     * isMatchRight - checks if this domino matches the right value of the given domino
     * @param match - domino object to match with
     * @return - true if match and false otherwise
     */
    public boolean isMatchRight(Domino match){
        return (this.left == match.right || this.right == match.right);
    }

    /**
     * rotate - rotate this domino by exchanging its values
     */
    public void rotate(){
        int temp;
        temp = this.left;
        this.left = this.right;
        this.right = temp;
    }

    /**
     * isWild - checks if this domino is wild [0 0]
     * @return - true if wild and false otherwise
     */
    public boolean isWild(){
        return ((this.left == 0) && (this.right == 0));
    }

}
