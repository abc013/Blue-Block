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
	public static int FieldColumns, FieldRows, MouseLava, MouseWall, MouseAcid;
	private static boolean indirect, mouse;
	public static boolean kill;
	public static boolean EndGame = false;

	public static void main(String[] args) {
		indirect = true;
		new Main(16, 16, 4, 2, true, true);
	}

	public Main(int width, int height, int playerCount, int PowerUps, boolean HasMouse, boolean PlayerKill) {
		PowerUpList = new PowerUp[PowerUps];
		FieldColumns = width;
		FieldRows = height;
		kill = PlayerKill;
		mouse = HasMouse;
		if (FieldColumns < 6) {
			FieldColumns = 6;
			System.out.println("FieldColumns can't be under six!");
		}
		if (FieldRows < 6) {
			FieldRows = 6;
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
		window.setLayout(new GridLayout(FieldColumns, FieldRows));
		labels = new Label[FieldColumns][FieldRows];
		for (int x = 0; x < labels.length; x++) {
			for (int y = 0; y < labels[x].length; y++) {
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
				final Human H2 = new Human(FieldColumns - 2, FieldRows - 2, players, "Grüner Block");
				Main.H2 = H2;
				Humans.add(H2);
				break;
			case 3:
				final Human H3 = new Human(1, FieldRows - 2, players, "Cyaner Block");
				Main.H3 = H3;
				Humans.add(H3);
				break;
			case 4:
				final Human H4 = new Human(FieldColumns - 2, 1, players, "Magenta Block");
				Main.H4 = H4;
				Humans.add(H4);
				break;
			}
			players++;
		}
		window.setDefaultCloseOperation(3);
		window.addMouseListener(this);
		window.addKeyListener(this);
		for (int i = 0; i < FieldRows; i++) {
			Locator.GetGround(0, i).SetGroundType(Wall);
			Locator.GetGround(i, FieldColumns - 1).SetGroundType(Wall);
		}
		for (int i = 0; i < FieldColumns; i++) {
			Locator.GetGround(i, 0).SetGroundType(Wall);
			Locator.GetGround(FieldRows - 1, i).SetGroundType(Wall);
		}
		for (int i = 0; i < PowerUps; i++) {
			int pos1 = new java.util.Random().nextInt(FieldColumns);
			int pos2 = new java.util.Random().nextInt(FieldRows);
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
		int y = Integer.parseInt(secondToLast);
		int x = Integer.parseInt(last);
		if (TypesActive[4] || Locator.GetHuman(x, y) != null || Locator.GetPowerUp(y, x) != null)
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
		if (!TypesActive[3]) {
			switch (String.valueOf(e.getKeyChar()).toLowerCase()) {
			// Left
			case "a":
				H1.Go("left");
				break;
			// Right
			case "d":
				H1.Go("right");
				break;
			// Up
			case "w":
				H1.Go("up");
				break;
			// Down
			case "s":
				H1.Go("down");
				break;
			case "g":
				H2.Go("left");
				break;
			case "j":
				H2.Go("right");
				break;
			case "z":
				H2.Go("up");
				break;
			case "h":
				H2.Go("down");
				break;
			case "k":
				H3.Go("left");
				break;
			case "ö":
				H3.Go("right");
				break;
			case ":":
				H3.Go("right");
				break;
			case "o":
				H3.Go("up");
				break;
			case "l":
				H3.Go("down");
				break;
			case "1":
				H4.Go("left");
				break;
			case "3":
				H4.Go("right");
				break;
			case "5":
				H4.Go("up");
				break;
			case "2":
				H4.Go("down");
				break;
			}
		} else {
			switch (String.valueOf(e.getKeyChar()).toLowerCase()) {
			// Left
			case "a":
				H1.Go("right");
				break;
			// Right
			case "d":
				H1.Go("left");
				break;
			// Up
			case "w":
				H1.Go("down");
				break;
			// Down
			case "s":
				H1.Go("up");
				break;
			case "g":
				H2.Go("right");
				break;
			case "j":
				H2.Go("left");
				break;
			case "z":
				H2.Go("down");
				break;
			case "h":
				H2.Go("up");
				break;
			case "k":
				H3.Go("right");
				break;
			case "ö":
				H3.Go("left");
				break;
			case ":":
				H3.Go("left");
				break;
			case "o":
				H3.Go("down");
				break;
			case "l":
				H3.Go("up");
				break;
			case "1":
				H4.Go("right");
				break;
			case "3":
				H4.Go("left");
				break;
			case "5":
				H4.Go("down");
				break;
			case "2":
				H4.Go("up");
				break;
			}
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
				Main.ChangeColor(labels[groundTiles.get(i).GetY()][groundTiles.get(i).GetX()], Color.BLACK);
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
