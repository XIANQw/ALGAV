package com.algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import com.structure.Line;


public class Toussaint {

	
	  public static ArrayList<Point> toussaint(ArrayList<Point> polygon){
		  double min = Double.MAX_VALUE, d_ = 0, r_ = 0, l_ = 0, width = 0, height = 0, s = 0;
		  Point [] rectangle = new Point[4];
		  int u = 1, r = 1, l = 1, n = polygon.size() - 1;
		  for(int i=0; i < n; ++i) {
			  while(Math.abs(Calcule.crossProduct(polygon.get(i + 1), polygon.get(i), polygon.get(u))) <=
					  Math.abs(Calcule.crossProduct(polygon.get(i + 1), polygon.get(i), polygon.get(u + 1)))){
				  u = (u + 1) % n;
			  }
			  
			  while(Calcule.dotProduct(polygon.get(i + 1), polygon.get(i), polygon.get(r)) <=
					  Calcule.dotProduct(polygon.get(i + 1), polygon.get(i), polygon.get(r + 1))) {
				  r = (r + 1) % n;
			  }
			  
			  if(i == 0) l = r;
			  
			  while(Calcule.dotProduct(polygon.get(i + 1), polygon.get(i), polygon.get(l)) >=
					  Calcule.dotProduct(polygon.get(i + 1), polygon.get(i), polygon.get(l + 1))) {
				  l = (l + 1) % n;
			  }
			  
			  d_ = Math.sqrt((double)Calcule.calculDistance(polygon.get(i), polygon.get(i + 1)));
			  r_ = Calcule.dotProduct(polygon.get(i + 1), polygon.get(i), polygon.get(r)) / d_;
			  l_ = Calcule.dotProduct(polygon.get(i + 1), polygon.get(i), polygon.get(l)) / d_;
			  width = Math.abs(Calcule.crossProduct(polygon.get(i + 1), polygon.get(i), polygon.get(u))) / d_;
			  height = r_ - l_; s = width * height;
			  if (s < min) {
				  min = s;
//				   top = Ei + (E(i+1) - Ei) * (r_/d_)
				  Point A = Calcule.dotMoinsdot(polygon.get(i), Calcule.dotProductConstant(Calcule.dotMoinsdot(polygon.get(i), polygon.get(i+1)), (r_/d_)));
				  rectangle[0] = A;
//				   right = top + ( (Er - top) * (dd / dis(Er, top)) )
				  Point B = Calcule.dotPlusdot(A, Calcule.dotProductConstant(Calcule.dotMoinsdot(polygon.get(r), A), (width/Math.sqrt(Calcule.calculDistance(polygon.get(r), A)))));
				  rectangle[1] = B;
//				  left = right + ( (pi - top) * (ll / r_) )
				  Point C = Calcule.dotPlusdot(B, Calcule.dotProductConstant(Calcule.dotMoinsdot(polygon.get(i), A), (height/r_)));
				  rectangle[2] = C;
				  Point D = Calcule.dotPlusdot(C, Calcule.dotMoinsdot(A, B));
				  rectangle[3] = D;
			  }
		  }
		  return new ArrayList<Point>(Arrays.asList(rectangle));
	  }
	  
	  public static Line rotatingCalipers(ArrayList<Point> points) {
		  points = Calcule.jarvis(points);
		  int j = 1, max = 0, n = points.size() - 1;
		  Line res = null;
		  for(int i = 0; i < n; i++) {
			  while(Math.abs(Calcule.crossProduct(points.get(i + 1), points.get(i), points.get(j))) <=
			  Math.abs(Calcule.crossProduct(points.get(i + 1), points.get(i), points.get(j + 1)))) {
				  j = (j+1) % n;
			  }
			  int dis = Calcule.calculDistance(points.get(i), points.get(j));
			  
			  if (dis > max) {
				  max = dis;
				  res = new Line(points.get(i), points.get(j));
			  }
		  }
		  return res;
	  }
	
}
