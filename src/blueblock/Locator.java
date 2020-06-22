package blueblock;

import java.awt.Color;
import java.awt.Label;

public class Locator {
	private static Label[][] labels;

	public static void ChangeVariables(Label[][] newLabels) {
		labels = newLabels;
	}

	public static PowerUp GetPowerup(int x, int y) {
		for (PowerUp powerup : Main.PowerUpList) {
			if (powerup.GetX() == x && powerup.GetY() == y)
				return powerup;
		}
		return null;
	}

	public static Ground GetGround(int x, int y) {
		for (Ground ground : Main.groundTiles) {
			if (ground.GetX() == x && ground.GetY() == y)
				return ground;
		}
		return null;
	}

	public static Human GetHuman(int x, int y) {
		for (Human human : Main.Humans) {
			if (human.GetX() == x && human.GetY() == y)
				return human;
		}
		return null;
	}

	public static void MovePlayer(Human h, int player, String direction) {
		int pos1 = h.GetX();
		int pos2 = h.GetY();
		backgroundColor(pos1, pos2);

		switch (direction) {
			case "up":
				if (pos2 > 0 && !GetGround(pos1, pos2 - 1).GetType().IsWall()) {
					pos2--;
					h.Steps++;
				} else {
					System.out.println(h + " hit the wall!");
				}
				break;
			case "down":
				if ((pos2 + 1) < Main.FieldHeight && !GetGround(pos1, pos2 + 1).GetType().IsWall()) {
					pos2++;
					h.Steps++;
				} else {
					System.out.println(h + " hit the wall!");
				}
				break;
			case "left":
				if (pos1 > 0 && !GetGround(pos1 - 1, pos2).GetType().IsWall()) {
					pos1--;
					h.Steps++;
				} else {
					System.out.println(h + " hit the wall!");
				}
				break;
			case "right":
				if ((pos1 + 1) < Main.FieldWidth && !GetGround(pos1 + 1, pos2).GetType().IsWall()) {
					pos1++;
					h.Steps++;
				} else {
					System.out.println(h + " hit the wall!");
				}
				break;
		}

		h.SetPosition(pos1, pos2);
		for (int i = 0; i < PowerUp.Types.length; i++) {
			if (Main.TypesActive[i]) {
				Main.TypesDuration[i]--;
				if (Main.TypesDuration[i] <= 0)
					Main.TypesActive[i] = false;
				if (Main.TypesActive[i] == Main.TypesActive[5]) {
					for (Human human : Main.Humans)
						human.SetGray();
				}
			}
		}

		checkKills(h);
		checkPowerups(h);

		Main.ChangeColor(labels[pos1][pos2], h.color[player]);
		Main.paint();
	}

	private static void checkKills(Human human) {
		if (!Settings.EnablePlayerKills)
			return;

		for (Human other : Main.Humans) {
			if (other != human && other.GetX() == human.GetX() && other.GetY() == human.GetY()) {
				other.IsLiving(false);
				human.Kills++;
			}
		}
	}

	private static void checkPowerups(Human human) {
		PowerUp PU = GetPowerup(human.GetX(), human.GetY());

		if (PU != null) {
			human.PowerUpEffekt(PU);
			PU.NewPosition();
		}
	}

	private static void backgroundColor(int pos1, int pos2) {
		Ground ground = GetGround(pos1, pos2);

		if (ground.GetType() == GroundType.Floor)
			ground.SetGroundType(GroundType.Trail);

		Main.ChangeColor(labels[pos1][pos2], ground.GetType().GetColor());
	}

	public static void RefreshPlayerColors() {
		for (Ground ground : Main.groundTiles)
			Main.ChangeColor(labels[ground.GetX()][ground.GetY()], ground.GetType().GetColor());

		// TODO necessary? What is this?
		for (Human human : Main.Humans) {
			if (human.Lives())
				Main.ChangeColor(labels[human.GetX()][human.GetY()], human.color[human.Player + 1]);
		}

		for (PowerUp powerup : Main.PowerUpList)
			Main.ChangeColor(labels[powerup.GetX()][powerup.GetY()], Color.YELLOW);
	}
}
