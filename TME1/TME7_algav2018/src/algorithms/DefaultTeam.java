package algorithms;

import java.awt.Point;
import java.util.ArrayList;

/***************************************************************
 * TME 1: calcul de diamètre et de cercle couvrant minimum.    *
 *   - Trouver deux points les plus éloignés d'un ensemble de  *
 *     points donné en entrée.                                 *
 *   - Couvrir l'ensemble de poitns donné en entrée par un     *
 *     cercle de rayon minimum.                                *
 *                                                             *
 * class Circle:                                               *
 *   - Circle(Point c, int r) constructs a new circle          *
 *     centered at c with radius r.                            *
 *   - Point getCenter() returns the center point.             *
 *   - int getRadius() returns the circle radius.              *
 *                                                             *
 * class Line:                                                 *
 *   - Line(Point p, Point q) constructs a new line            *
 *     starting at p ending at q.                              *
 *   - Point getP() returns one of the two end points.         *
 *   - Point getQ() returns the other end point.               *
 ***************************************************************/
import supportGUI.Circle;
import supportGUI.Line;

public class DefaultTeam {

  // calculDiametre: ArrayList<Point> --> Line
  //   renvoie une paire de points de la liste, de distance maximum.
  
	public int calculDistance(Point p1, Point p2) {
		return (p1.x-p2.x) * (p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y);
	}
	
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

  // calculCercleMin: ArrayList<Point> --> Circle
  //   renvoie un cercle couvrant tout point de la liste, de rayon minimum.
  public Circle calculCercleMin(ArrayList<Point> points) {
    if (points.isEmpty()) {
      return null;
    }
    Point center=points.get(0);
    Line maxLine = this.calculDiametre(points);
    Point p = maxLine.getP();
    Point q = maxLine.getQ();
    int upper = (int) Math.sqrt(this.calculDistance(p, q));
    int down = (int) upper / 2;
    center.x = (int) (p.getX() + q.getX()) / 2;
    center.y = (int) (p.getY() + q.getY()) / 2;	
    while (down + 1 < upper) {
    	int mid = (int) down + (upper - down) / 2;
    	Circle tmp = new Circle(center, mid);
    	Boolean contain = this.containsAllpoints(tmp, points);
    	if(contain){
    		upper = mid;
    	}else {
    		down = mid;
    	}
    }
    Circle res = new Circle(center, down);
    if(this.containsAllpoints(res, points)) return res;
    return new Circle(center,upper);
  }
  
  public Boolean containsPoint(Circle circle, Point point) {
	  return this.calculDistance(circle.getCenter(), point) <= (circle.getRadius() * circle.getRadius());
  }
  
  
  public Boolean containsAllpoints(Circle circle, ArrayList<Point> points) {
	  for (Point p : points) {
		  if (!this.containsPoint(circle, p)) return false;
	  }
	  return true;
  }
}
