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
	public int Kills, schritte, SuperScore, PowerUps, Spieler;
	private boolean lebt, poisoned, geschützt;

	public Human(int pos1, int pos2, int Spieler) {
		this.Spieler = Spieler;
		System.out.println("Player" + Spieler + " created");
		lebt = true;
		Main.FarbeWechseln(labels[pos1][pos2], color[Spieler]);
		this.pos1 = pos1;
		this.pos2 = pos2;
		Dpos1 = pos1;
		Dpos2 = pos2;
	}

	public void Gehen(int Richtung) {
		if (lebt) {
			Locator.MenschGeht(this, Spieler, Richtung);
			System.out.println("Player" + Spieler + ".pos:" + GetX() + "|" + GetY());
			if (Locator.GetGround(GetX(), GetY()).isDeadly()) {
				if (geschützt) {
					geschützt = false;
				} else {
					AmLeben(false);
				}
				Locator.GetGround(Dpos2, Dpos1).SetGroundType(Main.InactiveLava);
			}
			if (!Lebt())
				return;
			if (Locator.GetGround(GetX(), GetY()).isPoison()) {
				if (poisoned) {
					Main.FarbeWechseln(labels[GetY()][GetX()], Main.InactiveLava.GetColor());
					AmLeben(false);
					return;
				}
				if (geschützt) {
					geschützt = false;
				} else {
					SetPoisoned(true);
				}
				// Main.FarbeWechseln(labels[GetY()][GetX()], color[Spieler]);
			}
		} else {
			System.out.println("Mensch ist Tod!");
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
			SetRüstung(true);
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

	public boolean Lebt() {
		return lebt;
	}

	public boolean Poisoned() {
		return poisoned;
	}

	public boolean Geschützt() {
		return geschützt;
	}

	public int GetSchritte() {
		return schritte;
	}

	public void AmLeben(boolean lebt) {
		this.lebt = lebt;
		if (lebt == false)
			SetPosition(-1, -1);
		if (lebt)
			SetPosition(Dpos1, Dpos2);
	}

	public void SetPosition(int x, int y) {
		pos1 = x;
		pos2 = y;
		if (Lebt()) {
			Dpos1 = x;
			Dpos2 = y;
		}
	}

	public void SetRüstung(boolean Rüstung) {
		geschützt = Rüstung;
	}

	public void SetPoisoned(boolean poisoned) {
		this.poisoned = poisoned;
		if (poisoned) {
			color[Spieler] = poisonColor[Spieler];
		} else {
			color[Spieler] = normalColor[Spieler];
		}
		Main.FarbeWechseln(labels[GetY()][GetX()], color[Spieler]);
	}

	public void SetGray() {
		if (!Lebt())
			return;
		color[Spieler] = Color.GRAY;
		if (!Main.TypesActive[5]) {
			if (Poisoned()) {
				color[Spieler] = poisonColor[Spieler];
			} else {
				color[Spieler] = normalColor[Spieler];
			}
		}
		Locator.PlayerColorRefresh();
	}
}
