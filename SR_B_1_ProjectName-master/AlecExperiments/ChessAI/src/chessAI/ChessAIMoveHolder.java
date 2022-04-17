package chessAI;

import java.util.ArrayList;

import chessFiles.ChessBoard;
import chessFiles.ChessPiece;
import chessFiles.ChessSquare;

/**
 * Class to hold a chess piece and a list of its valid moves
 * @author alecl
 *
 */
public class ChessAIMoveHolder {
	/**
	 * The chess piece
	 */
	ChessPiece piece;
	/**
	 * ArrayList of valid moves found for the piece
	 */
	ArrayList<ChessSquare> moves = new ArrayList<ChessSquare>();
	/**
	 * The chess board
	 */
	ChessBoard board;
	
	/**
	 * Constructor to create an instance of this object.
	 * During creation the piece has all its moves tested for putting its own king in check.
	 * Should the king result in check, the move is removed from the list of valid options.
	 * @param piece the piece to be queried
	 * @param board the board to be queried
	 */
	public ChessAIMoveHolder(ChessPiece piece, ChessBoard board) {
		this.piece = piece;
		this.board = board;
		
		//grabs all moves and tests if moving here will put my king in check.  If not, add it to the move options
		ArrayList<ChessSquare> tempMoveOptions = piece.getMoves();
		for (int i=0; i<tempMoveOptions.size(); i++) {
			if (board.testMoveForCheck(piece, tempMoveOptions.get(i)) == false) {
				moves.add(tempMoveOptions.get(i));
			}
		}
	}
	
	/**
	 * Gets the piece
	 * @return returns the piece
	 */
	public ChessPiece getPiece() {
		return piece;
	}
	
	/**
	 * Returns all the moves
	 * @return returns moves
	 */
	public ArrayList<ChessSquare> getMoves(){
		return moves;
	}
}
