package boardArray;

import java.util.ArrayList;

public class BoardTest {

	public static void main(String[] args) {
		BoardArray b1 = new BoardArray();
		b1.printBoardName();
		
		Pawn p1 = new Pawn("Pawn1", Player.PLAYER_1, b1.getBoard()[3][4]);
		Pawn p2 = new Pawn("Pawn2", Player.PLAYER_2, b1.getBoard()[2][3]);
		Rook r1 = new Rook("Rook1", Player.PLAYER_1, b1.getBoard()[3][3]);
		ArrayList<Spot> test = p1.getMoves(b1, b1.getBoard()[3][4]);
		ArrayList<Spot> test2 = r1.getMoves(b1, b1.getBoard()[3][3]);
		for (Spot e : test) {
			System.out.println(e.getName());
		}
		System.out.println("=======");
		for (Spot e : test2) {
			System.out.println(e.getName());
		}
	}

}
