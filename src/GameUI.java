/**
 * GameUI class is the console user interface for the domino game, its main function is to interact with the player
 * @author: Mausam Shrestha
 * @date: 02/18/2020
 * @project: 2 - Domino game
 * @version: 1
 * @UNMId: 101865530
 * @course: CS351
 */
package sample;

import javafx.scene.control.TextField;

import java.util.ArrayList;

public class GameUI{

    /**
     * welcomeMessage - prints the welcome message
     */
    public String welcomeMessage(){
        String welcome;
        welcome = String.format("*******Dominos!*******\nComputer has 7 Dominos\nBoneyard has 14 Dominos");
        return welcome;
    }

    /**
     * getPlayerMoves - gets the move the player wants to perform
     * @param player - player who plays the game
     * @return - returns the list of commands given by the player
     * @throws NumberFormatException - exception when the player does not input a number for domino index
     */
    public ArrayList<String> getPlayerMoves (Player player, TextField index, TextField place, TextField rotate){
        ArrayList<String> commands = new ArrayList<>();
        String move;

        move = index.getText();
        commands.add(move);

        move = place.getText();
        commands.add(move);

        move = rotate.getText();
        commands.add(move);

        return commands;
    }

    /**
     * Update - helps to post updates about the progress of the game
     * @param computer - computer who plays the game
     */
    public String Update(Player computer, Boneyard boneyard){
        String computerUpdate, boneyardUpdate;

        computerUpdate = String.format("Computer has %d Dominoes\n", computer.getHands().size());
        boneyardUpdate = String.format("Boneyard contains %d Dominoes\n", boneyard.getBones().size());

        String update = computerUpdate + boneyardUpdate;
        return update;
    }


    /**
     * gameOver - Tells who won the game
     * @param human - human player who plays the game
     * @param computer - computer player who plays the game
     */
    public String gameOver(Player human, Player computer){
        String gameOver;
        String humanWins;
        String compWins;
        int hScore, cScore;

        hScore = human.getScore();
        cScore = computer.getScore();
        humanWins = String.format("The winner is human with %d points\n",hScore);
        compWins = String.format("The winner is computer with %d points\n",cScore);
        gameOver = String.format("Game Over!!!\nHuman Score: %d\nComputer Score: %d\n",hScore,cScore);

        if(human.getScore() <= computer.getScore()){
            gameOver = gameOver + humanWins;
        }
        else gameOver = gameOver + compWins;
        return gameOver;
    }
}
