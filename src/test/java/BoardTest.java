import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {
private static final Integer getValueResult = -4;
private Board board;

@Before
public void setUp() {
	Integer[][] board1 =
			{
					{0, null, 1, null, null},
					{0, 1, null, 1, null},
					{1, 0, null, null, null},
					{1, 1, null, null, 0},
					{0, null, null, null, null}};
	board = new Board(board1);
	board.getChains();
}


@Test
public void getValue() throws Exception {
	Assert.assertTrue(board.getFirstPlayerChains().size()!=0);
	Assert.assertTrue(board.getSecondPlayerChains().size()!=0);
	Assert.assertEquals(getValueResult,board.getValue(Board.firstPlayerValue));
}

@Test
public void getChains() throws Exception {
	Assert.assertEquals(5, board.getFirstPlayerChains().size());
	Assert.assertEquals(7, board.getSecondPlayerChains().size());
}

@Test
public void checkRight() throws Exception {
	Assert.assertTrue(board.checkRight(0, 1, 1));
	Assert.assertTrue(board.checkRight(2, 0, 0));
	Assert.assertFalse(board.checkRight(3, 4, 0));
	Assert.assertFalse(board.checkRight(0, 0, 1));
}

@Test
public void checkDown() throws Exception {
	Assert.assertTrue(board.checkDown(0, 0, 0));
	Assert.assertTrue(board.checkDown(0, 1, 1));
	Assert.assertFalse(board.checkDown(4, 4, 0));
	Assert.assertFalse(board.checkDown(0, 0, 1));
}

@Test
public void checkDiagonalRightDown() throws Exception {
	Assert.assertTrue(board.checkDiagonalRightDown(0, 0, 1));
	Assert.assertTrue(board.checkDiagonalRightDown(2, 3, 0));
	Assert.assertFalse(board.checkDiagonalRightDown(4, 1, 0));
	Assert.assertFalse(board.checkDiagonalRightDown(1, 4, 1));
	Assert.assertFalse(board.checkDiagonalRightDown(0, 1, 0));
}

@Test
public void checkDiagonalLeftDown() throws Exception {
	Assert.assertTrue(board.checkDiagonalLeftDown(0, 2, 1));
	Assert.assertTrue(board.checkDiagonalLeftDown(3, 1, 0));
	Assert.assertFalse(board.checkDiagonalLeftDown(4, 2, 0));
	Assert.assertFalse(board.checkDiagonalLeftDown(0, 0, 1));
	Assert.assertFalse(board.checkDiagonalLeftDown(0, 1, 1));
}

@Test
public void addPoint() throws Exception {
	board.addPoint(new PointOnMap(0, 1, 1));
}

@Test
public void simpleCheckLeft() throws Exception {
	Assert.assertTrue(board.simpleCheckLeft(2, 2, 0));
	Assert.assertFalse(board.simpleCheckLeft(4, 2, 0));
	Assert.assertFalse(board.simpleCheckLeft(4, 0, 0));
}

@Test
public void simpleCheckRight() throws Exception {
	Assert.assertTrue(board.simpleCheckRight(0, 1, 1));
	Assert.assertFalse(board.simpleCheckRight(2, 0, 1));
	Assert.assertFalse(board.simpleCheckRight(4, 4, 0));
}

@Test
public void simpleCheckUp() throws Exception {
	Assert.assertTrue(board.simpleCheckUp(2, 1, 1));
	Assert.assertFalse(board.simpleCheckUp(4, 0, 0));
	Assert.assertFalse(board.simpleCheckUp(0, 4, 0));
}

@Test
public void simpleCheckDown() throws Exception {
	Assert.assertTrue(board.simpleCheckDown(0, 3, 1));
	Assert.assertFalse(board.simpleCheckDown(2, 4, 1));
	Assert.assertFalse(board.simpleCheckDown(4, 4, 0));
}

@Test
public void simpleCheckDiagonalLeftUp() throws Exception {
	Assert.assertTrue(board.simpleCheckDiagonalLeftUp(1, 1, 0));
	Assert.assertFalse(board.simpleCheckDiagonalLeftUp(3, 1, 0));
	Assert.assertFalse(board.simpleCheckDiagonalLeftUp(0, 4, 0));
	Assert.assertFalse(board.simpleCheckDiagonalLeftUp(3, 0, 0));
}

@Test
public void simpleCheckDiagonalRightDown() throws Exception {
	Assert.assertTrue(board.simpleCheckDiagonalRightDown(0, 0, 1));
	Assert.assertFalse(board.simpleCheckDiagonalRightDown(2, 0, 0));
	Assert.assertFalse(board.simpleCheckDiagonalRightDown(0, 4, 0));
	Assert.assertFalse(board.simpleCheckDiagonalRightDown(4, 3, 0));
}

@Test
public void simpleCheckDiagonalRightUp() throws Exception {
	Assert.assertTrue(board.simpleCheckDiagonalRightUp(2, 2, 1));
	Assert.assertFalse(board.simpleCheckDiagonalRightUp(4, 3, 1));
	Assert.assertFalse(board.simpleCheckDiagonalRightUp(0, 2, 0));
	Assert.assertFalse(board.simpleCheckDiagonalRightUp(2, 4, 0));
}

@Test
public void simpleCheckDiagonalLeftDown() throws Exception {
	Assert.assertTrue(board.simpleCheckDiagonalLeftDown(2, 2, 1));
	Assert.assertFalse(board.simpleCheckDiagonalLeftDown(2, 1, 0));
	Assert.assertFalse(board.simpleCheckDiagonalLeftDown(4, 2, 0));
	Assert.assertFalse(board.simpleCheckDiagonalLeftDown(3, 0, 0));
}

}