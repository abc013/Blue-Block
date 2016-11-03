package blueblock;

import java.awt.Choice;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
//import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Options implements PropertyChangeListener, ActionListener, ItemListener {
	private int MaxX = 56;
	private int MaxY = 56;
	private int Spieler = 4;
	private int powerUps = 10;
	private boolean mouse = true;
	final Menu menu;
	JFrame options;
	JLabel Thöhe, TBreite;
	JButton reset, save, abort;
	JFormattedTextField Ehöhe, EBreite, PowerUpAnzahl;
	JCheckBox Mouse;
	Choice spieler, PowerUps;
	final ImageIcon BlueBlock = new ImageIcon("src/img/blblockbut.png");
	final ImageIcon RedBlock = new ImageIcon("src/img/rdblockbut.png");

	public Options(Menu menu) {
		this.menu = menu;
		LoadSettings();
		System.out.println(MaxX);
		options = new JFrame("Einstellungen");
		options.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		options.setBounds(300, 300, 450, 270);
		options.setLayout(null);

		Thöhe = new JLabel("Höhe:");
		options.add(Thöhe);
		Thöhe.setFont(new Font("Chiller", 2, 30));
		Thöhe.setBounds(100, 10, 60, 40);

		Ehöhe = new JFormattedTextField(int.class);
		options.add(Ehöhe);
		Ehöhe.setValue(MaxY);
		Ehöhe.setFont(new Font("Chiller", 0, 30));
		Ehöhe.setBounds(160, 10, 45, 40);
		Ehöhe.addPropertyChangeListener(this);

		TBreite = new JLabel("Breite:");
		options.add(TBreite);
		TBreite.setFont(new Font("Chiller", 2, 30));
		TBreite.setBounds(215, 10, 70, 40);

		EBreite = new JFormattedTextField(int.class);
		options.add(EBreite);
		EBreite.setValue(MaxX);
		EBreite.setFont(new Font("Chiller", 0, 30));
		EBreite.setBounds(285, 10, 45, 40);
		EBreite.addPropertyChangeListener(this);

		JLabel spielerText = new JLabel("Spieler:");
		options.add(spielerText);
		spielerText.setFont(new Font("Chiller", 1, 30));
		spielerText.setBounds(20, 60, 90, 40);

		spieler = new Choice();
		for (int i = 1; i < 5; i++)
			spieler.add(i + "");
		options.add(spieler);
		spieler.setBounds(110, 60, 80, 40);
		spieler.setFont(new Font("Arial", 1, 30));
		spieler.select(spieler + "");
		spieler.addItemListener(this);

		JLabel TPowerUps = new JLabel("Power-Ups:");
		options.add(TPowerUps);
		TPowerUps.setFont(new Font("Chiller", 1, 30));
		TPowerUps.setBounds(200, 60, 130, 40);

		PowerUps = new Choice();
		for (int i = 0; i < 11; i++)
			PowerUps.add(i + "");
		options.add(PowerUps);
		PowerUps.select(powerUps + "");
		PowerUps.setFont(new Font("Chiller", 1, 30));
		PowerUps.setBounds(340, 60, 60, 40);

		Mouse = new JCheckBox(BlueBlock);
		Mouse.setSelectedIcon(RedBlock);
		Mouse.setSelected(mouse);
		Mouse.setText("Maus Aktivieren");
		Mouse.setFont(new Font("Chiller", 1, 30));
		Mouse.setToolTipText("Displays wether the Mouse should be disabled in games or not.");
		Mouse.setBounds(120, 120, 240, 60);
		Mouse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mouse)
					mouse = false;
				else
					mouse = true;
			}
		});
		options.add(Mouse);

		save = new JButton("Speichern");
		save.addActionListener(this);

		reset = new JButton("Zurücksetzen");
		reset.addActionListener(this);

		abort = new JButton("Abbrechen");
		abort.addActionListener(this);

		options.add(save);
		options.add(reset);
		options.add(abort);

		save.setBounds(10, 180, 120, 40);
		save.setFont(new Font("Harrington", 1, 15));
		reset.setBounds(140, 180, 150, 40);
		reset.setFont(new Font("Harrington", 1, 17));
		abort.setBounds(300, 180, 120, 40);
		abort.setFont(new Font("Harrington", 1, 15));

	}

	public void Offen(boolean offen) {
		options.setVisible(offen);
		LoadSettings();
		Ehöhe.setText(MaxY + "");
		EBreite.setText(MaxX + "");
		spieler.select(Spieler + "");
		PowerUps.select(powerUps + "");
	}

	private void SaveSettings() {
		PrintWriter pWriter = null;
		int Y = Integer.parseInt(Ehöhe.getText());
		int X = Integer.parseInt(EBreite.getText());
		int player = Integer.parseInt(spieler.getSelectedItem());
		int PUs = Integer.parseInt(PowerUps.getSelectedItem());
		try {
			pWriter = new PrintWriter(new BufferedWriter(new FileWriter("settings.txt")));
			if (Y > 56)
				Y = 56;
			if (X > 56)
				X = 56;
			if (Y < 6) {
				PUs = 2;
				Y = 6;
			}
			if (X < 6) {
				PUs = 2;
				X = 6;
			}
			pWriter.println("Höhe: " + Y);
			pWriter.println("Breite: " + X);
			pWriter.println("Spieler: " + player);
			pWriter.println("PowerUps: " + PUs);
			pWriter.println("Mouse: " + mouse);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (pWriter != null) {
				pWriter.flush();
				pWriter.close();
			}
		}
	}

	public void LoadSettings() {
		// File f = new File("settings.txt");
		BufferedReader br;

		try {
			FileReader fr = new FileReader("settings.txt");
			br = new BufferedReader(fr);

			int Values[] = new int[4];
			String names[] = { "Höhe: ", "Breite: ", "Spieler: ", "PowerUps: ", "Mouse: " };

			for (int i = 0; i < Values.length; i++) {
				Values[i] = Integer.parseInt(br.readLine().substring(names[i].length()));
				System.out.println(Values[i]);
			}
			String Mouse = br.readLine().substring(names[4].length());
			System.out.println(Mouse);
			if (Mouse.equals("true"))
				mouse = true;
			else
				mouse = false;

			MaxY = Values[0];
			MaxX = Values[1];
			Spieler = Values[2];
			powerUps = Values[3];

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
	}

	@Override
	public void itemStateChanged(ItemEvent IE) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(reset)) {
			Ehöhe.setValue(16);
			EBreite.setValue(16);
			spieler.select("4 Spieler");
			PowerUps.select("0");
			Mouse.setSelected(true);
			mouse = true;
			return;
		} else if (e.getSource().equals(save)) {
			SaveSettings();
		}
		Offen(false);
		menu.Offen(true);
	}

	public int Player() {
		return Spieler;
	}

	public int PowerUps() {
		return powerUps;
	}

	public int X() {
		return MaxX;
	}

	public int Y() {
		return MaxY;
	}

	public boolean HasMouse() {
		return mouse;
	}
}
