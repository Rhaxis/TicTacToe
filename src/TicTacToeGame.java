import java.util.*;
/**
 * TicTacToe game with 2d array done as a project work for introduction to programming course.
 * 
 * 
 * Requirements stated that the user needs to be able to adjust gameareas height and width.
 * Gamearea needs to be atleast 3x3 and if its higher than 5x5 then 5 in a row to win is minimum.
 * Game ends when either player or computer wins. It can also end in a draw if the gamefield is full.
 * Computer inputs marks randomly to an empty array spot.
 * Player is asked for empty row and column. Game does victory checks after every move from the point where player/computer last inputted a mark.
 * 
 * 
 * @author Ville Ekholm
 */
class TicTacToe extends WinnerCheck {

    private String [][] gameField;
    private static int rows;
    private static int columns;
    private static int howManyInRowToWin;
    private static boolean winnerFound = false;
    private static final String empty = "[ ]";
    private static final String cross = "[X]";
    private static final String zero = "[0]";
    private int turnsInGame;
    private int maxInRowToWin;

    /**
     * Consctuctor for TicTacToe object
     * 
     * 
     * Constructor does methods that set the premises of the game area.
     * Including array creation and user input for array size
     * Sets how many marks in a row is required to win
     * Formats the array full of empty string
     * Prints the empty field
     * 
     */
    public TicTacToe() {
        gameIntro();
        setGameAreaSize();
        setHowManyInRowToWin();
        setTurnsInGame();
        emptyGameArea(gameField);
        printGameArea(gameField);
    } 

    /**
     * Asks the player for amount of rows and columns for the gamearea and checks the input for errors.
     * Formats the 2d array used for the game
     * Sets max marks in a row to the lowest number of columns or rows.
     */
    public void setGameAreaSize() {
        System.out.println("How many rows do you want for the tictactoe field");
        rows = Utility.readIntFromRange(3, 20, "please enter a number", "please enter a number between 3 and 20");

        System.out.println("How many columns do you want for the tictactoe field");
        columns = Utility.readIntFromRange(3, 20, "please enter a number", "please enter a number between 3 and 20");

        gameField = new String[rows][columns];

        if (rows < columns) {
            maxInRowToWin = rows;
        } else if (columns <= rows) {
            maxInRowToWin = columns;
        }
    }

    /**
     * Asks the player how many in a row is needed for win. Checks the input for errors and has a required minimum and maximum.
     */
    public void setHowManyInRowToWin() {
        if(rows >= 10 && columns >= 10) {
            System.out.println("How many in a row to win?");
            howManyInRowToWin = Utility.readIntFromRange(5, maxInRowToWin, "please enter a number", "please enter a number between 5 and " + maxInRowToWin);
            
        } else {
            System.out.println("How many in a row to win?");
            howManyInRowToWin = Utility.readIntFromRange(3, maxInRowToWin, "please enter a number", "please enter a number between 3 and " + maxInRowToWin);
        }
    }

    /**
     * Setter for amount of turns in the game.
     */
    public void setTurnsInGame() {
        turnsInGame = getColumns() * getRows();
    }

    /**
     * Takes the gamefield array as an argument and inputs it full of empty strings
     * @param gameField 2d array where the game is played.
     */
    public void emptyGameArea(String [][] gameField) {
        for(int i = 0; i< gameField.length; i++) {
            for(int j = 0; j<gameField[i].length; j++) {
                gameField[i][j] = empty;
            }
        }
    }

