import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class DrawPanel extends JPanel {
JFrame frame;

public static void main(String[] args) {
	DrawPanel drawPanel = new DrawPanel();
	drawPanel.drawGameFrame();
}

public void drawGameFrame() {
	frame = new JFrame("Tic Tac Toe");
	frame.setSize(Map.width, Map.height);
	frame.setLocationRelativeTo(null);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setResizable(false);
	frame.setVisible(true);
	frame.addMouseListener(createNewMouseListener());
	setBackground(new Color(138, 184, 226));
	frame.setContentPane(this);
	addShapes();
	frame.paintComponents(frame.getGraphics());
}

public void drawSettingsFrame() {
	frame = new JFrame("Settings");
	frame.setSize(Map.width, Map.height);
	frame.setLocationRelativeTo(null);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setResizable(false);
	frame.setVisible(true);
	setBackground(new Color(19, 32, 135));
	frame.setContentPane(this);
}

static Color randomColor() {
	Random rand = new Random();
	float r = rand.nextFloat();
	float g = rand.nextFloat();
	float b = rand.nextFloat();
	Color ret = new Color(r, g, b);
	return ret;
}

public void addShapes() {
	for (int c = 0; c < Map.columns; c++) {
		for (int r = 0; r < Map.rows; r++) {
			Shape newShape = null;
			if (Map.board.checkPointValue(r, c) != null) {
				if (Map.board.checkPointValue(r, c) == 0) {
					newShape = new DrawO(r * (Map.width / Map.rows), c * (Map.height / Map.columns));
					Map.listOfShapes.add(newShape);
				} else {
					if (Map.board.checkPointValue(r, c) == 1) {
						newShape = new DrawX(r * (Map.width / Map.rows), c * (Map.height / Map.columns));
						Map.listOfShapes.add(newShape);
					}
				}
			}
		}

	}
}

public MouseListener createNewMouseListener() {
	return new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (Map.board.getFirstPlayerChains() == null && Map.board.getSecondPlayerChains() == null && Map.players.get(0).getAIEnabled() && Map.players.get(1).getAIEnabled()) {
				PointOnMap firstMove = new PointOnMap(Map.rows / 2, Map.columns / 2, Map.currentPlayer.getValue());
				Map.board.addPoint(firstMove);
				Map.nextPlayer();
				addShapes();
				frame.paintComponents(frame.getGraphics());
				Map.board.getChains();
			}
			if (!Map.currentPlayer.getAIEnabled()) {
				int x = e.getX();
				int y = e.getY();
				if (Map.board.checkPointValue(x / (Map.width / Map.columns), y / (Map.height / Map.rows)) == null) {
					Map.board.addPoint(new PointOnMap(x / (Map.width / Map.columns), y / (Map.height / Map.rows), Map.currentPlayer.getValue()));
					Map.nextPlayer();
				}
				Map.board.getChains();
				if (Map.board.getFirstPlayerChains() != null && Map.board.getSecondPlayerChains() != null) {
					for (Chain chain : Map.board.getFirstPlayerChains()) {
						if (chain.getLength() == 5) {
							JOptionPane.showMessageDialog(null, "First Player Won");

						}
					}
					for (Chain chain : Map.board.getSecondPlayerChains()) {
						if (chain.getLength() == 5) {
							JOptionPane.showMessageDialog(null, "Second Player Won");
							drawGameFrame();
						}
					}
				}
				addShapes();
				frame.paintComponents(frame.getGraphics());
				if (Map.currentPlayer.getAIEnabled()) {
					try {
						Map.board.addPoint(getAnotherMoveForAI());
						Map.nextPlayer();
					} catch (ExecutionException e1) {

					} catch (InterruptedException e1) {

					}
					addShapes();
					frame.paintComponents(frame.getGraphics());
				}
				Map.board.getChains();
				if (Map.board.getFirstPlayerChains() != null && Map.board.getSecondPlayerChains() != null) {
					for (Chain chain : Map.board.getFirstPlayerChains()) {
						if (chain.getLength() == 5) {
							JOptionPane.showMessageDialog(null, "First Player Won");
							refreshBoard();
						}
					}
					for (Chain chain : Map.board.getSecondPlayerChains()) {
						if (chain.getLength() == 5) {
							JOptionPane.showMessageDialog(null, "Second Player Won");
							refreshBoard();
						}
					}
				}
			}
			while (Map.currentPlayer.getAIEnabled()) {
				try {
					Map.board.addPoint(getAnotherMoveForAI());
					Map.nextPlayer();
				} catch (ExecutionException e1) {

				} catch (InterruptedException e1) {

				}
				addShapes();
				frame.paintComponents(frame.getGraphics());
			}
			if (Map.board.getFirstPlayerChains() != null && Map.board.getSecondPlayerChains() != null) {
				for (Chain chain : Map.board.getFirstPlayerChains()) {
					if (chain.getLength() == 5) {
						JOptionPane.showMessageDialog(null, "First Player Won");
						refreshBoard();
					}
				}
				for (Chain chain : Map.board.getSecondPlayerChains()) {
					if (chain.getLength() == 5) {
						JOptionPane.showMessageDialog(null, "Second Player Won");
						refreshBoard();
					}
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}
	};

}

private void refreshBoard() {
	Map.listOfShapes.clear();
	Map.listOfShapes = new ArrayList<>();
	Map.board = new Board(new Integer[Map.rows][Map.columns]);
	frame.paintComponents(frame.getGraphics());
	Map.listOfShapes = new ArrayList<>();
}

public PointOnMap getAnotherMoveForAI() throws ExecutionException, InterruptedException {
	Integer[][] map = Map.board.getNewInstanceOfBoard();
	PointAndValue maxPoint = getNextMinMaxPoint(1, new Board(map));
	return maxPoint.point;
}

private PointAndValue getNextMinMaxPoint(Integer iteration, Board temporaryBoard) throws ExecutionException, InterruptedException {
	Boolean isMax = null;
	if (iteration % 2 == 1) {
		isMax = Boolean.TRUE;
	} else {
		isMax = Boolean.FALSE;
	}
	Integer max = -1000000;
	Integer min = 1000000;
	PointOnMap result = null;
	for (PointOnMap move : temporaryBoard.getValidMoves((Map.currentPlayer.getValue() + (iteration + 1) % 2))) {
		Board temporarierBoard = new Board(temporaryBoard.getNewInstanceOfBoard());
		temporarierBoard.addPoint(move);
		if (iteration == Map.howManyMovesForward) {
			Integer temp = temporarierBoard.getValue(Map.currentPlayer.getValue());
			if (isMax && max < temp) {
				max = temp;
				result = move;
			} else if (!isMax && min > temp) {
				min = temp;
				result = move;
			}
		} else {
			PointAndValue pointAndValue;
			pointAndValue = getNextMinMaxPoint(iteration + 1, temporaryBoard);
			if (isMax && max < pointAndValue.value) {
				max = pointAndValue.value;
				result = pointAndValue.point;
			} else if (!isMax && min > pointAndValue.value) {
				min = pointAndValue.value;
				result = pointAndValue.point;
			}
		}

	}
	if (isMax) {
		return new PointAndValue(result, max);
	} else {
		return new PointAndValue(result, min);
	}
}

public static void firstPlayerWonMessage() {
	JOptionPane.showMessageDialog(null, "First player won");
	new GUI();
}

@Override
public void paintComponent(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
	super.paintComponent(g2d);
	for (int i = 0; i < (Map.board.getBoard().length); i++) {
		g2d.setColor(new Color(0, 0, 0));
		int x = (Map.width / Map.board.getBoard()[0].length);
		int y = (Map.height / Map.board.getBoard().length);
		g2d.drawLine(15, i * y - 29, Map.width - 35, i * y - 29);
		g2d.drawLine(15, i * y - 31, Map.width - 35, i * y - 31);
		g2d.drawLine(15, i * y - 30, Map.width - 35, i * y - 30);
	}
	for (int i = 0; i < (Map.board.getBoard()[0].length); i++) {
		g2d.setColor(new Color(0, 0, 0));
		int x = (Map.width / Map.board.getBoard()[0].length);
		int y = (Map.height / Map.board.getBoard().length);
		g2d.drawLine(i * x - 7, 15, i * x - 7, Map.height - 55);
		g2d.drawLine(i * x - 6, 15, i * x - 6, Map.height - 55);
		g2d.drawLine(i * x - 5, 15, i * x - 5, Map.height - 55);
	}
	for (Shape s : Map.listOfShapes)
		s.draw(g2d);
}

}

