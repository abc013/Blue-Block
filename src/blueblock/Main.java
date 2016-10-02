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
	public static ArrayList<Human> h = new ArrayList<Human>();
	public static Human H1, H2, H3, H4;
	private int Player;
	// For each number a background is available; WARNING! They aren't directly
	// bound to x and y!
	public static ArrayList<Ground> g = new ArrayList<Ground>();
	// Windows
	public static JFrame window;
	static InfoWindow InfoWindowObject;
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
	public static boolean EndGame = false;

	public static void main(String[] args) {
		indirect = true;
		new Main(16, 16, 4, 2, true);
	}

	public Main(int Width, int Height, int Player, int PowerUps, boolean HasMouse) {
		PowerUpList = new PowerUp[PowerUps];
		FieldColumns = Width;
		FieldRows = Height;
		mouse = HasMouse;
		if (FieldColumns < 6) {
			FieldColumns = 6;
			System.out.println("FelderLinie can't be under six!");
		}
		if (FieldRows < 6) {
			FieldRows = 6;
			System.out.println("FelderReihe can't be under six!");
		}
		if (Player <= 0 || Player > 4) {
			System.out.println("Players can't be under 0 or above 4!");
			Player = 1;
		}
		String Heading = null;
		switch (Player) {
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
			Heading = "Armer, armer alleiner BLUE BLOCK";
			break;
		}
		window = new JFrame(Heading
				+ " --- NEUER ENGINE - NEUES GLÜCK!!! Jetzt größere Welten erschaffen und neue Wände, Lava, Säure oder Böden kreieren!!!!!");
		window.setLocation(-7, 0);
		window.setSize(700, 700);
		window.setBackground(Color.BLACK);
		window.setLayout(new GridLayout(FieldColumns, FieldRows));
		labels = new Label[FieldColumns][FieldRows];
		for (int i = 0; i < labels.length; i++) {
			for (int j = 0; j < labels[i].length; j++) {
				String name = "label";
				if (i < 10)
					name += "0";

				name += i;

				if (j < 10)
					name += "0";

				name += j;

				Label label = new Label("");
				label.setName(name);
				// if (new java.util.Random().nextInt(2) == 0); // Zufällige
				// zahl: 0 oder 1
				// else
				// label.setBackground(Color.BLACK);
				// label.addMouseListener(this);
				Ground ground = new Ground(i, j, Floor);
				g.add(ground);
				window.add(label);
				label.addMouseListener(this);
				labels[i][j] = label;
			}
		}
		InfoWindowObject = new InfoWindow(Player);
		for (int i = 0; i < g.size(); i++)
			g.get(i).ColorRefresh();
		Player++;
		for (int i = 0; i < Player; i++) {
			switch (Player) {
			case 1:
				final Human H1 = new Human(1, 1, Player, "Blauer Block");
				Main.H1 = H1;
				h.add(H1);
				break;
			case 2:
				final Human H2 = new Human(FieldColumns - 2, FieldRows - 2, Player, "Grüner Block");
				Main.H2 = H2;
				h.add(H2);
				break;
			case 3:
				final Human H3 = new Human(1, FieldRows - 2, Player, "Cyaner Block");
				Main.H3 = H3;
				h.add(H3);
				break;
			case 4:
				final Human H4 = new Human(FieldColumns - 2, 1, Player, "Magenta Block");
				Main.H4 = H4;
				h.add(H4);
				break;
			}
			Player++;
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
		if (indirect) {
			onStart(true);
			InfoWindowObject.onStart(true);
		}
		Locator.SetVariables(labels, Player);
		InfoWindowObject.Refresh();
		window.repaint();
		window.setAlwaysOnTop(true);
	}

	public static void colorChange(Label label, Color Color) {
		/*
		 * New Idea for future: label.setText("OOO");
		 * label.setForeground(Color);
		 */
		label.setBackground(Color);
	}

	public void onStart(boolean onStart) {
		window.setVisible(onStart);
		InfoWindowObject.onStart(onStart);
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
		if (TypesActive[4])
			return;
		if (Locator.GetHuman(x, y) != null)
			return;
		if (Locator.GetPowerUp(y, x) != null) {
			return;
		}
		if (e.getButton() == 1) {
			if (Locator.GetGround(x, y).isDeadly()) {
				GroundType NewGround = Locator.GetGround(x, y).GetGroundType().GetInactiveType();
				if (NewGround != null) {
					Locator.GetGround(x, y).SetGroundType(NewGround);
					MouseLava--;
				}
			} else {
				MouseLava++;
				Locator.GetGround(x, y).SetGroundType(Lava);
			}
		} else if (e.getButton() == 3) {
			if (Locator.GetGround(x, y).isWall()) {
				GroundType NewGround = Locator.GetGround(x, y).GetGroundType().GetInactiveType();
				if (NewGround != null) {
					Locator.GetGround(x, y).SetGroundType(NewGround);
					MouseWall--;
				}
			} else {
				MouseWall++;
				Locator.GetGround(x, y).SetGroundType(Wall);
			}
		} else if (e.getButton() == 2) {
			if (Locator.GetGround(x, y).isPoison()) {
				GroundType NewGround = Locator.GetGround(x, y).GetGroundType().GetInactiveType();

				if (NewGround != null) {
					Locator.GetGround(x, y).SetGroundType(NewGround);
					MouseAcid--;
				}
			} else {
				MouseAcid++;
				Locator.GetGround(x, y).SetGroundType(Acid);
			}
		}
		if (EndGame)
			NewGame();
		Main.paint();
		InfoWindowObject.Refresh();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (!TypesActive[3]) {
			switch (String.valueOf(e.getKeyChar()).toLowerCase()) {
			// Left
			case "a":
				H1.Move(3);
				break;
			// Right
			case "d":
				H1.Move(4);
				break;
			// Forward
			case "w":
				H1.Move(1);
				break;
			// Back
			case "s":
				H1.Move(2);
				break;
			case "g":
				H2.Move(3);
				break;
			case "j":
				H2.Move(4);
				break;
			case "z":
				H2.Move(1);
				break;
			case "h":
				H2.Move(2);
				break;
			case "k":
				H3.Move(3);
				break;
			case "ö":
				H3.Move(4);
				break;
			case "o":
				H3.Move(1);
				break;
			case "l":
				H3.Move(2);
				break;
			case "1":
				H4.Move(3);
				break;
			case "3":
				H4.Move(4);
				break;
			case "5":
				H4.Move(1);
				break;
			case "2":
				H4.Move(2);
				break;
			}
		} else {
			switch (String.valueOf(e.getKeyChar()).toLowerCase()) {
			// Left
			case "a":
				H1.Move(4);
				break;
			// Right
			case "d":
				H1.Move(3);
				break;
			// Forward
			case "w":
				H1.Move(2);
				break;
			// Back
			case "s":
				H1.Move(1);
				break;
			case "g":
				H2.Move(4);
				break;
			case "j":
				H2.Move(3);
				break;
			case "z":
				H2.Move(2);
				break;
			case "h":
				H2.Move(1);
				break;
			case "k":
				H3.Move(4);
				break;
			case "ö":
				H3.Move(3);
				break;
			case "o":
				H3.Move(2);
				break;
			case "l":
				H3.Move(1);
				break;
			case "1":
				H4.Move(4);
				break;
			case "3":
				H4.Move(3);
				break;
			case "5":
				H4.Move(2);
				break;
			case "2":
				H4.Move(1);
				break;
			}
		}
		if (EndGame)
			NewGame();
		Main.paint();
		InfoWindowObject.Refresh();
		// System.out.println(e.getKeyChar());
	}

	public static void SetEffectActive(int number, int Duration) {
		TypesActive[number] = true;
		TypesDuration[number] += Duration;
	}

	public static void paint() {
		if (TypesActive[6]) {
			for (int i = 0; i < g.size(); i++) {
				Main.colorChange(labels[g.get(i).GetY()][g.get(i).GetX()], Color.BLACK);
			}
		} else {
			Locator.PlayerColorRefresh();
		}
	}

	public void NewGame() {
		System.out.println("LOL");
		onStart(false);
		InfoWindowObject.onStart(false);
		new Menu();
		this.dispose();
		InfoWindowObject.dispose();

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
