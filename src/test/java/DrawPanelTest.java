import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DrawPanelTest {

@Before
public void setUp() {
	Board board;
	Integer[][] map =
			{
					{0, null, 1, null, null},
					{0, 1, null, 1, null},
					{1, 0, null, null, null},
					{1, 1, null, null, 0},
					{0, null, null, null, null}};
	board = new Board(map);
	Player firstPlayer = new Player(0, Sign.O);
	Player secondPlayer = new Player(1, Sign.X, Boolean.TRUE);
	List<Player> playerList = new ArrayList<>();
	playerList.add(firstPlayer);
	playerList.add(secondPlayer);
	Map.initializeMap(1200, 800, 5, 5, board, firstPlayer, playerList);
}


@Test
public void boardAddOnBoard() throws Exception {
	new PointOnMap(2, 3, 0);
	Map.board.addPoint(new PointOnMap(2, 3, 0));
	Assert.assertEquals((Integer) 0, Map.board.getBoard()[2][3]);
	Assert.assertEquals((Integer) 0, Map.board.addPoint(new PointOnMap(2, 3, 0))[2][3]);
}

@Test
public void getValidMoves() throws Exception {
	List<PointOnMap> points = Map.board.getValidMoves(Map.currentPlayer.getValue());
	Assert.assertEquals(14, points.size());
	Assert.assertEquals((Integer) 1, points.get(0).column);
	Assert.assertEquals((Integer) 0, points.get(0).row);
	Assert.assertEquals((Integer) 3, points.get(1).column);
	Assert.assertEquals((Integer) 0, points.get(1).row);
	Assert.assertEquals((Integer) 4, points.get(13).column);
	Assert.assertEquals((Integer) 4, points.get(13).row);
}

@Test
public void getAnotherMoveForAI() throws Exception {
	DrawPanel drawPanel = new DrawPanel();
	PointOnMap nextMove = drawPanel.getAnotherMoveForAI();
	Assert.assertNotNull(nextMove);
	Assert.assertNotNull(nextMove.column);
	Assert.assertNotNull(nextMove.row);
	Assert.assertEquals((Integer) 0, nextMove.value);
}

}