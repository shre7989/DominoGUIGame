/**
 * DominoGUI - Our main scene for the GUI events
 * @author: Mausam Shrestha
 * @date: 02/18/2020
 * @project: 2 - Domino game
 * @version: 1
 * @UNMId: 101865530
 * @course: CS351
 */

package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.ArrayList;
import java.util.LinkedList;

public class DominoGUI extends Scene {
    /* Main components of the game */
    private GraphicsContext gcBoard;
    private GraphicsContext gcPlayer;
    private final GameBoard BOARD = new GameBoard();
    private final Boneyard GAME_BONEYARD = new Boneyard();
    private final GameUI UI =  new GameUI();
    private final Player HUMAN;
    private final Player COMPUTER;
    private boolean quit = false;
    private boolean start = true;
    public boolean invalidMove = false;
    private TextField indexField;
    private TextField placeField;
    private TextField rotateField;
    private Label gameUpdates;

    /**
     * DominoGUI constructor that initializes our GUI scene
     * @param root - root node
     * @param width - width of the scene
     * @param height - height of the scene
     */
    public DominoGUI(Parent root, double width, double height) {
        super(root, width, height);
        setup((VBox) root);

        GAME_BONEYARD.init();

        HUMAN = new Player();
        HUMAN.setHands(GAME_BONEYARD.distributeHands());
        drawPlayerDomino(HUMAN.getHands());

        COMPUTER = new Player();
        COMPUTER.setHands(GAME_BONEYARD.distributeHands());
    }

    /**
     * setup - Sets up the components like buttons, canvas, Labels etc for our main scene
     * @param layout - layout of our main scene
     */
    public void setup(VBox layout){
        /* setting the background color */
        Color customColor = Color.rgb(30,30,30);
        layout.setBackground(new Background(new BackgroundFill(customColor, CornerRadii.EMPTY, Insets.EMPTY)));

        /* setting a Label to show our gameUpdates */
        gameUpdates = new Label(UI.welcomeMessage());
        gameUpdates.setFont(Font.font("Calibri", FontWeight.EXTRA_BOLD, 15));
        gameUpdates.setTextFill(Color.RED);

        /* Canvas for showing our game BOARD and player hands */
        Canvas mainBoard = new Canvas(1100,500);
        gcBoard = mainBoard.getGraphicsContext2D();
        gcBoard.setFill(Color.SADDLEBROWN);
        gcBoard.fillRoundRect(0,0,1100,500,20,20);

        Canvas playerBoard = new Canvas(900,300);
        gcPlayer = playerBoard.getGraphicsContext2D();
        gcPlayer.setFill(Color.rgb(234,170,0));
        gcPlayer.fillRoundRect(0,0,900,300,20,20);

        /* Buttons to interact with the user and to pass the input */
        Button play = new Button("PLAY");
        play.setOnAction(event -> {
            if(!invalidInput() && !quit ) {
                play();
                drawPlayerDomino(HUMAN.getHands());
                drawBoardDomino(BOARD.getBoard());
                indexField.setText("");
                placeField.setText("");
                rotateField.setText("");
                start = false;
            }
        });

        Button draw = new Button("DRAW");
        draw.setOnAction(event -> {
            if(!start && !GAME_BONEYARD.getBones().isEmpty() && !isStillPlayable(HUMAN.getHands())) {
                while(!isStillPlayable(HUMAN.getHands()) && !GAME_BONEYARD.getBones().isEmpty()) draw();
                drawPlayerDomino(HUMAN.getHands());
                drawBoardDomino(BOARD.getBoard());
            }
            else if(isStillPlayable(HUMAN.getHands())) gameUpdates.setText("You still have a playable domino!!");
        });

        Button quit = new Button("QUIT");
        quit.setOnAction(event -> gameOver(UI));

        /* Labels and textfields to get the input move from the player */
        Label index = new Label("Index");
        index.setTextFill(Color.WHITE);
        indexField = new TextField();
        indexField.setMaxWidth(40);
        indexField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                indexField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        Label place = new Label("Place");
        place.setTextFill(Color.WHITE);
        placeField = new TextField();
        placeField.setMaxWidth(40);

        Label rotate = new Label("Rotate?");
        rotate.setTextFill(Color.WHITE);
        rotateField = new TextField();
        rotateField.setMaxWidth(40);

        /* Hbox and Vbox to layout our Gui components in our scene */
        HBox inputMoves = new HBox(10);
        inputMoves.setAlignment(Pos.CENTER);
        inputMoves.getChildren().add(index);
        inputMoves.getChildren().add(indexField);
        inputMoves.getChildren().add(place);
        inputMoves.getChildren().add(placeField);
        inputMoves.getChildren().add(rotate);
        inputMoves.getChildren().add(rotateField);

        HBox buttonBox = new HBox(80);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(draw);
        buttonBox.getChildren().add(quit);

        VBox getInput = new VBox(5);
        getInput.setAlignment(Pos.CENTER);
        getInput.getChildren().add(inputMoves);
        getInput.getChildren().add(play);
        getInput.getChildren().add(buttonBox);

        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(gameUpdates);
        layout.getChildren().add(mainBoard);
        layout.getChildren().add(playerBoard);
        layout.getChildren().add(getInput);
    }

