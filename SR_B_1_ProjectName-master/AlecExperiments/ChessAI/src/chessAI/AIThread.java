package chessAI;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import chessFiles.ChessBoard;
import chessFiles.ChessPiece;
import chessFiles.ChessSquare;

import java.util.ArrayList;
import java.util.Random;

/**
 * Spawned thread to handle AI move computation.  This way the main listener thread isn't locked up
 * @author alecl
 *
 */
public class AIThread implements Runnable {
	Socket s; // this is socket on the server side that connects to the CLIENT
	int num; // keeps track of its number just for identifying purposes
	
	/**
	 * Runnable object constructor
	 * @param s the socket
	 * @param n the client number
	 */
	AIThread(Socket s, int n){
		this.s = s;
		num = n;
	}
	
	/**
	 * The run method.
	 * Handles all the logic for computing a move for the AI.
	 * Then posts the move to the database.
	 */
	@Override
	public void run() {
		Scanner in;
		PrintWriter out;
		try {
			boolean getMovesErrorOccurred = false;
			boolean makeMoveErrorOccurred = false;
			// 1. GET SOCKET IN/OUT STREAMS
			in = new Scanner(new BufferedInputStream(s.getInputStream())); 
			out = new PrintWriter(new BufferedOutputStream(s.getOutputStream()));
			
			//2. listen for a request and grab username
			String color = in.nextLine(); //what color the AI is
			String difficulty = in.nextLine(); //the AI difficulty
			String gameID = in.nextLine(); //the gameID we are playing
			
			//3. Grab the String from database
			String moves = AIDatabaseCommunication.getBoard(gameID);
			
			
			
			if(!moves.equals("GET request blew up")) {
				//3.5 convert from JSON to just a string
				GsonBuilder builder = new GsonBuilder(); 
			    builder.setPrettyPrinting(); 
			    Gson gson = builder.create();
			    GSONResponse responseJson = gson.fromJson(moves.toString(), GSONResponse.class);
			    moves = responseJson.getData().get(0).getHistory();
				
				//4. Create a new chessBoard
				ChessBoard board = new ChessBoard(8,8);
				
				//5. Use the string on the chessBoard
				board.readBoardFromString(moves);
				
				
				//6. Get all the pieces for the color we are
				ArrayList<ChessPiece> piecesTemp;
				if (color.toLowerCase().equals("white")) {
					//white moves
					piecesTemp = board.getWhitePieces();
				}
				else {
					//obviously we are black then
					piecesTemp = board.getBlackPieces();
				}
				
				//7. making sure to remove all dead pieces from the possibilities
				// also removing all pieces that don't have any valid moves
				ArrayList<ChessAIMoveHolder> moveOptions = new ArrayList<ChessAIMoveHolder>();
				for (int i = 0; i<piecesTemp.size(); i++) {
					if(piecesTemp.get(i).getAlive() == true && piecesTemp.get(i).getMoves().size() != 0) {
						//Create a holder class for each valid piece and check if they have any moves that don't put the king in check
						//if they do have moves after testing for this, add them to the list of possibilities
						ChessAIMoveHolder moveOptionsTemp = new ChessAIMoveHolder(piecesTemp.get(i), board);
						if(moveOptionsTemp.getMoves().size() != 0) {
							moveOptions.add(moveOptionsTemp);
						}
					}
				}
				
				//8. Chess AI Logic
				// Call get moves on pieces, remove all moves that will put my king in check
				ChessPiece toMove;
				ChessSquare origLoc;
				ChessSquare newLoc;
				
				//random move logic
				//make the random
				Random r = new Random();
				//generate a random number based on the number of valid pieces we can move
				int randPiece = r.nextInt(moveOptions.size());
				//use our random number to grab a random piece
				toMove = moveOptions.get(randPiece).getPiece();
				//generate a random number based on the number of valid moves that piece has
				int randMove = r.nextInt(moveOptions.get(randPiece).getMoves().size());				
				//use that random number to grab one of that pieces moves
				newLoc = moveOptions.get(randPiece).getMoves().get(randMove);
				origLoc = toMove.getLocation();
				
				//9. 
				
				
				
				//10. Move the piece
				toMove.movePiece(newLoc);
				
				//11. Update database
				System.out.println(origLoc.getName());
				System.out.println(newLoc.getName());
				makeMoveErrorOccurred = AIDatabaseCommunication.makeMove(gameID, origLoc.getName(), newLoc.getName());
				System.out.println(makeMoveErrorOccurred);
			}
			else {
				getMovesErrorOccurred = true;
			}
			
			
			//12. let client know that a move has been made
			if(makeMoveErrorOccurred || getMovesErrorOccurred) {
				//if either of these is true, then something broke
				String errorMessage = "An error occured with ";
				if (getMovesErrorOccurred) {
					errorMessage += "Getting a list of moves";
				}
				if (makeMoveErrorOccurred) {
					errorMessage += "Moving a piece";
				}
				out.println(errorMessage);
				out.flush();
			}
			else {
				out.println("Done");
				out.flush();
			}
			
			//cleanup
			in.close();
			out.close();
			s.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
