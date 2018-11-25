import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import tetris.Block;
import tetris.Direction;
import tetris.Point;
import tetris.Tetris;

import java.awt.*;

public class GameFieldTest {
	@BeforeEach
	void setUp() {
		Tetris.initGame();
	}


	@Test
	void testFall() {
		Tetris.gameField.spawnNewShape();
		Point position1 = new Point(Tetris.gameField.getActiveShape().getPosition());
		Tetris.updateGame();
		Point position2 = Tetris.gameField.getActiveShape().getPosition();
		Assertions.assertEquals(position1.below(), position2);
	}

	@Test
	void testMove() {
		Tetris.gameField.spawnNewShape();
		Point position1 = new Point(Tetris.gameField.getActiveShape().getPosition());
		Tetris.gameField.moveActiveShape(Direction.RIGHT);
		Point position2 = Tetris.gameField.getActiveShape().getPosition();
		Assertions.assertEquals(position1.rightTo(), position2);
	}

	@Test
	void testRowClear() {
		for (int x = 0; x < Tetris.gameField.getWidth(); x++) {
			Block block = new Block(x, 0, Color.BLACK);
			Tetris.gameField.getBlocks().add(block);
		}
		Assertions.assertEquals(Tetris.gameField.getBlocks().size(), Tetris.gameField.getWidth());
		Tetris.gameField.removeFullRows();
		Assertions.assertEquals(Tetris.gameField.getBlocks().size(), 0);

	}

	@Test
	void testPointAdd() {
		int x1 = 10;
		int y1 = 20;
		int x2 = 30;
		int y2 = 40;
		Point p1 = new Point(x1, y1);
		Point p2 = new Point(x2, y2);

		Assertions.assertEquals(p1.add(p2), new Point(x1 + x2, y1 + y2));
	}
}