    /**
     * play - Sets up a sequential play, taking turns and quitting when the game is over
     */
    public void play(){
        ArrayList<String> commands;
        Domino domino;

        /* we make the play if the player has playable dominos and the move is valid */
        if (BOARD.getBoard().isEmpty() || (isStillPlayable(HUMAN.getHands()))) {
            commands = UI.getPlayerMoves(HUMAN,indexField,placeField,rotateField);
            domino = HUMAN.getHands().get(Integer.parseInt(commands.get(0)));
            if(start) HUMAN.play(domino,BOARD, commands.get(1), commands.get(2));
            else if(domino.isValid(BOARD) && isValidMove(commands)) {
                HUMAN.play(domino, BOARD, commands.get(1),commands.get(2));
            }
            else{
                gameUpdates.setText("Please place a valid domino with the valid moves!!");
                invalidMove = true;
            }
        } else if (!GAME_BONEYARD.getBones().isEmpty() && isStillPlayable(GAME_BONEYARD.getBones())){
            HUMAN.draw(GAME_BONEYARD);
        }
        else if(isOver()) gameOver(UI);

        /* The player needs to enter a valid move before computer can play */
        if (!quit && !invalidMove) {
            gameUpdates.setText(UI.Update(COMPUTER, GAME_BONEYARD));
            COMPUTER.autoPlay(BOARD, GAME_BONEYARD);
            gameUpdates.setText(UI.Update(COMPUTER, GAME_BONEYARD));
        }
        if(isOver()) gameOver(UI);
        invalidMove = false;
    }


    /**
     * draw - Helps to draw from the boneyard
     */
    public void draw(){
        HUMAN.draw(GAME_BONEYARD);
        if(isOver()) gameOver(UI);
        if(!quit){
            gameUpdates.setText(UI.Update(COMPUTER, GAME_BONEYARD));
            COMPUTER.autoPlay(BOARD, GAME_BONEYARD);
            gameUpdates.setText(UI.Update(COMPUTER, GAME_BONEYARD));
        }
    }

    /**
     * gameOver - called when the game is over
     * @param UI - Our GameUI that helps to print the message when the game is over
     */
    public void gameOver(GameUI UI){
        quit = true;
        gameUpdates.setText(UI.gameOver(HUMAN, COMPUTER));
        //Platform.exit();
    }

    /**
     * isOver - checks if boneyard or player or COMPUTER have empty hands
     */
    public boolean isOver(){
        boolean humanPlayable = isStillPlayable(HUMAN.getHands());
        boolean computerPlayable = isStillPlayable(COMPUTER.getHands());
        boolean boneyardEmpty = GAME_BONEYARD.getBones().isEmpty();
        boolean boneyardPlayable = isStillPlayable(GAME_BONEYARD.getBones());

        if((!humanPlayable || !computerPlayable) && (boneyardEmpty || !boneyardPlayable)){
            quit = true;
            return true;
        }
        else if (boneyardEmpty && (HUMAN.getHands().isEmpty() || COMPUTER.getHands().isEmpty())){
            quit = true;
            return true;
        }
        return false;
    }

