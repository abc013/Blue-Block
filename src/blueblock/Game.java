package blueblock;

import java.awt.Color;

public class Game {
	Main Main;
	// PowerUps + Players
	public PowerUp[] PowerUpList;
	public Human[] Humans;
	// For each number a background is available; WARNING! They aren't directly
	// bound to x and y!
	public Ground[][] GroundTiles;

	public Locator Locator;

	// Types of PowerUps, their booleans and Duration;
	public int[] TypesDuration = new int[PowerUp.Types.length];
	public boolean[] TypesActive = new boolean[PowerUp.Types.length];

	public int Height, Width;
	public int MouseLava, MouseWall, MouseAcid;

	public Game(Main main) {
		Main = main;

		Width = Settings.Width;
		Height = Settings.Height;
		main.SetMapBounds(Width, Height);
		GroundTiles = new Ground[Width][Height];

		Locator = new Locator(this);

		PowerUpList = new PowerUp[Settings.PowerupCount];
		Humans = new Human[Settings.PlayerCount];
	}

	public void Load() {
		for (int x = 0; x < Width; x++) {
			for (int y = 0; y < Height; y++) {
				Ground ground = new Ground(this, x, y, GroundType.Floor);
				GroundTiles[x][y] = ground;
				ground.RefreshColor();
			}
		}

		// Generate walls surrounding the playground
		for (int i = 0; i < Height; i++) {
			Locator.GetGround(0, i).SetGroundType(GroundType.Wall);
			Locator.GetGround(Width - 1, i).SetGroundType(GroundType.Wall);
		}
		for (int i = 0; i < Width; i++) {
			Locator.GetGround(i, 0).SetGroundType(GroundType.Wall);
			Locator.GetGround(i, Height - 1).SetGroundType(GroundType.Wall);
		}

		// Generate players
		for (int i = 0; i < Settings.PlayerCount; i++) {
			switch (i) {
				case 0:
					final Human H1 = new Human(this, 1, 1, i);
					Humans[0] = H1;
					break;
				case 1:
					final Human H2 = new Human(this, Width - 2, Height  - 2, i);
					Humans[1] = H2;
					break;
				case 2:
					final Human H3 = new Human(this, 1, Height - 2, i);
					Humans[2] = H3;
					break;
				case 3:
					final Human H4 = new Human(this, Width - 2, 1, i);
					Humans[3] = H4;
					break;
			}
		}

		// Generate powerups
		for (int i = 0; i < Settings.PowerupCount; i++)
			PowerUpList[i] = new PowerUp(this);
	}

	public void ActivateEffect(int number, int Duration) {
		TypesActive[number] = true;
		TypesDuration[number] += Duration;
	}

	public void Paint() {
		if (TypesActive[6])
			Main.Clear(Color.BLACK);
		else {
			for (int x = 0; x < Settings.Width; x++) {
				for (int y = 0; y < Settings.Height; y++)
					Main.ChangeColor(x, y, GroundTiles[x][y].GetType().GetColor());
			}

			for (Human human : Humans)
				human.Paint();

			for (PowerUp powerup : PowerUpList)
				Main.ChangeColor(powerup.GetX(), powerup.GetY(), Color.YELLOW);
		}
	}

	public void HandleKeyInput(char key) {
		final char[][] keys = {
			{ 'a', 'd', 'w', 's' },
			{ 'f', 'h', 't', 'g' },
			{ 'j', 'l', 'i', 'k' },
			{ '1', '3', '5', '2' },
		};

		boolean confused = TypesActive[3];

		for (int i = 0; i < Settings.PlayerCount; i++)
		{
			if (key == keys[i][0]) // Left
				Humans[i].Go(confused ? "right" : "left");
			else if (key == keys[i][1]) // Right
				Humans[i].Go(confused ? "left" : "right");
			else if (key == keys[i][2]) // Up
				Humans[i].Go(confused ? "down" : "up");
			else if (key == keys[i][3]) // Down
				Humans[i].Go(confused ? "up" : "down");
		}

		Paint();
	}

	public void HandleMouseInput(int button, int x, int y) {
		if (!Settings.EnableMouse)
			return;

		if (TypesActive[4] || Locator.GetHuman(x, y) != null || Locator.GetPowerup(x, y) != null)
			return;

		Ground ground = Locator.GetGround(x, y);
		GroundType type = ground.GetType();

		if (button == 1) {
			if (type.IsDeadly()) {
				ground.SetGroundType(type.GetInactiveType());
				MouseLava--;
			} else {
				ground.SetGroundType(GroundType.Lava);
				MouseLava++;
			}
		} else if (button == 3) {
			if (type.IsPoison()) {
				ground.SetGroundType(type.GetInactiveType());
				MouseAcid--;
			} else {
				ground.SetGroundType(GroundType.Acid);
				MouseAcid++;
			}
		} else {
			if (type.IsWall()) {
				ground.SetGroundType(type.GetInactiveType());
				MouseWall--;
			} else {
				ground.SetGroundType(GroundType.Wall);
				MouseWall++;
			}
		}
	}
}
