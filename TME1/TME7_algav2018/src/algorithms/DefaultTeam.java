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
	   Point p1 = points.get(0);
	   Point p2 = points.get(1);
	   Circle ci = this.createCircleByTwoPoints(p1, p2);
	   System.out.println("pi " + points.get(0).x + " "+ points.get(0).y);
	   for(int i=0; i < points.size(); i++) {
		   
		   System.out.println("ci_ "+i+" : " + ci.getRadius() + " pi_"+i+" : " + points.get(i).x + " "+ points.get(i).y);
		   
		   if (this.containsPoint(ci, points.get(i))) continue;
		   
		   Circle cj = this.createCircleByTwoPoints(p1, points.get(i));
		   for(int j=0; j < i; j++) {
			   
			   if (this.containsPoint(cj, points.get(j))) continue;
			   Circle ck = this.createCircleByTwoPoints(p1, points.get(j));
			   for (int k=0; k < j; k++) {
				   if (this.containsPoint(ck, points.get(k))) continue;
				   ck = this.createCircleByThreePoints(points.get(i), points.get(j), points.get(k));
				   System.out.println("ck_" + k + ": " + ck.getRadius() + " p1 " + points.get(i).x + " "+ points.get(i).y
	    					+ " p2 " + points.get(j).x + " " + points.get(j).y 
	    					+ " p3 " + points.get(k).x + " " + points.get(k).y) ;
			   }
			   cj = ck;
		   }
		   ci = cj;
	   }
	   return ci;
	}
  
  // Calcul the distance between two points
	public int calculDistance(Point p1, Point p2) {
		return (int) Math.sqrt((p1.x-p2.x) * (p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y));
	}
  
  // check a circle contain the point or not
  public Boolean containsPoint(Circle circle, Point point) {
	  return this.calculDistance(circle.getCenter(), point) <= circle.getRadius();
  }
  
 // check a circle contain all the points or not
  public Boolean containsAllpoints(Circle circle, ArrayList<Point> points) {
	  for (Point p : points) {
		  if (!this.containsPoint(circle, p)) return false;
	  }
	  return true;
  }
  
  // create a circle by two points
  public Circle createCircleByTwoPoints(Point p1, Point p2) {
	 int diameter = calculDistance(p1, p2);
	 Point center = new Point((p1.x + p2.x)/2, (p1.y+p2.y)/2);
	 return new Circle(center, diameter/2);
  }
  
  //create a circle by three points
  public Circle createCircleByThreePoints(Point p1, Point p2, Point p3) {
	  double x1 = p1.x;
      double x2 = p2.x;
      double x3 = p3.x;
      double y1 = p1.y;
      double y2 = p2.y;
      double y3 = p3.y;
      double x = ((y2 - y1) * (y3 * y3 - y1 * y1 + x3 * x3 - x1 * x1) - (y3 - y1)
              * (y2 * y2 - y1 * y1 + x2 * x2 - x1 * x1))
              / (2 * (x3 - x1) * (y2 - y1) - 2 * ((x2 - x1) * (y3 - y1)));
      double y = ((x2 - x1) * (x3 * x3 - x1 * x1 + y3 * y3 - y1 * y1) - (x3 - x1)
              * (x2 * x2 - x1 * x1 + y2 * y2 - y1 * y1))
              / (2 * (y3 - y1) * (x2 - x1) - 2 * ((y2 - y1) * (x3 - x1)));
     Point center = new Point((int)x,(int)y);
     int radium = this.calculDistance(p1, center);
     return new Circle(center, radium);
  }
}
