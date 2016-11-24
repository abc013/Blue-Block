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
	private int playerCount = 4;
	private int powerUps = 10;
	private boolean mouse = true;
	private boolean kill = true;
	final Menu menu;
	JFrame options;
	JLabel heightLabel, widthLabel;
	JButton reset, save, abort;
	JFormattedTextField textFieldHeight, textFieldWidth, powerUpCount;
	JCheckBox mouseCheckbox, killCheckbox;
	Choice playerCountSelector, powerUpCountSelector;
	final ImageIcon BlueBlock = new ImageIcon("src/img/blblockbut.png");
	final ImageIcon RedBlock = new ImageIcon("src/img/rdblockbut.png");

	public Options(Menu menu) {
		this.menu = menu;
		LoadSettings();
		options = new JFrame("Einstellungen");
		options.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		options.setBounds(300, 300, 450, 370);
		options.setLayout(null);

		heightLabel = new JLabel("Höhe:");
		options.add(heightLabel);
		heightLabel.setFont(new Font("Chiller", 2, 30));
		heightLabel.setBounds(100, 10, 60, 40);

		textFieldHeight = new JFormattedTextField(int.class);
		options.add(textFieldHeight);
		textFieldHeight.setValue(MaxY);
		textFieldHeight.setFont(new Font("Chiller", 0, 30));
		textFieldHeight.setBounds(160, 10, 45, 40);
		textFieldHeight.addPropertyChangeListener(this);

		widthLabel = new JLabel("Breite:");
		options.add(widthLabel);
		widthLabel.setFont(new Font("Chiller", 2, 30));
		widthLabel.setBounds(215, 10, 70, 40);

		textFieldWidth = new JFormattedTextField(int.class);
		options.add(textFieldWidth);
		textFieldWidth.setValue(MaxX);
		textFieldWidth.setFont(new Font("Chiller", 0, 30));
		textFieldWidth.setBounds(285, 10, 45, 40);
		textFieldWidth.addPropertyChangeListener(this);

		JLabel playerText = new JLabel("Spieler:");
		options.add(playerText);
		playerText.setFont(new Font("Chiller", 1, 30));
		playerText.setBounds(20, 60, 90, 40);

		playerCountSelector = new Choice();
		for (int i = 1; i < 5; i++)
			playerCountSelector.add(i + "");
		options.add(playerCountSelector);
		playerCountSelector.setBounds(110, 60, 80, 40);
		playerCountSelector.setFont(new Font("Arial", 1, 30));
		playerCountSelector.select(playerCountSelector + "");
		playerCountSelector.addItemListener(this);

		JLabel powerUpLabel = new JLabel("Power-Ups:");
		options.add(powerUpLabel);
		powerUpLabel.setFont(new Font("Chiller", 1, 30));
		powerUpLabel.setBounds(200, 60, 130, 40);

		powerUpCountSelector = new Choice();
		for (int i = 0; i < 11; i++)
			powerUpCountSelector.add(i + "");
		options.add(powerUpCountSelector);
		powerUpCountSelector.select(powerUps + "");
		powerUpCountSelector.setFont(new Font("Chiller", 1, 30));
		powerUpCountSelector.setBounds(340, 60, 60, 40);

		mouseCheckbox = new JCheckBox(BlueBlock);
		mouseCheckbox.setSelectedIcon(RedBlock);
		mouseCheckbox.setSelected(mouse);
		mouseCheckbox.setText("Maus Aktivieren");
		mouseCheckbox.setFont(new Font("Chiller", 1, 30));
		mouseCheckbox.setToolTipText("Displays wether the Mouse should be disabled in games or not.");
		mouseCheckbox.setBounds(70, 120, 240, 60);
		mouseCheckbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mouse = !mouse;
			}
		});
		options.add(mouseCheckbox);

		killCheckbox = new JCheckBox(BlueBlock);
		killCheckbox.setSelectedIcon(RedBlock);
		killCheckbox.setSelected(kill);
		killCheckbox.setText("Player-Kill aktivieren");
		killCheckbox.setFont(new Font("Chiller", 1, 30));
		killCheckbox.setToolTipText("Displays wether players should kill each other or not.");
		killCheckbox.setBounds(70, 190, 310, 60);
		killCheckbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				kill = !kill;
			}
		});
		options.add(killCheckbox);

		save = new JButton("Speichern");
		save.addActionListener(this);

		reset = new JButton("Zurücksetzen");
		reset.addActionListener(this);

		abort = new JButton("Abbrechen");
		abort.addActionListener(this);

		options.add(save);
		options.add(reset);
		options.add(abort);

		save.setBounds(10, 270, 120, 40);
		save.setFont(new Font("Harrington", 1, 15));
		reset.setBounds(140, 270, 150, 40);
		reset.setFont(new Font("Harrington", 1, 17));
		abort.setBounds(300, 270, 120, 40);
		abort.setFont(new Font("Harrington", 1, 15));

	}

	public void setOpen(boolean open) {
		options.setVisible(open);
		LoadSettings();
		textFieldWidth.setText(MaxX + "");
		textFieldHeight.setText(MaxY + "");
		playerCountSelector.select(playerCount + "");
		powerUpCountSelector.select(powerUps + "");
	}

	private void SaveSettings() {
		PrintWriter pWriter = null;
		int x = Integer.parseInt(textFieldWidth.getText());
		int y = Integer.parseInt(textFieldHeight.getText());
		int players = Integer.parseInt(playerCountSelector.getSelectedItem());
		int PUs = Integer.parseInt(powerUpCountSelector.getSelectedItem());
		try {
			pWriter = new PrintWriter(new BufferedWriter(new FileWriter("settings.txt")));

			x = Math.min(56, Math.max(6, x));
			y = Math.min(56, Math.max(6, y));

			// Don't allow more powerups than there are free fields
			PUs = Math.min((x - 2) * (y - 2) - players, PUs);

			pWriter.println("Height: " + y);
			pWriter.println("Width: " + x);
			pWriter.println("Players: " + players);
			pWriter.println("PowerUps: " + PUs);
			pWriter.println("Mouse: " + mouse);
			pWriter.println("Kill: " + kill);
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
			String names[] = { "Height: ", "Width: ", "Players: ", "PowerUps: ", "Mouse: ", "Kill: " };

			for (int i = 0; i < Values.length; i++) {
				Values[i] = Integer.parseInt(br.readLine().substring(names[i].length()));
				System.out.println(Values[i]);
			}

			String Mouse = br.readLine().substring(names[4].length());
			System.out.println(Mouse);
			mouse = Mouse.equals("true");

			String Kill = br.readLine().substring(names[5].length());
			System.out.println(Kill);
			kill = Kill.equals("true");

			MaxY = Values[0];
			MaxX = Values[1];
			playerCount = Values[2];
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
			textFieldHeight.setValue(16);
			textFieldWidth.setValue(16);
			playerCountSelector.select("4 Spieler");
			powerUpCountSelector.select("0");
			mouseCheckbox.setSelected(true);
			mouse = true;
			killCheckbox.setSelected(true);
			kill = true;
			return;
		} else if (e.getSource().equals(save)) {
			SaveSettings();
		}
		setOpen(false);
		menu.setOpen(true);
	}

	public int Player() {
		return playerCount;
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

	public boolean PlayerKill() {
		return kill;
	}
}
