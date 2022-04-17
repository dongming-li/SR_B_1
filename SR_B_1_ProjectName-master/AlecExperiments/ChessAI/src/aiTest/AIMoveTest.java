package aiTest;

public class AIMoveTest {
	public static void main(String[] args) throws InterruptedException {
		ChessAIMoveRequest testw = new ChessAIMoveRequest("white", "1", "1512240590");
		testw.run();
		Thread.sleep(5000);
		ChessAIMoveRequest testb = new ChessAIMoveRequest("black", "1", "1512240590");
		testb.run();
	}
}
