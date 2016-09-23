package blueblock;

import java.awt.Color;

public class PowerUp {
	private int x, y;
	private boolean Aktiv;
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
		// TODO: Power-Up types: + 1 Live, Cure from Poison, Coloring all
		// Players Gray, verwirrung(verdrehte Steuerung), Maus-Black-out (Kann
		// nichts tun), Super Score, darkness;
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
		if (Locator.GetGround(x, y).isWall() || Locator.GetGround(x, y).isDeadly() || Locator.GetHuman(x, y) != null) {
			int pos1 = new java.util.Random().nextInt(Main.FelderLinie);
			int pos2 = new java.util.Random().nextInt(Main.FelderReihe);
			if (NoRandomPosition)
				NewPosition(x, y, true);
			else
				NewPosition(pos1, pos2, false);
			return;
		} else {
			if (Locator.GetGround(x, y).isPoison()) {
				Locator.GetGround(x, y).SetGroundType(Main.Floor);
			}
			this.x = x;
			this.y = y;
		}
		if (!NoRandomType) {
			Type = Types[new java.util.Random().nextInt(Main.Types.length)];
		}
		Main.FarbeWechseln(Main.labels[x][y], Color.YELLOW);
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

	public boolean IstAktiv() {
		return Aktiv;
	}
}
