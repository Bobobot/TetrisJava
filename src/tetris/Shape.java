package tetris;

/**
 * A class that represents the falling shape.
 */
public class Shape {
	ShapeData shapeData;
	int activeState; //The rotation the shape is in
	Point position; //Position of the top left corner of the shape
	Block[] blocks = new Block[4]; //The blocks have relative position to the shape's position

	/**
	 * A constructor that initializes a new shape with the given shapeData.
	 * @param shapeData The data of the shape read from a file.
	 */
	public Shape(ShapeData shapeData) {
		initNewShape(shapeData);
	}

	/**
	 * Initializes a new shape with the given shapeData.
	 * @param shapeData
	 */
	public void initNewShape(ShapeData shapeData) {
		this.shapeData = shapeData;
		activeState = shapeData.startingState;
		shapeDataToBlocks(activeState);
	}

	/**
	 * Returns a new shape that's been rotated in the direction given. We do this (instead of simply rotating the current shape) for collision checking reasons.
	 * @param direction The direction that the rotation would occur in
	 * @return The rotated shape
	 */
	public Shape getRotatedState(Direction direction) {
		Shape rotatedShape = new Shape(shapeData);
		rotatedShape.activeState = activeState;
		rotatedShape.position = new Point(position);
		switch (direction) {
		case RIGHT:
			if (rotatedShape.activeState == 3) rotatedShape.activeState = 0;
			else rotatedShape.activeState++;
			break;
		case LEFT:
			if (rotatedShape.activeState == 0) rotatedShape.activeState = 3;
			else rotatedShape.activeState--;
			break;
		default:
			System.out.println("Frick itt valami nem jó");
			break;
		}
		rotatedShape.shapeDataToBlocks(rotatedShape.activeState);
		return rotatedShape;
	}

	/**
	 * The shape moves down one block. This is called periodically.
	 */
	public void fallOneBlock() {
		position.y++;
	}

	/**
	 * Converts the shapeData to blocks.
	 * @param activeState The rotation of the shapeData
	 */
	private void shapeDataToBlocks(int activeState) {
		int currentBlockIndex = 0;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (shapeData.rotationData[activeState][y][x]) {
					blocks[currentBlockIndex++] = new Block(x, y, shapeData.color);
				}
			}
		}
	}

	/**
	 * Used for JUnit testing only
	 * @return The position of the shape.
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * Used for JUnit testing only
	 * @param position The position of the shape.
	 */
	public void setPosition(Point position) {
		this.position = position;
	}

}