import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class DrawPanel extends JPanel {
	JFrame frame;

public static void main(String[] args) {
	DrawPanel drawPanel = new DrawPanel();
	drawPanel.drawGameFrame();
}

public void drawGameFrame(){
	frame = new JFrame("Tic Tac Toe");
	frame.setSize(Map.width, Map.height);
	frame.setLocationRelativeTo(null);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setResizable(false);
	frame.setVisible(true);
	frame.addMouseListener(createNewMouseListener());
	setBackground(new Color(129, 135, 73));
	frame.setContentPane(this);
	addShapes();
	frame.paintComponents(frame.getGraphics());
}

public void drawSettingsFrame(){
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

@Override
public void paintComponent(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
	super.paintComponent(g2d);

	for (Shape s : Map.listOfShapes)
		s.draw(g2d);
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

public MouseListener createNewMouseListener(){
	return new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (!Map.currentPlayer.getAIEnabled()) {
				int x = e.getX();
				int y = e.getY();
				if (Map.board.checkPointValue(x / (Map.width / Map.columns), y / (Map.height / Map.rows)) == null) {
					Map.board.addPoint(new PointOnMap(x / (Map.width / Map.columns), y / (Map.height / Map.rows), Map.currentPlayer.getValue()));
					Map.nextPlayer();
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

public PointOnMap getAnotherMoveForAI() throws ExecutionException, InterruptedException {
	Integer[][] map = Map.board.getNewInstanceOfBoard();
	PointOnMap maxPoint = getNextMinMaxPoint(1, new Board(map));
	return maxPoint;
}

private PointOnMap getNextMinMaxPoint(Integer iteration, Board temporaryBoard) throws ExecutionException, InterruptedException {
	Boolean isMax = null;
	if (iteration % 2 == 1) {
		isMax = Boolean.TRUE;
	} else {
		isMax = Boolean.FALSE;
	}
	Integer max = -1000000;
	Integer min = 1000000;
	PointOnMap result = null;
	for (PointOnMap move : temporaryBoard.getValidMoves((iteration) % 2)) {
		Board temporarierBoard = new Board(temporaryBoard.getNewInstanceOfBoard());
		temporarierBoard.addPoint(move);
		if (iteration < Map.howManyMovesForward) {
			getNextMinMaxPoint(iteration + 1, temporaryBoard);
		}
		Integer temp = temporarierBoard.getValue(Map.currentPlayer.getValue());
		if (isMax && max < temp) {
			max = temp;
			result = move;
		} else if (!isMax && min > temp) {
			min = temp;
			result = move;
		}
	}
	return result;
}
}

