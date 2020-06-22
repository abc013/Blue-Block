package blueblock;

import java.awt.Color;
import java.awt.Label;

public class Human {
	public Color[] normalColor = { Color.WHITE, Color.BLUE, new Color(10, 220, 10), Color.CYAN, Color.MAGENTA,
			Color.RED };
	public Color[] color = { Color.WHITE, Color.BLUE, new Color(10, 220, 10), Color.CYAN, Color.MAGENTA, Color.RED };
	public Color[] poisonColor = { Color.WHITE, new Color(0, 0, 200), new Color(10, 120, 10), new Color(0, 155, 155),
			new Color(155, 0, 155) };
	private Label[][] labels = Main.labels;
	private int pos1, pos2, Dpos1, Dpos2;
	public int Kills, Steps, SuperScore, PowerUps, Player;
	private boolean lives, poisoned, armored;
	private String name;

	public Human(int pos1, int pos2, int player, String name) {
		Player = player;
		this.name = name;
		System.out.println("Player" + player + " created");
		lives = true;
		Main.ChangeColor(labels[pos1][pos2], color[player]);
		this.pos1 = Dpos1 = pos1;
		this.pos2 = Dpos2 = pos2;
	}

	public void Go(String direction) {
		if (lives) {
			Locator.MovePlayer(this, Player, direction);
			System.out.println("Player" + Player + ".pos:" + pos1 + "|" + pos2);
			if (Locator.GetGround(pos1, pos2).GetType().IsDeadly()) {
				if (armored) {
					armored = false;
				} else {
					IsLiving(false);
				}
				Locator.GetGround(Dpos1, Dpos2)
						.SetGroundType(Locator.GetGround(Dpos1, Dpos2).GetType().GetInactiveType());
			}

			if (!Lives())
				return;

			if (Locator.GetGround(pos1, pos2).GetType().IsPoison()) {
				if (armored) {
					armored = false;
				} else {
					if (poisoned) {
						Main.ChangeColor(labels[pos1][pos2],
								Locator.GetGround(Dpos1, Dpos2).GetType().GetColor());
						IsLiving(false);
						return;
					}
					SetPoisoned(true);
				}
				// Main.ChangeColor(labels[pos1][pos2], color[Player]);
			}
		} else {
			System.out.println("Player " + name + " is dead!");
		}
	}

	public void PowerUpEffekt(PowerUp powerup) {
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
			Main.SetEffectActive(3, Settings.PlayerCount * 10);
			break;
		case "MouseBlack":
			Main.SetEffectActive(4, Settings.PlayerCount * 5);
			break;
		case "PlayersGray":
			Main.SetEffectActive(5, Settings.PlayerCount * 7);
			for (Human human : Main.Humans)
				human.SetGray();
			break;
		case "Darkness":
			Main.SetEffectActive(6, Settings.PlayerCount * 3);
			break;
		}
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

	public int GetSchritte() {
		return Steps;
	}

	public String GetName() {
		return name;
	}

	public void IsLiving(boolean lives) {
		this.lives = lives;
		if (!lives)
			SetPosition(-1, -1);
		else
			SetPosition(Dpos1, Dpos2);
	}

	public void SetPosition(int x, int y) {
		pos1 = x;
		pos2 = y;
		if (Lives()) {
			Dpos1 = x;
			Dpos2 = y;
		}
	}

	public void SetArmor(boolean armor) {
		armored = armor;
	}

	public void SetPoisoned(boolean poisoned) {
		this.poisoned = poisoned;
		Color playerColor = poisoned ? poisonColor[Player] : normalColor[Player];
		color[Player] = playerColor;
		Main.ChangeColor(labels[pos1][pos2], playerColor);
	}

	public void SetGray() {
		if (!Lives())
			return;

		color[Player] = Color.GRAY;
		if (!Main.TypesActive[5])
			color[Player] = Poisoned() ? poisonColor[Player] : normalColor[Player];

		Locator.RefreshPlayerColors();
	}
}
