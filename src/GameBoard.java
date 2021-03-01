
/**
 * GameBoard class represents the actual game board in the domino game, where the player place their dominos
 * @author: Mausam Shrestha
 * @date: 02/18/2020
 * @project: 2 - Domino game
 * @version: 1
 * @UNMId: 101865530
 * @course: CS351
 */

package sample;

import java.util.LinkedList;

public class GameBoard {
    /* list of dominos in the game board */
    private final LinkedList<Domino> board = new LinkedList<>();

    /**
     * show - prints the list of dominos in board
     */
    public void show(){
        int length;
        length = this.board.size();
        for(int i = 0; i < length; i+= 2){
            System.out.printf("[%d %d] ", board.get(i).getLeft(), board.get(i).getRight());
        }
        System.out.println();
        for(int i = 1; i < length; i+= 2){
            if(i == 1) System.out.print("  ");
            System.out.printf("[%d %d] ", board.get(i).getLeft(), board.get(i).getRight());
        }
        System.out.print("\n\n");
    }

    /**
     * getBoard - gets the dominos on board
     * @return - the list of dominos on the game board
     */
    public LinkedList<Domino> getBoard(){
        return board;
    }

    /**
     * findMatch - checks if given domino is playable
     * @param domino - the domino we want to check
     * @return - true if match and false otherwise
     */
    public boolean findMatch(Domino domino){
        boolean matchLeft;
        boolean matchRight;

        matchLeft = domino.isMatchLeft(this.getBoard().getFirst());
        matchRight = domino.isMatchRight(this.getBoard().getLast());

        return (matchLeft || matchRight);
    }
}

