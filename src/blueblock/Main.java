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

public class Main extends JFrame implements MouseListener, KeyListener {
	private static final long serialVersionUID = -3251630880930749621L;
	// Basic GroundTypes;
	public static GroundType InactiveLava = new GroundType(new Color(150, 50, 50), "floor", null);
	public static GroundType Lava = new GroundType(Color.RED, "deadly", InactiveLava);
	public static GroundType InactiveWall = new GroundType(Color.DARK_GRAY, "floor", null);
	public static GroundType Wall = new GroundType(Color.BLACK, "wall", InactiveWall);
	public static GroundType Floor = new GroundType(Color.WHITE, "floor", null);
	public static GroundType Trail = new GroundType(Color.LIGHT_GRAY, "floor", null);
	public static GroundType Acid = new GroundType(new Color(10, 240, 10), "poison", null);
	// PowerUps + Players
	public static PowerUp[] PowerUpList;
	public static ArrayList<Human> Humans = new ArrayList<Human>();
	public static Human H1, H2, H3, H4;
	private int players;
	// For each number a background is available; WARNING! They aren't directly
	// bound to x and y!
	public static ArrayList<Ground> groundTiles = new ArrayList<Ground>();
	// Windows
	public static JFrame window;
	static InfoWindow infoWindow;
	// XY coordinate-system;
	public static Label[][] labels;
	// Types of PowerUps, their booleans and Duration;
	public static String[] Types = { "SuperScore", "OneLive", "PoisonCure", "Confusion", "MouseBlack", "PlayersGray",
			"Darkness" };
	public static int[] TypesDuration = new int[Types.length];
	public static boolean[] TypesActive = new boolean[Types.length];
	// the rest;
	public static int FieldHeight, FieldWidth, MouseLava, MouseWall, MouseAcid;
	private static boolean indirect, mouse;
	public static boolean kill;
	public static boolean EndGame = false;

	public static void main(String[] args) {
		indirect = true;
		new Main(16, 16, 4, 2, true, true);
	}