    /**
     * Prints the gamearea in its current state.
     * @param gameField 2d array where the game is played.
     */
    public void printGameArea(String [][] gameField) {
        for(int i = 0; i< gameField.length; i++) {
            for(int j = 0; j<gameField[i].length; j++) {
                System.out.print(gameField[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Handles player and computer movement and win declaration. Goes on until either turns run out and the field is full or a winner is found.
     */
    public void playGame() {
        for(int i = turnsInGame; i > 0 ; i--) {
            if(!winnerFound) {
                playerMove();
                if(winnerFound) {
                    System.out.println(howManyInRowToWin + " in a row! player wins!");
                }
            }
            if(!winnerFound && i > 1) {
                computerMove();
                i--;
                if(winnerFound) {
                    System.out.println(howManyInRowToWin + " in a row! Computer wins!");
                }
            }
        }
        if(!winnerFound) {
            System.out.println("Gamefield is full. Game is a draw!");
        }
    }

    /**
     * Gets input from the player for X positioning. Handles errors and requires an empty spot.
     */
    public void playerMove() {
        boolean merkkiAsetettu = false;
        while(!merkkiAsetettu) {
            
            System.out.println("Input row");
            int i = Utility.readIntFromRange(1, rows, "please enter a number", "please enter a number between 1 and " + rows);

            System.out.println("Input column");
            int j = Utility.readIntFromRange(1, columns, "please enter a number", "please enter a number between 1 and " + columns);
                
            if(gameField[i-1][j-1] == empty) {
                gameField[i-1][j-1] = cross;
                printGameArea(gameField);
                merkkiAsetettu = true;
                isWinner(gameField, i-1, j-1);
            } else {
                System.out.println("Paikka ei ollut tyhjä, yritä uudestaan!");
            } 
        }    
    }
    /**
     * Computer move is randomly generated but requires an empty spot.
     */
    public void computerMove() {
        boolean merkkiAsetettu = false;
        while(!merkkiAsetettu) {
           
            int i = Utility.getRandom(0, rows -1);
            int j = Utility.getRandom(0, columns -1);
            
            if(gameField[i][j] == empty) {
                gameField[i][j] = zero;
                printGameArea(gameField);
                merkkiAsetettu = true;
                isWinner(gameField, i, j);
            } else {
                merkkiAsetettu = false;
            }
        }
    }
    /**
     * Prints intro with information about the game and game area limits.
     */
    public void gameIntro() {
        System.out.println("Welcome to 2d TicTacToe! You can adjust the size of the game area and the marks in a row required for victory.");
        System.out.println("Please note that a game area of 3x3 is the bare minimum and on a 5x5 or larger area 5 marks in a row is minimum");
        System.out.println("After your input computer will play against you by placing a marker in a random spot");
        System.out.println();
    }

    /**
     * Getter for amount of rows in the gamearea
     * @return returns int value of rows in the gamearea
     */
    public static int getRows() {
        return rows;
    }

    /**
     * Getter for amount of columns in the gamearea
     * @return returns int value of columns in the gamearea
     */
    public static int getColumns() {
        return columns;
    }

    /**
     * Getter for how many marks in a row is needed for win.
     * @return returns int value of how many marks in a row is needed for win.
     */
    public static int getHowManyInRowToWin() {
        return howManyInRowToWin;
    }

    /**
     * Setter for winner found boolean.
     */
    public static void setWinnerFound() {
        winnerFound = true;
    }

     /**
     * Getter for winner found boolean
     * @return returns boolean value of winner found variable.
     */
    public static boolean getWinnerFound() {
        return winnerFound;
    }
}

/**
 * Class for checking if the recent move has won the game.
 * 
 * Class has methods to check rows, columns and diagonals for victory. It takes the last input from either player or computer and checks around it.
 * 
 * @author Ville Ekholm
 */
class WinnerCheck {
    
    private int counter = 0;
    private int row;
    private int column;
    private String player;
    private boolean changeCheckDirection = false;
    private int howManyInRowToWin;
    private int numberOfRows;
    private int numberOfColumns;
    
    /**
     * Takes the row and column coordinates of the last move made
     * and checks to see if that move was a winning one.
     * 
     * @param gameField 2d array where the game is played.
     * @param r Row coordinate for the last move.
     * @param c Rolumn coordinate for the last move.
     */
    public void isWinner(String [][]gameField, int r, int c) {
        howManyInRowToWin = TicTacToe.getHowManyInRowToWin();
        numberOfRows = TicTacToe.getRows();
        numberOfColumns = TicTacToe.getColumns();
        row = r;
        column = c;
        player = gameField[row][column];
        if(!TicTacToe.getWinnerFound()) {
            checkRows(gameField, row);
        }
        if(!TicTacToe.getWinnerFound()) {
            checkColumns(gameField, column);
        }
        if(!TicTacToe.getWinnerFound()) {
            checkDiagonalLeftToRight(gameField, row, column);
        }
        if(!TicTacToe.getWinnerFound()) {
            checkDiagonalRightToLeft(gameField, row, column);
        }    
    }
    /**
     * Checks the row around the last placed mark for victory
     * @param gameField 2d array where the game is played.
     * @param row Row coordinate for the last move.
     */
    public void checkRows(String [][]gameField, int row) {
        for(int i = 0; i < gameField[row].length; i++) {
            if(gameField[row][i].equals(player)) {
                counter ++;
                if(counter == howManyInRowToWin) {
                    TicTacToe.setWinnerFound();
                    break;
                }
            } else {
                setCounter(0);
            }
        }
        setCounter(0);
    }
    /**
     * Checks the column around the last placed mark for victory
     * @param gameField 2d array where the game is played.
     * @param column Column coordinate for the last move.
     */
    public void checkColumns(String [][]gameField, int column) {
        // Check columns
        for(int i = 0; i < gameField.length; i++) {
            if(gameField[i][column].equals(player)) {
                counter ++;
                if(counter == howManyInRowToWin) {
                    TicTacToe.setWinnerFound();
                    break;
                }
            } else {
                setCounter(0);
            }
        }
        setCounter(0);
    }

    /**
     * Diagonal win check from left to right around the last move. First it moves towards top left and counts how many in a row. 
     * After either edge of array or non current moved string is found it returns to the original point and changes direction.
     * @param gameField 2d array where the game is played.
     * @param row Row coordinate for the last move.
     * @param column Column coordinate for the last move.
     */
    public void checkDiagonalLeftToRight(String [][]gameField, int row, int column) {

        int tempRow = row;
        int tempColumn = column;

        // Diagonal win check from player input towards top left
         while(!changeCheckDirection && gameField[tempRow][tempColumn].equals(player) ) {
            counter ++;
            if(counter == howManyInRowToWin) {
                    TicTacToe.setWinnerFound();
                    break;
            }    
            if(tempRow > 0 && tempColumn > 0) {
                if(gameField[tempRow-1][tempColumn-1].equals(player)) {
                tempRow --;
                tempColumn --;
                } else {
                    tempRow = row;
                    tempColumn = column;
                    counter --;
                    changeCheckDirection = true;
                }
    
            } else {
                tempRow = row;
                tempColumn = column;
                counter --;
                changeCheckDirection = true;
            }
        }      
        // Diagonal win check from player input towards bottom right 
        while(changeCheckDirection && gameField[tempRow][tempColumn].equals(player) ) {
            counter ++;
            if(counter == howManyInRowToWin) {
                TicTacToe.setWinnerFound();
                break;
            }
            if(tempRow < numberOfRows -1 && tempColumn < numberOfColumns -1) {
                if(gameField[tempRow+1][tempColumn+1].equals(player)) {
                    tempRow ++;
                    tempColumn ++;
                } else {
                    setCounter(0);
                    changeCheckDirection = false;
                }  
            } else {
                setCounter(0);
                changeCheckDirection = false;
            }
        }
        setCounter(0);
    }

    /**
     * Diagonal win check from right to left around the last move. First it moves towards top right and counts how many in a row. 
     * After either edge of array or non current moved string is found it returns to the original point and changes direction.
     * @param gameField 2d array where the game is played.
     * @param row Row coordinate for the last move.
     * @param column Column coordinate for the last move.
     */
    public void checkDiagonalRightToLeft(String [][]gameField, int row, int column) {

        int tempRow = row;
        int tempColumn = column;

        // Diagonal win check from player input towards top right
        while(!changeCheckDirection && gameField[tempRow][tempColumn].equals(player) ) {
            counter ++;
            if(counter == howManyInRowToWin) {
                    TicTacToe.setWinnerFound();
                    break;
            }    
            if(tempRow > 0 && tempColumn < numberOfColumns -1) {
                if(gameField[tempRow-1][tempColumn+1].equals(player)) {
                tempRow --;
                tempColumn ++;
                } else {
                    tempRow = row;
                    tempColumn = column;
                    counter --;
                    changeCheckDirection = true;
                }
            } else {
                tempRow = row;
                tempColumn = column;
                counter --;
                changeCheckDirection = true;
            }
        }
        // Diagonal win check from player input towards bottom left 
        while(changeCheckDirection && gameField[tempRow][tempColumn].equals(player) ) {
            counter ++;
            if(counter == howManyInRowToWin) {
                TicTacToe.setWinnerFound();
                break;
            }
            if(tempRow < numberOfRows -1 && tempColumn > 0) {
                if(gameField[tempRow+1][tempColumn-1].equals(player)) {
                    tempRow ++;
                    tempColumn --;
                } else {
                    setCounter(0);
                    changeCheckDirection = false;
                }  
            } else {
                setCounter(0);
                changeCheckDirection = false;
            }
        }
        setCounter(0);  
    }
    public void setCounter(int value) {
        counter = value;
    }
}

/**
 * Contains tools to reading user inputs and giving out error messages and a math random generator.
 * 
 * @author Ville Ekholm
 */
class Utility {    

     /**
     * Reads user input and converts it to INT. Handles error checking and has custom error message possibilities
     * in arguments.
     * 
     * @param min Minimum value.  Checks that the input is within min max range.
     * @param max Maximum value. Checks that the input is within min max range.
     * @param errorMessageNonNumeric Error message if the given input is not a number.
     * @param errorMessageNonMinAndMax Error message if the given number is outside the min max range.
     * @return Returns the given number as INT
     */
    public static int readIntFromRange(int min, int max, String errorMessageNonNumeric, String errorMessageNonMinAndMax) {
        Scanner input = new Scanner(System.in);
        int userInt = 0;

        boolean correctInput = false;

        while (!correctInput) {
            try {
                userInt = Integer.parseInt(input.nextLine());

                if (userInt < min || userInt > max) {
                    System.out.println(errorMessageNonMinAndMax);
                } else {
                    correctInput = true;
                }
            } catch(NumberFormatException e) {
                System.out.println(errorMessageNonNumeric);
            }
        }
        return userInt;
    }
    /**
     * Returns a random value between two int values.
     * 
     * Random value is determined with int min and int max
     * returns random value between the two numbers.
     * 
     * @param min minimum value for the random
     * @param max maximum value for the random
     * @return returns the random value
     */
    public static int getRandom(int min, int max) {
        return min + (int) (java.lang.Math.random() * ((max - min) + 1));
    }
}

/**
 * Game's main class which creates a new object for the game and plays it.
 * 
 * @author Ville Ekholm
 */
public class TicTacToeGame {
    public static void main (String[] args) {
        
        TicTacToe gameField = new TicTacToe();
        gameField.playGame();
    }
}