    /**
     * isStillPlayable - checks if the player has playable dominos
     * @param list - list of player's dominos
     * @return - true if player has playable dominos and false otherwise
     */
    private boolean isStillPlayable(ArrayList<Domino> list){
        Domino first;
        Domino last;

        if(start) return true;
        else{
            first = BOARD.getBoard().getFirst();
            last = BOARD.getBoard().getLast();
        }
        for(Domino i: list){
            if(i.isMatchRight(last) || i.isMatchLeft(first)) return true;
        }
        return false;
    }

    /**
     * refresh - resets the BOARD and player canvas for our scene
     */
    private void refresh(){
        gcPlayer.setFill(Color.rgb(234,170,0));
        gcPlayer.fillRoundRect(0,0,900,300,20,20);
        gcBoard.setFill(Color.SADDLEBROWN);
        gcBoard.fillRoundRect(0,0,900,500,20,20);
    }

    /**
     * drawPlayerDomino - draws the players domino on the player's canvas
     * @param tiles - player's hand of dominos
     */
    private void drawPlayerDomino(ArrayList<Domino> tiles){
        double xCoor = 40;
        double yCoor = 40;

        /* reset the canvas for a new paint cycle */
        refresh();

        this.gcPlayer.setStroke(Color.BLACK);
        for(Domino i : tiles){

            /* the boundary of the domino */
            this.gcPlayer.setFill(Color.WHITE);
            this.gcPlayer.strokeRoundRect(xCoor,yCoor,35,70,10,10);
            this.gcPlayer.fillRoundRect(xCoor,yCoor,35,70,10,10);
            this.gcPlayer.setFill(Color.BLACK);

            /* making the actual number of dots in the domino */
            drawDomino(i.getLeft(),xCoor + 5, yCoor + 5,this.gcPlayer);
            this.gcPlayer.strokeLine(xCoor,yCoor + 34,xCoor + 35, yCoor + 34);
            drawDomino(i.getRight(),xCoor + 5, yCoor + 40,this.gcPlayer);

            /* check if placing our dominos will exceed the dimension of the canvas */
            xCoor += 60;
            if(xCoor > 820){
                xCoor = 40;
                yCoor += 120;
            }
        }
    }

    /**
     * drawDomino - helps to draw and configure individual domino pieces on the canvas
     * @param val - left or right value of the domino
     * @param xCoor - xCoor of the canvas
     * @param yCoor - yCoor of the canvas
     * @param gc - graphics context of the canvas
     */
    private void drawDomino(int val, double xCoor, double yCoor, GraphicsContext gc){
        switch (val){
            case 1:
                gc.fillOval(xCoor + 10,yCoor + 10,5,5);
                break;
            case 2:
                gc.fillOval(xCoor + 10, yCoor, 5, 5);
                gc.fillOval(xCoor + 10, yCoor + 20, 5 ,5);
                break;
            case 3:
                gc.fillOval(xCoor + 10,yCoor,5,5);
                gc.fillOval(xCoor + 10,yCoor + 10,5,5);
                gc.fillOval(xCoor + 10,yCoor + 20,5,5);
                break;
            case 4:
                gc.fillOval(xCoor,yCoor,5,5);
                gc.fillOval(xCoor + 20,yCoor,5,5);
                gc.fillOval(xCoor,yCoor + 20, 5 ,5);
                gc.fillOval(xCoor + 20, yCoor + 20, 5 ,5);
                break;
            case 5:
                gc.fillOval(xCoor,yCoor,5,5);
                gc.fillOval(xCoor + 20,yCoor,5,5);
                gc.fillOval(xCoor + 10,yCoor + 10,5,5);
                gc.fillOval(xCoor,yCoor + 20,5,5);
                gc.fillOval(xCoor + 20,yCoor + 20,5,5);
                break;
            case 6:
                gc.fillOval(xCoor,yCoor,5,5);
                gc.fillOval(xCoor + 20,yCoor,5,5);
                gc.fillOval(xCoor,yCoor + 10,5,5);
                gc.fillOval(xCoor + 20,yCoor + 10,5,5);
                gc.fillOval(xCoor,yCoor + 20,5,5);
                gc.fillOval(xCoor + 20,yCoor + 20,5,5);
                break;
            default:
                break;
        }
    }

