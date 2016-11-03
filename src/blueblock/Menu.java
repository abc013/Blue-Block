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
	JButton start, optionen, schliessen;
	Main Spiel;
	Options Option;
	final ImageIcon BlueBlock = new ImageIcon("src/img/blblock.png");
	final ImageIcon RedBlock = new ImageIcon("src/img/rdblock.png");
	final ImageIcon BackGround = new ImageIcon("src/img/Background.png");

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
				Offen(false);
				Option.LoadSettings();
				Spiel = new Main(Option.Y(), Option.X(), Option.Player(), Option.PowerUps(), Option.HasMouse());
				Spiel.Offen(true);
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
		optionen = new JButton("   Optionen   ", BlueBlock);
		optionen.setFont(new Font("gabriola", 0, 30));
		optionen.setIconTextGap(100);
		optionen.setForeground(Color.BLUE);
		optionen.setToolTipText(
				"Hier kann man die Größe des Spielfelds als auch Spieler- und Power-Up-Anzahl einstellen.");
		optionen.setBounds(10, 95, 370, 70);
		optionen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Offen(false);
				Option.Offen(true);
			}

		});
		optionen.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				optionen.setIcon(BlueBlock);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				optionen.setIcon(RedBlock);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		menu.add(optionen);
		schliessen = new JButton("     Spiel Beenden     ", BlueBlock);
		schliessen.setFont(new Font("gabriola", 0, 30));
		schliessen.setIconTextGap(40);
		schliessen.setForeground(Color.BLUE);
		schliessen.setToolTipText("Beendet das Spiel.");
		schliessen.setBounds(10, 180, 370, 70);
		schliessen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}

		});
		schliessen.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				schliessen.setIcon(BlueBlock);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				schliessen.setIcon(RedBlock);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		menu.add(schliessen);
		JLabel Bg = new JLabel(BackGround);
		Bg.setSize(400, 300);
		menu.add(Bg);
		menu.setVisible(true);
		menu.validate();
		menu.repaint();
	}

	public void Offen(boolean offen) {
		menu.setVisible(offen);
	}
}
