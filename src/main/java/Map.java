import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Map {
public static Integer width = 1200;
public static Integer height = 800;
public static Integer rows;
public static Integer columns;
public static Integer[][] board;
public static List<Shape> listOfShapes = new ArrayList<>();
public static Player currentPlayer;
public static List<Player> players;
public static Integer howManyThreads = 8;


public static void getValidMoves() {

}

public static void initializeMap(Integer _width, Integer _height, Integer _rows, Integer _columns, Integer[][] _board, Player _currentPlayer, List<Player> _players) {
	width = _width;
	height = _height;
	rows = _rows;
	columns = _columns;
	board = _board;
	currentPlayer = _currentPlayer;
	players = _players;

}

public static Integer[][] addPointsOnBoardAndReturnBoard(List<PointOnMap> pointOnMapList) {
	Integer[][] newBoard = Map.board;
	ForkJoinPool myPool = new ForkJoinPool(howManyThreads);
	return newBoard;
}

public static void initializeMap(Integer _width, Integer _height, Integer _rows, Integer _columns, Player _currentPlayer, List<Player> _players) {
	width = _width;
	height = _height;
	rows = _rows;
	columns = _columns;
	board = new Integer[rows][columns];
	currentPlayer = _currentPlayer;
	players = _players;
}

public static Boolean addOnBoard(Integer value, Integer column, Integer row) {
	if (board[column][row] == null) {
		board[column][row] = value;
		return Boolean.TRUE;
	} else {
		return Boolean.FALSE;
	}
}

public static Player nextPlayer() {
	if (players.indexOf(currentPlayer) + 1 < players.size())
		currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
	else
		currentPlayer = players.get(0);
	return currentPlayer;
}





public static Integer[][] randomBoard(int rows, int columns) {
	Random randomGenerator = new Random();
	Integer[][] board = new Integer[rows][columns];
	Integer difference = 0;
	for (int r = 0; r < rows; r++) {
		for (int c = 0; c < columns; c++) {
			if (randomGenerator.nextBoolean() && difference < 1) {
				board[r][c] = 1;
				difference++;
			} else {
				if (randomGenerator.nextBoolean() && difference > -1) {
					board[r][c] = 0;
					difference--;
				} else {
					board[r][c] = null;
				}
			}

		}

	}
	return board;
}

}