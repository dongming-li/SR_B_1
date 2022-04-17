package coms309.gridviewtest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Luke on 9/19/2017.
 * This class holds the data structure for a chess board in the game.
 */
public class ChessBoard {

    // used to store info for the GUI
    private ChessPiece currentlySelected;
    private ArrayList<ChessSquare> movable;
    // holds info on whose turn it is
    private Boolean evenTurn;
    // the board itself
    private ChessSquare[][] board;
    // holds the King of the white player
    private ChessKing whiteKing;
    // holds the King of the black player
    private ChessKing blackKing;
    // holds the pieces of Player 1
    private ArrayList<ChessPiece> whitePieces;
    // holds the pieces of Player 2
    private ArrayList<ChessPiece> blackPieces;

    /**
     * Returns whether it is the given player's turn
     * @param p - the player whose turn it is to check
     * @return - whether or not it is the given player's turn
     */
    public boolean getPlayersTurn(Player p) {
        if (p == Player.PLAYER_1) {
            return evenTurn;
        } else {
            return !evenTurn;
        }
    }

    /**
     * Returns the board's list of pieces belonging to player 2
     * @return  The list of pieces
     */
    public ArrayList<ChessPiece> getBlackPieces() {
        return blackPieces;
    }

    /**
     * Returns the board's list of pieces belonging to player 1
     * @return  The list of pieces
     */
    public ArrayList<ChessPiece> getWhitePieces() {
        return whitePieces;
    }

    /**
     * Sets the white and black King pieces. Meant to be called during readBoardFromString.
     */
    private void setKings() {
        whiteKing = (ChessKing)board[4][7].getOccupant();
        blackKing = (ChessKing)board[4][0].getOccupant();
    }

