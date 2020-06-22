package blueblock;

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
		int old1 = h.GetX();
		int old2 = h.GetY();
		int pos1 = old1;
		int pos2 = old2;

		backgroundColor(pos1, pos2);

		switch (direction) {
			case "up":
				if (pos2 > 0 && isFree(pos1, pos2 - 1)) {
					pos2--;
					h.Steps++;
				} else {
					System.out.println("Player" + player + " hit an obstacle!");
				}
				break;
			case "down":
				if ((pos2 + 1) < game.Height && isFree(pos1, pos2 + 1)) {
					pos2++;
					h.Steps++;
				} else {
					System.out.println("Player" + player + " hit an obstacle!");
				}
				break;
			case "left":
				if (pos1 > 0 && isFree(pos1 - 1, pos2)) {
					pos1--;
					h.Steps++;
				} else {
					System.out.println("Player" + player + " hit an obstacle!");
				}
				break;
			case "right":
				if ((pos1 + 1) < game.Width && isFree(pos1 + 1, pos2)) {
					pos1++;
					h.Steps++;
				} else {
					System.out.println("Player" + player + " hit an obstacle!");
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

		if (old1 != pos1 || old2 != pos2) {
			System.out.println("Player" + player + ".pos:" + pos1 + "|" + pos2);
			h.Paint();
		}
	}

	private boolean isFree(int x, int y) {
		return !GetGround(x, y).GetType().IsWall() && (Settings.EnablePlayerKills || GetHuman(x, y) == null);
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
			PU.TakeEffect(human);
			PU.NewPosition();
		}
	}

	private void backgroundColor(int pos1, int pos2) {
		Ground ground = GetGround(pos1, pos2);

		if (ground.GetType() == GroundType.Floor)
			ground.SetGroundType(GroundType.Trail);

		game.Main.ChangeColor(pos1, pos2, ground.GetType().GetColor());
	}
}
