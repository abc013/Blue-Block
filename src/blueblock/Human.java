package blueblock;

import java.awt.Color;

public class Human {
	private Game game;

	private int pos1, pos2;
	private int player;
	private boolean lives, poisoned, armored;
	private Color color;

	public int Kills, Steps, SuperScore, PowerUps;

	public Human(Game game, int pos1, int pos2, int player) {
		this.player = player;
		this.game = game;
		lives = true;
		this.pos1 = pos1;
		this.pos2 = pos2;

		color = ResourceManager.PlayerColor(player);
		Paint();
		System.out.println("Player " + player + " created.");
	}

	public void Go(String direction) {
		if (!Lives())
			return;

		game.Locator.MovePlayer(this, player, direction);
		System.out.println("Player" + player + ".pos:" + pos1 + "|" + pos2);
		Ground ground = game.Locator.GetGround(pos1, pos2);

		if (ground.GetType().IsDeadly()) {
			if (armored)
				armored = false;
			else
				IsLiving(false);

			ground.SetGroundType(ground.GetType().GetInactiveType());
		}
		else if (ground.GetType().IsPoison()) {
			if (armored)
				armored = false;
			else {
				if (poisoned) {
					game.Main.ChangeColor(pos1, pos2, ground.GetType().GetColor());
					IsLiving(false);
					return;
				}
				SetPoisoned(true);
			}
		}
	}

	public void PowerUpEffect(PowerUp powerup) {
		PowerUps++;
		System.out.println("PowerUp : " + powerup.GetType());
		switch (powerup.GetType()) {
		case "SuperScore":
			SuperScore++;
			break;
		case "OneLive":
			SetArmor(true);
			break;
		case "PoisonCure":
			SetPoisoned(false);
			break;
		case "Confusion":
			game.ActivateEffect(3, Settings.PlayerCount * 10);
			break;
		case "MouseBlack":
			game.ActivateEffect(4, Settings.PlayerCount * 5);
			break;
		case "PlayersGray":
			game.ActivateEffect(5, Settings.PlayerCount * 7);
			for (Human human : game.Humans)
				human.SetGray();
			break;
		case "Darkness":
			game.ActivateEffect(6, Settings.PlayerCount * 3);
			break;
		}
	}

	public void Paint()
	{
		if (lives)
			game.Main.ChangeColor(pos1, pos2, color);
	}

	public int GetX() {
		return pos1;
	}

	public int GetY() {
		return pos2;
	}

	public boolean Lives() {
		return lives;
	}

	public boolean Poisoned() {
		return poisoned;
	}

	public boolean Secured() {
		return armored;
	}

	public int GetSteps() {
		return Steps;
	}

	public void IsLiving(boolean lives) {
		this.lives = lives;
		if (!lives)
			SetPosition(-1, -1);
	}

	public void SetPosition(int x, int y) {
		pos1 = x;
		pos2 = y;
	}

	public void SetArmor(boolean armor) {
		armored = armor;
	}

	public void SetPoisoned(boolean poisoned) {
		this.poisoned = poisoned;
		color = poisoned ? ResourceManager.PlayerPoisonColor(player) : ResourceManager.PlayerColor(player);
		Paint();
	}

	public void SetGray() {
		if (!Lives())
			return;

		color = Color.GRAY;
		if (!game.TypesActive[5])
			SetPoisoned(poisoned);

		Paint();
	}
}
