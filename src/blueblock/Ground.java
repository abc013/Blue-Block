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

	public void ColorRefresh() {
		// TODO: ???? warum xy und nicht yx???????? WTF???
		Main.FarbeWechseln(Main.labels[y][x], color);
	}

	private void ChangeStatus() {
		floor = false;
		wall = false;
		deadly = false;
		poison = false;
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
			SetGroundType(new GroundType(Type.GetColor(), "floor"));
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
		ColorRefresh();
	}

	public boolean isDeadly() {
		return deadly;
	}

	public boolean isPoison() {
		return poison;
	}

	public boolean isWall() {
		return wall;
	}

	public boolean NotIsWall() {
		if (wall == false)
			return true;
		return false;
	}

	public boolean isfloor() {
		return floor;
	}

}
