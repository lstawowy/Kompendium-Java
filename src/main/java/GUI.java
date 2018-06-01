import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUI {
private JFrame guiFrame;


public static void main(String[] args) {
	new GUI();
}

public GUI() {
	guiFrame = new JFrame();
	guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	guiFrame.setTitle("TicTacToe GUI");
	guiFrame.setSize(Map.width, Map.height);
	guiFrame.setLocationRelativeTo(null);
	guiFrame.setResizable(false);
	JButton playerVsAiButton = new JButton("Player vs AI");
	setUpPlayButton(playerVsAiButton);
	guiFrame.add(playerVsAiButton, BorderLayout.NORTH);
	JButton aiVsAiButton = new JButton("AI vs AI");
	setUpPlayButton(aiVsAiButton);
	guiFrame.add(aiVsAiButton, BorderLayout.AFTER_LAST_LINE);
	JButton settingsButton = new JButton("Settings");
	setUpPlayButton(settingsButton);
	guiFrame.add(settingsButton, BorderLayout.AFTER_LINE_ENDS);
	guiFrame.setVisible(true);
}

private void setUpPlayButton(JButton playButton) {
	playButton.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					if (playButton.getActionCommand() == "Player vs AI") {
						setPlayersAndMapForPlayerVsAI();
						new DrawPanel().drawGameFrame();
						guiFrame.setVisible(Boolean.FALSE);
					}
					if (playButton.getActionCommand() == "AI vs AI") {
						setPlayersAndMapForAIVsAI();
						new DrawPanel().drawGameFrame();
						guiFrame.setVisible(Boolean.FALSE);
					}
					if (playButton.getActionCommand() == "Settings") {
						new DrawPanel().drawSettingsFrame();
						guiFrame.setVisible(Boolean.FALSE);
					}

				}
			});
}

private void setPlayersAndMapForPlayerVsAI() {
	Player firstPlayer = new Player(0, Sign.O);
	Player secondPlayer = new Player(1, Sign.X, Boolean.TRUE);
	List<Player> playerList = new ArrayList<>();
	playerList.add(firstPlayer);
	playerList.add(secondPlayer);
	if (Map.rows == null) {
		Map.initializeMap(1200, 800, 5, 5, firstPlayer, playerList);
	}
}

private void setPlayersAndMapForAIVsAI() {
	Player firstPlayer = new Player(0, Sign.O, Boolean.TRUE);
	Player secondPlayer = new Player(1, Sign.X, Boolean.TRUE);
	List<Player> playerList = new ArrayList<>();
	playerList.add(firstPlayer);
	playerList.add(secondPlayer);
	if (Map.rows == null) {
		Map.initializeMap(1200, 800, 5, 5, firstPlayer, playerList);
	}
}

}