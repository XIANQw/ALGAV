package algorithms;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class TestRectangle {
	
	public static ArrayList<Mytest<Double>> qualities = new ArrayList<Mytest<Double>>();
	public static ArrayList<Mytest<Integer>> times = new ArrayList<Mytest<Integer>>();
	public static ArrayList<Mytest<Integer>> nodes = new ArrayList<Mytest<Integer>>();
	
	@SuppressWarnings("unchecked")
	public static void printQualityies() {
		System.out.println("test");
		Collections.sort(qualities);
		for(int i=0; i<qualities.size(); i++) {
			System.out.printf("%d ", qualities.get(i).numTest);
		}
		System.out.printf("\n");
		System.out.println("qualities");
		for(int i=0; i<qualities.size(); i++) {
			System.out.printf("%g ", qualities.get(i).data);
		}
	}
	@SuppressWarnings("unchecked")
	public static void printTimes() {
		Collections.sort(times);
		System.out.println("test");
		for(int i=0; i<times.size(); i++) {
			System.out.printf("%d ", times.get(i).numTest);
		}
		System.out.printf("\n");
		System.out.println("times");
		for(int i=0; i<times.size(); i++) {
			System.out.printf("%d ", times.get(i).data);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void printNodes() {
		Collections.sort(nodes);
		System.out.println("test");
		for(int i=0; i<nodes.size(); i++) {
			System.out.printf("%d ", nodes.get(i).numTest);
		}
		System.out.printf("\n");
		System.out.println("numbre des points de polygon");
		for(int i=0; i<nodes.size(); i++) {
			System.out.printf("%d ", nodes.get(i).data);
		}
	}
	public static int treatNumber(String s) {
		int i = 0;
		while(i < s.length() && s.charAt(i) <= '9' && s.charAt(i) >= '0') {
			i++;
		}try {
			return Integer.parseInt(s.substring(0, i));
		}catch (Exception e) {
			return -1;
		}
	}
	

	
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
						ArrayList<Point> polygon = Calcule.jarvis(points);
						ArrayList<Point> rec = Toussaint.toussaint(polygon);
						long endTime =System.currentTimeMillis();
						int diff = (int) (endTime - startTime);
						rec.add(rec.get(0));
						double q = Calcule.quality(rec, polygon);
						Mytest<Double> testQuality = new Mytest<Double>(numTest, q);
						Mytest<Integer> testTime = new Mytest<Integer>(numTest, diff);
						Mytest<Integer> testPolygon = new Mytest<Integer>(numTest, polygon.size());
						qualities.add(testQuality);
						times.add(testTime);
						nodes.add(testPolygon);
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
			printNodes();
			in.close();
		}catch (IOException e) {
			System.err.println("io error");
		}
	}
}
