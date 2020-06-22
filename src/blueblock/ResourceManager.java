package blueblock;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.util.Random;

import javax.swing.ImageIcon;

public final class ResourceManager {
	public static final ImageIcon BlueBlock = new ImageIcon("src/img/blblock.png");
	public static final ImageIcon RedBlock = new ImageIcon("src/img/rdblock.png");

	public static final ImageIcon BlueButton = new ImageIcon("src/img/blblockbut.png");
	public static final ImageIcon RedButton = new ImageIcon("src/img/rdblockbut.png");

	public static final ImageIcon Background = new ImageIcon("src/img/Background.png");
	public static final ImageIcon New_Game = new ImageIcon("src/img/New Game.png");

	public static final Random SharedRandom = new Random();

	public static int ScreenWidth;
	public static int ScreenHeight;

	public static void LoadResources() {
		DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		ScreenWidth = dm.getWidth();
		ScreenHeight = dm.getHeight();
	}

	public static Color PlayerColor(int player) {
		final Color[] colors = { Color.BLUE, Color.GREEN, Color.CYAN, Color.MAGENTA };

		return colors[player];
	}

	public static String PlayerName(int player) {
		final String[] names = { "Blue", "Green", "Cyan", "Magenta" };

		return names[player];
	}
}
