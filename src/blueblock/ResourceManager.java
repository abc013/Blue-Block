package blueblock;

import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;

import javax.swing.ImageIcon;

public final class ResourceManager {
	public static final ImageIcon BlueBlock = new ImageIcon("src/img/blblock.png");
	public static final ImageIcon RedBlock = new ImageIcon("src/img/rdblock.png");

	public static final ImageIcon BlueButton = new ImageIcon("src/img/blblockbut.png");
	public static final ImageIcon RedButton = new ImageIcon("src/img/rdblockbut.png");

	public static final ImageIcon Background = new ImageIcon("src/img/Background.png");

	public static int ScreenWidth;
	public static int ScreenHeight;

	public static void LoadResources() {
		DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		ScreenWidth = dm.getWidth();
		ScreenHeight = dm.getHeight();
	}
}
