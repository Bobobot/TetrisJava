import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tetris.Point;
import tetris.Shape;
import tetris.ShapeData;
import tetris.ShapeEnum;

public class ShapeTest {

	@Test
	void testMoveDownOneBlock() {
		Shape shape = new Shape(new ShapeData(ShapeEnum.SQUARE));
		int x = 10;
		int y = 20;
		shape.setPosition(new Point(x, y));
		shape.fallOneBlock();
		Assertions.assertEquals(shape.getPosition(), new Point(x, y + 1));
	}
}
