package blueblock;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Main extends JFrame implements MouseListener, KeyListener {
	public Game Game;
	// Windows
	private JFrame window;
	private InfoWindow infoWindow;
	// XY coordinate-system;
	public Label[][] labels;

	public boolean StopGame;

	public static void main(String[] args) {
		Settings.LoadSettings();
		ResourceManager.LoadResources();

		new Main(true);
	}

	public Main(boolean setOpen) {
		window = new JFrame(title());
		window.setLocation(ResourceManager.ScreenWidth / 2 - 350, ResourceManager.ScreenHeight / 2 - 350);
		window.setSize(700, 700);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.addMouseListener(this);
		window.addKeyListener(this);

		NewGame();
		infoWindow = new InfoWindow(this);

		setOpen(setOpen);

		infoWindow.Refresh();
		window.repaint();
		window.setAlwaysOnTop(true);
	}

	public void EndGame(boolean toMenu) {
		if (!toMenu) {
			NewGame();
			return;
		}
		setOpen(false);
		infoWindow.setOpen(false);
		new Menu();
	}

	public void NewGame() {
		Game = new Game(this);
		Game.Load();
	}

	public void SetMapBounds(int w, int h) {
		window.setLayout(new GridLayout(w, h));
		labels = new Label[w][h];

		// We have to create the columns first, as window.add(label) will fill each column before starting on the next row
		for (int y = 0; y < w; y++) {
			for (int x = 0; x < h; x++) {
				// Name used for identifying the label later
				Label label = new Label(x + "," + y);
				label.setName(x + "," + y);
				label.addMouseListener(this);

				window.add(label);
				labels[x][y] = label;
			}
		}
	}

	String title() {
		String title;
		switch (Settings.PlayerCount) {
			case 4:
				title = "BLUE, GREEN, CYAN and MAGENTA BLOCK";
				break;
			case 3:
				title = "BLUE, GREEN and CYAN BLOCK";
				break;
			case 2:
				title = "BLUE and GREEN BLOCK";
				break;
			case 1:
				title = "Poor, poor alone BLUE BLOCK";
				break;
			default:
				title = "8752897457 BLOCKS?!";
				break;
		}

		return title + " || On Github! https://github.com/abc013/Blue-Block";
	}

	public void ChangeColor(int x, int y, Color Color) {
		/*
		 * New Idea for future: label.setText("OOO");
		 * label.setForeground(Color);
		 */
		labels[x][y].setBackground(Color);
	}

	public void setOpen(boolean open) {
		window.setVisible(open);
		infoWindow.setOpen(open);
	}

	public void mouseClicked(MouseEvent e) {
		String name = e.getComponent().getName();
		String[] split = name.split(",");

		if (split.length == 2)
		{
			int x = Integer.parseInt(split[0]);
			int y = Integer.parseInt(split[1]);

			Game.HandleMouseInput(e.getButton(), x, y);
		}

		if (StopGame)
			EndGame(true);

		paint();
		infoWindow.Refresh();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		Game.HandleKeyInput(e.getKeyChar());

		if (StopGame)
			EndGame(true);

		paint();
		infoWindow.Refresh();
	}

	public void paint() {
		Game.Paint();
	}

	public void Clear(Color color) {
		for (int x = 0; x < Settings.Width; x++) {
			for (int y = 0; y < Settings.Height; y++)
				ChangeColor(x, y, color);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
