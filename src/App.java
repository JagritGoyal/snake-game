import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
		int boardHeight = 500;
		int boardWidth = 500;

		JFrame frame = new JFrame("Snake");
		frame.setVisible(true);
		frame.setSize(boardWidth, boardHeight);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Game game = new Game(boardHeight, boardWidth);
		frame.add(game);
		frame.pack();
		
		game.requestFocus();
	}
}
