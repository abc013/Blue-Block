package blueblock;

import java.awt.Color;

public class PowerUp {
	// The different types
	public static final String[] Types = { "SuperScore", "OneLive", "PoisonCure", "Confusion", "MouseBlack", "PlayersGray", "Darkness" };

	private Game game;
	private int x, y;
	private boolean active;
	private String type;

	public PowerUp(Game game) {
		this.game = game;
		NewPosition();
	}

	public void NewPosition() {
		int pos1 = ResourceManager.SharedRandom.nextInt(game.Width);
		int pos2 = ResourceManager.SharedRandom.nextInt(game.Height);
		NewPosition(pos1, pos2);
	}

	public void NewPosition(int x, int y) {
		Ground gr = game.Locator.GetGround(x, y);
		GroundType grt = gr.GetType();

		if (grt.IsWall() || grt.IsDeadly() || game.Locator.GetHuman(x, y) != null) {
			NewPosition();

			return;
		}

		if (grt.IsPoison())
			gr.SetGroundType(GroundType.Floor);

		this.x = x;
		this.y = y;
		type = Types[ResourceManager.SharedRandom.nextInt(Types.length)];

		game.Main.ChangeColor(x, y, Color.YELLOW);
	}

	public int GetX() {
		return x;
	}

	public int GetY() {
		return y;
	}

	public String GetType() {
		return type;
	}

	public boolean IsActive() {
		return active;
	}
}
