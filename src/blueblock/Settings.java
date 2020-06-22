package blueblock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public final class Settings {
	// max values
	private static final int MinWidth = 8;
	private static final int MinHeight = 8;
	private static final int MaxWidth = 56;
	private static final int MaxHeight = 56;
	private static final int MaxPlayerCount = 4;
	private static final int MaxPowerupCount = 10;

	// default values
	public static final int DefWidth = 16;
	public static final int DefHeight = 16;
	public static final int DefPlayerCount = 4;
	public static final int DefPowerupCount = 2;
	public static final boolean DefEnableMouse = true;
	public static final boolean DefEnablePlayerKills = true;

	// setting values with defaults
	public static int Width = DefWidth;
	public static int Height = DefHeight;
	public static int PlayerCount = DefPlayerCount;
	public static int PowerupCount = DefPowerupCount;
	public static boolean EnableMouse = DefEnableMouse;
	public static boolean EnablePlayerKills = DefEnablePlayerKills;

	public static void LoadSettings() {
		FileReader fr = null;
		BufferedReader br = null;
		ArrayList<String> lines = new ArrayList<String>();

		try {
			fr = new FileReader("settings.txt");
			br = new BufferedReader(fr);

			while (br.ready())
				lines.add(br.readLine());

			fr.close();
			br.close();

			System.out.println("Loaded settings!");
		} catch(IOException e) {
			System.out.println("Failed to load Settings! Fallback to default values.");
			return;
		}

		for (String line : lines) {
			String[] tuple = line.split(",");
			switch (tuple[0])
			{
				case "Height":
					Height = Integer.parseInt(tuple[1]);

					break;
				case "Width":
					Width = Integer.parseInt(tuple[1]);

					break;
				case "PlayerCount":
					PlayerCount = Integer.parseInt(tuple[1]);

					break;
				case "PowerupCount":
					PowerupCount = Integer.parseInt(tuple[1]);

					break;
				case "EnableMouse":
					EnableMouse = Boolean.parseBoolean(tuple[1]);

					break;
				case "EnablePlayerKills":
					EnablePlayerKills = Boolean.parseBoolean(tuple[1]);

					break;
				default:
					System.out.println("Unknown line in settings: " + line);
					break;
			}
		}
		
		CheckSettings();
	}

	public static void SaveSettings() {
		PrintWriter pWriter = null;
		try {
			new File("settings.txt").createNewFile();

			pWriter = new PrintWriter(new BufferedWriter(new FileWriter("settings.txt")));

			pWriter.println("Height: " + Height);
			pWriter.println("Width: " + Width);
			pWriter.println("PlayerCount: " + PlayerCount);
			pWriter.println("PowerupCount: " + PowerupCount);
			pWriter.println("EnableMouse: " + EnableMouse);
			pWriter.println("EnablePlayerKills: " + EnablePlayerKills);
		} catch (Exception e) {
			System.out.println("Unable to save settings!");
		} finally {
			if (pWriter != null) {
				pWriter.flush();
				pWriter.close();
			}
		}
	}

	public static void CheckSettings() {
		Width = Math.min(MaxWidth, Math.max(MinWidth, Width));
		Height = Math.min(MaxHeight, Math.max(MinHeight, Height));

		PowerupCount = Math.min(MaxPowerupCount, PowerupCount);
		PlayerCount = Math.min(MaxPlayerCount, PlayerCount);
	}
}
