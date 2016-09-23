package blueblock;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Menu extends JFrame {
	public static Label[][] labels;
	private static final long serialVersionUID = 2747526033034065104L;
	JFrame menu;
	JButton start, optionen, schliessen;
	Main Spiel;
	Options Option;

	// Für eclipse
	public static void main(String[] args) {
		new Menu();
	}

	// Für BlueJ
	public Menu() {
		// TODO: Einstellungen
		menu = new JFrame("BLUE BLOCK - Menü");
		Option = new Options(this);
		menu.setSize(400, 300);
		menu.setLayout(null);
		menu.setResizable(false);
		start = new JButton("    Spiel Starten    ");
		start.setBounds(10, 10, 370, 70);
		start.setFont(new Font("gabriola", 0, 30));
		start.setForeground(Color.BLUE);
		start.setToolTipText("Startet das Spiel.");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Offen(false);
				Option.LoadSettings();
				// TODO: 56*56 geht, aber 24*34 nicht oder so.... ACHTUNG!!!
				// FEHLER IN DER GGGEEESSSAAAMMMTTTEEENNN PROGRAMMIERUNG!!! ;(
				Spiel = new Main(Option.Y(), Option.X(), Option.Player(), Option.PowerUps());
				Spiel.Offen(true);
			}
		});
		menu.add(start);
		optionen = new JButton("   Optionen   ");
		optionen.setFont(new Font("gabriola", 0, 30));
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
		menu.add(optionen);
		schliessen = new JButton("     Spiel Beenden     ");
		schliessen.setFont(new Font("gabriola", 0, 30));
		schliessen.setForeground(Color.BLUE);
		schliessen.setToolTipText("Beendet das Spiel.");
		schliessen.setBounds(10, 180, 370, 70);
		schliessen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}

		});
		menu.add(schliessen);

		menu.setVisible(true);
		menu.repaint();
	}

	public void Offen(boolean offen) {
		menu.setVisible(offen);
	}

}
