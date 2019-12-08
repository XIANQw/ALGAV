package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import supportGUI.Circle;
import supportGUI.Line;

public class DefaultTeam {

  // calculDiametre: ArrayList<Point> --> Line
  //   renvoie une pair de points de la liste, de distance maximum.
  public Line calculDiametre(ArrayList<Point> points) {
	  if (points.size()<3) {
		  return null;
	  }
	  Point p=points.get(0);
	  Point q=points.get(1);
	  int res = 0, tmp;
	  for(Point p1 : points) {
		  for (Point p2 : points) {
			  if (p1 == p2) continue;
			  tmp = this.calculDistance(p1, p2);
			  if(tmp > res) {
				  res = tmp;
				  p = p1;
				  q = p2;				  
			  }
		  }
	  }
	  return new Line(p,q);
  }

  // calculDiametreOptimise: ArrayList<Point> --> Line
  //   renvoie une pair de points de la liste, de distance maximum.
  public Line calculDiametreOptimise(ArrayList<Point> points){
    if (points.size()<3) {
      return null;
    }
    ArrayList<Point> enveloppe = new ArrayList<Point>();
    jarvis(points, enveloppe);
    Line res = new Line(enveloppe.get(0), enveloppe.get(1));
    res = rotatingCalipers(enveloppe); 
    return res;
  }
  
  // enveloppeConvexe: ArrayList<Point> --> ArrayList<Point>
  //   renvoie l'enveloppe convexe de la liste.
  public ArrayList<Point> enveloppeConvexe(ArrayList<Point> points){
	  if (points.size()<3) {
		  return null;
	  }
	  ArrayList<Point> enveloppe = new ArrayList<Point>();
	  jarvis(points, enveloppe);
	  Point[] rectangle = toussaint(enveloppe);
	  ArrayList<Point> rec = new ArrayList<Point>();
	  for(Point p : rectangle) {
		  rec.add(p);
		  System.out.println(p.x + " " + p.y);
	  }

	  return rec;
  }
  
  
  private Point[] toussaint(ArrayList<Point> polygon){
	  double min = Double.MAX_VALUE;
	  Point [] rectangle = new Point[4];
	  int u = 1, r = 1, l = 1, n = polygon.size() - 1;
	  for(int i=0; i < n; ++i) {
		  while(Math.abs(crossProduct(polygon.get(i + 1), polygon.get(i), polygon.get(u))) <=
				  Math.abs(crossProduct(polygon.get(i+1), polygon.get(i), polygon.get(u + 1)))){
			  u = (u + 1) % n;
		  }
		  
		  while(dotProduct(polygon.get(i + 1), polygon.get(i), polygon.get(r)) <=
				  dotProduct(polygon.get(i + 1), polygon.get(i), polygon.get(r + 1))) {
			  r = (r + 1) % n;
		  }
		  
		  if(i == 0) l = r;
		  
		  while(dotProduct(polygon.get(i + 1), polygon.get(i), polygon.get(l)) >=
				  dotProduct(polygon.get(i + 1), polygon.get(i), polygon.get(l + 1))) {
			  l = (l + 1) % n;
		  }
		  
		  double d_ = Math.sqrt((double)calculDistance(polygon.get(i), polygon.get(i + 1))),
				  r_ = dotProduct(polygon.get(i + 1), polygon.get(i), polygon.get(r)) / d_,
				  l_ = dotProduct(polygon.get(i + 1), polygon.get(i), polygon.get(l)) / d_,
				  dd = Math.abs(crossProduct(polygon.get(i + 1), polygon.get(i), polygon.get(u))) / d_,
				  ll = r_ - l_, s = ll * dd;
		  if (s < min) {
			  min = s;
			  // top = pi + (p(i+1) - pi) * (r_/d_)
			  Point top = dotPlusdot(polygon.get(i), dotProductConstant(dotMoinsdot(polygon.get(i + 1), polygon.get(i)), (r_/d_)));
			  rectangle[0] = top;
			  // right = top + ( (pr - top) * (dd / dis(pr, top)) )
			  Point right = dotPlusdot(top, dotProductConstant(dotMoinsdot(polygon.get(r), top), (dd/Math.sqrt(calculDistance(polygon.get(r), top)))));
			  rectangle[1] = right;
			  //left = right + ( (pi - top) * (ll/r_) )
			  Point left = dotPlusdot(right, dotProductConstant(dotMoinsdot(polygon.get(i), top), (ll/r_)));
			  rectangle[2] = left;
			  Point down = dotPlusdot(left, dotMoinsdot(top, right));
			  rectangle[3] = down;
		  }
	  }
	  return rectangle;
  }
  
  
  private Line rotatingCalipers(ArrayList<Point> points) {
	  int j = 1, max = 0, n = points.size() - 1;
	  Line res = null;
	  for(int i = 0; i < n; i++) {
		  while(Math.abs(crossProduct(points.get(i + 1), points.get(i), points.get(j))) <
		  Math.abs(crossProduct(points.get(i + 1), points.get(i), points.get(j + 1)))) {
			  j = (j+1) % n;
		  }
		  int dis = calculDistance(points.get(i), points.get(j));
		  if (dis > max) {
			  max = dis;
			  res = new Line(points.get(i), points.get(j));
		  }
	  }
	  return res;
  }
  
