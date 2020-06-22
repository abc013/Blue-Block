package blueblock;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import javax.swing.ImageIcon;

public final class ResourceManager {
	public static final ImageIcon BlueBlock = new ImageIcon("src/img/blblock.png");
	public static final ImageIcon RedBlock = new ImageIcon("src/img/rdblock.png");

	public static final ImageIcon BlueButton = new ImageIcon("src/img/blblockbut.png");
	public static final ImageIcon RedButton = new ImageIcon("src/img/rdblockbut.png");

	public static final ImageIcon Background = new ImageIcon("src/img/Background.png");
	public static final ImageIcon New_Game = new ImageIcon("src/img/New Game.png");

	public static final HashMap<String, String> LanguageStrings = new HashMap<String, String>();

	public static final Random SharedRandom = new Random();

	public static int ScreenWidth;
	public static int ScreenHeight;

	public static void LoadResources() {
		DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		ScreenWidth = dm.getWidth();
		ScreenHeight = dm.getHeight();

		String lang = Locale.getDefault().getCountry().substring(0, 2);
		loadLanguage(lang);
	}

	private static void loadLanguage(String lang) {
		File langFile = new File(lang + ".txt");
		if (langFile.exists()) {
			try {
				FileReader fr = new FileReader(langFile);
				BufferedReader br = new BufferedReader(fr);

				while (br.ready()) {
					String[] split = br.readLine().split(":", 2);
					LanguageStrings.put(split[0].trim(), split[1].trim());
				}

				fr.close();
				br.close();

				System.out.println("Loaded language " + lang + "!");
			} catch(IOException e) {
				System.out.println("Failed to load language! Fallback to english.");
				loadLanguage("en");
				return;
			}
		}
		else {
			System.out.println("Unable to find language file " + lang + ".txt, fallback to english.");
			loadLanguage("en");
		}
	}

	public static Color PlayerColor(int player) {
		final Color[] colors = { Color.BLUE, new Color(10, 220, 10), Color.CYAN, Color.MAGENTA };

		return colors[player];
	}

	public static Color PlayerPoisonColor(int player) {
		final Color[] colors = { new Color(0, 0, 200), new Color(10, 120, 10), new Color(0, 155, 155), new Color(155, 0, 155) };

		return colors[player];
	}

	public static String PlayerName(int player) {
		String[] names = { LanguageStrings.get("Blue"), LanguageStrings.get("Green"), LanguageStrings.get("Cyan"), LanguageStrings.get("Magenta") };

		return names[player];
	}
}
