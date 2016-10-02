package blueblock;

import java.awt.Color;

public class PowerUp {
	private int x, y;
	private boolean Active;
	private String[] Types;
	private String Type;
	private boolean NoRandomPosition, NoRandomType;

	public PowerUp(int x, int y, boolean NoRandomPosition, String Type, boolean NoRandomType) {
		Types = new String[Main.Types.length];
		for (int i = 0; i < Main.Types.length; i++)
			Types[i] = Main.Types[i];
		if (Type.equals("Random"))
			Type = Types[new java.util.Random().nextInt(Types.length)];
		this.NoRandomPosition = NoRandomPosition;
		this.NoRandomType = NoRandomType;
		NewPosition(x, y, NoRandomPosition);
		for (int i = 0; i < Types.length; i++) {
			if (Type == Types[i]) {
				this.Type = Type;
				return;
			}
		}
		Type = Types[new java.util.Random().nextInt(Types.length)];
	}

	public void NewPosition(int x, int y, boolean NoRandomPosition) {
		Ground gr = Locator.GetGround(y, x);
		if (gr.isWall() || gr.isDeadly() || Locator.GetHuman(y, x) != null) {
			int pos1 = new java.util.Random().nextInt(Main.FieldColumns);
			int pos2 = new java.util.Random().nextInt(Main.FieldRows);
			if (NoRandomPosition)
				NewPosition(x, y, true);
			else
				NewPosition(pos1, pos2, false);

			return;
		} else {
			if (gr.isPoison()) {
				gr.SetGroundType(Main.Floor);
			}
			this.x = x;
			this.y = y;
		}
		if (!NoRandomType) {
			Type = Types[new java.util.Random().nextInt(Main.Types.length)];
		}
		Main.colorChange(Main.labels[x][y], Color.YELLOW);
	}

	public boolean GetNotRandom() {
		return NoRandomPosition;
	}

	public int GetX() {
		return x;
	}

	public int GetY() {
		return y;
	}

	public String GetType() {
		return Type;
	}

	public boolean IstActive() {
		return Active;
	}
}
