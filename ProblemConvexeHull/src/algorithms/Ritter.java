package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class Ritter {

	public static Circle ritter(ArrayList<Point> points) {
		HashSet<Point> rest = new HashSet<>();
		for(Point p:points) rest.add(p);
		Random rand = new Random();
		// 1. Point dummy
		int dummy = rand.nextInt(points.size());
		Point p = Calcule.theFarthestPoint(points, points.get(dummy));
		// 3. Point q is the farthest point of p
		Point q = Calcule.theFarthestPoint(points, p);
		// 4. Point c
		Point C = new Point((p.x + q.x)/2, (p.y + q.y)/2);
		// 5. Circle constructed by c with radius |cq|
		int r = (int) Math.sqrt(Calcule.calculDistance(C, p));
		Circle res = new Circle(C, r);
		// 6. remove p q from set
		rest.remove(p);
		rest.remove(q);
		// 7. A loop  
		while (!rest.isEmpty()) {
			Iterator<Point> itr = rest.iterator();
			while(itr.hasNext()) {
				Point s = itr.next();
				// 8. remove s if s is covered
				if (Calcule.containsPoint(res, s)) {
					itr.remove();
					continue;
				}
				// 9. 
				double sc = Math.sqrt(Calcule.calculDistance(C, s)),
						sq = sc + res.getRadius(),
						sc2 = sq / 2,
						cc2 = sc - sc2;
				// new centre c2 = c + cs * (|cc2|/|sc|)
				Point c2 = Calcule.dotPlusdot(C, Calcule.dotProductConstant(Calcule.dotMoinsdot(s, C), cc2/sc));
				res = new Circle(c2, (int) sc2 + 1);

			}
		}
		return res;
	}
}
