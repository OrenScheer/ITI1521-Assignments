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

   /**
	* The board of the game, stored as a single array.
	*/
	private CellValue[] board;


   /**
	* level records the number of rounds that have been
	* played so far. Starts at 0.
	*/
	private int level;

   /**
	* gameState records the current state of the game.
	*/
	private GameState gameState;


   /**
	* lines is the number of lines in the grid
	*/
	public final int lines;

   /**
	* columns is the number of columns in the grid
	*/
	public final int columns;


   /**
	* sizeWin is the number of cell of the same type
	* that must be aligned to win the game
	*/
	public final int sizeWin;

   /**
	* default constructor, for a game of 3x3, which must
	* align 3 cells
	*/
	public TicTacToeGame(){
		this(3,3,3);
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
		this(lines, columns, 3);
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
		this.lines = lines;
		this.columns = columns;
		this.sizeWin = sizeWin;
		board = new CellValue[lines*columns];
		for(int i = 0; i < lines*columns ; i ++) {
			board[i] = CellValue.EMPTY;
		}
		level = 0;
		gameState = GameState.PLAYING;
	}


   /**
	* constructor allowing to create a new game based
	* on an exisiting game, on which one move is added.
	* The resulting new instance is a deep copy of
	* the game reference passed as parameter.
   	* @param base
    *  the TicTacToeGame instance on which this new game
    *  is based
    * @param next
    *  the index of the next move.
  	*/

	public TicTacToeGame(TicTacToeGame base, int next){


		// YOUR CODE HERE
		level = base.getLevel();
		gameState = base.getGameState();
		lines = base.lines;
		columns = base.columns;
		sizeWin = base.sizeWin;
		board = new CellValue[lines*columns];
		for (int i = 0; i < lines*columns; i++) {
			board[i] = base.valueAt(i);
		}
		play(next);
	}


   /**
	* Compares this instance of the game with the
	* instance passed as parameter. Return true
	* if and only if the two instance represent
	* the same state of the game.
   	* @param other
    *  the TicTacToeGame instance to be compared with this one
		* @return
		*  true/false depending on whether the two games are identical
  	*/
	public boolean equals(TicTacToeGame other) {

		// YOUR CODE HERE
		if (other == null) {
			return false;
		}
		if (lines != other.lines || columns != other.columns || sizeWin != other.sizeWin || level != other.getLevel() || gameState != other.getGameState()) {
			return false;
		}
		else {
			for (int i = 0; i < lines*columns; i++) {
				if (board[i] != other.valueAt(i)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
   * Compares the board of the instance games
 	 * with another board.
	 * @param other
	 *  the array of CellValue to be compared with the instance board
	 * @return
	 * 	true/false depending on whether the instance board and the argument board are identical
	 */
	public boolean boardEquals (CellValue[] other) {
		if (other == null) {
			return false;
		}
		for (int i = 0; i < other.length; i++) {
			if (valueAt(i) != other[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Flips a nxn game board horizontally (along centre vertical line of symmetry)
	 * Works for any nxn game board, including a 3x3.
	 * @param boardArray
	 *  the array of CellValue (board) to be flipped
	 * @return
	 *  the same array flipped along the centre vertical axis
	 */
	private CellValue[] flipVertSym (CellValue[] boardArray) {
		int n = (int) Math.sqrt(boardArray.length);
		CellValue[] newArray = new CellValue[boardArray.length];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        newArray[((i + 1) * n) - j - 1] = boardArray[(i * n) + j];
      }
    }
		return newArray;
	}

	/**
	 * Rotates an nxn game board by 90 degrees clockwise by transposing and then flipping it.
	 * Although hard-coding the rotation for only 3x3 board resulted in a faster
	 * execution time, this algorithm would technically work for any square board.
	 * @param boardArray
	 *  the array of CellValue (board) to be rotated
	 * @return
	 *  the same array rotated 90 degrees clockwise
	 */
	private CellValue[] rotate (CellValue[] boardArray) {
		int n = (int) Math.sqrt(boardArray.length);
		CellValue[] newArray = new CellValue[boardArray.length];
		for (int i = 0; i < boardArray.length; i++) { // Transpose matrix
      newArray[(i % n) * n + (i / n)] = boardArray[i];
		}
		newArray = flipVertSym(newArray); // Then flip along vertical axis
		return newArray;
	}

	/**
	 * Checks if two Tic Tac Toe games are equivalent.
	 * Studying different cases revealed that there are 7 possible
   * boards equivalen to an 8th. Specifically, one way to generate these 8
	 * equivalent boards is by taking the original board, rotating it 90, 180,
	 * and 270 degrees clockwise, and then flipping each of the original
	 * and three rotated boards across any axis (horizontal, vertical, or diagonal).
	 * An efficient way to generate these seven other boards is by first rotating
	 * the board three times, flipping it, then rotating it another three times.
	 * After each time, the function checks whether the two boards are equal.
	 * @param other
	 *  an instance of TicTacToeGame to compare to the current instance
	 * @return
	 *  true/false depending on whether or not the game passed as an argument
	 *  is equivalent to the current game instance
	 */
	public boolean equivalent(TicTacToeGame other) {
		CellValue[] compBoard = new CellValue[other.lines * other.columns];
		for (int i = 0; i < other.lines * other.columns; i++) {
			compBoard[i] = other.valueAt(i); // Just using board to avoid overhead of creating another TicTacToeGame object
		}

		for (int i = 0; i < 3; i++) {
			compBoard = rotate(compBoard); // Rotate three times
			if (boardEquals(compBoard)) return true;
		}

		compBoard = flipVertSym(compBoard); // Then flip along vertical axis
		if (boardEquals(compBoard)) return true;

		for (int i = 0; i < 3; i++) {
			compBoard = rotate(compBoard); // Rotate another three times
			if (boardEquals(compBoard)) return true;
		}

		return false;
	}



   /**
	* getter for the variable level
	* @return
	* 	the value of level
	*/
	public int getLevel(){
		return level;
	}


   /**
	* getter for the variable gameState
	* @return
	* 	the value of gameState
	*/
	public GameState getGameState(){
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
		return (level%2 == 0) ? CellValue.X : CellValue.O;
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

		if(i < 0 || i >= lines*columns){
			throw new IllegalArgumentException("Illegal position: " + i);
		}

		return board[i];
	}

   /**
	* This method is call by the next player to play
	* at the cell  at index i.
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

		if(i < 0 || i >= lines*columns){
			throw new IllegalArgumentException("Illegal position: " + i);
		}
		if(board[i] != CellValue.EMPTY) {
			throw new IllegalArgumentException("CellValue not empty: " + i + " in game " + toString());
		}

		board[i] = nextCellValue();
		level++;
		if(gameState != GameState.PLAYING) {
			System.out.println("hum, extending a finished game... keeping original winner");
		} else {
			setGameState(i);
		}

	}


   /**
	* A helper method which updates the gameState variable
	* correctly after the cell at index i was just set.
	* The method assumes that prior to setting the cell
	* at index i, the gameState variable was correctly set.
	* it also assumes that it is only called if the game was
	* not already finished when the cell at index i was played
	* (the the game was playing). Therefore, it only needs to
	* check if playing at index i has concluded the game
	*
   	* @param i
    *  the index of the cell in the array board that has just
    * been set
  	*/


	private void setGameState(int index){

		int left = Math.min(sizeWin-1,index%columns);
		int right= Math.min(sizeWin-1,columns - (index%columns +1));
		if( (countConsecutive(index-1, left,-1,board[index]) +
		   	 countConsecutive(index+1, right,1,board[index]))
			>= sizeWin-1 ) {
			setGameState(board[index]);
			return;
		}



		int up 	= Math.min(sizeWin-1,index/columns);
		int down= Math.min(sizeWin-1, lines - (index/columns +1));
		if( (countConsecutive(index-columns, up,-columns,board[index]) +
		   	 countConsecutive(index+columns, down,columns,board[index]))
			>= sizeWin-1 ) {
			setGameState(board[index]);
			return;
		}

		int upLeft = Math.min(up, left);
		int downRight= Math.min(down, right);
		if( (countConsecutive(index-(columns+1), upLeft,-(columns+1),board[index]) +
		   	 countConsecutive(index+(columns+1), downRight,columns+1,board[index]))
			>= sizeWin-1 ) {
			setGameState(board[index]);
			return;
		}

		int upRight= Math.min(up, right);
		int downLeft = Math.min(down, left);
		if( (countConsecutive(index-(columns-1), upRight,-(columns-1),board[index]) +
		   	 countConsecutive(index+(columns-1), downLeft,columns-1,board[index]))
			>= sizeWin-1 ) {
			setGameState(board[index]);
			return;
		}


		if (level == lines*columns) {
			gameState = GameState.DRAW;
		} else {
			gameState = GameState.PLAYING;
		}

	}


	private int countConsecutive(int startingPosition, int numberOfSteps,
		int stepGap, CellValue value){

		int result= 0;
		for(int i = 0; i < numberOfSteps;i++){
			if(board[startingPosition + i*stepGap] != value)
				break;
			result++;
		}
		return result;

	}


	private void setGameState(CellValue value){
		switch(value){
			case X:
				gameState = GameState.XWIN;
				break;
			case O:
				gameState = GameState.OWIN;
				break;
			default:
				throw new IllegalArgumentException("cannot set Game State to value " + value);
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
		String res = "";
		for(int i = 0; i < lines ; i++){
			if(i>0) {
				for(int j = 0; j < 4*columns - 1; j++){
					res+="-";
				}
				res+= Utils.NEW_LINE;
			}
			for(int j = 0; j < columns ; j++){
				switch(board[i*columns + j]){
					case X:
						res+= " X ";
						break;
					case O:
						res+= " O ";
						break;
					default:
						res+=  "   ";
				}
				if(j<columns - 1){
					res += "|";
				} else{
					res += Utils.NEW_LINE;
				}
			}
		}
		return res ;

	}

}
