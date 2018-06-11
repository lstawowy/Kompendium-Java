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
	guiFrame.setLayout(new GridLayout());
	guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	guiFrame.setTitle("TicTacToe GUI");
	guiFrame.setSize(Map.width, Map.height);
	guiFrame.setLocationRelativeTo(null);
	guiFrame.setResizable(false);
	JButton playerVsAiButton = new JButton("Player vs AI");
	setUpPlayButton(playerVsAiButton);
	JButton aiVsAiButton = new JButton("AI vs AI");
	setUpPlayButton(aiVsAiButton);
	JButton settingsButton = new JButton("Settings");
	setUpPlayButton(settingsButton);

	JPanel panel = new JPanel();
	panel.add(playerVsAiButton);
	panel.add(aiVsAiButton);
	panel.add(settingsButton);
	panel.setSize(300, 200);
	panel.setAlignmentX(Map.width / 2);
	panel.setAlignmentY(Map.height / 2);
	guiFrame.getContentPane().add(panel);
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
	Map.listOfShapes.clear();
	Map.listOfShapes = new ArrayList<>();
	Player firstPlayer = new Player(0, Sign.O);
	Player secondPlayer = new Player(1, Sign.X, Boolean.TRUE);
	List<Player> playerList = new ArrayList<>();
	playerList.add(firstPlayer);
	playerList.add(secondPlayer);
	if (Map.rows == null) {
		Map.initializeMap(1600, 1000, 5, 5, firstPlayer, playerList);
	}
}

private void setPlayersAndMapForAIVsAI() {
	Map.listOfShapes.clear();
	Map.listOfShapes = new ArrayList<>();
	Player firstPlayer = new Player(0, Sign.O, Boolean.TRUE);
	Player secondPlayer = new Player(1, Sign.X, Boolean.TRUE);
	List<Player> playerList = new ArrayList<>();
	playerList.add(firstPlayer);
	playerList.add(secondPlayer);
	if (Map.rows == null) {
		Map.initializeMap(1600, 1000, 5, 5, firstPlayer, playerList);
	}
}

}