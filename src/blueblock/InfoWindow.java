package blueblock;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class InfoWindow extends JFrame {
	private InfoPanel[] panels;

	int currentRank;
	private Main main;
	private JFrame infoWindow;
	private JTextArea InfoLog;
	private JTextArea Score;

	public InfoWindow(final Main main) {
		this.main = main;

		currentRank = Settings.PlayerCount;

		infoWindow = new JFrame("Blue Block | " + ResourceManager.LanguageStrings.get("InformationTitle"));
		infoWindow.setLocation(ResourceManager.ScreenWidth / 2 + 350 /* Game window */, ResourceManager.ScreenHeight / 2 - 350);
		infoWindow.setSize(300, 700);
		infoWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		infoWindow.setLayout(null);
		infoWindow.setResizable(false);
		infoWindow.setAlwaysOnTop(true);

		Font font = new Font("papyrus", 1, 25);

		JButton NewGame = new JButton(ResourceManager.New_Game);
		NewGame.setFont(font);
		NewGame.setBounds(10, 10, 260, 40);
		NewGame.setToolTipText(ResourceManager.LanguageStrings.get("NewGameDesc"));
		NewGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				main.EndGame(true);;
			}

		});
		infoWindow.add(NewGame);

		InfoLog = new JTextArea();
		InfoLog.setFont(font);
		InfoLog.setBounds(10, 65, 260, 35);
		InfoLog.setText(ResourceManager.LanguageStrings.get("Information"));
		InfoLog.setSelectionColor(Color.WHITE);
		InfoLog.setEditable(false);
		infoWindow.add(InfoLog);

		Font font2 = new Font("forte", Font.PLAIN, 15);

		Score = new JTextArea();
		Score.setFont(font2);
		Score.setBounds(10, 110, 260, 140);
		Score.setMargin(new Insets(5, 10, 5, 10));
		Score.setBackground(Color.YELLOW);
		Score.setEditable(false);
		infoWindow.add(Score);

		panels = new InfoPanel[Settings.MaxPlayerCount];
		for (int i = 0; i < Settings.MaxPlayerCount; i++) {
			InfoPanel panel = new InfoPanel(i, 10, 255 + i * 95, font2);
			panels[i] = panel;
			infoWindow.add(panel);
		}

		Refresh();
	}

	public void Refresh() {
		Game game = main.Game;

		String TextScore = ResourceManager.LanguageStrings.get("Points");

		for (InfoPanel panel : panels)
			TextScore += "\n" + panel.GetScore();

		for (int i = 0; i < Settings.PlayerCount; i++)
			panels[i].Update(game.Humans[i], this);

		int mouseScore = game.MouseLava + game.MouseWall * 2 + game.MouseAcid * 3;

		TextScore += "\n" + ResourceManager.LanguageStrings.get("Mouse") + ": " + mouseScore;
		Score.setText(TextScore);
	}

	public void setOpen(boolean open) {
		infoWindow.setVisible(open);
	}
}