    /**
     * drawBoardDomino - draws the dominos in the BOARD on canvas
     * @param tiles - set of dominos in our GameBoard
     */
    private void drawBoardDomino(LinkedList<Domino> tiles){
        double xCoor = 20;
        double yCoor = 140;
        int count = 0;

        this.gcBoard.setStroke(Color.BLACK);
        for(Domino i : tiles){

            /* calculations to get the dominos to match the required pattern on the screen */
            if(count % 2 == 0 && count != 0){
                xCoor -= 35;
                yCoor -= 36;
                xCoor += 70;
            }
            else{
                xCoor = xCoor + 35;
                yCoor = yCoor + 36;
            }

            /* drawing the boundary of the domino */
            this.gcBoard.setFill(Color.WHITE);
            this.gcBoard.strokeRoundRect(xCoor,yCoor,70,35,10,10);
            this.gcBoard.fillRoundRect(xCoor,yCoor,70,35,10,10);
            this.gcBoard.setFill(Color.BLACK);

            /* drawing the domino dots */
            drawDomino(i.getLeft(),xCoor + 5, yCoor + 5, this.gcBoard);
            this.gcBoard.strokeLine(xCoor + 34,yCoor,xCoor + 34, yCoor + 35);
            drawDomino(i.getRight(),xCoor + 40, yCoor + 5, this.gcBoard);

            count++;

        }
    }

    /**
     * invalidInput - checks for invalid or empty input from the user
     * @return true if invalid and false if valid input.
     */
    private boolean invalidInput(){
        boolean indexEmpty = indexField.getText().isEmpty();
        boolean placeEmpty = placeField.getText().isEmpty();
        boolean rotateEmpty = rotateField.getText().isEmpty();
        int humanHandsSize = HUMAN.getHands().size();
        String index = indexField.getText();
        String place = placeField.getText();
        String rotate = rotateField.getText();

        /* condition check for invalid inputs */
        if(indexEmpty || placeEmpty || rotateEmpty) return true;
        else if(Integer.parseInt(index) < 0 || Integer.parseInt(index) >= humanHandsSize) return true;
        else if(!place.equals("l") && !place.equals("r")) return true;
        else return !rotate.equals("y") && !rotate.equals("n");
    }


    /**
     * isValidMove - Checks if the player move is a valid move in the game
     * @param command - list of player's move
     * @return - true id valid move and false otherwise.
     */
    public boolean isValidMove(ArrayList<String> command) {
        /* variables to break long lines lines of code */
        int leftVal;
        int rightVal;
        int leftOfDomino;
        int rightOfDomino;
        Domino indexDomino;

        indexDomino = HUMAN.getHands().get(Integer.parseInt(command.get(0)));
        leftVal = BOARD.getBoard().getFirst().getLeft();
        rightVal = BOARD.getBoard().getLast().getRight();
        leftOfDomino = indexDomino.getLeft();
        rightOfDomino = indexDomino.getRight();

        /* conditions to check valid moves */
        if(leftOfDomino == 0 && rightOfDomino == 0) return true;
        if (indexDomino.isMatchLeft(BOARD.getBoard().getFirst())) {
            if (command.get(1).equals("r")) return false;
            else if(leftOfDomino == leftVal || rightOfDomino == leftVal){
                if(command.get(2).equals("n") && rightOfDomino == leftVal) return true;
                else return (command.get(2).equals("y") && leftOfDomino == leftVal);
            }
            else return true;
        } else if (indexDomino.isMatchRight(BOARD.getBoard().getLast())) {
            if (command.get(1).equals("l")) return false;
            else if(rightOfDomino == rightVal || leftOfDomino == rightVal) {
                if(command.get(2).equals("n") && leftOfDomino == rightVal) return true;
                else return (command.get(2).equals("y") && rightOfDomino == rightVal);
            }
            else return true;
        }
        return false;
    }


}
