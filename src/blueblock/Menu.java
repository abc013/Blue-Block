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
	JButton start, options, close;
	Main Game;
	Options Option;
	final ImageIcon BlueBlock = new ImageIcon("blblock.png");
	final ImageIcon RedBlock = new ImageIcon("rdblock.png");
	final ImageIcon BackGround = new ImageIcon("Background.png");

	// For eclipse
	public static void main(String[] args) {
		new Menu();
	}

	// For BlueJ
	public Menu() {
		// setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("blblock.png")));
		menu = new JFrame("BLUE BLOCK - Menü");
		Option = new Options(this);
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
				onStart(false);
				Option.LoadSettings();
				Game = new Main(Option.Y(), Option.X(), Option.Player(), Option.PowerUps(), Option.HasMouse());
				Game.onStart(true);
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
		options = new JButton("   Optionen   ", BlueBlock);
		options.setFont(new Font("gabriola", 0, 30));
		options.setIconTextGap(100);
		options.setForeground(Color.BLUE);
		options.setToolTipText(
				"Hier kann man die Größe des Spielfelds als auch Spieler- und Power-Up-Anzahl einstellen.");
		options.setBounds(10, 95, 370, 70);
		options.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				onStart(false);
				Option.onStart(true);
			}

		});
		options.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				options.setIcon(BlueBlock);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				options.setIcon(RedBlock);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		menu.add(options);
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

	public void onStart(boolean onStart) {
		menu.setVisible(onStart);
	}
}
