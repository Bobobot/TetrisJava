package tetris;

import java.awt.*;

/**
 * This class represents a block in the Tetris game.
 * Has a position, and a color.
 */
public class Block {
	Point position;
	Color color;

	/**
	 * The constructor, creates a block with the given parameters.
	 * @param x The x coordinate of the block
	 * @param y The y coordinate of the block
	 * @param color The color of the block
	 */
	public Block(int x, int y, Color color) {
		this.color = color;
		position = new Point(x, y);
	}
}