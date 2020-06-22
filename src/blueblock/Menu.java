package blueblock;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Menu extends JFrame {
	Main game;
	final Options options;

	private JFrame menu;
	private JButton start, optionsButton, close;

	public static void main(String[] args) {
		Settings.LoadSettings();
		ResourceManager.LoadResources();

		new Menu();
	}

	public Menu() {
		menu = new JFrame("Blue Block | " + ResourceManager.LanguageStrings.get("Menu"));
		options = new Options(this);
		menu.setBounds(ResourceManager.ScreenWidth / 2 - 200, ResourceManager.ScreenHeight / 2 - 150, 400, 300);
		menu.setLayout(null);
		menu.setResizable(false);

		Font font = new Font("gabriola", 0, 30);

		start = new JButton(ResourceManager.LanguageStrings.get("Start"), ResourceManager.BlueBlock);
		start.setFont(font);
		start.setBounds(10, 10, 370, 70);
		start.setIconTextGap(75);
		start.setForeground(Color.BLUE);
		start.setToolTipText(ResourceManager.LanguageStrings.get("StartDesc"));
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				game = new Main(false);
				setOpen(false);
				game.setOpen(true);
			}
		});
		start.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				start.setIcon(ResourceManager.BlueBlock);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				start.setIcon(ResourceManager.RedBlock);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		menu.add(start);

		optionsButton = new JButton(ResourceManager.LanguageStrings.get("Settings"), ResourceManager.BlueBlock);
		optionsButton.setFont(font);
		optionsButton.setBounds(10, 95, 370, 70);
		optionsButton.setIconTextGap(100);
		optionsButton.setForeground(Color.BLUE);
		optionsButton.setToolTipText(ResourceManager.LanguageStrings.get("SettingsDesc"));
		optionsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setOpen(false);
				options.setOpen(true);
			}

		});
		optionsButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				optionsButton.setIcon(ResourceManager.BlueBlock);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				optionsButton.setIcon(ResourceManager.RedBlock);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		menu.add(optionsButton);

		close = new JButton(ResourceManager.LanguageStrings.get("Exit"), ResourceManager.BlueBlock);
		close.setFont(font);
		close.setBounds(10, 180, 370, 70);
		close.setIconTextGap(80);
		close.setForeground(Color.BLUE);
		close.setToolTipText(ResourceManager.LanguageStrings.get("ExitDesc"));
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}

		});
		close.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				close.setIcon(ResourceManager.BlueBlock);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				close.setIcon(ResourceManager.RedBlock);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		menu.add(close);

		JLabel Bg = new JLabel(ResourceManager.Background);
		Bg.setSize(400, 300);
		menu.add(Bg);

		menu.setVisible(true);
	}

	public void setOpen(boolean open) {
		menu.setVisible(open);
	}
}
