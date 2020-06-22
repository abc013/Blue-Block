package blueblock;

public class Ground {
	private int x, y;
	private GroundType Type;

	public Ground(int x, int y, GroundType Type) {
		this.x = x;
		this.y = y;
		this.Type = Type;
	}

	public void RefreshColor() {
		Main.ChangeColor(Main.labels[x][y], Type.GetColor());
	}

	public int GetX() {
		return x;
	}

	public int GetY() {
		return y;
	}

	public GroundType GetType() {
		return Type;
	}

	public void SetGroundType(GroundType Type) {
		this.Type = Type;
		RefreshColor();
	}
}
