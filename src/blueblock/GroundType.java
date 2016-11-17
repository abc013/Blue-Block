package blueblock;

import java.awt.Color;

public class GroundType {
	private Color color;
	private String type;
	private GroundType inactiveType;

	public GroundType(Color color, String type, GroundType inactiveType) {
		this.color = color;
		this.inactiveType = inactiveType;

		if (type != null)
			this.type = type;
	}

	public Color GetColor() {
		return color;
	}

	public String GetType() {
		return type;
	}

	public GroundType GetInactiveType() {
		return inactiveType;
	}

	public void SetType(String type) {
		this.type = type;
		refresh();
	}

	public void SetColor(Color color) {
		this.color = color;
		refresh();
	}

	private void refresh() {
		for (int i = 0; i < Main.groundTiles.size(); i++) {
			if (Main.groundTiles.get(i).GetGroundType() == this) {
				Main.groundTiles.get(i).SetGroundType(this);
			}
		}
	}
}
