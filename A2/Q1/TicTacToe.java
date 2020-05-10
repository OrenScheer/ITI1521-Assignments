

public class TicTacToe{

   /**
     * <b>main</b> of the application. Creates the instance of  GameController
     * and starts the game. If two parameters line  and column
     * are passed, they are used.
     * Otherwise, a default value is used. Defaults values are also
     * used if the paramters are too small (less than 2).
     *
     * @param args
     *            command line parameters
     */
     public static void main(String[] args) {

        StudentInfo.display();

        TicTacToeGame game;
        int lines = 3;
        int columns = 3;
        int win = 3;


        try{
            if (args.length >= 2) {
                lines = Integer.parseInt(args[0]);
                if(lines<2){
                    System.out.println("Invalid argument, using default...");
                    lines = 3;
                }
                columns = Integer.parseInt(args[1]);
                if(columns<2){
                    System.out.println("Invalid argument, using default...");
                    columns = 3;
                }
            }
            if (args.length >= 3){
                win = Integer.parseInt(args[2]);
                if(win<2){
                    System.out.println("Invalid argument, using default...");
                    win = 3;
                }
            }
            if (args.length > 3){
                System.out.println("Too many arguments. Only the first 3 are used.");
            }

        } catch(NumberFormatException e){
            System.out.println("Invalid argument, using default...");
            lines   = 3;
            columns  = 3;
            win = 3;
        }

        Player[] players;

        // YOUR CODE HERE
        int first = Utils.generator.nextInt(2); // To determine whether the human or the computer plays first
        int gameNumber = 0; // Counts how many games have been played
        players = new Player[2];
        players[0] = new HumanPlayer();
        players[1] = new ComputerRandomPlayer();

        String anotherGame = "y";
        int move; // Figures out whose move it is based on who went first the first game and the game number
        while (anotherGame.toLowerCase().equals("y")) { // Keep playing while the user enters "y" (upper or lower case)
          game = new TicTacToeGame(lines, columns, win);
          if (gameNumber % 2 == first) { // For example, if first = 0 and gameNumber = 0, Player 1 goes first since they were chosen and it's the first game
            move = 0;
          }
          else { // Otherwise Player 2 goes first
            move = 1;
          }
          while (game.getGameState() == GameState.PLAYING) {
            if (game.getLevel() % 2 == move) { // For example, if move = 0 and level = 0, Player 1 should be going since it's the first move and Player 1 is going first
              System.out.println("Player 1's turn.");
              System.out.println(game);
              players[0].play(game); // players[0] is always the HumanPlayer
            }
            else { // Otherwise it's Player 2's move
              System.out.println("Player 2's turn.");
              players[1].play(game); // Computer plays silently
            }
          }

          System.out.println("Game over");
          System.out.println(game);
          System.out.println("Result: " + game.getGameState());
          System.out.print("Play again (Y)?:");
          anotherGame = Utils.console.readLine();
          gameNumber++;
        }
    }

}
