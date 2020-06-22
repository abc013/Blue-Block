package blueblock;

import java.awt.Color;

public class PowerUp {
	// The different types
	public static final String[] Types = { "SuperScore", "OneLive", "PoisonCure", "Confusion", "MouseBlack", "PlayersGray",
	"Darkness" };

	private int x, y;
	private boolean active;
	private String type;

	public PowerUp() {
		NewPosition();
	}

	public void NewPosition() {
		int pos1 = ResourceManager.SharedRandom.nextInt(Main.FieldWidth);
		int pos2 = ResourceManager.SharedRandom.nextInt(Main.FieldHeight);
		NewPosition(pos1, pos2);
	}

	public void NewPosition(int x, int y) {
		Ground gr = Locator.GetGround(x, y);
		if (gr.GetType().IsWall() || gr.GetType().IsDeadly() || Locator.GetHuman(x, y) != null) {
			NewPosition();

			return;
		} else {
			if (gr.GetType().IsPoison())
				gr.SetGroundType(GroundType.Floor);

			this.x = x;
			this.y = y;
		}
		type = Types[ResourceManager.SharedRandom.nextInt(Types.length)];

		Main.ChangeColor(Main.labels[x][y], Color.YELLOW);
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
