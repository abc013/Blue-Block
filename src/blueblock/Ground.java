package blueblock;

import java.awt.Color;

public class Ground {
	private int x, y;
	private GroundType Type;
	private boolean deadly, wall, floor, poison;
	private Color color;

	public Ground(int x, int y, GroundType Type) {
		this.x = x;
		this.y = y;
		this.Type = Type;
		color = Type.GetColor();
		ChangeStatus();
	}

	public void RefreshColor() {
		Main.ChangeColor(Main.labels[x][y], color);
	}

	private void ChangeStatus() {
		floor = wall = deadly = poison = false;
		switch (Type.GetType()) {
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
			System.out.println("INVALID TYPE: " + Type.GetType());
			SetGroundType(new GroundType(Type.GetColor(), "floor", null));
			break;
		}
	}

	public int GetX() {
		return x;
	}

	public int GetY() {
		return y;
	}

	public GroundType GetGroundType() {
		return Type;
	}

	public void SetGroundType(GroundType Type) {
		this.Type = Type;
		color = Type.GetColor();
		ChangeStatus();
		RefreshColor();
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

}
