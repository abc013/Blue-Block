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
	public static GroundType Schweif = new GroundType(Color.LIGHT_GRAY, "floor", null);
	public static GroundType Sourness = new GroundType(new Color(10, 240, 10), "poison", null);
	// PowerUps + Players
	public static PowerUp[] PowerUpList;
	public static ArrayList<Human> h = new ArrayList<Human>();
	public static Human H1, H2, H3, H4;
	private int Spieler;
	// For each number a background is available; WARNING! They aren't directly
	// bound to x and y!
	public static ArrayList<Ground> g = new ArrayList<Ground>();
	// Windows
	public static JFrame fenster;
	static InfoWindow InfoFenster;
	// XY coordinate-system;
	public static Label[][] labels;
	// Types of PowerUps, their booleans and Duration;
	public static String[] Types = { "SuperScore", "OneLive", "PoisonCure", "Confusion", "MouseBlack", "PlayersGray",
			"Darkness" };
	public static int[] TypesDuration = new int[Types.length];
	public static boolean[] TypesActive = new boolean[Types.length];
	// the rest;
	public static int FelderLinie, FelderReihe, MouseLava, MouseWall, MouseSäure;
	private static boolean indirekt, mouse;
	public static boolean kill;
	public static boolean EndGame = false;

	public static void main(String[] args) {
		indirekt = true;
		new Main(16, 16, 4, 2, true, true);
	}

	public Main(int Breite, int Höhe, int spieler, int PowerUps, boolean HasMouse, boolean PlayerKill) {
		PowerUpList = new PowerUp[PowerUps];
		FelderLinie = Breite;
		FelderReihe = Höhe;
		kill = PlayerKill;
		mouse = HasMouse;
		if (FelderLinie < 6) {
			FelderLinie = 6;
			System.out.println("FelderLinie can't be under six!");
		}
		if (FelderReihe < 6) {
			FelderReihe = 6;
			System.out.println("FelderReihe can't be under six!");
		}
		if (spieler <= 0 || spieler > 4) {
			System.out.println("Players can't be under 0 or above 4!");
			spieler = 1;
		}
		String Heading = null;
		switch (spieler) {
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
		fenster = new JFrame(Heading + " || Visit us on Github! --> https://github.com/abc013/Blue-Block <--");
		fenster.setLocation(-7, 0);
		fenster.setSize(700, 700);
		fenster.setBackground(Color.BLACK);
		fenster.setLayout(new GridLayout(FelderLinie, FelderReihe));
		labels = new Label[FelderLinie][FelderReihe];
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
				// if (new java.util.Random().nextInt(2) == 0); // Zufällige
				// zahl: 0 oder 1
				// else
				// label.setBackground(Color.BLACK);
				// label.addMouseListener(this);
				Ground ground = new Ground(x, y, Floor);
				g.add(ground);
				fenster.add(label);
				label.addMouseListener(this);
				labels[x][y] = label;
			}
		}
		InfoFenster = new InfoWindow(spieler);
		for (int i = 0; i < g.size(); i++)
			g.get(i).ColorRefresh();
		Spieler++;
		for (int i = 0; i < spieler; i++) {
			switch (Spieler) {
			case 1:
				final Human H1 = new Human(1, 1, Spieler, "Blauer Block");
				Main.H1 = H1;
				h.add(H1);
				break;
			case 2:
				final Human H2 = new Human(FelderLinie - 2, FelderReihe - 2, Spieler, "Grüner Block");
				Main.H2 = H2;
				h.add(H2);
				break;
			case 3:
				final Human H3 = new Human(1, FelderReihe - 2, Spieler, "Cyaner Block");
				Main.H3 = H3;
				h.add(H3);
				break;
			case 4:
				final Human H4 = new Human(FelderLinie - 2, 1, Spieler, "Magenta Block");
				Main.H4 = H4;
				h.add(H4);
				break;
			}
			Spieler++;
		}
		fenster.setDefaultCloseOperation(3);
		fenster.addMouseListener(this);
		fenster.addKeyListener(this);
		for (int i = 0; i < FelderReihe; i++) {
			Locator.GetGround(0, i).SetGroundType(Wall);
			Locator.GetGround(i, FelderLinie - 1).SetGroundType(Wall);
		}
		for (int i = 0; i < FelderLinie; i++) {
			Locator.GetGround(i, 0).SetGroundType(Wall);
			Locator.GetGround(FelderReihe - 1, i).SetGroundType(Wall);
		}
		for (int i = 0; i < PowerUps; i++) {
			int pos1 = new java.util.Random().nextInt(FelderLinie);
			int pos2 = new java.util.Random().nextInt(FelderReihe);
			PowerUp PU = new PowerUp(pos1, pos2, false, Types[new java.util.Random().nextInt(Types.length)], false);
			PowerUpList[i] = PU;
		}
		if (indirekt) {
			Offen(true);
			InfoFenster.offen(true);
		}
		Locator.VariablenAustausch(labels, Spieler);
		InfoFenster.Refresh();
		fenster.repaint();
		fenster.setAlwaysOnTop(true);
	}

	public static void FarbeWechseln(Label label, Color Color) {
		/*
		 * New Idea for future: label.setText("OOO");
		 * label.setForeground(Color);
		 */
		label.setBackground(Color);
	}

	public void Offen(boolean offen) {
		fenster.setVisible(offen);
		InfoFenster.offen(offen);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!mouse)
			return;
		String name = e.getComponent().getName();
		String letzter = name.substring(name.length() - 2);
		String vorletzter = name.substring(name.length() - 4, name.length() - 2);
		int y = Integer.parseInt(vorletzter);
		int x = Integer.parseInt(letzter);
		if (TypesActive[4])
			return;
		if (Locator.GetHuman(x, y) != null)
			return;
		if (Locator.GetPowerUp(y, x) != null) {
			return;
		}
		Ground ground = Locator.GetGround(x, y);
		if (e.getButton() == 1) {
			if (ground.isDeadly()) {
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
			if (ground.isWall()) {
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
			if (ground.isPoison()) {
				GroundType NewGround = ground.GetGroundType().GetInactiveType();

				if (NewGround != null) {
					ground.SetGroundType(NewGround);
					MouseSäure--;
				}
			} else {
				MouseSäure++;
				ground.SetGroundType(Sourness);
			}
		}
		if (EndGame)
			NewGame();
		Main.paint();
		InfoFenster.Refresh();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (!TypesActive[3]) {
			switch (String.valueOf(e.getKeyChar()).toLowerCase()) {
			// Links
			case "a":
				H1.Gehen(3);
				break;
			// Rechts
			case "d":
				H1.Gehen(4);
				break;
			// Vorne
			case "w":
				H1.Gehen(1);
				break;
			// Hinten
			case "s":
				H1.Gehen(2);
				break;
			case "g":
				H2.Gehen(3);
				break;
			case "j":
				H2.Gehen(4);
				break;
			case "z":
				H2.Gehen(1);
				break;
			case "h":
				H2.Gehen(2);
				break;
			case "k":
				H3.Gehen(3);
				break;
			case "ö":
				H3.Gehen(4);
				break;
			case "o":
				H3.Gehen(1);
				break;
			case "l":
				H3.Gehen(2);
				break;
			case "1":
				H4.Gehen(3);
				break;
			case "3":
				H4.Gehen(4);
				break;
			case "5":
				H4.Gehen(1);
				break;
			case "2":
				H4.Gehen(2);
				break;
			}
		} else {
			switch (String.valueOf(e.getKeyChar()).toLowerCase()) {
			// Links
			case "a":
				H1.Gehen(4);
				break;
			// Rechts
			case "d":
				H1.Gehen(3);
				break;
			// Vorne
			case "w":
				H1.Gehen(2);
				break;
			// Hinten
			case "s":
				H1.Gehen(1);
				break;
			case "g":
				H2.Gehen(4);
				break;
			case "j":
				H2.Gehen(3);
				break;
			case "z":
				H2.Gehen(2);
				break;
			case "h":
				H2.Gehen(1);
				break;
			case "k":
				H3.Gehen(4);
				break;
			case "ö":
				H3.Gehen(3);
				break;
			case "o":
				H3.Gehen(2);
				break;
			case "l":
				H3.Gehen(1);
				break;
			case "1":
				H4.Gehen(4);
				break;
			case "3":
				H4.Gehen(3);
				break;
			case "5":
				H4.Gehen(2);
				break;
			case "2":
				H4.Gehen(1);
				break;
			}
		}
		if (EndGame)
			NewGame();
		Main.paint();
		InfoFenster.Refresh();
		// System.out.println(e.getKeyChar());
	}

	public static void SetEffectActive(int number, int Duration) {
		TypesActive[number] = true;
		TypesDuration[number] += Duration;
	}

	public static void paint() {
		if (TypesActive[6]) {
			for (int i = 0; i < g.size(); i++) {
				Main.FarbeWechseln(labels[g.get(i).GetY()][g.get(i).GetX()], Color.BLACK);
			}
		} else {
			Locator.PlayerColorRefresh();
		}
	}

	public void NewGame() {
		System.out.println("LOL");
		Offen(false);
		InfoFenster.offen(false);
		new Menu();
		this.dispose();
		InfoFenster.dispose();

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
