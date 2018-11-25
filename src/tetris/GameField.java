package tetris;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * A class that represents the playing field.
 * Has a width, a height, contains the currently falling blocks (activeShape), as well as the frozen, inactive ones (blocks)
 */
public class GameField {
	int width = 10;
	int height = 20;
	ArrayList<Block> blocks = new ArrayList<Block>();
	Random rand;
	Shape activeShape;

	/**
	 * The constructor of the GameField.
	 * Creates a new active shape with a random shape data when called.
	 * This is needed because the activeShape can't be null when the spawnNewShape method is called, otherwise we'd get a nullPointerException in the synchronized code block.
	 */
	GameField() {
		rand = new Random();
		activeShape = new Shape(Tetris.shapeDatas.get(ShapeEnum.values()[rand.nextInt(6)])); //Selects a random shape in the shapeDatas EnumMap, and creates a new Shape with it
	}

	/**
	 * Updates the game field.
	 * Checks for collision, then moves the activeShape down/generates inactive blocks from the activeShape and creates a new activeShape accordingly.
	 */
	public void updateGameField() {
		if (!checkCollisionMove(Direction.BELOW)) {
			activeShape.fallOneBlock();
		} else {
			Tetris.fallsPerSecond = 2;
			turnActiveShapeIntoBlocks();
			removeFullRows();
			spawnNewShape();
			if (checkCollisionMove(Direction.BELOW))
				Tetris.gameRunning = false;
		}
	}

	/**
	 * Selects a random shape, and creates it at the top of the screen
	 */
	public void spawnNewShape() {
		synchronized (activeShape) {
			System.out.println("spawning new shape");
			int randomShapeNumber = rand.nextInt(6);
			activeShape.initNewShape(Tetris.shapeDatas.get(ShapeEnum.values()[randomShapeNumber])); //Selects a random shape in the shapeDatas EnumMap, and creates a new Shape with it
			activeShape.position = new Point(width / 2 - 2, 0);
		}
	}

	/**
	 * Checks if the current active shape would be over a block/out of bounds if it moved in the direction given
	 * @return true if a collision would occur, otherwise false
	 */
	private boolean checkCollisionMove(Direction direction) {
		for (Block activeBlock : activeShape.blocks) {
			Point activeBlockPosition = activeShape.position.add(activeBlock.position);
			for (Block inactiveBlock : blocks) {
				//block collision check
				switch (direction) {
					case BELOW:
						if (activeBlockPosition.below().equals(inactiveBlock.position))
							return true;
						break;
					case RIGHT:
						if (activeBlockPosition.rightTo().equals(inactiveBlock.position))
							return true;
						break;
					case LEFT:
						if (activeBlockPosition.leftTo().equals(inactiveBlock.position))
							return true;
						break;
				}

			}
			//out of bounds check
			switch (direction) {
				case BELOW:
					if (activeBlockPosition.below().y >= height)
						return true; //If the block is on the bottom of the game field
					break;
				case RIGHT:
					if (activeBlockPosition.rightTo().x >= width)
						return true;
					break;
				case LEFT:
					if (activeBlockPosition.leftTo().x < 0)
						return true;
					break;
			}
		}
		return false;
	}

	/**
	 * Checks if the current active shape would be over a block/out of bounds if it rotated in the direction given
	 * @return true if a collision would occur, otherwise false
	 */
	private boolean checkCollisionRotate(Direction direction) {
		Shape rotatedActiveShape = activeShape.getRotatedState(direction);

		for (Block rotatedActiveBlock : rotatedActiveShape.blocks) {
			//Checking if the rotation would collide with any of the inactive blocks
			Point rotatedActiveBlockPosition = rotatedActiveShape.position.add(rotatedActiveBlock.position);
			for (Block inactiveBlock : blocks) {
				if (rotatedActiveBlockPosition.equals(inactiveBlock.position)) return true;
			}
			//Checking if the rotation would go out of bounds
			if (rotatedActiveBlockPosition.y >= height
					|| rotatedActiveBlockPosition.y < 0
					|| rotatedActiveBlockPosition.x >= width
					|| rotatedActiveBlockPosition.x < 0) return true;
		}
		return false;
	}

	/**
	 * Turns the activeShape into inactive blocks.
	 * This should be called when the activeShape collided with the ground/an inactive block.
	 */
	private void turnActiveShapeIntoBlocks() {
		for (Block block : activeShape.blocks) {
			block.position = block.position.add(activeShape.position);
			blocks.add(block);
		}
	}

	/**
	 * Moves the activeShape in the direction given.
	 * @param direction Either left, or right, depending on user input.
	 */
	public void moveActiveShape(Direction direction) {
		switch (direction) {
			case RIGHT:
				if (!checkCollisionMove(Direction.RIGHT))
					activeShape.position.x++;
				break;
			case LEFT:
				if (!checkCollisionMove(Direction.LEFT))
					activeShape.position.x--;
				break;
			default:
				System.out.println("Frick itt valami nem jó");
				break;
		}
	}

	/**
	 * Checks for collision, then rotates the active shape in the direction given
	 * @param direction Either left, or right, depending on user input.
	 */
	public void rotateActiveShape(Direction direction) {
		Shape rotatedActiveShape;
		rotatedActiveShape = activeShape.getRotatedState(direction);
		if (!checkCollisionRotate(direction)) {
			activeShape.blocks = rotatedActiveShape.blocks;
			activeShape.activeState = rotatedActiveShape.activeState;
		}
	}

	/**
	 * Checks whether the GameField has any rows that are full with inactive blocks
	 * If yes, that row gets removed and every inactive block above it gets moved down by one block.
	 */
	public void removeFullRows() {
		int[] rowBlockCount = new int[height];
		for (Block block : blocks) {
			rowBlockCount[block.position.y]++;
		}

		synchronized (blocks) {
			//We check if any of the rows have <width> amount of blocks in them
			for (int i = 0; i < height; i++) {
				//If yes, we delete all the blocks that are on said y value
				if (rowBlockCount[i] == width) {
					for (Iterator<Block> blockIterator = blocks.iterator(); blockIterator.hasNext(); ) {
						Block block = blockIterator.next();
						if (block.position.y == i) {
							System.out.println("Removing block at " + block.position);
							blockIterator.remove();
						}
						//We lower every single block above said row
						if (block.position.y < i) {
							block.position.y++;
						}
					}

				}
			}
		}
	}

	/**
	 * Used for JUnit testing only
	 * @return The active shape.
	 */
	public Shape getActiveShape() {
		return activeShape;
	}


	/**
	 * Used for JUnit testing only
	 * @return The width.
	 */
	public int getWidth() {
		return width;
	}


	/**
	 * Used for JUnit testing only
	 * @return The inactive blocks.
	 */
	public ArrayList<Block> getBlocks() {
		return blocks;
	}
}
