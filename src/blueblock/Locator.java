package blueblock;

import java.awt.Color;
import java.awt.Label;

public class Locator {
	private static Label[][] labels;

	public static void VariablenAustausch(Label[][] Labels, int Spieler) {
		labels = Labels;
	}

	public static PowerUp GetPowerUp(int x, int y) {
		for (int i = 0; i < Main.PowerUpList.length; i++) {
			PowerUp powerup = Main.PowerUpList[i];
			if (powerup.GetX() == x && powerup.GetY() == y)
				return powerup;
		}
		return null;
	}

	public static Ground GetGround(int x, int y) {
		for (int i = 0; i < Main.g.size(); i++) {
			Ground ground = Main.g.get(i);
			// System.out.print("ground: " + ground + " | " + ground.GetX() + "
			// "
			// + ground.GetY() + "***");
			// System.out.println();
			if (ground.GetX() == x && ground.GetY() == y)
				return ground;
		}
		return null;
	}

	public static Human GetHuman(int x, int y) {
		if (Main.h.size() == 0) {
			return null;
		} else {
			for (int i = 0; i < Main.h.size(); i++) {
				Human human = Main.h.get(i);
				// System.out.print("Human: " + human + " | " + human.GetX() + "
				// "
				// + human.GetY() + "***");
				// System.out.println();
				if (human.GetX() == x && human.GetY() == y) {
					return human;
				}
			}
		}
		return null;
	}

	public static void MenschGeht(Human h, int Spieler, int Richtung) {
		int pos1 = h.GetY();
		int pos2 = h.GetX();
		int Y0 = h.GetY() - 1;
		if (Y0 < 0)
			Y0 = 0;
		int YG = h.GetY() + 1;
		if (YG >= Main.FelderReihe)
			YG = h.GetY();
		int X0 = h.GetX() - 1;
		if (X0 < 0)
			X0 = 0;
		int XG = h.GetX() + 1;
		if (XG >= Main.FelderLinie)
			XG = h.GetX();
		HinterGrundFarbe(pos1, pos2);
		switch (Richtung) {
		case 1:
			if (pos1 > 0 && !GetGround(h.GetX(), Y0).isWall()) {
				pos1--;
				h.schritte++;
			} else {
				System.out.println(h + " hit the wall!");
			}
			break;
		case 2:
			if ((h.GetY() + 1) < Main.FelderReihe && !GetGround(h.GetX(), YG).isWall()) {
				pos1++;
				h.schritte++;
			} else {
				System.out.println(h + " hit the wall!");
			}
			break;
		case 3:
			if (h.GetX() > 0 && !GetGround(X0, h.GetY()).isWall()) {
				pos2--;
				h.schritte++;
			} else {
				System.out.println(h + " hit the wall!");
			}
			break;
		case 4:
			if ((h.GetX() + 1) < Main.FelderLinie && !GetGround(XG, h.GetY()).isWall()) {
				pos2++;
				h.schritte++;
			} else {
				System.out.println(h + " hit the wall!");
			}
			break;
		}
		h.SetPosition(pos1, pos2);
		for (int i = 0; i < Main.Types.length; i++) {
			if (Main.TypesActive[i]) {
				Main.TypesDuration[i]--;
				if (Main.TypesDuration[i] <= 0)
					Main.TypesActive[i] = false;
				if (Main.TypesActive[i] == Main.TypesActive[5]) {
					for (int in = 0; in < Main.h.size(); in++) {
						Main.h.get(in).SetGray();
					}
				}
			}
		}
		Main.FarbeWechseln(labels[pos1][pos2], h.color[Spieler]);
		if (Main.kill)
			MenschenTreffen(h, pos1, pos2);
		PowerUpEinsammeln(h, pos1, pos2);
		Main.paint();
	}

	private static void MenschenTreffen(Human human, int pos1, int pos2) {
		for (int i = 0; i < Main.h.size(); i++) {
			if (Main.h.get(i).GetX() == pos2 && Main.h.get(i).GetY() == pos1 && Main.h.get(i) != human) {
				Main.h.get(i).IsLiving(false);
				human.Kills = human.Kills + 1;
			}
		}
	}

	private static void PowerUpEinsammeln(Human human, int pos1, int pos2) {
		try {
			PowerUp PU = GetPowerUp(pos1, pos2);
			if (PU.GetX() == pos1 && PU.GetY() == pos2) {
				if (PU.GetNotRandom()) {
					PU.NewPosition(pos1, pos2, true);
				} else {
					int x = new java.util.Random().nextInt(Main.FelderLinie);
					int y = new java.util.Random().nextInt(Main.FelderReihe);
					PU.NewPosition(x, y, false);
				}
				human.PowerUpEffekt(PU);
			}
		} catch (Exception E) {

		}
	}

	private static void HinterGrundFarbe(int pos1, int pos2) {
		if (GetGround(pos2, pos1).GetGroundType() == Main.InactiveLava) {
			Main.FarbeWechseln(labels[pos1][pos2], Main.InactiveLava.GetColor());
		} else if (GetGround(pos2, pos1).GetGroundType() == Main.InactiveWall) {
			Main.FarbeWechseln(labels[pos1][pos2], Main.InactiveWall.GetColor());
		} else if (GetGround(pos2, pos1).GetGroundType() == Main.Sourness) {
			Main.FarbeWechseln(labels[pos1][pos2], Main.Sourness.GetColor());
		} else {
			GetGround(pos2, pos1).SetGroundType(Main.Schweif);
		}
	}

	public static void PlayerColorRefresh() {
		for (int i = 0; i < Main.g.size(); i++) {
			int x = Main.g.get(i).GetX();
			int y = Main.g.get(i).GetY();
			Main.FarbeWechseln(labels[y][x], Main.g.get(i).GetGroundType().GetColor());
		}
		for (int i = 0; i < Main.h.size(); i++) {
			int x = Main.h.get(i).GetX();
			int y = Main.h.get(i).GetY();
			if (Main.h.get(i).Lives()) {
				Main.FarbeWechseln(labels[y][x], Main.h.get(i).color[i + 1]);
			}
		}
		for (int i = 0; i < Main.PowerUpList.length; i++) {
			int x = Main.PowerUpList[i].GetX();
			int y = Main.PowerUpList[i].GetY();
			Main.FarbeWechseln(labels[x][y], Color.YELLOW);
		}
	}
}
