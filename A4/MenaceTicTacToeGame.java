public class MenaceTicTacToeGame extends TicTacToeGame {

	private int[] beads; // Array to keep track of how many beads are available in each cell
	private int numBeads; // Total number of beads
	private int cellPlayed; // Used to keep track of which cell was chosen to be played for use by ComputerMenacePlayer to redistribute beads

	/**
	* General constructor.
	*/
	public MenaceTicTacToeGame() {
		super(3, 3, 3);
		numBeads = 0;
		beads = new int[9];
		for (int i = 0; i < 9; i++) {
			beads[i] = 0;
		}
	}

	/**
	* Constructor to bulid off a base game.
	* @param base
	* 	game to base this object off of
	* @param next
	* 	position to play on this game
	*/
	public MenaceTicTacToeGame(MenaceTicTacToeGame base, int next) {
		super(base, next);
		numBeads = 0;
		beads = new int[9];
		for (int i = 0; i < 9; i++) {
			beads[i] = 0;
		}
	}

	/**
	* @return
	*  	the total number of beads associated with this game ("in this matchbox").
	*/
	public int getNumBeads() {
		return numBeads;
	}

	/**
	* @return
	*	 	the cell of this game which was selected to play.
	*/
	public int getCellPlayed() {
		return cellPlayed;
	}

	/**
	* Adds the specified number of beads at the specified index.
	* @param index
	* 	index at which to add the beads
	* @param num
	* 	number of beads to add at the specified index
	*/
	public void addBeadsAt(int index, int num) {
		beads[index] += num;
		numBeads += num;
	}

	/**
	* Removes the specified number of beads at the specified index. Calling function must make sure there are not going to be 0 beads in the game after removal.
	* @param index
	* 	index at which to remove the beads
	* @param num
	* 	number of beads to remove at the specified index
	*/
	public void removeBeadsAt(int index, int num) {
		beads[index] -= num;
		numBeads -= num;
	}


	/**
	* Choose a cell on this game to play, based on the distribution of beads.
	* @return
	* 	the chosen index to play
	*/
	public int chooseMove() {
		if(getGameState() != GameState.PLAYING){
			throw new IllegalStateException("Game already finished");
		}
		if (numBeads == 0) { // This shouldn't happen since ComputerMenacePlayer is never going to remove all the beads from a game
			throw new IllegalStateException("There are no beads.");
		}
		int pos = Utils.generator.nextInt(numBeads); // Generate a random number between 0 and numBeads-1 (inclusive)
		int i = 0;
		while (i < 9) { // Simulating weighted odds
			if (beads[transformedBoard[i]] == 0) { // Looking at transformedBoard to account for the possibility of this game being a symmetry to the one being played on
				i++; // Selecting a cell with no beads will not happen
			}
			else {
				pos -= beads[transformedBoard[i]]; // Remove the beads associated with this cell from the random number
				if (pos < 0) { // If the random number is negative (has been "fully used"), this is the selection
					cellPlayed = transformedBoard[i]; // Assign cellPLayed to the transformed board since this is the index from which we will be adding or removing beads after the game is over
					break;
				}
				i++;
			}
		}
		//System.out.println(toStringBeads());
		return i; // For the game actually being played on, though, we don't look at transformedBoard
	}

	/**
	* Debugging function that prints the given board with the number of beads at each cell.
	* @return
	* 	a string showing the number of beads at each cell of this board
	*/
	public String toStringBeads() {
		if(beads == null) {
			throw new NullPointerException("beads not initialized");
		}

		String res = "";
		for(int i = 0; i < lines ; i++){
			if(i>0) {
				for(int j = 0; j < 4*columns - 1; j++){
					res+="-";
				}
				res+= Utils.NEW_LINE;
			}
			for(int j = 0; j < columns ; j++){
				res+=  " " + beads[transformedBoard[i * 3 + j]] + " ";
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
