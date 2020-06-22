package blueblock;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Main extends JFrame implements MouseListener, KeyListener {
	// PowerUps + Players
	public static PowerUp[] PowerUpList;
	public static Human[] Humans;
	// For each number a background is available; WARNING! They aren't directly
	// bound to x and y!
	public static ArrayList<Ground> groundTiles = new ArrayList<Ground>();
	// Windows
	public static JFrame window;
	static InfoWindow infoWindow;
	// XY coordinate-system;
	public static Label[][] labels;
	// Types of PowerUps, their booleans and Duration;
	public static int[] TypesDuration = new int[PowerUp.Types.length];
	public static boolean[] TypesActive = new boolean[PowerUp.Types.length];
	// the rest;
	public static int FieldHeight, FieldWidth, MouseLava, MouseWall, MouseAcid;
	private static boolean mouse;
	public static boolean kill;
	public static boolean EndGame = false;

	public static void main(String[] args) {
		Settings.LoadSettings();
		ResourceManager.LoadResources();

		new Main(true);
	}

	public Main(boolean setOpen) {
		FieldWidth = Settings.Width;
		FieldHeight = Settings.Height;

		PowerUpList = new PowerUp[Settings.PowerupCount];
		Humans = new Human[Settings.PlayerCount];
		kill = Settings.EnablePlayerKills;
		mouse = Settings.EnableMouse;

		window = new JFrame(title());
		window.setLocation(ResourceManager.ScreenWidth / 2 - 350, ResourceManager.ScreenHeight / 2 - 350);
		window.setSize(700, 700);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.addMouseListener(this);
		window.addKeyListener(this);

		window.setLayout(new GridLayout(FieldWidth, FieldHeight));
		labels = new Label[FieldWidth][FieldHeight];

		infoWindow = new InfoWindow();

		// We have to create the columns first, as window.add(label) will fill each column before starting on the next row
		for (int y = 0; y < FieldHeight; y++) {
			for (int x = 0; x < FieldWidth; x++) {
				String name = "label";
				if (x < 10)
					name += "0";

				name += x;

				if (y < 10)
					name += "0";

				name += y;

				Label label = new Label("");
				label.setName(name);
				Ground ground = new Ground(x, y, GroundType.Floor);
				groundTiles.add(ground);
				window.add(label);
				label.addMouseListener(this);
				labels[x][y] = label;
			}
		}

		// Generate walls surrounding the playground
		for (int i = 0; i < FieldHeight; i++) {
			Locator.GetGround(0, i).SetGroundType(GroundType.Wall);
			Locator.GetGround(FieldWidth - 1, i).SetGroundType(GroundType.Wall);
		}
		for (int i = 0; i < FieldWidth; i++) {
			Locator.GetGround(i, 0).SetGroundType(GroundType.Wall);
			Locator.GetGround(i, FieldHeight - 1).SetGroundType(GroundType.Wall);
		}

		for (Ground ground : groundTiles)
			ground.RefreshColor();

		// Generate players
		for (int i = 0; i < Settings.PlayerCount; i++) {
			switch (i) {
				case 0:
					final Human H1 = new Human(1, 1, i, "Blue Block");
					Humans[0] = H1;
					break;
				case 1:
					final Human H2 = new Human(FieldWidth - 2, FieldHeight  - 2, i, "Green Block");
					Humans[1] = H2;
					break;
				case 2:
					final Human H3 = new Human(1, FieldHeight - 2, i, "Cyan Block");
					Humans[2] = H3;
					break;
				case 3:
					final Human H4 = new Human(FieldWidth - 2, 1, i, "Magenta Block");
					Humans[3] = H4;
					break;
			}
		}

		// Generate powerups
		for (int i = 0; i < Settings.PowerupCount; i++)
			PowerUpList[i] = new PowerUp();

		setOpen(setOpen);

		Locator.ChangeVariables(labels);
		infoWindow.Refresh();
		window.repaint();
		window.setAlwaysOnTop(true);
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

		return title + " || Available on Github! --> https://github.com/abc013/Blue-Block <--";
	}

	public static void ChangeColor(Label label, Color Color) {
		/*
		 * New Idea for future: label.setText("OOO");
		 * label.setForeground(Color);
		 */
		label.setBackground(Color);
	}

	public void setOpen(boolean open) {
		window.setVisible(open);
		infoWindow.setOpen(open);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!mouse)
			return;

		String name = e.getComponent().getName();
		String last = name.substring(name.length() - 2);
		String secondToLast = name.substring(name.length() - 4, name.length() - 2);
		int x = Integer.parseInt(secondToLast);
		int y = Integer.parseInt(last);
		if (TypesActive[4] || Locator.GetHuman(x, y) != null || Locator.GetPowerup(x, y) != null)
			return;

		Ground ground = Locator.GetGround(x, y);
		GroundType type = ground.GetType();
		if (e.getButton() == 1) {
			if (type.IsDeadly()) {
				GroundType NewGround = type.GetInactiveType();
				if (NewGround != null) {
					ground.SetGroundType(NewGround);
					MouseLava--;
				}
			} else {
				MouseLava++;
				ground.SetGroundType(GroundType.Lava);
			}
		} else if (e.getButton() == 3) {
			if (type.IsWall()) {
				GroundType NewGround = type.GetInactiveType();
				if (NewGround != null) {
					ground.SetGroundType(NewGround);
					MouseWall--;
				}
			} else {
				MouseWall++;
				ground.SetGroundType(GroundType.Wall);
			}
		} else if (e.getButton() == 2) {
			if (type.IsPoison()) {
				GroundType NewGround = type.GetInactiveType();

				if (NewGround != null) {
					ground.SetGroundType(NewGround);
					MouseAcid--;
				}
			} else {
				MouseAcid++;
				ground.SetGroundType(GroundType.Acid);
			}
		}

		if (EndGame)
			NewGame();

		Main.paint();
		infoWindow.Refresh();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		final char[][] keys = {
			{ 'a', 'd', 'w', 's' },
			{ 'g', 'j', 'z', 'h' },
			{ 'k', 'ö', 'o', 'l' },
			{ '1', '3', '5', '2' },
		};

		boolean confused = TypesActive[3];
		char input = e.getKeyChar();

		for (int i = 0; i < Settings.PlayerCount; i++)
		{
			if (input == keys[i][0]) // Left
				Humans[i].Go(confused ? "right" : "left");
			else if (input == keys[i][1]) // Right
				Humans[i].Go(confused ? "left" : "right");
			else if (input == keys[i][2]) // Up
				Humans[i].Go(confused ? "down" : "up");
			else if (input == keys[i][3]) // Down
				Humans[i].Go(confused ? "up" : "down");
		}

		if (EndGame)
			NewGame();

		Main.paint();
		infoWindow.Refresh();
	}

	public static void SetEffectActive(int number, int Duration) {
		TypesActive[number] = true;
		TypesDuration[number] += Duration;
	}

	public static void paint() {
		if (TypesActive[6]) {
			for (int i = 0; i < groundTiles.size(); i++) {
				Main.ChangeColor(labels[groundTiles.get(i).GetX()][groundTiles.get(i).GetY()], Color.BLACK);
			}
		} else {
			Locator.RefreshPlayerColors();
		}
	}

	public void NewGame() {
		setOpen(false);
		infoWindow.setOpen(false);
		new Menu();
		this.dispose();
		infoWindow.dispose();
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
