package tetris;

import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
/**
 * This class renders the game.
 */
public class SwingGraphics extends JPanel {

	int blockRenderSize = 35;

	/**
	 * Overrides the paintComponent class from JPanel. This class is called whenever the canvas is being rendered.
	 * Renders the falling shape (activeShape), and the frozen, inactive blocks.
	 * @param g
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Synchronized, because if we delete a block right at the moment we want to render the blocks array, we get a lot of errors
		synchronized (Tetris.gameField.blocks) {
			for (Block block : Tetris.gameField.blocks) {
				g.setColor(block.color);
				int blockX = block.position.x * blockRenderSize;
				int blockY = block.position.y * blockRenderSize;
				g.fillRect(blockX, blockY, blockRenderSize, blockRenderSize);
			}
		}
		//Synchronized: if we create a new shape right at the moment we want to render it, again, not good
		synchronized (Tetris.gameField.activeShape) {
			for (Block block : Tetris.gameField.activeShape.blocks) {
				g.setColor(block.color);
				int blockX = (block.position.x + Tetris.gameField.activeShape.position.x) * blockRenderSize;
				int blockY = (block.position.y + Tetris.gameField.activeShape.position.y) * blockRenderSize;
				g.fillRect(blockX, blockY, blockRenderSize, blockRenderSize);
			}
		}
	}
}