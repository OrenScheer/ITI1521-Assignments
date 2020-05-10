import java.util.LinkedList;

public class ListOfGamesGenerator {


   /**
	* generates all different games for the specified
	* parameters. Each game is recorded only once.
	* once a game is finished, it is not extended further
	* @param lines
    *  the number of lines in the game
    * @param columns
    *  the number of columns in the game
    * @param sizeWin
    *  the number of cells that must be aligned to win.
    * @return
    * a list of lists of game instances, ordered by levels
  	*/
	public static LinkedList<LinkedList<TicTacToeGame>> generateAllGames(int lines, int columns, int winLength){

		//YOUR CODE HERE
		LinkedList<LinkedList<TicTacToeGame>> games = new LinkedList<LinkedList<TicTacToeGame>>();
		boolean gamesThisLevel = true; // To know when to stop adding new levels
		int level = 0;
		games.add(new LinkedList<TicTacToeGame>());
		games.getFirst().add(new TicTacToeGame(lines, columns, winLength)); // Initial game on first level
		level++;
		while (gamesThisLevel) { // Generates a linked list at each level
			gamesThisLevel = false;
			games.add(new LinkedList<TicTacToeGame>()); // Add a new level (linked list)
			for (TicTacToeGame cur: games.get(level - 1)) { // Goes through each game board at the previous level
				if (cur.getGameState() != GameState.PLAYING) { // If the game is done, ignore it
					continue;
				}
				int j = 0;
				while (j < lines * columns) { // Play every possible next move
					if (cur.valueAt(j) == CellValue.EMPTY) {
						TicTacToeGame next = new TicTacToeGame(cur, j);
						boolean found = false; // Flag to find duplicate
						for (TicTacToeGame comp: games.getLast()) {
							if (comp.equals(next)) {
								found = true;
								break;
							}
						}
						if (!found) { // Add to list if game is not a duplicate
							games.getLast().add(next);
							if (next.getGameState() == GameState.PLAYING) { // To keep track of when to stop adding new levels
								gamesThisLevel = true;
							}
						}
					}
					j++;
				}
			}
			level++;
		}
		return games;
	}
}
