/**
 * Player is a class for player objects in the domino game. It has all the functionalities a player could do
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

public class Player {
    /* PLayers set of Domino */
    private ArrayList<Domino> hands;

    /**
     * setHands - sets the dominos for the player
     * @param myHands - list of the dominos the player has
     */
    public void setHands(ArrayList<Domino> myHands){
        hands = myHands;
    }

    /**
     * getHands - gets the dominos of the player
     * @return - list of dominos the player has
     */
    public ArrayList<Domino> getHands(){ return hands; }

    /**
     * play - tries to execute the specified move in the game
     * @param domino - our domino that we want to play
     * @param board - board where we want the domino to be placed
     * @param place - play either in left or the right
     * @param rotate - rotate the domino if required
     */
    public void play(Domino domino, GameBoard board, String place, String rotate){
        if(board.getBoard().isEmpty() || board.findMatch(domino) || domino.isWild()){
            this.getHands().remove(domino);
            if(rotate.equals("y")) domino.rotate();
            if(place.equals("l")) board.getBoard().addFirst(domino);
            else board.getBoard().addLast(domino);
        }
    }

    /**
     * draw - draw a domino from the boneyard
     * @param boneyard - boneyard from which the domino is drawn
     */
    public void draw(Boneyard boneyard){
        Domino newDomino;
        Random random = new Random();
        newDomino = boneyard.getBones().get(random.nextInt(boneyard.getBones().size()));
        this.hands.add(newDomino);
        boneyard.getBones().remove(newDomino);
    }


    /**
     * getScore - gets the score of the player
     * @return - the score of the player
     */
    public int getScore(){
        int count = 0;
        for(Domino i : hands){
            count = count + i.getLeft() + i.getRight();
        }
        return count;
    }

    /**
     * autoPlay - autoPlay functionality for the computer player
     * @param board - board to place the domino
     * @param boneyard - boneyard from which to draw domino
     */
    public void autoPlay(GameBoard board, Boneyard boneyard){
        int index = 0;
        boolean found = false;
        Domino domino;

        while(!found){
            if(index >= this.hands.size() ) this.draw(boneyard);
            if(index < this.hands.size()) {
                domino = this.hands.get(index);
                if (domino.isWild()) {
                    found = true;
                    this.getHands().remove(domino);
                    board.getBoard().addLast(domino);
                } else if (board.getBoard().size() == 1) {
                    if (domino.isMatch(board.getBoard().getFirst())) {
                        found = true;
                        this.checkSinglePiece(domino, board);
                    }
                } else if (domino.isMatchLeft(board.getBoard().getFirst())) {
                    found = true;
                    this.checkBoardLeft(domino, board);
                } else if (domino.isMatchRight(board.getBoard().getLast())) {
                    found = true;
                    this.checkBoardRight(domino, board);
                }
                index++;
            }else found = true;
        }
    }

    /**
     * checkBoardLeft - checks if our domino matches the left side of the left end of the dominos in board
     * @param domino - domino to check
     * @param board - board of the domino game
     */
    private void checkBoardLeft(Domino domino, GameBoard board){
        if(domino.getRight() == board.getBoard().getFirst().getLeft()) {
            this.getHands().remove(domino);
            board.getBoard().addFirst(domino);
        }
        else {
            this.getHands().remove(domino);
            domino.rotate();
            board.getBoard().addFirst(domino);
        }
    }

    /**
     * checkBoardRight - checks if our domino matches the right side of the right end of the dominos in board
     * @param domino - domino to check
     * @param board - board of the domino game
     */
    private void checkBoardRight(Domino domino, GameBoard board){
        if(domino.getLeft() == board.getBoard().getLast().getRight()) {
            this.getHands().remove(domino);
            board.getBoard().addLast(domino);
        }
        else {
            this.getHands().remove(domino);
            domino.rotate();
            board.getBoard().addLast(domino);
        }
    }

    /**
     * checkSinglePiece - helps computer decide its move when there is only one domino on board
     * @param domino - domino the computer wants to play
     * @param board - board where the computer wants to place the domino
     */
    private void checkSinglePiece(Domino domino, GameBoard board){
        if(domino.getLeft() == board.getBoard().getFirst().getLeft()){
            this.getHands().remove(domino);
            domino.rotate();
            board.getBoard().addFirst(domino);

        }
        else if(domino.getLeft() == board.getBoard().getFirst().getRight()){
            this.getHands().remove(domino);
            board.getBoard().addLast(domino);
        }
        else if(domino.getRight() == board.getBoard().getFirst().getRight()){
            this.getHands().remove(domino);
            domino.rotate();
            board.getBoard().addLast(domino);
        }
        else{
            this.getHands().remove(domino);
            board.getBoard().addFirst(domino);
        }
    }

}
