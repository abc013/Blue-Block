package blueblock;

import java.awt.Color;

public class GroundType {
	private Color color;
	private String Type;

	public GroundType(Color color, String Type) {
		this.color = color;
		if (Type != null) {
			this.Type = Type;
		}
	}

	public Color GetColor() {
		return color;
	}

	public String GetType() {
		return Type;
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