    /**
     * Sets the white and black pieces list. Meant to be called during readBoardFromString.
     */
    private void setPlayerPieces() {
        whitePieces = new ArrayList<ChessPiece>();
        blackPieces = new ArrayList<ChessPiece>();
        // grab the white pieces
        for (int i = 6; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                whitePieces.add(board[j][i].getOccupant());
            }
        }
        // grab the black pieces
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                blackPieces.add(board[j][i].getOccupant());
            }
        }
    }

    /**
     * Gets the ChessKing stored in the whiteKing, which should be set by a setKings call
     * @return  The white king on this board
     */
    public ChessKing getWhiteKing() {
        return whiteKing;
    }

    /**
     * Gets the ChessKing stored in the blackKing, which should be set by a setKings call
     * @return  The black king on this board
     */
    public ChessKing getBlackKing() {
        return blackKing;
    }

    /**
     * Returns whether or not the white king is in check
     * @return  Whether or not the white kings is in check.
     */
    public boolean testWhiteCheck() {
        boolean flag = false;
        ChessSquare kingsSpot = whiteKing.getLocation();
        ChessSquare temp = new ChessSquare(this, "temp", Color.BLACK, -1, -1);
        whiteKing.movePiece(temp);
        ArrayList<ChessSquare> list = new ArrayList<ChessSquare>();

        ChessPawn pawn = new ChessPawn(kingsSpot, Player.PLAYER_1);
        list = pawn.getMoves();
        for (ChessSquare e : list) {
            if(e.getOccupant() != null && e.getOccupant().getOwner() == Player.PLAYER_2 && e.getOccupant().getClass() == pawn.getClass()) {
                flag = true;
            }
        }
        pawn.getLocation().removeOccupant();

        ChessKnight knight = new ChessKnight(kingsSpot, Player.PLAYER_1);
        list = knight.getMoves();
        for (ChessSquare e : list) {
            if(e.getOccupant() != null && e.getOccupant().getOwner() == Player.PLAYER_2 && e.getOccupant().getClass() == knight.getClass()) {
                flag = true;
            }
        }
        knight.getLocation().removeOccupant();

        ChessBishop bishop = new ChessBishop(kingsSpot, Player.PLAYER_1);
        list = bishop.getMoves();
        for (ChessSquare e : list) {
            if(e.getOccupant() != null && e.getOccupant().getOwner() == Player.PLAYER_2 && e.getOccupant().getClass() == bishop.getClass()) {
                flag = true;
            }
        }
        bishop.getLocation().removeOccupant();

        ChessRook rook = new ChessRook(kingsSpot, Player.PLAYER_1);
        list = rook.getMoves();
        for (ChessSquare e : list) {
            if(e.getOccupant() != null && e.getOccupant().getOwner() == Player.PLAYER_2 && e.getOccupant().getClass() == rook.getClass()) {
                flag = true;
            }
        }
        rook.getLocation().removeOccupant();

        ChessQueen queen = new ChessQueen(kingsSpot, Player.PLAYER_1);
        list = queen.getMoves();
        for (ChessSquare e : list) {
            if(e.getOccupant() != null && e.getOccupant().getOwner() == Player.PLAYER_2 && e.getOccupant().getClass() == queen.getClass()) {
                flag = true;
            }
        }
        queen.getLocation().removeOccupant();

        whiteKing.movePiece(kingsSpot);
        list = whiteKing.getMoves();
        for (ChessSquare e : list) {
            if(e.getOccupant() != null && e.getOccupant().getOwner() == Player.PLAYER_2 && e.getOccupant().getClass() == whiteKing.getClass()) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Returns whether or not the black king is in check
     * @return  Whether or not the black kings is in check.
     */
    public boolean testBlackCheck() {
        boolean flag = false;
        ChessSquare kingsSpot = blackKing.getLocation();
        ChessSquare temp = new ChessSquare(this, "temp", Color.BLACK, -1, -1);
        blackKing.movePiece(temp);
        ArrayList<ChessSquare> list = new ArrayList<ChessSquare>();

        ChessPawn pawn = new ChessPawn(kingsSpot, Player.PLAYER_2);
        list = pawn.getMoves();
        for (ChessSquare e : list) {
            if(e.getOccupant() != null && e.getOccupant().getOwner() == Player.PLAYER_1 && e.getOccupant().getClass() == pawn.getClass()) {
                flag = true;
            }
        }
        pawn.getLocation().removeOccupant();

        ChessKnight knight = new ChessKnight(kingsSpot, Player.PLAYER_2);
        list = knight.getMoves();
        for (ChessSquare e : list) {
            if(e.getOccupant() != null && e.getOccupant().getOwner() == Player.PLAYER_1 && e.getOccupant().getClass() == knight.getClass()) {
                flag = true;
            }
        }
        knight.getLocation().removeOccupant();

        ChessBishop bishop = new ChessBishop(kingsSpot, Player.PLAYER_2);
        list = bishop.getMoves();
        for (ChessSquare e : list) {
            if(e.getOccupant() != null && e.getOccupant().getOwner() == Player.PLAYER_1 && e.getOccupant().getClass() == bishop.getClass()) {
                flag = true;
            }
        }
        bishop.getLocation().removeOccupant();

        ChessRook rook = new ChessRook(kingsSpot, Player.PLAYER_2);
        list = rook.getMoves();
        for (ChessSquare e : list) {
            if(e.getOccupant() != null && e.getOccupant().getOwner() == Player.PLAYER_1 && e.getOccupant().getClass() == rook.getClass()) {
                flag = true;
            }
        }
        rook.getLocation().removeOccupant();

        ChessQueen queen = new ChessQueen(kingsSpot, Player.PLAYER_2);
        list = queen.getMoves();
        for (ChessSquare e : list) {
            if(e.getOccupant() != null && e.getOccupant().getOwner() == Player.PLAYER_1 && e.getOccupant().getClass() == queen.getClass()) {
                flag = true;
            }
        }
        queen.getLocation().removeOccupant();

        blackKing.movePiece(kingsSpot);
        list = blackKing.getMoves();
        for (ChessSquare e : list) {
            if(e.getOccupant() != null && e.getOccupant().getOwner() == Player.PLAYER_1 && e.getOccupant().getClass() == blackKing.getClass()) {
                flag = true;
            }
        }

        return flag;
    }

    /**
     * This method returns whether or not the test piece's player's king will be in check after a move to the given location
     * @param piece the piece to test
     * @param newLocation   the spot to test a move to
     * @return  whether or not the piece's king will be in check after a move to the given location
     */
    public boolean testMoveForCheck(ChessPiece piece, ChessSquare newLocation) {
        boolean flag = true;
        ChessSquare oldLocation = piece.getLocation();
        // temp square to move a piece in the case that the spot to move to is already filled
        ChessSquare temp = new ChessSquare(this, "temp", Color.WHITE, -1, -1);
        boolean piecePawnBoolean = false;
        boolean tempPawnBoolean = false;
        if (piece.getClass() == ChessPawn.class) {
            piecePawnBoolean = ((ChessPawn)piece).getHasMoved();
        }
        // save piece currently there
        if (newLocation.getOccupant() != null) {
            if (newLocation.getOccupant().getClass() == ChessPawn.class) {
                tempPawnBoolean = ((ChessPawn)newLocation.getOccupant()).getHasMoved();
            }
            newLocation.getOccupant().movePiece(temp);
        }
        // temporarily move piece to the test location
        piece.movePiece(newLocation);
        // set the flag accordingly if in check after move
        if (piece.getOwner() == Player.PLAYER_1) {
            flag = testWhiteCheck();
        } else {
            flag = testBlackCheck();
        }
        // restore state of board before this function call
        piece.movePiece(oldLocation);
        if (piece.getClass() == ChessPawn.class) {
            ((ChessPawn)piece).setHasMoved(piecePawnBoolean);
        }
        if (temp.getOccupant() != null) {
            temp.getOccupant().movePiece(newLocation);
            // if it was a Pawn
            if (newLocation.getOccupant().getClass() == ChessPawn.class) {
                ((ChessPawn)(newLocation.getOccupant())).setHasMoved(tempPawnBoolean);
            }
        }
        // return the flag
        return flag;
    }

    /**
     * Returns whether the given player is in a checkmate state for this board
     * @param player    The player to test checkmate on
     * @return  Whether that player is in checkmate or not
     */
    public boolean testCheckMate(Player player) {
        boolean flag = true;
        if (player == Player.PLAYER_1) {
            // go through each piece
            for (ChessPiece e : whitePieces) {
                // only look at the piece if they are still alive
                if (e.getAlive()) {
                    // for each move they can make
                    for (ChessSquare move : e.getMoves()) {
                        // if they would not be in checkmate after the move, set flag to false
                        if (!testMoveForCheck(e, move)) {
                            flag = false;
                        }
                    }
                }
            }
        } else {
            // go through each piece
            for (ChessPiece e : blackPieces) {
                // only look at the piece if they are still alive
                if (e.getAlive()) {
                    // for each move they can make
                    for (ChessSquare move : e.getMoves()) {
                        // if they would not be in checkmate after the move, set flag to false
                        if (!testMoveForCheck(e, move)) {
                            flag = false;
                        }
                    }
                }
            }
        }
        return flag;
    }

    /**
     * Call this method to simulate taking a turn - it flips the boolean
     */
    public void takeTurn() {
        evenTurn = !evenTurn;
    }

    /**
     * Selects the given ChessPiece as the current ChessPiece
     * @param toSelect - the Piece to store as current
     */
    public void selectPiece(ChessPiece toSelect) {
        currentlySelected = toSelect;
    }

    /**
     * Sets the value of the current ChessPiece to null
     */
    public void deSelectPiece() {
        currentlySelected = null;
    }

    /**
     * Gives the currently selected ChessPiece
     * @return - the currently selected ChessPiece
     */
    public ChessPiece getSelectedPiece() {
        return currentlySelected;
    }

    /**
     * Updates the stored list of movable squares
     * @param newList - the new list of squares that can be moved to
     */
    public void setMovableList(ArrayList<ChessSquare> newList) {
        movable = newList;
    }

    /**
     * Gets the current list of movable squares
     * @return - the list squares that can be moved to
     */
    public ArrayList<ChessSquare> getMovableList() {
        return movable;
    }

    /**
     * Sets the value of the movable list to null
     */
    public void deSelectMovableList() {
        movable = null;
    }

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
        evenTurn = true;

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
                board[i][j] = new ChessSquare(this, ("" + (char)letterNumber + rowNumber), c, j, i);
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

    /**
     * Initializes pieces in a standard Chess game setup on the board - meant to be called at the beginning
     */
    public static void initBoard(ChessSquare[][] board) {
        // create player1 pieces
        new ChessKing(board[4][7], Player.PLAYER_1);
        new ChessQueen(board[3][7], Player.PLAYER_1);
        new ChessBishop(board[2][7], Player.PLAYER_1);
        new ChessBishop(board[5][7], Player.PLAYER_1);
        new ChessKnight(board[1][7], Player.PLAYER_1);
        new ChessKnight(board[6][7], Player.PLAYER_1);
        new ChessRook(board[0][7], Player.PLAYER_1);
        new ChessRook(board[7][7], Player.PLAYER_1);
        // make the pawns for player1
        for (int i = 0; i < 8; i++) {
            new ChessPawn(board[i][6], Player.PLAYER_1);
        }

        // create player2 pieces
        new ChessKing(board[4][0], Player.PLAYER_2);
        new ChessQueen(board[3][0], Player.PLAYER_2);
        new ChessBishop(board[2][0], Player.PLAYER_2);
        new ChessBishop(board[5][0], Player.PLAYER_2);
        new ChessKnight(board[1][0], Player.PLAYER_2);
        new ChessKnight(board[6][0], Player.PLAYER_2);
        new ChessRook(board[0][0], Player.PLAYER_2);
        new ChessRook(board[7][0], Player.PLAYER_2);
        // make the pawns for player2
        for (int i = 0; i < 8; i++) {
            new ChessPawn(board[i][1], Player.PLAYER_2);
        }
    }

    /**
     * Given the string of moves from a game history file, this method updates a board to include all the moves stored in the file
     * @param data - the String containing the list moves
     */
    public void readBoardFromString(String data) {
        initBoard(board);
        this.setKings();
        this.setPlayerPieces();
        Scanner sc = new Scanner(data);
        while (sc.hasNext()) {
            String fromString = sc.next();
            String toString = sc.next();
            int fromRow = 8 - Integer.parseInt(fromString.substring(1));
            int fromCol = (int) fromString.charAt(0) - 97;
            int toRow = 8 - Integer.parseInt(toString.substring(1));
            int toCol = (int) toString.charAt(0) - 97;
            board[fromCol][fromRow].getOccupant().movePiece(board[toCol][toRow]);
            takeTurn();
        }
    }

    /**
     * This method takes an InputStream of a file and reads the file for moves on a game board.
     * It starts with a classic game setup, and then reads each move and implements them.
     * The file should be written with pairs of lines. The first line contains the starting spot
     * of a move, the second line contains the end spot of a move. For instance if line 1 was
     * "a1" and line 2 was "b3", that would mean to move a piece at a1 to b3. This method assumes
     * that the file is written in the correct format.
     * @param is - the InputStream from the file
     */
    public void readBoardFromFile(InputStream is) {
        initBoard(board); // start with a standard board
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String data = "";
            String line;
            // loop over each pair of lines
            while ((line = bufferedReader.readLine()) != null) { // read first line
                data = data + line + "\n";
            }
            readBoardFromString(data);
            bufferedReader.close();
            inputStreamReader.close();
            is.close();
        } catch (Exception e) {
            String stack = Log.getStackTraceString(e);
            Log.d("Tag", stack);
            board[0][0].getOccupant().movePiece(board[0][4]); // catch statement included to allow code to compile - will fill this later
        }
    }

    /**
     * Given two ChessSquares, returns a string with the names of the two squares. This String should be
     * written to the game history file to be recorded as a move.
     * @param fromSquare - the starting square
     * @param toSquare - the square moved to
     * @return - String with the names of the squares, each on its own line, to be stored in the game history file
     */
    public String getMoveString(ChessSquare fromSquare, ChessSquare toSquare) {
        String fromString = fromSquare.getName();
        String toString = toSquare.getName();
        // place line breaks between each square name
        return (fromString + "\n" + toString + "\n");
    }

}
