package coms309.gridviewtest;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Luke on 10/1/2017.
 */

public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
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
            imageView.setLayoutParams(new GridView.LayoutParams(200, 100));
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

    /**
     * Given an ArrayList of ChessSquares, it turns their color red to signal that the square is movable to
     * @param moves - the ArrayList<ChessSquares> whose colors are to change
     */
    public void updateSpotsOnGetMove(ArrayList<ChessSquare> moves) {
        for (ChessSquare spot : moves) {
            int i = spot.getRow();
            int j = spot.getCol();
            mThumbIds[8 * i + j] = R.drawable.red_square;
        }
    }

    /**
     * Function that can be called to restore the board to all black and white squares
     */
    public void restoreBoard() {
        mThumbIds = makeBoard();
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
                        board[8*i+j] = R.drawable.w_square;
                    }
                    // for odd columns
                    else {
                        board[8*i+j] = R.drawable.gray_square;
                    }
                }
                // for odd rows
                else {
                    // for even columns
                    if ( j % 2 == 0) {
                        board[8*i+j] = R.drawable.gray_square;
                    }
                    // for odd columns
                    else {
                        board[8*i+j] = R.drawable.w_square;
                    }
                }

            }
        }
        return board;
    }
}
