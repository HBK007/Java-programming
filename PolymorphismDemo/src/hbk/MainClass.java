package hbk;

import java.util.ArrayList;
import java.util.Random;

public class MainClass {
	public static void main(String []arg){
		int PANEL_AREA = 100;
		int totalArea = 0;
		ArrayList<Shape> arrShape = new ArrayList<Shape>();
		while(totalArea < PANEL_AREA * 0.8){
			Random random = new Random();
			int typeShape = random.nextInt(3);
			Shape shape;
			switch (typeShape){
			case 0:
				shape = new Circle();
				break;
			case 1:
				shape = new Rectangle();
				break;
			case 2:
				shape = new Triangle();
				break;
			default:
				shape = null;
				break;
			}
			if(shape != null){
				arrShape.add(shape);
			}
			for (Shape s : arrShape) {
				totalArea += s.calculateArea();
			}
			System.out.println("Total area " + totalArea);
		}
	}
}
