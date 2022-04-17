package coms309.chesssuitev1;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Luke on 10/2/2017.
 *
 * Class that is used to update the views of the pieces and powerups, but not the board itself (which is controlled by ImageAdapter).
 */

public class PieceImageAdapter extends BaseAdapter {

    private static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private Context mContext;
    private MainGameActivity theActivity;

    /**
     * Creates a PieceImageAdapter with the given context and activity.
     * @param c - The content of the Activity this will be in.
     * @param theActivity - The activity this will be placed in.
     */
    public PieceImageAdapter(Context c, MainGameActivity theActivity) {
        mContext = c;
        this.theActivity = theActivity;
    }

    // following three methods are required by super class, but not used
    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(getScreenWidth()/8, getScreenWidth()/8));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // stores the references to the different images of pieces
    private Integer[] mThumbIds = createBoardIds();

    // initializes all of the PieceGridView to have empty spaces
    // blank_square is required to prevent null pointers
    private Integer[] createBoardIds(){
        Integer[] ids = new Integer[64];
        for (int i = 0; i < 64; i++) {
            ids[i] = R.drawable.blank_square;
        }
        return ids;
    }

    /**
     * Iterates through the board and updates the ids of Pieces to be displayed
     * by changing the reference numbers in mThumbIds
     */
    public void updateBoard() {
        ChessBoard theBoard = theActivity.getTheBoard();
        Integer[][] pieceIds = new Integer[8][8];
        // get the id numbers for each spot on the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                pieceIds[j][i] = fillPictureID(theBoard.getBoard()[j][i]);
            }
        }
        // convert the 2D array into a 1D array for GridView
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                mThumbIds[8 * i + j] = pieceIds[j][i];
                notifyDataSetChanged();
            }
        }
    }

    /**
     * Returns a picture id Integer based on the given ChessSquare
     * @param spot - the ChessSquare that will determine the picture id
     */
    private Integer fillPictureID(ChessSquare spot) {
        // if no powerup present, check for pieces
        if (spot.getPowerUp() == null) {
            ChessPiece piece = spot.getOccupant();
            if (piece == null) {
                return R.drawable.blank_square;
            }
            // player 1 owns the piece
            else if (piece.getOwner() == Player.PLAYER_1) {
                if (piece.getClass() == ChessPawn.class) {
                    return R.drawable.w_pawn;
                }
                if (piece.getClass() == TempChessPawn.class) {
                    return R.drawable.w_pawn;
                }
                if (piece.getClass() == ChessKnight.class) {
                    return R.drawable.w_knight;
                }
                if (piece.getClass() == ChessBishop.class) {
                    return R.drawable.w_bishop;
                }
                if (piece.getClass() == ChessRook.class) {
                    return R.drawable.w_rook;
                }
                if (piece.getClass() == ChessQueen.class) {
                    return R.drawable.w_queen;
                }
                if (piece.getClass() == TempChessQueen.class) {
                    return R.drawable.w_queen;
                }
                if (piece.getClass() == ChessKing.class) {
                    return R.drawable.w_king;
                }
            }
            // else player 2 owns the piece
            else {
                if (piece.getClass() == ChessPawn.class) {
                    return R.drawable.b_pawn;
                }
                if (piece.getClass() == TempChessPawn.class) {
                    return R.drawable.b_pawn;
                }
                if (piece.getClass() == ChessKnight.class) {
                    return R.drawable.b_knight;
                }
                if (piece.getClass() == ChessBishop.class) {
                    return R.drawable.b_bishop;
                }
                if (piece.getClass() == ChessRook.class) {
                    return R.drawable.b_rook;
                }
                if (piece.getClass() == ChessQueen.class) {
                    return R.drawable.b_queen;
                }
                if (piece.getClass() == TempChessQueen.class) {
                    return R.drawable.b_queen;
                }
                if (piece.getClass() == ChessKing.class) {
                    return R.drawable.b_king;
                }
            }
        }
        // otherwise a powerup is present
        else {
            if (spot.getPowerUp().getClass() == SmallBombPowerUp.class) {
                return R.drawable.bomb_green;
            }
            if (spot.getPowerUp().getClass() == LargeBombPowerUp.class) {
                return R.drawable.bomb_red;
            }
            if (spot.getPowerUp().getClass() == ExtraTurnPowerUp.class) {
                return R.drawable.g_plus;
            }
            if (spot.getPowerUp().getClass() == ToQueenPowerUp.class) {
                return R.drawable.g_queen;
            }
            if (spot.getPowerUp().getClass() == ToPawnPowerUp.class) {
                return R.drawable.g_pawn;
            }
            if (spot.getPowerUp().getClass() == ConverterPowerUp.class) {
                return R.drawable.g_delta;
            }
        }
        // draws a red square to signal an error
        return R.drawable.red_square;
    }
}
