package algorithms;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	
	public static ArrayList<Double> qualities = new ArrayList<Double>();
	public static ArrayList<Integer> tests = new ArrayList<Integer>();
	
	public static void print() {
		System.out.println("test");
		for(int i=0; i<tests.size(); i++) {
			System.out.printf("%d ", tests.get(i));
		}
		System.out.println("qualities");
		for(int i=0; i<qualities.size(); i++) {
			System.out.printf("%.5f ", qualities.get(i));
		}
	}
	
	public static void main( String[] argv ) {
		String line;
		int test = 0;
		try {
			BufferedReader in = new BufferedReader(new FileReader("input.points"));
			DefaultTeam d = new DefaultTeam();
			ArrayList<Point> points = new ArrayList<Point>();
			String prev = null;
			line = in.readLine();
			while (line != null) {
				if(line.charAt(0) == 't') {
					if(test > 0) {
						ArrayList<Point> polygon = d.jarvis(points);
						ArrayList<Point> rec = d.toussaint(polygon);
						rec.add(rec.get(0));
						double q = d.quality(rec, polygon);
						qualities.add(q);
						tests.add(test);
						points.clear();
					}
					int end = Math.min(line.length(), 9);
					prev = line.substring(0, end);
					test++;
				}else {
					String [] cor = line.split(" ");
					points.add(new Point(Integer.parseInt(cor[0]),Integer.parseInt(cor[1])));
				}
				line = in.readLine();
			}
			print();
			in.close();
		}catch (IOException e) {
			System.err.println("io error");
		}
	}
}
