package algorithms;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import supportGUI.Circle;

public class TestCircle extends TestRectangle{
	
	public static void main( String[] argv ) {
		String line;
		int test = 0;
		try {
			BufferedReader in = new BufferedReader(new FileReader("input.points"));
			ArrayList<Point> points = new ArrayList<Point>();
			String prev = null;
			int numTest = -1;
			line = in.readLine();
			while (line != null) {
				if(line.charAt(0) == 't') {
					if(test > 0) {
						long startTime = System.currentTimeMillis();
						Circle res = Ritter.ritter(points);
						ArrayList<Point> polygon = Calcule.jarvis(points);
						long endTime = System.currentTimeMillis();
						int diff = (int) (endTime - startTime);
						double q = Calcule.quality(res, polygon);
						Mytest<Double> testQuality = new Mytest<Double>(numTest, q);
						Mytest<Integer> testTime = new Mytest<Integer>(numTest, diff);
						Mytest<Integer> testPoint = new Mytest<Integer>(numTest, points.size());
						qualities.add(testQuality);
						times.add(testTime);
						nodes.add(testPoint);
						points.clear();
					}
					prev = line.substring(5, 10);
					numTest = treatNumber(prev);
					test++;
				}else {
					String [] cor = line.split(" ");
					points.add(new Point(Integer.parseInt(cor[0]),Integer.parseInt(cor[1])));
				}
				line = in.readLine();
			}
			printQuality();
			in.close();
		}catch (IOException e) {
			System.err.println("io error");
		}
	}
}
