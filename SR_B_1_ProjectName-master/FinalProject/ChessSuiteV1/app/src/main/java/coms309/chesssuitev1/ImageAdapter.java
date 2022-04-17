package coms309.chesssuitev1;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Luke on 10/1/2017.
 *
 * Class that is used to update the view of the board itself - the pieces and powerups' views are stored in PieceImageAdapter.
 */

public class ImageAdapter extends BaseAdapter {

    private static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private Context mContext;
    int moveSquaresColor;
    int whiteSquaresColor;
    int blackSquaresColor;
    int lastMoveSquaresColor;
    int moveSquaresLightColor;

    /**
     * Creates a new ImageAdapter on the given context, with three int values that represent the color of the different squares.
     * @param c - Context to place the ImageAdapter
     * @param whiteSquares - Selects the color of the white Squares.
     * @param blackSquares - Selects the color of the black Squares.
     * @param moveSquares - Selects the color of the move Squares.
     */
    public ImageAdapter(Context c, int whiteSquares, int blackSquares, int moveSquares) {
        mContext = c;
        moveSquaresColor = moveSquares;
        whiteSquaresColor = whiteSquares;
        blackSquaresColor = blackSquares;
        lastMoveSquaresColor = 0;
        moveSquaresLightColor = 0;
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

    // holds Integers that reference either a black or white square initially
    private Integer[] mThumbIds = makeBoard();

    private Integer getMoveSquaresColor() {
        if (moveSquaresColor == 0) {
            return R.drawable.red_square;
        }
        if (moveSquaresColor == 1) {
            return R.drawable.red_square;
        }
        if (moveSquaresColor == 2) {
            return R.drawable.red_square;
        }
        // in case of an incorrect value, show red as default
        else {
            return R.drawable.red_square;
        }
    }

    private Integer getMoveSquaresLightColor() {
        if (moveSquaresLightColor == 0) {
            return R.drawable.pink_square;
        }
        if (moveSquaresLightColor == 1) {
            return R.drawable.pink_square;
        }
        if (moveSquaresLightColor == 2) {
            return R.drawable.pink_square;
        }
        // in case of an incorrect value, show red as default
        else {
            return R.drawable.pink_square;
        }
    }

    private Integer getWhiteSquaresColor() {
        if (whiteSquaresColor == 0) {
            return R.drawable.w_square;
        } else if (whiteSquaresColor == 1) {
            return R.drawable.w_square;
        }
        else if (whiteSquaresColor == 2) {
            return R.drawable.w_square;
        }
        // in case of an incorrect value, show white as default
        else {
            return R.drawable.w_square;
        }
    }

    private Integer getBlackSquaresColor() {
        if (blackSquaresColor == 0) {
            return R.drawable.gray_square;
        }
        else if (blackSquaresColor == 1) {
            return R.drawable.blue_square;
        }
        else if (blackSquaresColor == 2) {
            return R.drawable.brown_square;
        }
        // in case of an incorrect value, show gray as default
        else {
            return R.drawable.gray_square;
        }
    }

    private Integer getLastMoveSquaresColor() {
        if (lastMoveSquaresColor == 0) {
            return R.drawable.yellow_square;
        }
        // in case of an incorrect value, show yellow as default
        else {
            return R.drawable.yellow_square;
        }
    }

    /**
     * Given an ArrayList of ChessSquares, it turns their color red to signal that the square is movable to
     * @param moves - the ArrayList<ChessSquares> whose colors are to change
     */
    public void updateSpotsOnGetMove(ArrayList<ChessSquare> moves) {
        for (ChessSquare spot : moves) {
            int i = spot.getRow();
            int j = spot.getCol();
            // for even rows
            if (i % 2 == 0) {
                // for even columns
                if (j % 2 == 0) {
                    mThumbIds[8 * i + j] = getMoveSquaresLightColor();
                }
                // for odd columns
                else {
                    mThumbIds[8*i+j] = getMoveSquaresColor();
                }
            }
            // for odd rows
            else {
                // for even columns
                if ( j % 2 == 0) {
                    mThumbIds[8*i+j] = getMoveSquaresColor();
                }
                // for odd columns
                else {
                    mThumbIds[8*i+j] = getMoveSquaresLightColor();
                }
            }
        }
    }

    /**
     * Given an ArrayList of the Squares that are in the most recent move, change the color to show the most recent move
     * @param theMove - the list of ChessSquares that were in the most recent move
     */
    public void updateSpotsAfterMove(ArrayList<ChessSquare> theMove) {
        for (ChessSquare spot : theMove) {
            int i = spot.getRow();
            int j = spot.getCol();
            mThumbIds[8 * i + j] = getLastMoveSquaresColor();
        }
    }

    /**
     * Function that can be called to restore the board to all black and white squares - except squares of the most recent move.
     */
    public void restoreBoard(ArrayList<ChessSquare> lastMove) {
        mThumbIds = makeBoard();
        if (lastMove != null) {
            updateSpotsAfterMove(lastMove);
        }
    }

    // places black and white square references in a chessboard pattern into the returned Integer[]
    private Integer[] makeBoard() {
        Integer[] board = new Integer[64];
        // cycle through each spot on the board
        for (int i = 0; i < board.length/8; i++) {
            for (int j = 0; j < board.length/8; j++) {
                // for even rows
                if (i % 2 == 0) {
                    // for even columns
                    if (j % 2 == 0) {
                        board[8 * i + j] = getWhiteSquaresColor();
                    }
                    // for odd columns
                    else {
                        board[8*i+j] = getBlackSquaresColor();
                    }
                }
                // for odd rows
                else {
                    // for even columns
                    if ( j % 2 == 0) {
                        board[8*i+j] = getBlackSquaresColor();
                    }
                    // for odd columns
                    else {
                        board[8*i+j] = getWhiteSquaresColor();
                    }
                }

            }
        }
        return board;
    }
}
