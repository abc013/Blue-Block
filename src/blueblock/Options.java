package blueblock;

import java.awt.Choice;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Options {
	final Menu menu;

	private JFrame options;
	private JButton reset, save, abort;

	private JFormattedTextField textFieldHeight, textFieldWidth;
	private JCheckBox mouseCheckbox, killCheckbox;
	private Choice playerCountSelector, powerUpCountSelector;

	public Options(final Menu menu) {
		this.menu = menu;

		options = new JFrame("Blue Block | " + ResourceManager.LanguageStrings.get("Settings"));
		options.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		options.setBounds(ResourceManager.ScreenWidth / 2 - 225, ResourceManager.ScreenHeight / 2 - 185, 450, 370);
		options.setLayout(null);
		options.setResizable(false);

		Font font = new Font("chiller", 0, 30);

		JLabel widthLabel = new JLabel(ResourceManager.LanguageStrings.get("Width") + ":");
		widthLabel.setFont(font);
		widthLabel.setBounds(90, 10, 70, 40);
		options.add(widthLabel);

		textFieldWidth = new JFormattedTextField(int.class);
		textFieldWidth.setFont(font);
		textFieldWidth.setBounds(160, 10, 45, 40);
		textFieldWidth.setValue(Settings.Width);
		options.add(textFieldWidth);

		JLabel heightLabel = new JLabel(ResourceManager.LanguageStrings.get("Height") + ":");
		heightLabel.setFont(font);
		heightLabel.setBounds(215, 10, 60, 40);
		options.add(heightLabel);

		textFieldHeight = new JFormattedTextField(int.class);
		textFieldHeight.setFont(font);
		textFieldHeight.setBounds(285, 10, 45, 40);
		textFieldHeight.setValue(Settings.Height);
		options.add(textFieldHeight);

		JLabel playerText = new JLabel(ResourceManager.LanguageStrings.get("Players") + ":");
		playerText.setFont(font);
		playerText.setBounds(20, 60, 90, 40);
		options.add(playerText);

		playerCountSelector = new Choice();
		playerCountSelector.setFont(font);
		playerCountSelector.setBounds(110, 60, 80, 40);

		for (int i = 1; i < 5; i++)
			playerCountSelector.add(i + "");
		playerCountSelector.select(playerCountSelector + "");

		options.add(playerCountSelector);

		JLabel powerUpLabel = new JLabel(ResourceManager.LanguageStrings.get("Powerups") + ":");
		powerUpLabel.setFont(font);
		powerUpLabel.setBounds(200, 60, 130, 40);
		options.add(powerUpLabel);

		powerUpCountSelector = new Choice();
		powerUpCountSelector.setFont(font);
		powerUpCountSelector.setBounds(340, 60, 60, 40);

		for (int i = 0; i < 11; i++)
			powerUpCountSelector.add(i + "");
		powerUpCountSelector.select(Settings.PowerupCount + "");

		options.add(powerUpCountSelector);

		mouseCheckbox = new JCheckBox(ResourceManager.BlueButton);
		mouseCheckbox.setFont(font);
		mouseCheckbox.setBounds(70, 120, 240, 60);
		mouseCheckbox.setSelectedIcon(ResourceManager.RedButton);
		mouseCheckbox.setSelected(Settings.EnableMouse);
		mouseCheckbox.setText(ResourceManager.LanguageStrings.get("EnableMouse"));
		mouseCheckbox.setToolTipText(ResourceManager.LanguageStrings.get("EnableMouseDesc"));
		options.add(mouseCheckbox);

		killCheckbox = new JCheckBox(ResourceManager.BlueButton);
		killCheckbox.setFont(font);
		killCheckbox.setBounds(70, 190, 310, 60);
		killCheckbox.setSelectedIcon(ResourceManager.RedButton);
		killCheckbox.setSelected(Settings.EnablePlayerKills);
		killCheckbox.setText(ResourceManager.LanguageStrings.get("EnablePvP"));
		killCheckbox.setToolTipText(ResourceManager.LanguageStrings.get("EnablePvPDesc"));
		options.add(killCheckbox);

		Font harrington = new Font("Harrington", 1, 15);

		save = new JButton(ResourceManager.LanguageStrings.get("Save"));
		save.setFont(harrington);
		save.setBounds(10, 270, 120, 40);
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SaveSettings();
				setOpen(false);
				menu.setOpen(true);
			}

		});
		options.add(save);

		reset = new JButton(ResourceManager.LanguageStrings.get("Reset"));
		reset.setFont(harrington);
		reset.setBounds(140, 270, 150, 40);
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textFieldHeight.setValue(Settings.DefHeight);
				textFieldWidth.setValue(Settings.DefWidth);
				playerCountSelector.select(Settings.DefPlayerCount + "");
				powerUpCountSelector.select(Settings.DefPowerupCount + "");
				mouseCheckbox.setSelected(Settings.DefEnableMouse);
				killCheckbox.setSelected(Settings.DefEnablePlayerKills);
			}

		});
		options.add(reset);

		abort = new JButton(ResourceManager.LanguageStrings.get("Cancel"));
		abort.setFont(harrington);
		abort.setBounds(300, 270, 120, 40);
		abort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setOpen(false);
				menu.setOpen(true);
			}

		});
		options.add(abort);
	}

	public void setOpen(boolean open) {
		options.setVisible(open);

		if (open) {
			textFieldWidth.setText(Settings.Width + "");
			textFieldHeight.setText(Settings.Height + "");

			playerCountSelector.select(Settings.PlayerCount + "");
			powerUpCountSelector.select(Settings.PowerupCount + "");

			mouseCheckbox.setSelected(Settings.EnableMouse);
			killCheckbox.setSelected(Settings.EnablePlayerKills);
		}
	}

	private void SaveSettings() {
		Settings.Width = Integer.parseInt(textFieldWidth.getText());
		Settings.Height = Integer.parseInt(textFieldHeight.getText());
		Settings.PlayerCount = Integer.parseInt(playerCountSelector.getSelectedItem());
		Settings.PowerupCount = Integer.parseInt(powerUpCountSelector.getSelectedItem());
		Settings.EnableMouse = mouseCheckbox.isSelected();
		Settings.EnablePlayerKills = killCheckbox.isSelected();

		Settings.CheckSettings();
		Settings.SaveSettings();
	}
}
