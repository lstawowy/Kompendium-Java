import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Board {
private Integer[][] board;
private List<Chain> firstPlayerChains;
public static final Integer firstPlayerValue = 0;
private List<Chain> secondPlayerChains;
public static final Integer secondPlayerValue = 1;
private Integer value;

public Board(Integer[][] board) {
	this.board = board;
}

public Board(Integer[][] board, PointOnMap pointOnMap) {
	Board tempBoard = new Board(board);
	this.board = tempBoard.addPoint(pointOnMap);
}

public Integer[][] getBoard() {
	return board;
}

public void setBoard(Integer[][] board) {
	this.board = board;
}

public Board(Board board, PointOnMap pointOnMap) {
	this.board = board.addPoint(pointOnMap);
}

public Integer checkPointValue(int row, int column) {
	return board[row][column];
}

public List<Chain> getFirstPlayerChains() {
	return firstPlayerChains;
}

public List<Chain> getSecondPlayerChains() {
	return secondPlayerChains;
}

public Integer getValue(Integer playerValue) throws ExecutionException, InterruptedException {
	this.getChains();
	Integer countedValue = 0;
	if (playerValue.equals(firstPlayerValue)) {
		if (firstPlayerChains != null) {
			countedValue += firstPlayerChains.parallelStream().collect(Collectors.summingInt(Chain::countValue));
		}
		if (secondPlayerChains != null) {
			countedValue -= secondPlayerChains.parallelStream().collect(Collectors.summingInt(Chain::countValue));
		}

	} else {
		if (firstPlayerChains != null) {
			countedValue -= firstPlayerChains.parallelStream().collect(Collectors.summingInt(Chain::countValue));
		}
		if (secondPlayerChains != null) {
			countedValue += secondPlayerChains.parallelStream().collect(Collectors.summingInt(Chain::countValue));
		}
	}
	this.value = countedValue;
	return countedValue;
}

public void getChains() {
	firstPlayerChains = new ArrayList<>();
	secondPlayerChains = new ArrayList<>();
	for (int r = 0; r < board.length; r++) {
		for (int c = 0; c < board[0].length; c++) {
			Integer playerValue;
			if (board[r][c] != null) {
				if (board[r][c] != null) {
					if (board[r][c].equals(firstPlayerValue)) {
						playerValue = firstPlayerValue;
					} else {
						playerValue = secondPlayerValue;
					}
					Chain chain = null;
					PointOnMap startPoint = new PointOnMap(r, c, playerValue);
					if (checkRight(r, c, playerValue)) {
						Integer right = 1;
						while (checkRight(r, c + right, playerValue)) {
							right++;
						}
						PointOnMap endPoint = new PointOnMap(r, c + right, playerValue);
						Boolean leftSideOpen = simpleCheckLeft(r, c, playerValue);
						Boolean rightSideOpen = simpleCheckRight(r, c + right, playerValue);
						chain = new Chain(leftSideOpen, rightSideOpen, right + 1, startPoint, endPoint);
						addChain(playerValue, chain);
					}
					if (checkDown(r, c, playerValue)) {
						Integer down = 1;
						while (checkDown(r + down, c, playerValue)) {
							down++;
						}
						PointOnMap endPoint = new PointOnMap(r + down, c, playerValue);
						Boolean leftSideOpen = simpleCheckUp(r, c, playerValue);
						Boolean rightSideOpen = simpleCheckDown(r + down, c, playerValue);
						chain = new Chain(leftSideOpen, rightSideOpen, down + 1, startPoint, endPoint);
						addChain(playerValue, chain);
					}
					if (checkDiagonalRightDown(r, c, playerValue)) {
						Integer down = 1;
						while (checkDiagonalRightDown(r + down, c + down, playerValue)) {
							down++;
						}
						PointOnMap endPoint = new PointOnMap(r + down, c + down, playerValue);
						Boolean leftSideOpen = simpleCheckDiagonalLeftUp(r, c, playerValue);
						Boolean rightSideOpen = simpleCheckDiagonalRightDown(r + down, c + down, playerValue);
						chain = new Chain(leftSideOpen, rightSideOpen, down + 1, startPoint, endPoint);
						addChain(playerValue, chain);
					}
					if (checkDiagonalLeftDown(r, c, playerValue)) {
						Integer down = 1;
						while (checkDiagonalLeftDown(r + down, c - down, playerValue)) {
							down++;
						}
						PointOnMap endPoint = new PointOnMap(r + down, c - down, playerValue);
						Boolean leftSideOpen = simpleCheckDiagonalRightUp(r, c, playerValue);
						Boolean rightSideOpen = simpleCheckDiagonalLeftDown(r + down, c - down, playerValue);
						chain = new Chain(leftSideOpen, rightSideOpen, down + 1, startPoint, endPoint);
						addChain(playerValue, chain);
					}
					if (chain == null && !simpleCheckAll(r, c, playerValue)) {
						chain = new Chain(Boolean.TRUE, Boolean.TRUE, 1, startPoint, startPoint);
						addChain(playerValue, chain);
					}
				}

			}
		}
	}
}

public void addChain(Integer playerValue, Chain chain) {
	if (chain != null) {
		if (playerValue.equals(firstPlayerValue)) {
			firstPlayerChains.add(chain);
		} else {
			secondPlayerChains.add(chain);
		}
	}
}

public Boolean checkRight(Integer row, Integer column, Integer playerValue) {
	if (column + 1 >= board[0].length) {
		return Boolean.FALSE;
	}
	if (column - 1 >= 0) {
		if (!simpleCheckLeft(row, column, playerValue)) {
			if (simpleCheckRight(row, column, playerValue)) {
				return Boolean.TRUE;
			}
		}
	}
	if (column == 0) {
		if (simpleCheckRight(row, column, playerValue)) {
			return Boolean.TRUE;
		}
	}
	return Boolean.FALSE;
}

public Boolean checkDown(Integer row, Integer column, Integer playerValue) {
	if (row + 1 >= board[0].length) {
		return Boolean.FALSE;
	}
	if (row - 1 >= 0) {
		if (simpleCheckUp(row, column, playerValue)) {
			if (simpleCheckDown(row, column, playerValue)) {
				return Boolean.TRUE;
			}
		}
	}
	if (row == 0) {
		if (simpleCheckDown(row, column, playerValue)) {
			return Boolean.TRUE;
		}
	}
	return Boolean.FALSE;
}


public Boolean checkDiagonalRightDown(Integer row, Integer column, Integer playerValue) {
	if (row + 1 >= board.length || column + 1 >= board[0].length) {
		return Boolean.FALSE;
	}
	if (row - 1 >= 0 && column - 1 >= 0) {
		if (!simpleCheckDiagonalLeftUp(row, column, playerValue)) {
			if (simpleCheckDiagonalRightDown(row, column, playerValue)) {
				return Boolean.TRUE;
			}
		}
	}
	if (row == 0 || column == 0) {
		if (simpleCheckDiagonalRightDown(row, column, playerValue)) {
			return Boolean.TRUE;
		}
	}
	return Boolean.FALSE;
}

public Boolean checkDiagonalLeftDown(Integer row, Integer column, Integer playerValue) {
	if (row + 1 >= board.length || column - 1 < 0) {
		return Boolean.FALSE;
	}
	if (row - 1 >= 0 && column - 1 >= 0 && column + 1 <= board[0].length && row + 1 <= board.length) {
		if (!simpleCheckDiagonalRightUp(row, column, playerValue)) {
			if (simpleCheckDiagonalLeftDown(row, column, playerValue)) {
				return Boolean.TRUE;
			}
		}
	}
	if (row == 0 || column == (board[0].length - 1)) {
		if (simpleCheckDiagonalLeftDown(row, column, playerValue)) {
			return Boolean.TRUE;
		}
	}
	return Boolean.FALSE;
}

public Boolean simpleCheckAll(Integer row, Integer column, Integer playerValue) {
	if (row + 2 <= board.length && column + 2 <= board[0].length && row - 1 > 1 && column - 1 > 1) {
		if (simpleCheckUp(row, column, playerValue) || simpleCheckDown(row, column, playerValue) || simpleCheckRight(row, column, playerValue) || simpleCheckLeft(row, column, playerValue) || simpleCheckDiagonalRightDown(row, column, playerValue) || simpleCheckDiagonalLeftDown(row, column, playerValue) || simpleCheckDiagonalRightUp(row, column, playerValue) || simpleCheckDiagonalLeftUp(row, column, playerValue)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	if (row == 0 && column + 1 <= board[0].length && column - 1 > 0) {
		if (simpleCheckDown(row, column, playerValue) || simpleCheckRight(row, column, playerValue) || simpleCheckLeft(row, column, playerValue) || simpleCheckDiagonalRightDown(row, column, playerValue) || simpleCheckDiagonalLeftDown(row, column, playerValue)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	if (row == (board.length - 1) && column + 1 <= board[0].length && column - 1 > 0) {
		if (simpleCheckUp(row, column, playerValue) || simpleCheckRight(row, column, playerValue) || simpleCheckLeft(row, column, playerValue) || simpleCheckDiagonalRightUp(row, column, playerValue) || simpleCheckDiagonalLeftUp(row, column, playerValue)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	if (row + 2 <= board.length && column == (board[0].length - 1) && row - 1 > 1) {
		if (simpleCheckUp(row, column, playerValue) || simpleCheckDown(row, column, playerValue) || simpleCheckLeft(row, column, playerValue) || simpleCheckDiagonalLeftDown(row, column, playerValue) || simpleCheckDiagonalLeftUp(row, column, playerValue)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	if (row + 2 <= board.length && column == 0 && row - 1 > 1) {
		if (simpleCheckUp(row, column, playerValue) || simpleCheckDown(row, column, playerValue) || simpleCheckRight(row, column, playerValue) || simpleCheckDiagonalRightDown(row, column, playerValue) || simpleCheckDiagonalRightUp(row, column, playerValue)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	if (row == 0 && column == 0) {
		if (simpleCheckDown(row, column, playerValue) || simpleCheckRight(row, column, playerValue) || simpleCheckDiagonalRightDown(row, column, playerValue)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	if (row == 0 && column == (board[0].length - 1)) {
		if (simpleCheckDown(row, column, playerValue) || simpleCheckLeft(row, column, playerValue) || simpleCheckDiagonalLeftDown(row, column, playerValue)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	if (row == (board.length - 1) && column == 0) {
		if (simpleCheckUp(row, column, playerValue) || simpleCheckRight(row, column, playerValue) || simpleCheckDiagonalRightUp(row, column, playerValue)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	if (row == (board.length - 1) && column == (board[0].length - 1)) {
		if (simpleCheckUp(row, column, playerValue) || simpleCheckLeft(row, column, playerValue) || simpleCheckDiagonalLeftUp(row, column, playerValue)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}


	return Boolean.FALSE;

}

public Boolean simpleCheckLeft(Integer row, Integer column, Integer playerValue) {
	if (column - 1 < 0) {
		return Boolean.FALSE;
	}
	if (board[row][column - 1] == playerValue) {
		return Boolean.TRUE;
	} else {
		return Boolean.FALSE;
	}
}

public Boolean simpleCheckRight(Integer row, Integer column, Integer playerValue) {
	if (column + 1 >= board[0].length) {
		return Boolean.FALSE;
	}
	if (board[row][column + 1] == playerValue) {
		return Boolean.TRUE;
	} else {
		return Boolean.FALSE;
	}
}

public Boolean simpleCheckUp(Integer row, Integer column, Integer playerValue) {
	if (row - 1 < 0) {
		return Boolean.FALSE;
	}
	if (board[row - 1][column] == playerValue) {
		return Boolean.TRUE;
	} else {
		return Boolean.FALSE;
	}
}

public Boolean simpleCheckDown(Integer row, Integer column, Integer playerValue) {
	if (row + 1 >= board.length) {
		return Boolean.FALSE;
	}
	if (board[row + 1][column] == playerValue) {
		return Boolean.TRUE;
	} else {
		return Boolean.FALSE;
	}

}

public Boolean simpleCheckDiagonalLeftUp(Integer row, Integer column, Integer playerValue) {
	if (row - 1 < 0 || column - 1 < 0) {
		return Boolean.FALSE;
	}
	if (board[row - 1][column - 1] == playerValue) {
		return Boolean.TRUE;
	} else {
		return Boolean.FALSE;
	}
}

public Boolean simpleCheckDiagonalRightDown(Integer row, Integer column, Integer playerValue) {
	if (row + 1 >= board.length || column + 1 >= board[0].length) {
		return Boolean.FALSE;
	}
	if (board[row + 1][column + 1] == playerValue) {
		return Boolean.TRUE;
	} else {
		return Boolean.FALSE;
	}
}

public Boolean simpleCheckDiagonalRightUp(Integer row, Integer column, Integer playerValue) {
	if (row - 1 < 0 || column + 1 >= board[0].length) {
		return Boolean.FALSE;
	}
	if (board[row - 1][column + 1] == playerValue) {
		return Boolean.TRUE;
	} else {
		return Boolean.FALSE;
	}
}

public Boolean simpleCheckDiagonalLeftDown(Integer row, Integer column, Integer playerValue) {
	if (row + 1 >= board.length || column - 1 < 0) {
		return Boolean.FALSE;
	}
	if (board[row + 1][column - 1] == playerValue) {
		return Boolean.TRUE;
	} else {
		return Boolean.FALSE;
	}
}

public Integer[][] addPoint(PointOnMap pointOnMap) {
	if (this.board[pointOnMap.row][pointOnMap.column] == null) {
		this.board[pointOnMap.row][pointOnMap.column] = pointOnMap.value;
	}
	return this.board;
}

public void checkIfNotWon() {
	getChains();
	for (Chain chain : firstPlayerChains) {
		if (chain.countValue() == 1000) {
			Map.firstPlayerWonMessage();
		}
	}
	for (Chain chain : secondPlayerChains) {
		if (chain.countValue() == 1000) {
			Map.firstPlayerWonMessage();
		}
	}
}

public List<PointOnMap> getValidMoves(Integer currentPlayerValue) {
	List<PointOnMap> validMoves = new ArrayList<>();
	for (int r = 0; r < board.length; r++) {
		for (int c = 0; c < board[0].length; c++) {
			if (board[r][c] == null) {
				validMoves.add(new PointOnMap(r, c, currentPlayerValue));
			}
		}
	}
	return validMoves;
}

}
