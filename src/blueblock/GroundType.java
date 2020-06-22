package blueblock;

import java.awt.Color;

public class GroundType {
	// Basic GroundTypes
	public static final GroundType InactiveLava = new GroundType(new Color(150, 50, 50), "floor", null);
	public static final GroundType Lava = new GroundType(Color.RED, "deadly", InactiveLava);
	public static final GroundType InactiveWall = new GroundType(Color.DARK_GRAY, "floor", null);
	public static final GroundType Wall = new GroundType(Color.BLACK, "wall", InactiveWall);
	public static final GroundType Floor = new GroundType(Color.WHITE, "floor", null);
	public static final GroundType Trail = new GroundType(Color.LIGHT_GRAY, "floor", null);
	public static final GroundType Acid = new GroundType(new Color(10, 240, 10), "poison", null);

	private Color color;
	private boolean deadly, wall, floor, poison;
	private GroundType inactiveType;

	public GroundType(Color color, String type, GroundType inactiveType) {
		this.color = color;
		this.inactiveType = inactiveType;

		switch (type) {
		case "wall":
			wall = true;
			break;
		case "floor":
			floor = true;
			break;
		case "deadly":
			deadly = true;
			break;
		case "poison":
			poison = true;
			break;
		default:
			System.out.println("INVALID TYPE: " + type);
			break;
		}
	}

	public Color GetColor() {
		return color;
	}

	public boolean IsDeadly() {
		return deadly;
	}

	public boolean IsPoison() {
		return poison;
	}

	public boolean IsWall() {
		return wall;
	}

	public boolean IsFloor() {
		return floor;
	}

	public GroundType GetInactiveType() {
		return inactiveType;
	}
}
