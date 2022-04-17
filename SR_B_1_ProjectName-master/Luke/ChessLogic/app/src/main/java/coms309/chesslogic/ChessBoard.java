package coms309.chesslogic;

/**
 * Created by Luke on 9/19/2017.
 * This class holds the data structure for a chess board in the game.
 */
public class ChessBoard {

    private ChessSquare[][] board;

    /**
     * Creates a new ChessBoard object with the given number of rows and
     * columns. The standard for classic chess is 8 rows and 8 columns.
     * @param numRows - the number of rows
     * @param numCols - the number of columns
     */
    public ChessBoard(int numRows, int numCols) {
        board = new ChessSquare[numCols][numRows];
        Color c;
        int letterNumber;
        int rowNumber;

        // iterate over all the ChessSquares in the board and initialize them
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                // 97 is the ascii code for 'a'
                letterNumber = 97 + i;
                // the bottom row has the number 1, the top is 8 (or numRows - j for a non-standard board)
                rowNumber = numRows - j;
                // if the row and col are both even or both odd, the square is black, otherwise the square is white
                if ((letterNumber % 2 == 0 && letterNumber % 2 == 0) || (letterNumber % 2 != 0 && letterNumber % 2 != 0)) {
                    c = Color.BLACK;
                }
                else {
                    c = Color.WHITE;
                }
                // the name will be akin to 'a1' or 'g3', etc.
                board[i][j] = new ChessSquare(this, ("" + (char)letterNumber + rowNumber), c, i, j);
            }
        }
    }

    /**
     * Returns the double array of ChessSquares that make up the board.
     * @return - the double array of ChessSquares called board
     */
    public ChessSquare[][] getBoard() {
        return board;
    }

    /**
     * Returns the total number of columns on the board
     * @return - the number of columns
     */
    public int getNumCols() {
        return board.length;
    }

    /**
     * Returns the total number of rows on the board
     * @return - the number of rows
     */
    public int getNumRows() {
        return board[0].length;
    }
}
