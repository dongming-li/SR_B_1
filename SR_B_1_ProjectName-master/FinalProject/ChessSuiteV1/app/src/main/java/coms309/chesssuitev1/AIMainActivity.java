package coms309.chesssuitev1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import android.media.SoundPool;
import android.media.AudioManager;

/**
 * Created by Luke on 11/6/2017.
 *
 * This class implements a Chess Game between the player and an AI on the server. It implements the MainGameActivity interface.
 */
public class AIMainActivity extends AppCompatActivity implements MainGameActivity {

    private ChessBoard theBoard;
    private GameState state;
    private SessionManager session;

    // for sound effect
    private SoundPool sounds;
    private int pMoves;
    private ArrayList<ChessSquare> latestMove;



    private String gameStringID;
    private int gameID;
    private String playerString;

    private Player player;
    public static AIMainActivity THING;


    public ChessBoard getTheBoard() {
        return theBoard;
    }

    /**
     * This method reloads the screen - and gets updates from the database. This should be called by a refresh button.
     * @param view - The view that calls this method.
     */
    public void refresh(View view) {
        SharedPreferences pref2 = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref2.edit();

        Intent thisIntent = getIntent();
        Bundle b = thisIntent.getExtras();
        gameStringID = b.getString("gameID");
        playerString = b.getString("player");
        state = new GameState("", this, gameID);
        state.updateString();
        String history = "";

        history = pref2.getString("history","");

        Intent intent = new Intent(this, AIRefreshActivity.class);
        intent.putExtra("history", history);
        intent.putExtra("gameID", gameStringID);
        intent.putExtra("player", playerString);
        Log.d("refresh", history);

        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        gameStringID = b.getString("gameID");
        gameID = Integer.parseInt(gameStringID);
        playerString = b.getString("player");

        Log.d("help2", gameID + " " + playerString);

        if (playerString.equals("player1")) {
            player = Player.PLAYER_1;
        } else {
            player = Player.PLAYER_2;
        }

        THING = this;

        theBoard = new ChessBoard(8,8);
        state = new GameState("", this, gameID);
        state.updateString();

        String history = "";

        history = pref.getString("history","");

        Log.d("help4", history);

        if (b.containsKey(history)) {
            history = b.getString(history);
        }

        Log.d("help5", history);
        //InputStream is = this.getResources().openRawResource(R.raw.chess_game);
        //theBoard.readBoardFromFile(is);
        //TODO
        // change input string to be from server/database

        theBoard.readBoardFromString(history);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ai_activity_main);

        // session manager
        session = new SessionManager(getApplicationContext());
        final Boolean soundOn = session.getDefaultPref().getBoolean(SettingsActivity.KEY_PREF_EXAMPLE_SWITCH, false);
        String color = session.getDefaultPref().getString(SettingsActivity.KEY_PREF_BOARD_COLOR,"0");
        int boardcolor = Integer.parseInt(color);
        //int boardcolor = session.pref.getInt("board_colors", 0);
        Log.d("boardcolor", ""+ boardcolor);

        // use for sounds
        sounds = new SoundPool.Builder().setMaxStreams(10).build();
        pMoves = sounds.load(this, R.raw.movepiece, 1);

        latestMove = new ArrayList<ChessSquare>();

        // boardGridView holds information about the board layer - the squares
        GridView boardGridView = (GridView) findViewById(R.id.gridview);
        // TODO - set variables of the colors for squares
        boardGridView.setAdapter(new ImageAdapter(this, boardcolor, boardcolor, 0));

        // pieceGridView holds information about the piece layer - the pieces on the board
        GridView pieceGridView = (GridView) findViewById(R.id.pieceGridView);
        PieceImageAdapter pieceAdapter = new PieceImageAdapter(this, this);
        pieceGridView.setAdapter(pieceAdapter);
        pieceAdapter.updateBoard();
        ((ImageAdapter)boardGridView.getAdapter()).restoreBoard(theBoard.getLastMove());

        // TextView is used for debugging purposes
//        TextView text = (TextView) findViewById(R.id.textView);
//        text.setText("Some Text");
//        TextView text2 = (TextView) findViewById(R.id.textView2);
//        text.setText("Some Text");

