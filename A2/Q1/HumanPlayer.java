/**
 * The implementation of the Player interface that allows
 * a human player to play Tic Tac Toe.
 * @author Oren Scheer
 */
public class HumanPlayer implements Player {
  /**
  * Method that lets a HumanPlayer play a move on a Tic Tac Toe board.
  * @param game
  *  the game board on which the HumanPlayer plays their move.
  */
  public void play(TicTacToeGame game) {
    if (game.getGameState() != GameState.PLAYING) {
      System.out.println("This game is not playable");
    }
    else {
      boolean valid = false;
      while (!valid) { // Keep asking for input
        System.out.print(game.nextCellValue() + " to play: ");
        String next = Utils.console.readLine();
        int pos = Integer.parseInt(next);

        if (pos < 1 || pos > (game.columns * game.lines)) {
          System.out.println("The value should be between 1 and " + (game.lines * game.columns));
          System.out.println(game);
        }
        else if (game.valueAt(pos - 1) != CellValue.EMPTY) {
          System.out.println("This cell has already been played");
          System.out.println(game);
        }
        else {
          valid = true;
          game.play(pos - 1);
        }
      }
    }
  }
}