  private Point dotMoinsdot(Point a, Point b) {
	  return new Point(a.x - b.x, a.y - b.y);
  }
  
  private Point dotPlusdot(Point a, Point b) {
	  return new Point(a.x + b.x, a.y + b.y);
  }
  
  private Point dotProductConstant(Point p, double c) {
	  return new Point((int)(p.x * c),(int)(p.y * c));
  }
  
  private int dotProduct(Point pre, Point cur, Point next) {
	  int x1 = cur.x - pre.x, y1 = cur.y - pre.y,
			  x2 = cur.x - next.x, y2 = cur.y - next.y;
	  return x1*x2 + y1*y2;
  }
  
  // calculCercleMin: ArrayList<Point> --> Circle
  //   renvoie un cercle couvrant tout point de la liste, de rayon minimum.
  public Circle calculCercleMin(ArrayList<Point> points) {
		if (points.isEmpty()) {
			return null;
		}
		Point p1 = points.get(0);
		Point p2 = points.get(1);
		Circle ci = this.createCircleByTwoPoints(p1, p2);
		for(int i=0; i < points.size(); i++) {
			if (this.containsPoint(ci, points.get(i))) continue;
			Circle cj = this.createCircleByTwoPoints(p1, points.get(i));
			for(int j=0; j < i; j++) {
				if (this.containsPoint(cj, points.get(j))) continue;
				Circle ck = this.createCircleByTwoPoints(p1, points.get(j));
				if(ck == null) continue;
				for (int k=0; k < j; k++) {
					if (this.containsPoint(ck, points.get(k))) continue;
					ck = this.createCircleByThreePoints(points.get(i), points.get(j), points.get(k));
					if (ck == null) continue;
				}
				cj = ck;
			}
			ci = cj;
		   }
		return ci;
  }
  
  // Calcul the distance between two points
  public int calculDistance(Point p1, Point p2) {
	  return (p1.x-p2.x) * (p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y);
  }
  
  // check a circle contain the point or not
  public Boolean containsPoint(Circle circle, Point point) {
	  if(circle == null) return false;
	  return this.calculDistance(circle.getCenter(), point) <= circle.getRadius() * circle.getRadius();
  }
  
 // check a circle contain all the points or not
  public Boolean containsAllpoints(Circle circle, ArrayList<Point> points) {
	for (Point p : points) {
		if (!this.containsPoint(circle, p)) return false;
	}
	return true;
  }
  
  // create a circle by two points
  private Circle createCircleByTwoPoints(Point p1, Point p2) {
	  int diameter = (int) Math.sqrt(calculDistance(p1, p2));
	  Point center = new Point((p1.x + p2.x)/2, (p1.y+p2.y)/2);
	  return new Circle(center, diameter/2);
  }
  
  //create a circle by three points
  private Circle createCircleByThreePoints(Point p1, Point p2, Point p3) {
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
	  int radium = (int) Math.sqrt(this.calculDistance(p1, center));
	  if(radium == 0) return null;
	  return new Circle(center, radium + 1);
  }


  
  public void jarvis(ArrayList<Point> points, ArrayList<Point> result){
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
			  if(crossProduct(cur, min, next) <= 0) continue;
			  min = next;
		  }
		  cur = min;
		  result.add(cur);
	  } while(!cur.equals(top));
  }

  private int crossProduct(Point pre, Point cur, Point next) {
	  int ax = cur.x - pre.x, ay = cur.y - pre.y, 
			  bx = cur.x - next.x, by = cur.y - next.y;
	  int res = (ax * by) - (ay * bx);
	  return res;
  }
  
}
