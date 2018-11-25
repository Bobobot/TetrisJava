package tetris;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads the data of a shape from a file and stores it.
 * This data includes all the rotations of a specific shape, the rotation state it should
 * start in when it spawns (startingState), and its color.
 */
public class ShapeData {
	boolean[][][] rotationData = new boolean[4][4][4];
	int startingState;
	Color color;

	/**
	 * Reads the data of the shape from file
	 * @param shapeEnum The shape that has to be read from file
	 */
	public ShapeData(ShapeEnum shapeEnum) {
		switch (shapeEnum) {
			case LINE:
				initJson("res/line.json");
				break;
			case SQUARE:
				initJson("res/square.json");
				break;
			case L_SHAPE:
				initJson("res/L_shape.json");
				break;
			case J_SHAPE:
				initJson("res/J_shape.json");
				break;
			case T_SHAPE:
				initJson("res/T_shape.json");
				break;
			case Z_SHAPE:
				initJson("res/Z_shape.json");
				break;
			case S_SHAPE:
				initJson("res/S_shape.json");
				break;
		}
	}

	/**
	 * Initializes a shape by reading its data from the given file. Uses JSON parsing.
	 * @param fileName The name of the file that stores the data of a shape.
	 */
	private void initJson(String fileName) {
		try {
			JSONObject jsonObject = (JSONObject)(new JSONParser().parse(new FileReader(fileName)));
			startingState = ((Number)jsonObject.get("startingState")).intValue();
			JSONArray jsonColor = (JSONArray)jsonObject.get("color");
			color = new Color(((Number)jsonColor.get(0)).intValue(), ((Number)jsonColor.get(1)).intValue(), ((Number)jsonColor.get(2)).intValue());

			for (int rotationIndex = 0; rotationIndex < 4; rotationIndex++) {
				JSONArray rotation = (JSONArray)jsonObject.get("rotation" + rotationIndex);
				for (int y = 0; y < 4; y++) {
					for (int x = 0; x < 4; x++) {
						rotationData[rotationIndex][y][x] = String.valueOf(rotation.get(y)).charAt(x) == '1';
					}
				}
			}
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}
}