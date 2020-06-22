package blueblock;

public class Ground {
	private Game game;
	private int x, y;
	private GroundType Type;

	public Ground(Game game, int x, int y, GroundType Type) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.Type = Type;
	}

	public void RefreshColor() {
		game.Main.ChangeColor(x, y, Type.GetColor());
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
		if (Type == null)
			return;

		this.Type = Type;
		RefreshColor();
	}
}
