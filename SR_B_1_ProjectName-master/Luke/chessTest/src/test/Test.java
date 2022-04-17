package test;

public class Test {

	public static void main(String[] args) {
		Integer[][] pieceIds = new Integer[8][8];
		Integer[] mThumbIds = new Integer[64];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				pieceIds[j][i] = j + i;
			}
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.println(pieceIds[j][i]);
				mThumbIds[8 * i + j] = pieceIds[j][i];
			}
		}

	}

}
