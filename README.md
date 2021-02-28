**Instructions on how to play the Domino game:**

**Domino Index:** the index for the dominos for the player starts from 0, visually on the screen the left most domino is 0, and the index increases as we move right.

**Valid Domino:** A domino is valid if it matches the left side of the leftmost domino or the right side of the rightmost of GameBoard.

**Computer:** A computer is a player who automatically plays the domino game without any external guidance.

**Tutorial:**__
- **Run the application**
- **Give valid inputs on the text field**
    - index => only non-negative integers with the constraint (i.e 0 < index < player.getHands().size())
    - place => only enter "l" or "r"
    - rotate => only enter "l" or "r"
- **play:** Press the play button to play your move. 
    - You cannot play until you have a valid input in the textfields above the play button.
    - You cannot play a invalid domino on the gameBoard.
    - you will automatically draw a domino if you do not have any playable dominos.
- **draw:** Press the draw button to draw from the boneYard.
    - you cannot draw if you have a playable domino in hand.
    - you cannot draw if the boneyard is empty.
- **quit:** Press quit to end the game.
    - you will automatically quit the game if the game ends.
    - the resulting score of the players and the winner of the game is declared when the game ends.
    - you can quit the game before the game ends if you press the quit button.