        // this sets a listener on the pieces, which responds to be selected or clicked
        pieceGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                boolean moveFlag = false;
                // only let them move if it is their turn
                if (theBoard.getPlayersTurn(player)) {
                    TextView text = (TextView) findViewById(R.id.textView2);
                    text.setText("");
                    boolean isEnPassant = false;
                    ChessSquare enPassantSpot = null;
                    boolean isCastle = false;
                    ChessSquare castleSpot = null;

                    //Toast.makeText(MainActivity.this, "" + position,
                    //        Toast.LENGTH_SHORT).show();
//                    TextView text = (TextView) findViewById(R.id.textView);
//                    TextView text2 = (TextView) findViewById(R.id.textView2);
                    GridView boardGridView = (GridView) findViewById(R.id.gridview);
                    ((ImageAdapter) boardGridView.getAdapter()).restoreBoard(theBoard.getLastMove());
                    // obtain the row and column of the board from the 1D array
                    int i = position / 8;
                    int j = position % 8;

                    // clear the justMadeTwoStepMove flags of the correct player for en passant calculations
                    if (theBoard.getPlayersTurn(Player.PLAYER_1)) {
                        // if it is player 1's turn, then player 2 forfeits the ability to en passant player's pieces as of now
                        for (ChessPawn p : theBoard.getWhitePawns()) {
                            p.setJustMadeTwoStepMove(false);
                        }
                    } else {
                        // same for in reverse for player two
                        for (ChessPawn p : theBoard.getBlackPawns()) {
                            p.setJustMadeTwoStepMove(false);
                        }
                    }
                    // if there is no previously selected piece
                    if (theBoard.getSelectedPiece() == null) {
                        // if there is no occupant in the square - deselect all
                        if (theBoard.getBoard()[j][i].getOccupant() == null) {
                            theBoard.deSelectMovableList();
                            theBoard.deSelectPiece();
                            ((ImageAdapter) boardGridView.getAdapter()).restoreBoard(theBoard.getLastMove());
//                            text.setText("");
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
//                                text.setText("Selected: " + theBoard.getSelectedPiece().getName());
                            } else {
                                theBoard.deSelectMovableList();
                                theBoard.deSelectPiece();
                                ((ImageAdapter) boardGridView.getAdapter()).restoreBoard(theBoard.getLastMove());
//                                text.setText("");
                            }
                        }
                    }
                    // else there is a selected piece
                    else {
                        ((ImageAdapter) boardGridView.getAdapter()).restoreBoard(theBoard.getLastMove());
                        // check if new spot is reachable by the selected piece
                        if (theBoard.getMovableList().contains(theBoard.getBoard()[j][i])) {
                            // store current Square for later use
                            ChessSquare fromSquare = theBoard.getSelectedPiece().getLocation();
                            latestMove.clear();
                            latestMove.add(fromSquare);
                            // call special move method if it is a temp piece
                            if (theBoard.getSelectedPiece().getClass() == TempChessQueen.class) {
                                ((TempChessQueen) (theBoard.getSelectedPiece())).specialMovePiece(theBoard.getBoard()[j][i]);
                            } else if (theBoard.getSelectedPiece().getClass() == TempChessPawn.class) {
                                ((TempChessPawn) (theBoard.getSelectedPiece())).specialMovePiece(theBoard.getBoard()[j][i]);
                            }
                            // otherwise call normal move method
                            else {
                                theBoard.getSelectedPiece().movePiece(theBoard.getBoard()[j][i]);
                                // testing sound
                                if (soundOn) {
                                    sounds.play(pMoves, 1, 1, 1, 0, 1);
                                }
                            }
                            // if the move made was two steps forward for a pawn, record it for calculating en passants
                            if (theBoard.getSelectedPiece().getClass() == ChessPawn.class) {
                                if (Math.abs(fromSquare.getRow() - theBoard.getBoard()[j][i].getRow()) == 2) {
                                    ((ChessPawn) theBoard.getSelectedPiece()).setJustMadeTwoStepMove(true);
                                }
                            }
                            // check if move was en passant - if it is, remove pawn in addition to the move
                            if (theBoard.getSelectedPiece().getClass() == ChessPawn.class) {
                                // if player 1
                                if (theBoard.getSelectedPiece().getOwner() == Player.PLAYER_1) {
                                    // if the piece behind is a pawn that just made a two step move, remove it (because of en passant)
                                    if (theBoard.getBoard()[j][i + 1].getOccupant() != null && theBoard.getBoard()[j][i + 1].getOccupant().getClass() == ChessPawn.class && ((ChessPawn) theBoard.getBoard()[j][i + 1].getOccupant()).getJustMadeTwoStepMove()) {
                                        enPassantSpot = theBoard.getBoard()[j][i + 1];
                                        enPassantSpot.removeOccupant();
                                        isEnPassant = true;
                                    }
                                }
                                // otherwise check player 2
                                else {
                                    // if the piece behind is a pawn that just made a two step move, remove it (because of en passant)
                                    if (theBoard.getBoard()[j][i - 1].getOccupant() != null && theBoard.getBoard()[j][i - 1].getOccupant().getClass() == ChessPawn.class && ((ChessPawn) theBoard.getBoard()[j][i - 1].getOccupant()).getJustMadeTwoStepMove()) {
                                        enPassantSpot = theBoard.getBoard()[j][i - 1];
                                        enPassantSpot.removeOccupant();
                                        isEnPassant = true;
                                    }
                                }
                            }
                            // check to see if the move was a castle move, if so, also move the rook
                            if (theBoard.getSelectedPiece().getClass() == ChessKing.class) {
                                if (fromSquare.getCol() - theBoard.getBoard()[j][i].getCol() == 2) {
                                    isCastle = true;
                                    castleSpot = theBoard.getBoard()[j + 1][fromSquare.getRow()];
                                    theBoard.getBoard()[0][fromSquare.getRow()].getOccupant().movePiece(castleSpot);
                                } else if (fromSquare.getCol() - theBoard.getBoard()[j][i].getCol() == -2) {
                                    isCastle = true;
                                    castleSpot = theBoard.getBoard()[j - 1][fromSquare.getRow()];
                                    theBoard.getBoard()[7][fromSquare.getRow()].getOccupant().movePiece(castleSpot);
                                }
                            }
                            theBoard.takeTurn();
                            String powerUpString = theBoard.generatePowerUp();
                            if (powerUpString != null) {
                                //TODO // send the string to server if a powerup was made
                                state.sendMove(powerUpString);
                            }
                            // TODO //right now the text storing data of the move is just written to the screen
                            // this should eventually write data to a file on the server instead
//                            text.setText("Move: " + theBoard.getMoveString(fromSquare, theBoard.getBoard()[j][i]) + "White Check: " + theBoard.testCheck(Player.PLAYER_1) + "Black Check: " + theBoard.testCheck(Player.PLAYER_2));
                            state.sendMove(theBoard.getMoveString(fromSquare, theBoard.getBoard()[j][i]));
                            latestMove.add(theBoard.getBoard()[j][i]);
                            theBoard.setLastMove(latestMove);
                            if (isEnPassant) {
                                //TODO
                                state.sendMove("r" + enPassantSpot.getName() + "\nblah\n");
                                // contains a filler extra line to keep moves as a list of pairs
//                                text2.setText("r" + enPassantSpot.getName() + "\n" + "blah\n");
                            } else if (isCastle) {
                                //TODO
                                state.sendMove(theBoard.getMoveString(theBoard.getBoard()[j][i], castleSpot));
//                                text2.setText(theBoard.getMoveString(theBoard.getBoard()[j][i], castleSpot));
                                // Also will need to send a a null move command to keep turn boolean accurate
                                // i.e. send String "n\nstuff here doesn't matter\n"
                            } else {
//                                text2.setText("");
                            }
                            //TODO Uncomment when Alec updates methods
                            // create params array for communicating with AI
                            Object[] params = {"black", "1", gameStringID, THING};
                            // method should work once Alec updates files in the project
                            // at end of running AI, it calls the refresh method to refresh screen
                            Log.d("test1", "Connection Initiated");
                            new ChessAIMoveRequest().execute(params);
                            if (theBoard.testCheck(Player.PLAYER_1)) {
//                                text2.setText("White Checkmate: " + theBoard.testCheckMate(Player.PLAYER_1));
                            } else if (theBoard.testCheck(Player.PLAYER_2)) {
//                                text2.setText("Black Checkmate: " + theBoard.testCheckMate(Player.PLAYER_2));
                            }
                            theBoard.deSelectMovableList();
                            theBoard.deSelectPiece();
                            moveFlag = true;
                        }
                        // the spot is not reachable
                        else {
                            theBoard.deSelectMovableList();
                            theBoard.deSelectPiece();
//                            text.setText("Cannot move there");
                        }
                    }


                    // following six function calls refresh the screen
                    ImageAdapter boardAdapter = (ImageAdapter) boardGridView.getAdapter();
                    boardGridView.invalidateViews();
                    boardGridView.setAdapter(boardAdapter);

                    GridView pieceGridView = (GridView) findViewById(R.id.pieceGridView);
                    PieceImageAdapter pieceAdapter = (PieceImageAdapter) pieceGridView.getAdapter();
                    pieceAdapter.updateBoard();

                    if (moveFlag) {
                        try {
                            Thread.sleep(500);
                        } catch (Exception e) {
                            //do something here
                        }

                        refresh(pieceGridView);
                    }
                }
                // tell them if it isn't their turn - try to update the string from the db
                else {
                    TextView text = (TextView) findViewById(R.id.textView2);
                    text.setText("It is not your turn! Wait for AI.");
                }

            }
        });
    }
}
