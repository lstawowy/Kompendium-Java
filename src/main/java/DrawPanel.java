import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

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
			if(Map.board[r][c]!=null) {
				if(Map.board[r][c]==0) {
					newShape = new DrawO(r * (Map.width / Map.rows), c * (Map.height / Map.columns));
					Map.listOfShapes.add(newShape);
				} else {
					if(Map.board[r][c]==1){
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
			int x = e.getX();
			int y = e.getY();
			Boolean validMove = Map.addOnBoard(Map.currentPlayer.getValue(),x/(Map.width/Map.columns),y/(Map.height/Map.rows));
			if(validMove){
				Map.nextPlayer();
			}
			addShapes();
			frame.paintComponents(frame.getGraphics());
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}
	};

}



}