	public Main(int width, int height, int playerCount, int PowerUps, boolean HasMouse, boolean PlayerKill) {
		PowerUpList = new PowerUp[PowerUps];
		FieldWidth = width;
		FieldHeight = height;
		kill = PlayerKill;
		mouse = HasMouse;

		if (FieldHeight < 6) {
			FieldHeight = 6;
			System.out.println("FieldColumns can't be under six!");
		}
		if (FieldWidth < 6) {
			FieldWidth = 6;
			System.out.println("FieldRows can't be under six!");
		}

		if (playerCount <= 0 || playerCount > 4) {
			System.out.println("Players can't be under 0 or above 4!");
			playerCount = 1;
		}

		String Heading = null;
		switch (playerCount) {
		case 4:
			Heading = "BLUE, GREEN, CYAN and MAGENTA BLOCK";
			break;
		case 3:
			Heading = "BLUE, GREEN and CYAN BLOCK";
			break;
		case 2:
			Heading = "BLUE and GREEN BLOCK";
			break;
		case 1:
			Heading = "Poor, poor alone BLUE BLOCK";
			break;
		}

		window = new JFrame(Heading + " || Visit us on Github! --> https://github.com/abc013/Blue-Block <--");
		window.setLocation(-7, 0);
		window.setSize(700, 700);
		window.setBackground(Color.BLACK);
		window.setLayout(new GridLayout(FieldWidth, FieldHeight));
		labels = new Label[FieldWidth][FieldHeight];

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
				Ground ground = new Ground(x, y, Floor);
				groundTiles.add(ground);
				window.add(label);
				label.addMouseListener(this);
				labels[x][y] = label;
			}
		}

		infoWindow = new InfoWindow(playerCount);
		for (int i = 0; i < groundTiles.size(); i++)
			groundTiles.get(i).RefreshColor();

		players++;
		for (int i = 0; i < playerCount; i++) {
			switch (players) {
			case 1:
				final Human H1 = new Human(1, 1, players, "Blauer Block");
				Main.H1 = H1;
				Humans.add(H1);
				break;
			case 2:
				final Human H2 = new Human(FieldWidth - 2, FieldHeight  - 2, players, "Grüner Block");
				Main.H2 = H2;
				Humans.add(H2);
				break;
			case 3:
				final Human H3 = new Human(1, FieldHeight - 2, players, "Cyaner Block");
				Main.H3 = H3;
				Humans.add(H3);
				break;
			case 4:
				final Human H4 = new Human(FieldWidth - 2, 1, players, "Magenta Block");
				Main.H4 = H4;
				Humans.add(H4);
				break;
			}
			players++;
		}
		window.setDefaultCloseOperation(3);
		window.addMouseListener(this);
		window.addKeyListener(this);

		for (int i = 0; i < FieldHeight; i++) {
			Locator.GetGround(0, i).SetGroundType(Wall);
			Locator.GetGround(FieldWidth - 1, i).SetGroundType(Wall);
		}
		for (int i = 0; i < FieldWidth; i++) {
			Locator.GetGround(i, 0).SetGroundType(Wall);
			Locator.GetGround(i, FieldHeight - 1).SetGroundType(Wall);
		}

		for (int i = 0; i < PowerUps; i++) {
			int pos1 = new java.util.Random().nextInt(FieldWidth);
			int pos2 = new java.util.Random().nextInt(FieldHeight);
			PowerUp PU = new PowerUp(pos1, pos2, false, Types[new java.util.Random().nextInt(Types.length)], false);
			PowerUpList[i] = PU;
		}

		if (indirect)
			setOpen(true);

		Locator.ChangeVariables(labels, players);
		infoWindow.Refresh();
		window.repaint();
		window.setAlwaysOnTop(true);
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
		if (TypesActive[4] || Locator.GetHuman(x, y) != null || Locator.GetPowerUp(x, y) != null)
			return;

		Ground ground = Locator.GetGround(x, y);
		if (e.getButton() == 1) {
			if (ground.IsDeadly()) {
				GroundType NewGround = ground.GetGroundType().GetInactiveType();
				if (NewGround != null) {
					ground.SetGroundType(NewGround);
					MouseLava--;
				}
			} else {
				MouseLava++;
				ground.SetGroundType(Lava);
			}
		} else if (e.getButton() == 3) {
			if (ground.IsWall()) {
				GroundType NewGround = ground.GetGroundType().GetInactiveType();
				if (NewGround != null) {
					ground.SetGroundType(NewGround);
					MouseWall--;
				}
			} else {
				MouseWall++;
				ground.SetGroundType(Wall);
			}
		} else if (e.getButton() == 2) {
			if (ground.IsPoison()) {
				GroundType NewGround = ground.GetGroundType().GetInactiveType();

				if (NewGround != null) {
					ground.SetGroundType(NewGround);
					MouseAcid--;
				}
			} else {
				MouseAcid++;
				ground.SetGroundType(Acid);
			}
		}

		if (EndGame)
			NewGame();

		Main.paint();
		infoWindow.Refresh();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		boolean confused = TypesActive[3];
		String input = String.valueOf(e.getKeyChar()).toLowerCase();

		switch (input) {
			// Left
			case "a":
				H1.Go(confused ? "right" : "left");
				break;
			// Right
			case "d":
				H1.Go(confused ? "left" : "right");
				break;
			// Up
			case "w":
				H1.Go(confused ? "down" : "up");
				break;
			// Down
			case "s":
				H1.Go(confused ? "up" : "down");
				break;
			case "g":
				H2.Go(confused ? "right" : "left");
				break;
			case "j":
				H2.Go(confused ? "left" : "right");
				break;
			case "z":
				H2.Go(confused ? "down" : "up");
				break;
			case "h":
				H2.Go(confused ? "up" : "down");
				break;
			case "k":
				H3.Go(confused ? "right" : "left");
				break;
			case "ö":
				H3.Go(confused ? "left" : "right");
				break;
			case ":":
				H3.Go(confused ? "left" : "right");
				break;
			case "o":
				H3.Go(confused ? "down" : "up");
				break;
			case "l":
				H3.Go(confused ? "up" : "down");
				break;
			case "1":
				H4.Go(confused ? "right" : "left");
				break;
			case "3":
				H4.Go(confused ? "left" : "right");
				break;
			case "5":
				H4.Go(confused ? "down" : "up");
				break;
			case "2":
				H4.Go(confused ? "up" : "down");
				break;
		}

		if (EndGame)
			NewGame();

		Main.paint();
		infoWindow.Refresh();
		// System.out.println(e.getKeyChar());
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
