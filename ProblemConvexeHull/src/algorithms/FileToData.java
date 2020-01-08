package algorithms;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileToData {
	private static File files = new File("../Varoumas_benchmark/samples/");
	private static int cur = 0;
	
	public static ArrayList<Point> fileToData(String filename){
		ArrayList<Point> points = new ArrayList<Point>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line;
			String[] cor;
			while ((line = in.readLine()) != null) {
				cor = line.split(" ");
				points.add(new Point(Integer.parseInt(cor[0]), Integer.parseInt(cor[1])));
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
		return points;
	}
	
	public static ArrayList<Point> nextFile(){
		if(cur == files.listFiles().length) {
			return null;
		}
		String file = files.listFiles()[cur].getName();
		cur++;
		return fileToData("../Varoumas_benchmark/samples/" + file);
	}
}
