package blueblock;

import java.awt.Color;

public class Locator {
	private Game game;

	public Locator(Game game) {
		this.game = game;
	}

	public PowerUp GetPowerup(int x, int y) {
		for (PowerUp powerup : game.PowerUpList) {
			if (powerup.GetX() == x && powerup.GetY() == y)
				return powerup;
		}
		return null;
	}

	public Ground GetGround(int x, int y) {
		if (x < 0 || y < 0 || x >= game.Width || y >= game.Height)
			return null;

		return game.GroundTiles[x][y];
	}

	public Human GetHuman(int x, int y) {
		for (Human human : game.Humans) {
			if (human.GetX() == x && human.GetY() == y)
				return human;
		}
		return null;
	}

	public void MovePlayer(Human h, int player, String direction) {
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
				if ((pos2 + 1) < game.Height && !GetGround(pos1, pos2 + 1).GetType().IsWall()) {
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
				if ((pos1 + 1) < game.Width && !GetGround(pos1 + 1, pos2).GetType().IsWall()) {
					pos1++;
					h.Steps++;
				} else {
					System.out.println(h + " hit the wall!");
				}
				break;
		}

		h.SetPosition(pos1, pos2);
		for (int i = 0; i < PowerUp.Types.length; i++) {
			if (game.TypesActive[i]) {
				game.TypesDuration[i]--;
				if (game.TypesDuration[i] <= 0)
					game.TypesActive[i] = false;
				if (game.TypesActive[i] == game.TypesActive[5]) {
					for (Human human : game.Humans)
						human.SetGray();
				}
			}
		}

		checkKills(h);
		checkPowerups(h);

		game.Main.ChangeColor(pos1, pos2, h.color[player]);
		game.Main.paint();
	}

	private void checkKills(Human human) {
		if (!Settings.EnablePlayerKills)
			return;

		for (Human other : game.Humans) {
			if (other != human && other.GetX() == human.GetX() && other.GetY() == human.GetY()) {
				other.IsLiving(false);
				human.Kills++;
			}
		}
	}

	private void checkPowerups(Human human) {
		PowerUp PU = GetPowerup(human.GetX(), human.GetY());

		if (PU != null) {
			human.PowerUpEffect(PU);
			PU.NewPosition();
		}
	}

	private void backgroundColor(int pos1, int pos2) {
		Ground ground = GetGround(pos1, pos2);

		if (ground.GetType() == GroundType.Floor)
			ground.SetGroundType(GroundType.Trail);

		game.Main.ChangeColor(pos1, pos2, ground.GetType().GetColor());
	}

	public void RefreshPlayerColors() {
		for (int x = 0; x < Settings.Width; x++) {
			for (int y = 0; y < Settings.Height; y++)
				game.Main.ChangeColor(x, y, GetGround(x, y).GetType().GetColor());
		}

		// TODO necessary? What is this?
		for (Human human : game.Humans) {
			if (human.Lives())
				game.Main.ChangeColor(human.GetX(), human.GetY(), human.color[human.Player + 1]);
		}

		for (PowerUp powerup : game.PowerUpList)
			game.Main.ChangeColor(powerup.GetX(), powerup.GetY(), Color.YELLOW);
	}
}
