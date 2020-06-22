package blueblock;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class InfoPanel extends JTextArea {
	private Color color;
	private String name;
	private int rank;
	private boolean dead;
	private int score;

	public InfoPanel(int player, int xPos, int yPos, Font font) {
		super();

		name = ResourceManager.PlayerName(player);
		color = ResourceManager.PlayerColor(player);

		setFont(font);
		setBounds(xPos, yPos, 260, 90);
		setMargin(new Insets(10, 10, 10, 10));
		setBackground(color);
		setForeground(Color.WHITE);
		setEditable(false);
	}

	public void Update(Human block, InfoWindow window) {
		if (dead || block == null)
			return;

		String text = name + " " + ResourceManager.LanguageStrings.get("Player") + ": ";

		if (block.Lives()) {

			if (block.Poisoned())
				text += ResourceManager.LanguageStrings.get("Poisoned");
			else
				text += ResourceManager.LanguageStrings.get("Alive");

			if (block.Secured())
				text += ResourceManager.LanguageStrings.get("And_Armored");

			// We are the last block
			if (window.currentRank == 1)
				rank = window.currentRank--;
		} else {
			text += ResourceManager.LanguageStrings.get("Dead");
			rank = window.currentRank--;
			dead = true;
			setBackground(color.darker());
		}
		score = block.Kills * 300 + block.Steps + block.SuperScore * 50 + block.PowerUps * 5;

		setText(text + "\n" + ResourceManager.LanguageStrings.get("Rank") + ": " + (rank == 0 ? "-" : rank + "") + "\n" + ResourceManager.LanguageStrings.get("Kills") + ": " + block.Kills);
	}

	public String GetScore() {
		return name + " " + ResourceManager.LanguageStrings.get("Player") + ": " + score;
	}
}
