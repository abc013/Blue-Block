package blueblock;

import java.awt.Color;

public class GroundType {
	private Color color;
	private String Type;
	private GroundType InactiveType;

	public GroundType(Color color, String Type, GroundType InactiveType) {
		this.color = color;
		if (Type != null) {
			this.Type = Type;
		}
		this.InactiveType = InactiveType;

	}

	public Color GetColor() {
		return color;
	}

	public String GetType() {
		return Type;
	}

	public GroundType GetInactiveType() {
		return InactiveType;
	}

	public void setType(String Type) {
		this.Type = Type;
		refresh();
	}

	public void setColor(Color color) {
		this.color = color;
		refresh();
	}

	private void refresh() {
		for (int i = 0; i < Main.g.size(); i++) {
			if (Main.g.get(i).GetGroundType() == this) {
				Main.g.get(i).SetGroundType(this);
			}
		}
	}
}
