package blueblock;

import java.awt.Color;

public class PowerUp {
	// The different types
	public static final String[] Types = { "SuperScore", "OneLive", "PoisonCure", "Confusion", "MouseBlack", "PlayersGray", "Darkness" };

	private Game game;
	private int x, y;
	private boolean active;
	private String type;

	public PowerUp(Game game) {
		this.game = game;
		NewPosition();
	}

	public void TakeEffect(Human human) {
		human.PowerUps++;
		System.out.println("PowerUp : " + GetType());
		switch (GetType()) {
		case "SuperScore":
			human.SuperScore++;
			break;
		case "OneLive":
			human.SetArmor(true);
			break;
		case "PoisonCure":
			human.SetPoisoned(false);
			break;
		case "Confusion":
			game.ActivateEffect(3, Settings.PlayerCount * 10);
			break;
		case "MouseBlack":
			game.ActivateEffect(4, Settings.PlayerCount * 5);
			break;
		case "PlayersGray":
			game.ActivateEffect(5, Settings.PlayerCount * 7);
			for (Human human2 : game.Humans)
				human2.SetGray();
			break;
		case "Darkness":
			game.ActivateEffect(6, Settings.PlayerCount * 3);
			break;
		}
	}

	public void NewPosition() {
		int pos1 = ResourceManager.SharedRandom.nextInt(game.Width);
		int pos2 = ResourceManager.SharedRandom.nextInt(game.Height);
		NewPosition(pos1, pos2);
	}

	public void NewPosition(int x, int y) {
		Ground gr = game.Locator.GetGround(x, y);
		GroundType grt = gr.GetType();

		if (grt.IsWall() || grt.IsDeadly() || game.Locator.GetHuman(x, y) != null) {
			NewPosition();

			return;
		}

		if (grt.IsPoison())
			gr.SetGroundType(GroundType.Floor);

		this.x = x;
		this.y = y;
		type = Types[ResourceManager.SharedRandom.nextInt(Types.length)];

		game.Main.ChangeColor(x, y, Color.YELLOW);
	}

	public int GetX() {
		return x;
	}

	public int GetY() {
		return y;
	}

	public String GetType() {
		return type;
	}

	public boolean IsActive() {
		return active;
	}
}
