package algorithms;

import java.awt.Point;
import java.util.ArrayList;

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
			  tmp = Calcule.calculDistance(p1, p2);
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
    ArrayList<Point> enveloppe = jarvis(points);
    Line res = new Line(enveloppe.get(0), enveloppe.get(1));
    res = Toussaint.rotatingCalipers(enveloppe); 
    return res;
  }
  
  // enveloppeConvexe: ArrayList<Point> --> ArrayList<Point>
  //   renvoie l'enveloppe convexe de la liste.
  public ArrayList<Point> enveloppeConvexe(ArrayList<Point> points){
	  if (points.size()<3) {
		  return null;
	  }
	  ArrayList<Point> enveloppe = jarvis(points);
	  ArrayList<Point> rec = Toussaint.toussaint(enveloppe);
	  rec.add(rec.get(0));
	  return rec;
  }	
  
  
  
  // calculCercleMin: ArrayList<Point> --> Circle
  //   renvoie un cercle couvrant tout point de la liste, de rayon minimum.
  public Circle calculCercleMin(ArrayList<Point> points) {
		if (points.isEmpty()) {
			return null;
		}
		return Ritter.ritter(points);
  }
  
  public Circle calculMinCercle(ArrayList<Point> points) {
		Point p1 = points.get(0);
		Point p2 = points.get(1);
		Circle ci = Calcule.createCircleByTwoPoints(p1, p2);
		for(int i=0; i < points.size(); i++) {
			if (Calcule.containsPoint(ci, points.get(i))) continue;
			Circle cj = Calcule.createCircleByTwoPoints(p1, points.get(i));
			for(int j=0; j < i; j++) {
				if (Calcule.containsPoint(cj, points.get(j))) continue;
				Circle ck = Calcule.createCircleByTwoPoints(p1, points.get(j));
				if(ck == null) continue;
				for (int k=0; k < j; k++) {
					if (Calcule.containsPoint(ck, points.get(k))) continue;
					ck = Calcule.createCircleByThreePoints(points.get(i), points.get(j), points.get(k));
					if (ck == null) continue;
				}
				cj = ck;
			}
			ci = cj;
		   }
		return ci;
  }
  


  
}
