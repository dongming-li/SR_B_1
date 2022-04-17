package coms309.gridviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ChessBoard theBoard;

    public ChessBoard getTheBoard() {
        return theBoard;
    }

    public Boolean evenTurn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        theBoard = new ChessBoard(8,8);
        InputStream is = this.getResources().openRawResource(R.raw.chess_game);
        theBoard.readBoardFromFile(is);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // attempts to get a nice even GridView - not working at the moment
        float xdpi = this.getResources().getDisplayMetrics().xdpi;
        int mKeyHeight = (int)(480/8);

        // boardGridView holds information about the board layer - the sqaures
        GridView boardGridView = (GridView) findViewById(R.id.gridview);
        boardGridView.setAdapter(new ImageAdapter(this));
        boardGridView.setColumnWidth(mKeyHeight);

        // pieceGridView holds information about the piece layer - the pieces on the board
        GridView pieceGridView = (GridView) findViewById(R.id.pieceGridView);
        PieceImageAdapter pieceAdapter = new PieceImageAdapter(this, this);
        pieceGridView.setAdapter(pieceAdapter);
        pieceGridView.setColumnWidth(mKeyHeight);
        pieceAdapter.updateBoard();

        // TextView is used for debugging purposes
        TextView text = (TextView) findViewById(R.id.textView);
        text.setText("Some Text");
        TextView text2 = (TextView) findViewById(R.id.textView2);
        text.setText("Some Text");

        // this sets a listener on the pieces, which responds to be selected or clicked
        pieceGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                //Toast.makeText(MainActivity.this, "" + position,
                //        Toast.LENGTH_SHORT).show();
                TextView text = (TextView) findViewById(R.id.textView);
                TextView text2 = (TextView) findViewById(R.id.textView2);
                GridView boardGridView = (GridView) findViewById(R.id.gridview);
                ((ImageAdapter)boardGridView.getAdapter()).restoreBoard();
                // obtain the row and column of the board from the 1D array
                int i = position / 8;
                int j = position % 8;

                // if there is no previouly selected piece
                if (theBoard.getSelectedPiece() == null) {
                    // if there is no occupant in the square - deselect all
                    if (theBoard.getBoard()[j][i].getOccupant() == null) {
                        theBoard.deSelectMovableList();
                        theBoard.deSelectPiece();
                        ((ImageAdapter)boardGridView.getAdapter()).restoreBoard();
                        text.setText("");
                    }
                    // else there is a piece there - select it
                    else {
                        // only select piece if it is their turn
                        if (theBoard.getPlayersTurn(theBoard.getBoard()[j][i].getOccupant().getOwner())) {
                            theBoard.selectPiece(theBoard.getBoard()[j][i].getOccupant());
                            theBoard.setMovableList(theBoard.getBoard()[j][i].getOccupant().getMoves());
                            ArrayList<ChessSquare> temp = new ArrayList<ChessSquare>();
                            // only add locations that won't have the king in check
                            for (ChessSquare e : theBoard.getMovableList()) {
                                if (!theBoard.testMoveForCheck(theBoard.getSelectedPiece(), e)) {
                                    temp.add(e);
                                }
                            }
                            theBoard.setMovableList(temp);
                            ((ImageAdapter) boardGridView.getAdapter()).updateSpotsOnGetMove(theBoard.getMovableList());
                            text.setText("Selected: " + theBoard.getSelectedPiece().getName());
                        } else {
                            theBoard.deSelectMovableList();
                            theBoard.deSelectPiece();
                            ((ImageAdapter)boardGridView.getAdapter()).restoreBoard();
                            text.setText("");
                        }
                    }
                }
                // else there is a selected piece
                else {
                    ((ImageAdapter)boardGridView.getAdapter()).restoreBoard();
                    // check if new spot is reachable by the selected piece
                    if (theBoard.getMovableList().contains(theBoard.getBoard()[j][i])) {
                        // store current Square for later use
                        ChessSquare fromSquare = theBoard.getSelectedPiece().getLocation();
                        theBoard.getSelectedPiece().movePiece(theBoard.getBoard()[j][i]);
                        theBoard.takeTurn();
                        // right now the text storing data of the move is just written to the screen
                        // this should eventually write data to a file on the server instead
                        text.setText("Move: " + theBoard.getMoveString(fromSquare, theBoard.getBoard()[j][i]) + "White Check: " + theBoard.testWhiteCheck()  + "Black Check: " + theBoard.testBlackCheck());
                        if (theBoard.testWhiteCheck()) {
                            text2.setText("White Checkmate: " + theBoard.testCheckMate(Player.PLAYER_1));
                        } else if (theBoard.testBlackCheck()) {
                            text2.setText("Black Checkmate: " + theBoard.testCheckMate(Player.PLAYER_2));
                        } else {
                            text2.setText("");
                        }
                        theBoard.deSelectMovableList();
                        theBoard.deSelectPiece();
                    }
                    // the spot is not reachable
                    else {
                        theBoard.deSelectMovableList();
                        theBoard.deSelectPiece();
                        text.setText("Cannot move there");
                    }
                }
                // following six function calls refresh the screen
                ImageAdapter boardAdapter = (ImageAdapter) boardGridView.getAdapter();
                boardGridView.invalidateViews();
                boardGridView.setAdapter(boardAdapter);

                GridView pieceGridView = (GridView) findViewById(R.id.pieceGridView);
                PieceImageAdapter pieceAdapter = (PieceImageAdapter) pieceGridView.getAdapter();
                pieceAdapter.updateBoard();

            }
        });
    }
}
