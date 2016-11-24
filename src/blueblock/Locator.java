package blueblock;

import java.awt.Color;
import java.awt.Label;

public class Locator {
	private static Label[][] labels;

	public static void ChangeVariables(Label[][] newLabels, int players) {
		labels = newLabels;
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
		for (int i = 0; i < Main.groundTiles.size(); i++) {
			Ground ground = Main.groundTiles.get(i);
			if (ground.GetX() == x && ground.GetY() == y)
				return ground;
		}
		return null;
	}

	public static Human GetHuman(int x, int y) {
		if (Main.Humans.size() == 0) {
			return null;
		} else {
			for (int i = 0; i < Main.Humans.size(); i++) {
				Human human = Main.Humans.get(i);
				if (human.GetX() == x && human.GetY() == y) {
					return human;
				}
			}
		}
		return null;
	}

	public static void MovePlayer(Human h, int player, String direction) {
		int pos1 = h.GetX();
		int pos2 = h.GetY();
		backgroundColor(pos1, pos2);

		switch (direction) {
			case "up":
				if (pos2 > 0 && !GetGround(pos1, pos2 - 1).IsWall()) {
					pos2--;
					h.Steps++;
				} else {
					System.out.println(h + " hit the wall!");
				}
				break;
			case "down":
				if ((pos2 + 1) < Main.FieldHeight && !GetGround(pos1, pos2 + 1).IsWall()) {
					pos2++;
					h.Steps++;
				} else {
					System.out.println(h + " hit the wall!");
				}
				break;
			case "left":
				if (pos1 > 0 && !GetGround(pos1 - 1, pos2).IsWall()) {
					pos1--;
					h.Steps++;
				} else {
					System.out.println(h + " hit the wall!");
				}
				break;
			case "right":
				if ((pos1 + 1) < Main.FieldWidth && !GetGround(pos1 + 1, pos2).IsWall()) {
					pos1++;
					h.Steps++;
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
					for (int in = 0; in < Main.Humans.size(); in++) {
						Main.Humans.get(in).SetGray();
					}
				}
			}
		}

		Main.ChangeColor(labels[pos1][pos2], h.color[player]);
		if (Main.kill)
			checkPosition(h, pos1, pos2);

		collectPowerUp(h, pos1, pos2);
		Main.paint();
	}

	private static void checkPosition(Human human, int pos1, int pos2) {
		for (int i = 0; i < Main.Humans.size(); i++) {
			Human otherHuman = Main.Humans.get(i);
			if (otherHuman.GetX() == pos1 && otherHuman.GetY() == pos2 && otherHuman != human) {
				otherHuman.IsLiving(false);
				human.Kills++;
			}
		}
	}

	private static void collectPowerUp(Human human, int pos1, int pos2) {
		try {
			PowerUp PU = GetPowerUp(pos1, pos2);
			if (PU.GetX() == pos1 && PU.GetY() == pos2) {
				if (PU.GetNotRandom()) {
					PU.NewPosition(pos1, pos2, true);
				} else {
					int x = new java.util.Random().nextInt(Main.FieldWidth);
					int y = new java.util.Random().nextInt(Main.FieldHeight);
					PU.NewPosition(x, y, false);
				}
				human.PowerUpEffekt(PU);
			}
		} catch (Exception E) {

		}
	}

	private static void backgroundColor(int pos1, int pos2) {
		if (GetGround(pos1, pos2).GetGroundType() == Main.InactiveLava) {
			Main.ChangeColor(labels[pos1][pos2], Main.InactiveLava.GetColor());
		} else if (GetGround(pos1, pos2).GetGroundType() == Main.InactiveWall) {
			Main.ChangeColor(labels[pos1][pos2], Main.InactiveWall.GetColor());
		} else if (GetGround(pos1, pos2).GetGroundType() == Main.Acid) {
			Main.ChangeColor(labels[pos1][pos2], Main.Acid.GetColor());
		} else {
			GetGround(pos1, pos2).SetGroundType(Main.Trail);
		}
	}

	public static void RefreshPlayerColors() {
		for (int i = 0; i < Main.groundTiles.size(); i++) {
			Ground ground = Main.groundTiles.get(i);
			int x = ground.GetX();
			int y = ground.GetY();
			Main.ChangeColor(labels[x][y], ground.GetGroundType().GetColor());
		}

		for (int i = 0; i < Main.Humans.size(); i++) {
			Human currentHuman = Main.Humans.get(i);
			int x = currentHuman.GetX();
			int y = currentHuman.GetY();
			if (currentHuman.Lives()) {
				Main.ChangeColor(labels[x][y], currentHuman.color[i + 1]);
			}
		}

		for (int i = 0; i < Main.PowerUpList.length; i++) {
			int x = Main.PowerUpList[i].GetX();
			int y = Main.PowerUpList[i].GetY();
			Main.ChangeColor(labels[x][y], Color.YELLOW);
		}
	}
}
