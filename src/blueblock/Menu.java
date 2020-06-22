package blueblock;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Menu extends JFrame {
	public static Label[][] labels;
	private static final long serialVersionUID = 2747526033034065104L;
	JFrame menu;
	JButton start, optionsButton, close;
	Main game;
	Options options;
	final ImageIcon BlueBlock = new ImageIcon("src/img/blblock.png");
	final ImageIcon RedBlock = new ImageIcon("src/img/rdblock.png");
	final ImageIcon BackGround = new ImageIcon("src/img/Background.png");

	public static void main(String[] args) {
		Settings.LoadSettings();

		new Menu();
	}

	public Menu() {
		// setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("blblock.png")));
		menu = new JFrame("BLUE BLOCK - Menü");
		options = new Options(this);
		menu.setSize(400, 300);
		menu.setLayout(null);
		menu.setResizable(false);
		start = new JButton("    Spiel Starten    ", BlueBlock);
		start.setBounds(10, 10, 370, 70);
		start.setFont(new Font("gabriola", 0, 30));
		start.setIconTextGap(60);
		start.setForeground(Color.BLUE);
		start.setToolTipText("Startet das Spiel.");
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
				start.setIcon(BlueBlock);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				start.setIcon(RedBlock);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		menu.add(start);
		optionsButton = new JButton("   Optionen   ", BlueBlock);
		optionsButton.setFont(new Font("gabriola", 0, 30));
		optionsButton.setIconTextGap(100);
		optionsButton.setForeground(Color.BLUE);
		optionsButton.setToolTipText(
				"Hier kann man die Größe des Spielfelds als auch Spieler- und Power-Up-Anzahl einstellen.");
		optionsButton.setBounds(10, 95, 370, 70);
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
				optionsButton.setIcon(BlueBlock);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				optionsButton.setIcon(RedBlock);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		menu.add(optionsButton);
		close = new JButton("     Spiel Beenden     ", BlueBlock);
		close.setFont(new Font("gabriola", 0, 30));
		close.setIconTextGap(40);
		close.setForeground(Color.BLUE);
		close.setToolTipText("Beendet das Spiel.");
		close.setBounds(10, 180, 370, 70);
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
				close.setIcon(BlueBlock);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				close.setIcon(RedBlock);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		menu.add(close);
		JLabel Bg = new JLabel(BackGround);
		Bg.setSize(400, 300);
		menu.add(Bg);
		menu.setVisible(true);
		menu.validate();
		menu.repaint();
	}

	public void setOpen(boolean open) {
		menu.setVisible(open);
	}
}
