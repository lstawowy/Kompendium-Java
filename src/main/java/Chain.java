public class Chain {
private Boolean leftSideLimit;


private Boolean rightSideLimit;
private Integer length;
private PointOnMap startPoint;
private PointOnMap endPoint;
private Integer value;

public Chain(Boolean leftSideLimit, Boolean rightSideLimit, Integer length, PointOnMap startPoint, PointOnMap endPoint) {
	this.leftSideLimit = leftSideLimit;
	this.rightSideLimit = rightSideLimit;
	this.length = length;
	this.startPoint = startPoint;
	this.endPoint = endPoint;
}

public Integer getValue() {
	return value;
}


public Integer getLength() {
	return length;
}

public Boolean getLeftSideLimit() {
	return leftSideLimit;
}

public Boolean getRightSideLimit() {
	return rightSideLimit;
}


public Integer countValue() {
	if (this != null) {
		if (length <= 5 && length > 0) {
			switch (length) {
				case 1:
					if (leftSideLimit && rightSideLimit) {
						value = 1;
					} else {
						if (!leftSideLimit && rightSideLimit || leftSideLimit && !rightSideLimit) {
							value = 1;
						} else {
							value = 0;
						}
					}
					break;
				case 2:
					if (leftSideLimit && rightSideLimit) {
						value = 8;
					} else {
						if (!leftSideLimit && rightSideLimit || leftSideLimit && !rightSideLimit) {
							value = 4;
						} else {
							value = 0;
						}
					}
					break;
				case 3:
					if (leftSideLimit && rightSideLimit) {
						value = 20;
					} else {
						if (!leftSideLimit && rightSideLimit || leftSideLimit && !rightSideLimit) {
							value = 10;
						} else {
							value = 0;
						}
					}
					break;
				case 4:
					if (leftSideLimit && rightSideLimit) {
						value = 250;
					} else {
						if (!leftSideLimit && rightSideLimit || leftSideLimit && !rightSideLimit) {
							value = 100;
						} else {
							value = 0;
						}
					}
					break;
				case 5:
					value = 1000;
					break;
			}
		}
		return value;
	} else {
		return 0;
	}
}


}
