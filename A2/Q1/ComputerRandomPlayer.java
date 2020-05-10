/**
 * The implementation of the Player interface that allows
 * a computer randomly play Tic Tac Toe.
 * @author Oren Scheer
 */
public class ComputerRandomPlayer implements Player {
  /**
  * Method that lets a ComputerRandomPlayer play a move on a Tic Tac Toe board.
  * @param game
  *  the game board on which the HumanPlayer plays their move.
  */
  public void play(TicTacToeGame game) {
    if (game.getGameState() != GameState.PLAYING) {
      System.out.println("This game is not playable");
    }
    else {
      int guess = Utils.generator.nextInt(game.lines * game.columns); // Range from [0, board length]
      while (game.valueAt(guess) != CellValue.EMPTY) { // Keep guessing while guess is not an empty cell
        guess = Utils.generator.nextInt(game.lines * game.columns);
      }
      game.play(guess);
    }
  }
}
