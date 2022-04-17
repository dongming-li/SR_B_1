package chessFiles;

//import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Luke on 9/19/2017.
 *
 * This class holds the data structure for a chess board in the game. It contains methods that are used to test for check,
 * as well as methods that return info on the state of the board. It also contains methods that can create a board from
 * an input string, and create strings that can be stored to later recreate the game state.
 */
public class ChessBoard {

    // instance variables: ****************************************************************************

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
    // holds the pawns of player 1
    private ArrayList<ChessPawn> whitePawns;
    // holds the pawns of player 2
    private ArrayList<ChessPawn> blackPawns;

    // end instance variables ****************************************************************************

    // constructors: ****************************************************************************

    /**
     * Creates a new ChessBoard object with the given number of rows and
     * columns. The standard for classic chess is 8 rows and 8 columns.
     * @param numRows - the number of rows
     * @param numCols - the number of columns
     */
    public ChessBoard(int numRows, int numCols) {
        board = new ChessSquare[numCols][numRows];
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
                // the name will be akin to 'a1' or 'g3', etc.
                board[i][j] = new ChessSquare(this, ("" + (char)letterNumber + rowNumber), j, i);
            }
        }
    }

    // end constructors ****************************************************************************

    // get instance variable methods: ****************************************************************************

    /**
     * Gives the currently selected ChessPiece
     * @return - the currently selected ChessPiece
     */
    public ChessPiece getSelectedPiece() {
        return currentlySelected;
    }

    /**
     * Gets the current list of movable squares
     * @return - the list squares that can be moved to
     */
    public ArrayList<ChessSquare> getMovableList() {
        return movable;
    }

    /**
     * Returns the double array of ChessSquares that make up the board.
     * @return - the double array of ChessSquares called board
     */
    public ChessSquare[][] getBoard() {
        return board;
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
     * Returns the board's list of pawns belonging to player 2
     * @return  The list of pieces
     */
    public ArrayList<ChessPawn> getBlackPawns() {
        return blackPawns;
    }

    /**
     * Returns the board's list of pawns belonging to player 1
     * @return  The list of pieces
     */
    public ArrayList<ChessPawn> getWhitePawns() {
        return whitePawns;
    }

    // end get instance variable methods ****************************************************************************

    // get states from instance variable methods: ****************************************************************************

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

    // end get states from instance variable methods ****************************************************************************

    // set methods for instance variables: ****************************************************************************

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
     * Updates the stored list of movable squares
     * @param newList - the new list of squares that can be moved to
     */
    public void setMovableList(ArrayList<ChessSquare> newList) {
        movable = newList;
    }

    /**
     * Sets the value of the movable list to null
     */
    public void deSelectMovableList() {
        movable = null;
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
     * Adds the given piece to the list of pieces of the given player. Meant to be used by power ups
     * @param player - the player to give the piece to
     * @param piece - the piece to add to the list
     */
    public void addToList(Player player, ChessPiece piece) {
        if (player == Player.PLAYER_1) {
            whitePieces.add(piece);
        }
        else {
            blackPieces.add(piece);
        }
    }

    /**
     * Removes the given piece from the list of pieces of the given player. Meant to be used by power ups
     * @param player - the player to remove the piece from
     * @param piece - the piece to be removed
     */
    public void removeFromList(Player player, ChessPiece piece) {
        if (player == Player.PLAYER_1) {
            whitePieces.remove(piece);
        }
        else {
            blackPieces.remove(piece);
        }
    }

    /**
     * Sets the white and black pawns list. Meant to be called during readBoardFromString.
     */
    private void setPlayerPawns() {
        whitePawns = new ArrayList<ChessPawn>();
        blackPawns = new ArrayList<ChessPawn>();
        // grab the white pawns
        for (int j = 0; j < 8; j++) {
            whitePawns.add((ChessPawn)(board[j][6].getOccupant()));
        }
        // grab the black pawns
        for (int j = 0; j < 8; j++) {
            blackPawns.add((ChessPawn)(board[j][1].getOccupant()));
        }
    }

    // end set methods for instance variables ****************************************************************************

    // methods related to check: ****************************************************************************

    /**
     * Tests if the given player's king is under check with the current state of the board. Returns a boolean
     * with the result. Can be called to test a hypothetical move after it is made (such as with testMoveForCheck())
     * to see if the move is valid, or after a move is actually made to test for in game.
     * @param player - the player's king to test for check
     * @return - true if the given king is in check, false if it is not
     */
    public boolean testCheck(Player player) {
        // set the king variable to be the one owned by the given player
        ChessKing king;
        if (player == Player.PLAYER_1) {
            king = whiteKing;
        } else {
            king = blackKing;
        }
        // testing check relies on the following principle: if the king is replaced by a pawn, and could then attack an
        // enemy pawn, then it is under check from that pawn. Same principle applies to the other chess pieces. So this
        // method temporarily moves the king to a temporary space, and tests the above principle.

        // since the king will be moved by this test method, the hasMoved variable will be changed. We save the initial status
        // to be able to restore it later
        boolean kingHasMoved = king.getHasMoved();
        // flag records final answer of the method
        boolean flag = false;
        ChessSquare kingsSpot = king.getLocation();
        // create a temp chessqaure to move the king to - allow for testing the spot
        ChessSquare temp = new ChessSquare(this, "temp", -1, -1);
        king.movePiece(temp);
        ArrayList<ChessSquare> list = new ArrayList<ChessSquare>();

        // check if the king is under attack from a pawn
        // put a temporary pawn in the king's spot
        ChessPawn pawn = new ChessPawn(kingsSpot, player);
        list = pawn.getMoves();
        // test all possible moves for an attack on an enemy pawn
        for (ChessSquare e : list) {
            // note it could be either a pawn or a TempPawn piece
            if(e.getOccupant() != null && e.getOccupant().getOwner() != player && (e.getOccupant().getClass() == pawn.getClass() || e.getOccupant().getClass() == TempChessPawn.class)) {
                // if it can attack an enemy pawn, the king is in check - repeat principle for the other pieces
                flag = true;
            }
        }
        // remove the temporary pawn after the test
        pawn.getLocation().removeOccupant();

        // check if the king is under attack from a knight
        ChessKnight knight = new ChessKnight(kingsSpot, player);
        list = knight.getMoves();
        for (ChessSquare e : list) {
            if(e.getOccupant() != null && e.getOccupant().getOwner() != player && e.getOccupant().getClass() == knight.getClass()) {
                flag = true;
            }
        }
        knight.getLocation().removeOccupant();

        // check if the king is under attack from a bishop
        ChessBishop bishop = new ChessBishop(kingsSpot, player);
        list = bishop.getMoves();
        for (ChessSquare e : list) {
            if(e.getOccupant() != null && e.getOccupant().getOwner() != player && e.getOccupant().getClass() == bishop.getClass()) {
                flag = true;
            }
        }
        bishop.getLocation().removeOccupant();

        // check if king is under attack from a rook
        ChessRook rook = new ChessRook(kingsSpot, player);
        list = rook.getMoves();
        for (ChessSquare e : list) {
            if(e.getOccupant() != null && e.getOccupant().getOwner() != player && e.getOccupant().getClass() == rook.getClass()) {
                flag = true;
            }
        }
        rook.getLocation().removeOccupant();

        // check if king is under attack from a queen
        ChessQueen queen = new ChessQueen(kingsSpot, player);
        list = queen.getMoves();
        for (ChessSquare e : list) {
            // note it could be a queen or a TempQueen piece
            if(e.getOccupant() != null && e.getOccupant().getOwner() != player && (e.getOccupant().getClass() == queen.getClass() || e.getOccupant().getClass() == TempChessQueen.class)) {
                flag = true;
            }
        }
        queen.getLocation().removeOccupant();

        // check if king is under attack from another king
        king.movePiece(kingsSpot);
        list = king.getMoves();
        for (ChessSquare e : list) {
            if(e.getOccupant() != null && e.getOccupant().getOwner() != player && e.getOccupant().getClass() == king.getClass()) {
                flag = true;
            }
        }

        // reset the king's hasMoved status to what it was before this call to preserve the board status
        king.setHasMoved(kingHasMoved);
        // finally, return the result
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
        ChessSquare temp = new ChessSquare(this, "temp", -1, -1);
        ChessSquare powerUpTemp = new ChessSquare(this, "temp2", -2, -2);
        boolean hasPowerUp = false;
        if (newLocation.getPowerUp() != null) {
            hasPowerUp = true;
            newLocation.getPowerUp().setLocation(powerUpTemp);
        }
        boolean piecePawnBoolean = false;
        boolean pieceRookBoolean = false;
        boolean pieceKingBoolean = false;
        boolean tempPawnBoolean = false;
        boolean tempRookBoolean = false;
        boolean tempKingBoolean = false;
        if (piece.getClass() == ChessPawn.class) {
            piecePawnBoolean = ((ChessPawn)piece).getHasMoved();
        } else if (piece.getClass() == ChessRook.class) {
            pieceRookBoolean = ((ChessRook)piece).getHasMoved();
        } else if (piece.getClass() == ChessKing.class) {
            pieceKingBoolean = ((ChessKing)piece).getHasMoved();
        }
        // save piece currently there
        if (newLocation.getOccupant() != null) {
            if (newLocation.getOccupant().getClass() == ChessPawn.class) {
                tempPawnBoolean = ((ChessPawn)newLocation.getOccupant()).getHasMoved();
            }
            if (newLocation.getOccupant().getClass() == ChessRook.class) {
                tempRookBoolean = ((ChessRook)newLocation.getOccupant()).getHasMoved();
            }
            if (newLocation.getOccupant().getClass() == ChessKing.class) {
                tempKingBoolean = ((ChessKing)newLocation.getOccupant()).getHasMoved();
            }
            newLocation.getOccupant().movePiece(temp);
        }
        // temporarily move piece to the test location
        piece.movePiece(newLocation);
        // set the flag accordingly if in check after move
        if (piece.getOwner() == Player.PLAYER_1) {
            flag = testCheck(Player.PLAYER_1);
        } else {
            flag = testCheck(Player.PLAYER_2);
        }
        // restore state of board before this function call
        piece.movePiece(oldLocation);
        // if it was a Pawn, Rook, or King, set hasMoved booleans to what they were before this function call
        if (piece.getClass() == ChessPawn.class) {
            ((ChessPawn)piece).setHasMoved(piecePawnBoolean);
        } else if (piece.getClass() == ChessRook.class) {
            ((ChessRook)piece).setHasMoved(pieceRookBoolean);
        } else if (piece.getClass() == ChessKing.class) {
            ((ChessKing)piece).setHasMoved(pieceKingBoolean);
        }
        if (temp.getOccupant() != null) {
            temp.getOccupant().movePiece(newLocation);
            // if it was a Pawn, Rook, or King, set hasMoved booleans to what they were before this function call
            if (newLocation.getOccupant().getClass() == ChessPawn.class) {
                ((ChessPawn)(newLocation.getOccupant())).setHasMoved(tempPawnBoolean);
            } else if (newLocation.getOccupant().getClass() == ChessRook.class) {
                ((ChessRook)(newLocation.getOccupant())).setHasMoved(tempRookBoolean);
            } else if (newLocation.getOccupant().getClass() == ChessKing.class) {
                ((ChessKing) (newLocation.getOccupant())).setHasMoved(tempKingBoolean);
            }
        }
        // restore powerup to previous spot if it was there
        if (hasPowerUp) {
            powerUpTemp.getPowerUp().setLocation(newLocation);
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
        // assume the player is in checkmate
        boolean flag = true;
        // test for player 1's king
        if (player == Player.PLAYER_1) {
            // go through each piece the player has
            for (ChessPiece e : whitePieces) {
                // only look at the piece if they are still alive
                if (e.getAlive()) {
                    // for each move they can make
                    for (ChessSquare move : e.getMoves()) {
                        // if they would not be in checkmate after the move, set flag to false
                        // this means there is a move they can make to not be in check
                        if (!testMoveForCheck(e, move)) {
                            flag = false;
                        }
                    }
                }
            }
        }
        // test for player 2's king
        else {
            // go through each piece
            for (ChessPiece e : blackPieces) {
                // only look at the piece if they are still alive
                if (e.getAlive()) {
                    // for each move they can make
                    for (ChessSquare move : e.getMoves()) {
                        // if they would not be in checkmate after the move, set flag to false
                        // this means there is a move they can make to not be in check
                        if (!testMoveForCheck(e, move)) {
                            flag = false;
                        }
                    }
                }
            }
        }
        // return the result - if no valid moves were found, then it is checkmate
        return flag;
    }

    // end methods related to check ****************************************************************************

    // methods to interpret and create strings to send to the server/database: ***********************************

    /**
     * Given the string of moves from a game history file, this method updates a board to include all the moves stored in the file
     * @param data - the String containing the list moves
     */
    public void readBoardFromString(String data) {
        initBoard(board);
        this.setKings();
        this.setPlayerPieces();
        this.setPlayerPawns();
        Scanner sc = new Scanner(data);
        while (sc.hasNext()) {
            String fromString = sc.next();
            String toString = sc.next();
            // check for remove commands (meant to be used with en passants)
            if (fromString.charAt(0) == 'r') {
                int fromRow = 8 - Integer.parseInt(fromString.substring(2));
                int fromCol = (int)fromString.charAt(1) - 97;
                board[fromCol][fromRow].removeOccupant();
            }
            // check for blank commands (meant to be used with a castle move)
            else if (fromString.charAt(0) == 'n') {
                takeTurn();
            }
            // check for powerup creation commands
            else if (fromString.charAt(0) == 'p') {
                // location stored on first line
                int fromRow = 8 - Integer.parseInt(fromString.substring(2));
                int fromCol = (int)fromString.charAt(1) - 97;
                // type of powerup stored in second line
                if (toString.equals("smallBomb")) {
                    new SmallBombPowerUp(board[fromCol][fromRow]);
                    //Log.d("tag", "made it");
                } else if (toString.equals("largeBomb")) {
                    new LargeBombPowerUp(board[fromCol][fromRow]);
                } else if (toString.equals("extraTurn")) {
                    new ExtraTurnPowerUp(board[fromCol][fromRow]);
                } else if (toString.equals("toQueen")) {
                    new ToQueenPowerUp(board[fromCol][fromRow]);
                } else if (toString.equals("toPawn")) {
                    new ToPawnPowerUp(board[fromCol][fromRow]);
                } else if (toString.equals("converter")) {
                    new ConverterPowerUp(board[fromCol][fromRow]);
                }
            }
            // otherwise read as normal
            else {
                int fromRow = 8 - Integer.parseInt(fromString.substring(1));
                int fromCol = (int) fromString.charAt(0) - 97;
                int toRow = 8 - Integer.parseInt(toString.substring(1));
                int toCol = (int) toString.charAt(0) - 97;
                // make the move a special move if required
                if (board[fromCol][fromRow].getOccupant().getClass() == TempChessQueen.class) {
                    ((TempChessQueen)board[fromCol][fromRow].getOccupant()).specialMovePiece(board[toCol][toRow]);
                } else if (board[fromCol][fromRow].getOccupant().getClass() == TempChessPawn.class) {
                    ((TempChessPawn)board[fromCol][fromRow].getOccupant()).specialMovePiece(board[toCol][toRow]);
                }
                // otherwise make it a normal move
                else {
                    board[fromCol][fromRow].getOccupant().movePiece(board[toCol][toRow]);
                }
                takeTurn();
            }
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
            //String stack = Log.getStackTraceString(e);
           // Log.d("Tag", stack);
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

    /**
     * When called, it has a chance of placing a random powerup on any empty space on the board. Meant to be called whenever a move is made.
     * @return - the String storing info of where a powerup was made. Meant to be sent to the server and database. Is null if no powerup was made
     */
    public String generatePowerUp() {
        String creationString = null;
        Random r = new Random();
        int isGenerated = r.nextInt(10);
        // only create a powerup 10% of the time (1 in every 10 moves)
        if (isGenerated == 1) {
            // create a list to hold all possible spots
            ArrayList<ChessSquare> freeSpaces = new ArrayList<ChessSquare>();
            // add all free spaces to the list
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if(board[i][j].getOccupant() == null && board[i][j].getPowerUp() == null) {
                        freeSpaces.add(board[i][j]);
                    }
                }
            }
            // pick a random spot in the list
            int whichSpot = r.nextInt(freeSpaces.size());
            ChessSquare theSpot = freeSpaces.get(whichSpot);
            // pick a random powerup, and put it in the spot found earlier
            int whichPowerUp = r.nextInt(6);
            // 0 is for a SmallBombPowerUp
            if (whichPowerUp == 0) {
                new SmallBombPowerUp(theSpot);
                creationString = "p" + theSpot.getName() + "\nsmallBomb\n";
            }
            // 1 is for a LargeBombPowerUp
            else if (whichPowerUp == 1) {
                new LargeBombPowerUp(theSpot);
                creationString = "p" + theSpot.getName() + "\nlargeBomb\n";
            }
            // 2 is for a ExtraTurnPowerUp
            else if (whichPowerUp == 2) {
                new ExtraTurnPowerUp(theSpot);
                creationString = "p" + theSpot.getName() + "\nextraTurn\n";
            }
            // 3 is for a ToQueenPowerUp
            else if (whichPowerUp == 3) {
                new ToQueenPowerUp(theSpot);
                creationString = "p" + theSpot.getName() + "\ntoQueen\n";
            }
            // 4 is for a ToPawnPowerUp
            else if (whichPowerUp == 4) {
                new ToPawnPowerUp(theSpot);
                creationString = "p" + theSpot.getName() + "\ntoPawn\n";
            }
            // 5 is for a ConverterPowerUp
            else if (whichPowerUp == 5) {
                new ConverterPowerUp(theSpot);
                creationString = "p" + theSpot.getName() + "\nconverter\n";
            }
        }
        return creationString;
    }

    // end methods to interpret and create strings to send to the server/database ********************************
}