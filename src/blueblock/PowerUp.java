package blueblock;

import java.awt.Color;

public class PowerUp {
	private int x, y;
	private boolean active;
	private String[] types;
	private String type;
	private boolean noRandomPosition, noRandomType;

	public PowerUp(int x, int y, boolean noRandomPosition, String type, boolean noRandomType) {
		types = new String[Main.Types.length];
		for (int i = 0; i < Main.Types.length; i++)
			types[i] = Main.Types[i];

		if (type.equals("Random"))
			type = types[new java.util.Random().nextInt(types.length)];

		this.noRandomPosition = noRandomPosition;
		this.noRandomType = noRandomType;
		NewPosition(x, y, noRandomPosition);
		for (int i = 0; i < types.length; i++) {
			if (type == types[i]) {
				this.type = type;
				return;
			}
		}
		type = types[new java.util.Random().nextInt(types.length)];
	}

	public void NewPosition(int x, int y, boolean NoRandomPosition) {
		Ground gr = Locator.GetGround(x, y);
		if (gr.IsWall() || gr.IsDeadly() || Locator.GetHuman(x, y) != null) {
			int pos1 = new java.util.Random().nextInt(Main.FieldWidth);
			int pos2 = new java.util.Random().nextInt(Main.FieldHeight);
			if (NoRandomPosition)
				NewPosition(x, y, true);
			else
				NewPosition(pos1, pos2, false);

			return;
		} else {
			if (gr.IsPoison()) {
				gr.SetGroundType(Main.Floor);
			}
			this.x = x;
			this.y = y;
		}
		if (!noRandomType) {
			type = types[new java.util.Random().nextInt(Main.Types.length)];
		}
		Main.ChangeColor(Main.labels[x][y], Color.YELLOW);
	}

	public boolean GetNotRandom() {
		return noRandomPosition;
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
