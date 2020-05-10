/**
 * The class <b>TicTacToeGame</b> is the
 * class that implements the Tic Tac Toe Game.
 * It contains the grid and tracks its progress.
 * It automatically maintain the current state of
 * the game as players are making moves.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class TicTacToeGame {

// FINISH THE VARIABLE DECLARATION
   /**
	* The board of the game, stored as a one dimension array.
	*/
	CellValue[] board;


   /**
	* level records the number of rounds that have been
	* played so far.
	*/
	int level;

   /**
	* gameState records the current state of the game.
	*/
	GameState gameState;


   /**
	* lines is the number of lines in the grid
	*/
	int lines;

   /**
	* columns is the number of columns in the grid
	*/
	int columns;


   /**
	* sizeWin is the number of cell of the same type
	* that must be aligned to win the game
	*/
	int sizeWin;


   /**
	* default constructor, for a game of 3x3, which must
	* align 3 cells
	*/
	public TicTacToeGame(){

		// YOUR CODE HERE
		board = new CellValue[9];
		for (int i = 0; i < 9; i++) {
			board[i] = CellValue.EMPTY; // Initialize cell values to empty
		}
		lines = 3;
		columns = 3;
		sizeWin = 3;
		gameState = GameState.PLAYING; // Intialize game state to playing
		level = 0;

	}

   /**
	* constructor allowing to specify the number of lines
	* and the number of columns for the game. 3 cells must
	* be aligned.
   	* @param lines
    *  the number of lines in the game
    * @param columns
    *  the number of columns in the game
  	*/
	public TicTacToeGame(int lines, int columns){

		// YOUR CODE HERE
		board = new CellValue[lines * columns];
		for (int i = 0; i < lines * columns; i++) {
			board[i] = CellValue.EMPTY;
		}
		this.lines = lines;
		this.columns = columns;
		sizeWin = 3; // Default win size
		gameState = GameState.PLAYING;
		level = 0;

	}

   /**
	* constructor allowing to specify the number of lines
	* and the number of columns for the game, as well as
	* the number of cells that must be aligned to win.
   	* @param lines
    *  the number of lines in the game
    * @param columns
    *  the number of columns in the game
    * @param sizeWin
    *  the number of cells that must be aligned to win.
  	*/
	public TicTacToeGame(int lines, int columns, int sizeWin){

		// YOUR CODE HERE
		board = new CellValue[lines * columns];
		for (int i = 0; i < lines * columns; i++) {
			board[i] = CellValue.EMPTY;
		}
		this.lines = lines;
		this.columns = columns;
		this.sizeWin = sizeWin;
		gameState = GameState.PLAYING;
		level = 0;

	}



   /**
	* getter for the variable lines
	* @return
	* 	the value of lines
	*/
	public int getLines(){

		// YOUR CODE HERE
		return lines;
	}

   /**
	* getter for the variable columns
	* @return
	* 	the value of columns
	*/
	public int getColumns(){

		// YOUR CODE HERE
		return columns;
	}

   /**
	* getter for the variable level
	* @return
	* 	the value of level
	*/
	public int getLevel(){

		// YOUR CODE HERE
		return level;
	}

  	/**
	* getter for the variable sizeWin
	* @return
	* 	the value of sizeWin
	*/
	public int getSizeWin(){

		// YOUR CODE HERE
		return sizeWin;
	}

   /**
	* getter for the variable gameState
	* @return
	* 	the value of gameState
	*/
	public GameState getGameState(){

		// YOUR CODE HERE
		return gameState;
	}

   /**
	* returns the cellValue that is expected next,
	* in other word, which played (X or O) should
	* play next.
	* This method does not modify the state of the
	* game.
	* @return
    *  the value of the enum CellValue corresponding
    * to the next expected value.
  	*/
	public CellValue nextCellValue(){

		// YOUR CODE HERE
		if (level % 2 == 0) {
			return CellValue.X;
		}
		else {
			return CellValue.O;
		}
	}

   /**
	* returns the value  of the cell at
	* index i.
	* If the index is invalid, an error message is
	* printed out. The behaviour is then unspecified
   	* @param i
    *  the index of the cell in the array board
    * @return
    *  the value at index i in the variable board.
  	*/
	public CellValue valueAt(int i) {

		// YOUR CODE HERE
		if (i >= lines * columns || i < 0) {
			System.out.println("The value should be between 1 and " + (lines * columns));
			return CellValue.EMPTY;
		}
		else {
			return board[i];
		}
	}

   /**
	* This method is called when the next move has been
	* decided by the next player. It receives the index
	* of the cell to play as parameter.
	* If the index is invalid, an error message is
	* printed out. The behaviour is then unspecified
	* If the chosen cell is not empty, an error message is
	* printed out. The behaviour is then unspecified
	* If the move is valide, the board is updated, as well
	* as the state of the game.
	* To faciliate testing, is is acceptable to keep playing
	* after a game is already won. If that is the case, the
	* a message should be printed out and the move recorded.
	* the  winner of the game is the player who won first
   	* @param i
    *  the index of the cell in the array board that has been
    * selected by the next player
  	*/
	public void play(int i) {

		// YOUR CODE HERE
		if (i >= lines * columns || i < 0) {
			System.out.println("The value should be between 1 and " + (lines * columns));
		}
		else if (board[i] != CellValue.EMPTY) {
			System.out.println("This cell has already been played");
		}
		else {
			board[i] = nextCellValue();
			level++;
			if (gameState == GameState.PLAYING) {
				setGameState(i);
			}
			else {
				System.out.println("The game is already over.");
			}
		}
	}

	/**
	* An additional helper method for setGameState that is only called if
	* a player has one. It returns a GameState based on the
	* CellValue passed to the function.
	*
		* @param target the value CellValue of the cell just played
		* @return the win value associated with this CellValue
	*/
	private GameState setWinState(CellValue target) {
		if (target == CellValue.X) {
			return GameState.XWIN;
		}
		else if (target == CellValue.O) {
			return GameState.OWIN;
		}
		else {
			return GameState.PLAYING;
		}
	}

	/**
 * A helper method which updates the gameState variable
 * correctly after the cell at index i was just set in
 * the method play(int i)
 * The method assumes that prior to setting the cell
 * at index i, the gameState variable was correctly set.
 * it also assumes that it is only called if the game was
 * not already finished when the cell at index i was played
 * (i.e. the game was playing). Therefore, it only needs to
 * check if playing at index i has concluded the game, and if
 * set the oucome correctly
 *
	 * @param i
	 *  the index of the cell in the array board that has just
	 * been set
	 */
	private void setGameState(int i){

		// YOUR CODE HERE
		int align = 1; // Number of cells of right type currently aligned
		int j; // Used to traverse current column/row/diagonal
		int curRow; // Used during diagonal checking
		CellValue target = board[i]; // Comparing cells in line to match target

		// Check the row
		j = i - 1;
		while (j >= i - (i % columns)) { // left of cell
			if (board[j] == target) {
				align++;
			}
			else {
				break; // Cell does not match target
			}
			j--;
		}
		j = i + 1;
		while (j < i + (columns - (i % columns))) { // Right of cell
			if (board[j] == target) {
				align++;
			}
			else {
				break;
			}
			j++;
		}

		if (align >= sizeWin) {
			gameState = setWinState(target); // Set the gameState to the win state associated with target
			return; // This function can end execution
		}
		else {
			align = 1; // Otherwise, reset align to check next possible alignment
		}

		// Check the column
		j = i - columns;
		while (j >= 0) { // Above cell
			if (board[j] == target) {
				align++;
			}
			else {
				break;
			}
			j = j - columns;
		}
		j = i + columns;
		while (j < board.length) { // Below cell
			if (board[j] == target) {
				align++;
			}
			else {
				break;
			}
			j = j + columns;
		}
		if (align >= sizeWin) {
			gameState = setWinState(target);
			return;
		}
		else {
			align = 1;
		}

		// Check the main diagonal
		curRow = i / columns; // Keep track of the current row
		j = i - columns - 1;
		while (j >= 0) { // Up and to the left
			if (j / columns != curRow - 1) {
				break; // Avoid situations in which the above if "spills over" onto the wrong line
			}
			if (board[j] == target) {
				align++;
			}
			else {
				break;
			}
			curRow = j / columns;
			j = j - columns - 1;
		}
		curRow = i / columns;
		j = i + columns + 1;
		while (j < board.length) { // Down and to the right
			if (j / columns != curRow + 1) {
				break;
			}
			if (board[j] == target) {
				align++;
			}
			else {
				break;
			}
			curRow = j / columns;
			j = j + columns + 1;
		}
		if (align >= sizeWin) {
			gameState = setWinState(target);
			return;
		}
		else {
			align = 1;
		}

		// Check the antidiagonal
		curRow = i / columns;
		j = i - columns + 1;
		while (j > 0) {  // Up and to the right
			if (j / columns != curRow - 1) {
				break;
			}
			if (board[j] == target) {
				align++;
			}
			else {
				break;
			}
			curRow = j / columns;
			j = j - columns + 1;
		}
		curRow = i / columns;
		j = i + columns - 1;
		while (j < board.length) { // Down and to the left
			if (j / columns != curRow + 1) {
				break;
			}
			if (board[j] == target) {
				align++;
			}
			else {
				break;
			}
			curRow = j / columns;
			j = j + columns - 1;
		}
		if (align >= sizeWin) {
			gameState = setWinState(target);
			return;
		}

		// Full
		j = 0;
		while (j < board.length && board[j] != CellValue.EMPTY) {
			j++;
		}
		if (j == board.length) { // If j has reached the end of the board, the board is full
			gameState = GameState.DRAW;
		}
	}



   /**
	* Returns a String representation of the game matching
	* the example provided in the assignment's description
	*
   	* @return
    *  String representation of the game
  	*/

	public String toString(){
		String lineBreak = System.getProperty("line.separator");
		for (int i = 0; i < columns - 1; i++) {
			lineBreak = lineBreak + "----";
		}
		lineBreak = lineBreak + "---" + System.getProperty("line.separator"); // To be inserted after each line of cells

		String s = new String(""); // String to be returned
		for (int i = 0; i < board.length; i++) { //
			if (i % columns == columns - 1) { // Last cell in a row
				if (board[i] == CellValue.X) {
					s = s + " X ";
				}
				else if (board[i] == CellValue.O) {
					s = s + " O ";
				}
				else {
					s = s + "  ";
				}
				if (i != board.length - 1) { // If i isn't the last cell in the board, add a line break at the end of the line
					s = s + lineBreak;
				}
			}
			else { // First or a middle cell in a row
				if (board[i] == CellValue.X) {
					s = s + " X |";
				}
				else if (board[i] == CellValue.O) {
					s = s + " O |";
				}
				else {
					s = s + "   |";
				}
			}
		}
		return s;
	}

}
