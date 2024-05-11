import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Game extends JPanel implements ActionListener {
	private class Tile {
		int x;
		int y;

		Tile(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	private class KeyPressed extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();

			if (key == KeyEvent.VK_R) {
				gameOver = false;
				directionX = 0;
				directionY = 0;
				head.x = 2;
				head.y = 2;
				body.clear();
				loop.restart();
				randomFood();
				repaint();
			}

			if (key == KeyEvent.VK_UP && directionY != 1) {
				// System.out.println("up");
				directionX = 0;
				directionY = -1;
			} else if (key == KeyEvent.VK_DOWN && directionY != -1) {
				directionX = 0;
				directionY = 1;
			} else if (key == KeyEvent.VK_LEFT && directionX != 1) {
				directionX = -1;
				directionY = 0;
			} else if (key == KeyEvent.VK_RIGHT && directionX != -1) {
				directionX = 1;
				directionY = 0;
			}
		}
	}

	// Window Config
	int boardHeight;
	int boardWidth;
	int tileSize = 20;

	Tile head;
	ArrayList<Tile> body;

	Tile food;
	Random randomxy;

	Timer loop;
	int directionX;
	int directionY;
	boolean gameOver = false;

	// KeyPressed key = new KeyPressed();

	Game(int boardHeight, int boardWidth) {
		this.boardHeight = boardHeight;
		this.boardWidth = boardWidth;
		setPreferredSize(new Dimension(this.boardHeight, this.boardWidth));
		setBackground(Color.black);
		addKeyListener(new KeyPressed());
		setFocusable(true);

		head = new Tile(2, 2);
		body = new ArrayList<Tile>();

		food = new Tile(10, 10);
		randomxy = new Random();
		randomFood();

		directionX = 0;
		directionY = 0;

		loop = new Timer(100, this);
		loop.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
		// Drawing grid
		// for (int i = 0; i < boardHeight / tileSize; i++) {
		// 	g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
		// 	g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
		// }

		// Drawing Food
		g.setColor(Color.red);
		g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);

		// Drawing Snake
		// g.setColor(Color.RGBtoHSB(43, 255, 0, null));
		g.setColor(new Color(50,205,50));
		g.fillRect(head.x * tileSize, head.y * tileSize, tileSize, tileSize);
		
		for (int i = 0; i < body.size(); i++) {
			g.setColor(new Color(0,128,0));

			g.fillRect(body.get(i).x * tileSize, body.get(i).y * tileSize, tileSize, tileSize);
		}

		g.setFont(new Font("Arial", Font.PLAIN, 20));
		if (gameOver) {
			g.setColor(Color.red);
			g.drawString("Game Over!!", boardWidth / 3 + tileSize, boardHeight / 3);
			g.drawString("Your Score: " + body.size(), boardWidth / 3 + tileSize, boardHeight / 3 + tileSize);
			g.drawString("Press 'R' to Restart", boardWidth / 3, boardHeight / 3 + tileSize + tileSize);
		} else {
			g.setColor(Color.green);
			g.drawString("Score: " + body.size(), tileSize, tileSize);
		}
	}

	public void randomFood() {
		food.x = randomxy.nextInt(boardWidth / tileSize);
		food.y = randomxy.nextInt(boardHeight / tileSize);
	}

	public void moveSnake() {

		if (collision(head, food)) {
			body.add(new Tile(food.x, food.y));
			randomFood();
		}

		for (int i = body.size() - 1; i >= 0; i--) {
			if (i == 0) {
				body.get(i).x = head.x;
				body.get(i).y = head.y;
			} else {
				body.get(i).x = body.get(i - 1).x;
				body.get(i).y = body.get(i - 1).y;
			}
		}

		head.x += directionX;
		head.y += directionY;

		for (int i = 0; i < body.size(); i++) {
			if (collision(head, body.get(i))) {
				gameOver = true;
			}
		}

		if (head.x * tileSize < 0 || head.x * tileSize > boardWidth || head.y * tileSize < 0
				|| head.y * tileSize > boardHeight) {
			gameOver = true;
		}
	}

	public boolean collision(Tile tile1, Tile tile2) {
		return tile1.x == tile2.x && tile1.y == tile2.y;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		moveSnake();
		repaint();

		if (gameOver) {
			// addKeyListener(new KeyPressed());
			loop.stop();
		}
	}
}