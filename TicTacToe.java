
import java.io.Console;
import java.util.Scanner;

public class TicTacToe{

   /**
     * <b>main</b> of the application. Creates the instance of  TicTacToeGame
     * and starts the game. If two parameters lines  and columns
     * are passed, they are used. If the paramters lines, columns
     * and win are passed, they are used.
     * Otherwise, a default value is used. Defaults values (3) are also
     * used if the paramters are too small (less than 2).
     * Here, we assume that the command lines arguments are indeed integers
     *
     * @param args
     *            command lines parameters
     */
     public static void main(String[] args) {

        StudentInfo.display();

        Console console = System.console();
        TicTacToeGame game;
        int lines, columns, win;
        lines = 3;
        columns = 3;
        win = 3;

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

        game = new TicTacToeGame(lines, columns,win);


        // YOUR CODE HERE
        CellValue nextTurn; // Next value to play
        int pos; // Cell to play at
        Scanner sc = new Scanner(System.in);
        while (game.getGameState() == GameState.PLAYING) {
          nextTurn = game.nextCellValue();
          System.out.println(game); // Print game board before each turn (calls toString method for TicTacToeGame)
          if (nextTurn == CellValue.X) {
            System.out.print("X to play: ");
          }
          else if (nextTurn == CellValue.O){
            System.out.print("O to play: ");
          }
          pos = sc.nextInt();
          game.play(pos - 1); // Index  in array is position - 1
        }
        System.out.println(game); // Print the board one last time when the game's over
        switch(game.getGameState()) { // At the end of each game, prints state
          case XWIN:
          System.out.println("Result: XWIN");
          break;

          case OWIN:
          System.out.println("Result: OWIN");
          break;

          case DRAW:
          System.out.println("Result: DRAW");
          break;
        }

    }

}
