package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import supportGUI.Circle;

public class Calcule{
	
	  // The difference of two dot.
	  public static Point dotMoinsdot(Point a, Point b) {
		  return new Point(a.x - b.x, a.y - b.y);
	  }
	  
	  // The sum of two dot
	  public static Point dotPlusdot(Point a, Point b) {
		  return new Point(a.x + b.x, a.y + b.y);
	  }
	  
	  // A dot product a constant
	  public static Point dotProductConstant(Point p, double c) {
		  return new Point((int)(p.x * c),(int)(p.y * c));
	  }
	  
	 // Dot product of two vector pre->cur, next->cur
	  public static int dotProduct(Point pre, Point cur, Point next) {
		  int x1 = cur.x - pre.x, y1 = cur.y - pre.y,
				  x2 = cur.x - next.x, y2 = cur.y - next.y;
		  return x1*x2 + y1*y2;
	  }
	  
	  // Cross product of two vector pre->cur, next->cur
	  public static int crossProduct(Point pre, Point cur, Point next) {
		  int ax = cur.x - pre.x, ay = cur.y - pre.y, 
				  bx = cur.x - next.x, by = cur.y - next.y;
		  int res = (ax * by) - (ay * bx);
		  return res;
	  }
	  // Computing the quality of a rectangle and a polygon
	  public static double quality(ArrayList<Point> rec, ArrayList<Point> polygon) {
		  double aireRec = areaConvexPolygon(rec),
				  airePolygon = areaConvexPolygon(polygon),
				  quality = aireRec/airePolygon - 1.00;
		  return quality;
	  }
	  
	  // Computing the area of a polygon
	  private static double areaConvexPolygon(ArrayList<Point> polygon) {
		  double aire = 0;
		  for(int i = 0; i < polygon.size() - 1; i++) {
			  aire += polygon.get(i).x * polygon.get(i + 1).y - polygon.get(i).y * polygon.get(i + 1).x;
		  }
		  return Math.abs(aire) * 0.5;
	  }
	  
	  // Computing the distance between two points
	  public static int calculDistance(Point p1, Point p2) {
		  return (p1.x-p2.x) * (p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y);
	  }
	  
	  // Check a circle contain the point or not
	  public static Boolean containsPoint(Circle circle, Point point) {
		  if(circle == null) return false;
		  return calculDistance(circle.getCenter(), point) <= circle.getRadius() * circle.getRadius();
	  }
	  
	  // Check a circle contain all the points or not
	  public static Boolean containsAllpoints(Circle circle, ArrayList<Point> points) {
		for (Point p : points) {
			if (!containsPoint(circle, p)) return false;
		}
		return true;
	  }
	  
	  // Find the farthest points of point index in a set points.
	  public static Point theFarthestPoint(ArrayList<Point> points, Point p) {
		  int distMax = 0, farthest = 0;
			for(int i=0; i<points.size(); i++) {
				int dist = Calcule.calculDistance(p, points.get(i));
				if(dist > distMax) {
					distMax = dist;
					farthest = i;
				}
			}
			return points.get(farthest);
	  }
	  
	  // Create a circle by two points
	  public static Circle createCircleByTwoPoints(Point p1, Point p2) {
		  int diameter = (int) Math.sqrt(calculDistance(p1, p2));
		  Point center = new Point((p1.x + p2.x)/2, (p1.y+p2.y)/2);
		  return new Circle(center, diameter/2);
	  }
	  
	  // Create a circle by three points
	  public static Circle createCircleByThreePoints(Point p1, Point p2, Point p3) {
		  double x1 = p1.x;
		  double x2 = p2.x;
		  double x3 = p3.x;
		  double y1 = p1.y;
		  double y2 = p2.y;
		  double y3 = p3.y;	    
		  double t1 = x1*x1 + y1*y1;
		  double t2 = x2*x2 + y2*y2;
		  double t3 = x3*x3 + y3*y3;
		  double temp = x1*y2 + x2*y3 + x3*y1 - x1*y3 - x2*y1 - x3*y2;
		  if (temp == 0) return null;
		  double x = (t2*y3 + t1*y2 + t3*y1 - t2*y1 - t3*y2 - t1*y3)/temp/2;
		  double y = (t3*x2 + t2*x1 + t1*x3 - t1*x2 - t2*x3 - t3*x1)/temp/2;
		  Point center = new Point((int)x, (int)y);
		  int radium = (int) Math.sqrt(calculDistance(p1, center));
		  if(radium == 0) return null;
		  return new Circle(center, radium + 1);
	  }
	  
	  // Computing the minimum polygon covering 
	  public static ArrayList<Point> jarvis(ArrayList<Point> points){
		  ArrayList<Point> result = new ArrayList<Point>();
		  Point top = points.get(0);
		  for(Point p : points) {
			  if (p.y < top.y) top = p;
		  }
		  result.add(top);
		  Point cur = top;
		  do {
			  Point min = null;
			  for(Point next : points) {
				  if (next.equals(cur)) continue;
				  if(min == null) { 
					  min = next;
					  continue;
				  }
				  if(Calcule.crossProduct(cur, min, next) <= 0) continue;
				  min = next;
			  }
			  cur = min;
			  result.add(cur);
		  } while(!cur.equals(top));
		  return result;
	  }

	public static double quality(Circle res, ArrayList<Point> polygon) {
		double areaCircle = Math.PI * res.getRadius() * res.getRadius();
		double areaPolygon = Calcule.areaConvexPolygon(polygon);
		return areaCircle/areaPolygon;
	}
}
