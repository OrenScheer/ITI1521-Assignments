/**
 * The interface Player represents a TicTacToe player
 * of any type.
 * @author Oren Scheer
 */
public interface Player {
  /**
 * Method that lets a Player play a move on a Tic Tac Toe board.
 * @param game
 *  the game board on which the Player plays their move.
 */
  public void play(TicTacToeGame game);
}
