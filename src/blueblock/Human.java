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
	public int Kills, steps, SuperScore, PowerUps, Player;
	private boolean lives, poisoned, guarded;
	private String name;

	public Human(int pos1, int pos2, int Player, String name) {
		this.Player = Player;
		this.name = name;
		System.out.println("Player" + Player + " created");
		lives = true;
		Main.colorChange(labels[pos1][pos2], color[Player]);
		this.pos1 = pos1;
		this.pos2 = pos2;
		Dpos1 = pos1;
		Dpos2 = pos2;
	}

	public void Move(int Direction) {
		if (lives) {
			Locator.GetPlayerPosition(this, Player, Direction);
			System.out.println("Player" + Player + ".pos:" + GetX() + "|" + GetY());
			if (Locator.GetGround(GetX(), GetY()).isDeadly()) {
				if (guarded) {
					guarded = false;
				} else {
					IsLiving(false);
				}
				Locator.GetGround(Dpos2, Dpos1).SetGroundType(Main.InactiveLava);
			}
			if (!Lives())
				return;
			if (Locator.GetGround(GetX(), GetY()).isPoison()) {
				if (poisoned) {
					Main.colorChange(labels[GetY()][GetX()], Main.InactiveLava.GetColor());
					IsLiving(false);
					return;
				}
				if (guarded) {
					guarded = false;
				} else {
					SetPoisoned(true);
				}
				// Main.colorChange(labels[GetY()][GetX()], color[Player]);
			}
		} else {
			System.out.println("Mensch ist Tod!");
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
			Main.SetEffectActive(3, Main.h.size() * 10);
			break;
		case "MouseBlack":
			Main.SetEffectActive(4, Main.h.size() * 5);
			break;
		case "PlayersGray":
			Main.SetEffectActive(5, Main.h.size() * 7);
			for (int i = 0; i < Main.h.size(); i++)
				Main.h.get(i).SetGray();
			break;
		case "Darkness":
			Main.SetEffectActive(6, Main.h.size() * 3);
			break;
		}
	}

	public int GetX() {
		return pos2;
	}

	public int GetY() {
		return pos1;
	}

	public boolean Lives() {
		return lives;
	}

	public boolean Poisoned() {
		return poisoned;
	}

	public boolean guarded() {
		return guarded;
	}

	public int GetSteps() {
		return steps;
	}

	public String GetName() {
		return name;
	}

	public void IsLiving(boolean lives) {
		this.lives = lives;
		if (lives == false)
			SetPosition(-1, -1);
		if (lives)
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

	public void SetArmor(boolean HasArmor) {
		guarded = HasArmor;
	}

	public void SetPoisoned(boolean poisoned) {
		this.poisoned = poisoned;
		if (poisoned) {
			color[Player] = poisonColor[Player];
		} else {
			color[Player] = normalColor[Player];
		}
		Main.colorChange(labels[GetY()][GetX()], color[Player]);
	}

	public void SetGray() {
		if (!Lives())
			return;
		color[Player] = Color.GRAY;
		if (!Main.TypesActive[5]) {
			if (Poisoned()) {
				color[Player] = poisonColor[Player];
			} else {
				color[Player] = normalColor[Player];
			}
		}
		Locator.PlayerColorRefresh();
	}
}
