import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tetris.Point;

public class PointTest {
	private int x1, x2, y1, y2;
	private Point p1, p2;

	@BeforeEach
	void setUp() {
		x1 = 10;
		x2 = 20;
		y1 = 30;
		y2 = 40;
		p1 = new Point(x1, y1);
		p2 = new Point(x2, y2);

	}

	@Test
	void testBelow() {
		Assertions.assertEquals(p1.below(), new Point(x1, y1 + 1));
	}

	@Test
	void testRightTo() {
		Assertions.assertEquals(p1.rightTo(), new Point(x1 + 1, y1));
	}


	@Test
	void testLeftTo() {
		Assertions.assertEquals(p1.leftTo(), new Point(x1 - 1, y1));
	}


	@Test
	void testAdd() {
		Assertions.assertEquals(p1.add(p2), new Point(x1 + x2, y1 + y2));
	}

	@Test
	void testToString() {
		Assertions.assertEquals(p1.toString(), "(" + x1 + ", " + y1 + ")");
		Assertions.assertEquals(p2.toString(), "(" + x2 + ", " + y2 + ")");
	}
}
