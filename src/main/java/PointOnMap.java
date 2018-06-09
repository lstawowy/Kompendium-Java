public class PointOnMap extends PointsOnMap {
Integer value;
Integer column;
Integer row;

public PointOnMap(Integer row, Integer column, Integer value) {
	this.value = value;
	this.column = column;
	this.row = row;
}

public Integer[][] addThisPointOnBoard(Integer[][] board) {
	board[this.column][this.row] = this.value;
	return board;
}

}
